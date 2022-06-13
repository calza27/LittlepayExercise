package OrderMonkey.LittlepayExercise.Utilities;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import OrderMonkey.LittlepayExercise.DataObjects.Event.Event;
import OrderMonkey.LittlepayExercise.DataObjects.Trip.Trip;

public class FileFunctions {
	public static List<Event> readInputCsv(String fileName) throws IOException {
    	File csvInputFile = new File(fileName);
    	return readInputCsv(csvInputFile);
    }
    
    private static List<Event> readInputCsv(File inputFile) throws IOException {
    	CsvMapper csvMapper = CsvMapper.builder()
			.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
			.enable(CsvParser.Feature.TRIM_SPACES)
			.build();
    	CsvSchema schema = CsvSchema.emptySchema().withHeader(); 
    	 
    	ObjectReader oReader = csvMapper.readerFor(Event.class).with(schema);
    	try (Reader reader = new FileReader(inputFile)) {
    	    MappingIterator<Event> mi = oReader.readValues(reader);
    	    return mi.readAll();
    	}
    }
    
    public static void writeOutputCsv(String fileName, List<Trip> tripList) throws IOException {
    	File csvOutputFile = new File(fileName);
    	if(!csvOutputFile.isFile()) {
    		csvOutputFile.createNewFile();
    	}
    	writeOutputCsv(csvOutputFile, tripList);
    }
    
    private static void writeOutputCsv(File outputFile, List<Trip> tripList) throws IOException {
    	CsvMapper csvMapper = CsvMapper.builder()
			.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
			.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false)
			.build();

    	CsvSchema schema = CsvSchema.builder()
			.setUseHeader(true)
    		.addColumn("Started")
    		.addColumn("Finished")
    		.addColumn("DurationSecs")
    		.addColumn("FromStopId")
    		.addColumn("ToStopId")
    		.addColumn("ChargeAmount")
    		.addColumn("CompanyId")
    		.addColumn("BusID")
    		.addColumn("PAN")
    		.addColumn("Status")
		.build();
    	
    	ObjectWriter oWriter = csvMapper.writerFor(Trip.class).with(schema);
    	oWriter.writeValues(outputFile).writeAll(tripList);
    }
}
