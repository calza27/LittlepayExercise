Littlepay Technical Interview submission
	-Callum Pullyblank

Program written in Java 11 with Maven package manager

The program takes in a csv file of tap data, and generates an output CSV of trip information

To run the program, use the execuatable jar provided in the bin folder using the following windows command:

	java -jar App.jar
	
Program runs in the command line, and accepts two arguments.

	-The first is the path to the input csv file of taps
	
	-The second is the desired path of the output file the program will generate.

Program can be run without these arguments, in which case the program will prmopt the user for input for these two paths.

There is a sample input file provided in the data directory; taps.csv

===ASSUMPTIONS===

The first assumption I made is that the tap data coming into the system, may not necessarily be in order.
For example, the device detecting the tap data, may register the tap event and record the date/time of the event, but due to a network error, may not be able to report the event to a central system immediatly. Therefore, the date/time on the event determines the order in which the events occurred, and not their position in the CSV file. For this reason, after reading the tap events from the CSV into a list of objects,t aht list is sorted by the date/time of the events, before proccessing them.

The second assumption I made, is that an INCOMPLETE trip will have no tap data with regards to the end of the journey, therefore the finished date/time, toStopId, and duration cannot be inferred/calculated.

A third assumption I made, is that a TAP OFF event, is always paired with the most recent TAP ON event from the same PAN. Therefore, a TAP ON event from a particular PAN, followed by another TAP ON event from the same PAN, will results in the first TAP ON being treated as an INCOMPLETE trip.

And finally, I assumed that the companyId and busId for a given pair of tap events will always be the same, and so the companyId and busId for the resulting trip record will always take the details from the TAP ON event.
