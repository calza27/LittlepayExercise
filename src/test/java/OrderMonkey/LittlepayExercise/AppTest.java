package OrderMonkey.LittlepayExercise;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import OrderMonkey.LittlepayExercise.DataObjects.Event.Event;
import OrderMonkey.LittlepayExercise.DataObjects.Event.EventFunctions;
import OrderMonkey.LittlepayExercise.DataObjects.Route.Route;
import OrderMonkey.LittlepayExercise.DataObjects.Trip.Trip;
import OrderMonkey.LittlepayExercise.DataObjects.Trip.TripFunctions;
import OrderMonkey.LittlepayExercise.Utilities.Constants;

public class AppTest 
{
    @Test
    public void testRouteCharges() {
    	//validly built trips with from and (optional) to stops
    	assert(testValidRouteCharge(Route.STOP_1, null, "$7.30"));
    	assert(testValidRouteCharge(Route.STOP_1, Route.STOP_1, "$0.00"));
    	assert(testValidRouteCharge(Route.STOP_1, Route.STOP_2, "$3.25"));
    	assert(testValidRouteCharge(Route.STOP_1, Route.STOP_3, "$7.30"));
    	assert(testValidRouteCharge(Route.STOP_2, null, "$5.50"));
    	assert(testValidRouteCharge(Route.STOP_2, Route.STOP_1, "$3.25"));
    	assert(testValidRouteCharge(Route.STOP_2, Route.STOP_2, "$0.00"));
    	assert(testValidRouteCharge(Route.STOP_2, Route.STOP_3, "$5.50"));
    	assert(testValidRouteCharge(Route.STOP_3, null, "$7.30"));
    	assert(testValidRouteCharge(Route.STOP_3, Route.STOP_1, "$7.30"));
    	assert(testValidRouteCharge(Route.STOP_3, Route.STOP_2, "$5.50"));
    	assert(testValidRouteCharge(Route.STOP_3, Route.STOP_3, "$0.00"));
    	//invalidly built trips, with null from route
    	try {
    		testInvalidRouteCharge(null, Route.STOP_1);
    		//if the construction goes off without a hitch, then something is wrong
    		assert(false);
    	} catch(IllegalArgumentException e) {
    		//we're expecting an IllegalArgumentException here
    		assert(true);
    	}
    	try {
	    	testInvalidRouteCharge(null, Route.STOP_2);
	    	//if the construction goes off without a hitch, then something is wrong
	    	assert(false);
    	} catch(IllegalArgumentException e) {
    		//we're expecting an IllegalArgumentException here
    		assert(true);
    	}
	    try {
	    	testInvalidRouteCharge(null, Route.STOP_3);
	    	//if the construction goes off without a hitch, then something is wrong
	    	assert(false);
    	} catch(IllegalArgumentException e) {
    		//we're expecting an IllegalArgumentException here
    		assert(true);
    	}
	    try {
	    	testInvalidRouteCharge(null, null);
	    	//if the construction goes off without a hitch, then something is wrong
	    	assert(false);
    	} catch(IllegalArgumentException e) {
    		//we're expecting an IllegalArgumentException here
    		assert(true);
    	}
	    	
    }
    
    private boolean testValidRouteCharge(String fromStop, String toStop, String correctCharge) {
    	Trip trip = new Trip();
    	trip.setFromStopId(fromStop);
    	trip.setToStopId(toStop);
    	return correctCharge.equalsIgnoreCase(TripFunctions.constructDollarChargeAmount(trip));
    }
    
    private void testInvalidRouteCharge(String fromStop, String toStop) throws IllegalArgumentException {
    	Trip trip = new Trip();
    	trip.setFromStopId(fromStop);
    	trip.setToStopId(toStop);
    	TripFunctions.constructDollarChargeAmount(trip);
    }
    
    @Test
    public void testDurationCalculation() {
    	Date startDate;
    	Date finishDate;
    	//validly built trips with start and finished times
    	Calendar startCalendar = Calendar.getInstance();
    	startDate = startCalendar.getTime();
    	Calendar finishCalendar = Calendar.getInstance();
    	//initialise the start calendar to an identical time to the start calendar
    	finishCalendar.setTime(startDate);
    	
    	finishCalendar.add(Calendar.SECOND, 10);
    	finishDate = finishCalendar.getTime();
    	//finish calendar is 10s into the future
    	assert(testValidDuration(startDate, finishDate, 10));
    	
    	//add another minute to the end calendar
    	finishCalendar.add(Calendar.MINUTE, 1);
    	finishDate = finishCalendar.getTime();
    	//finish calendar is now 70s into the future
    	assert(testValidDuration(startDate, finishDate, 70));
    	
    	//add another hour to the end calendar
    	finishCalendar.add(Calendar.HOUR, 1);
    	finishDate = finishCalendar.getTime();
    	//finish calendar is now 3670s into the future
    	assert(testValidDuration(startDate, finishDate, 3670));
    	
    	//now add 420 minutes to the end calendar
    	finishCalendar.add(Calendar.MINUTE, 420);
    	finishDate = finishCalendar.getTime();
    	//finish calendar is now 28870s into the future
    	assert(testValidDuration(startDate, finishDate, 28870));
    	
    	//now add 20 days to the end calendar
    	finishCalendar.add(Calendar.DAY_OF_MONTH, 20);
    	finishDate = finishCalendar.getTime();
    	//finish calendar is now 1756870s into the future
    	assert(testValidDuration(startDate, finishDate, 1756870));
    	
    	//invalidly built trips, with missing start and/or finish times
    	try {
    		//if the calculation goes off without a hitch, then something is wrong
	    	testInvalidDuration(Calendar.getInstance().getTime(), null);
	    	assert(false);
	 	} catch(IllegalArgumentException e) {
	  		//we're expecting an IllegalArgumentException here
	  		assert(true);
  		}
    	try {
    		//if the calculation goes off without a hitch, then something is wrong
	    	testInvalidDuration(null, Calendar.getInstance().getTime());
	    	assert(false);
	 	} catch(IllegalArgumentException e) {
	  		//we're expecting an IllegalArgumentException here
	  		assert(true);
  		}
    	try {
    		//if the calculation goes off without a hitch, then something is wrong
	    	testInvalidDuration(null, null);
	    	assert(false);
	 	} catch(IllegalArgumentException e) {
	  		//we're expecting an IllegalArgumentException here
	  		assert(true);
  		}
    	
    	//invalidly built trips, with a finish time before the start time
    	try {
    		Calendar cal = Calendar.getInstance();
    		startDate = cal.getTime();
    		cal.add(Calendar.MINUTE, -20);
    		finishDate = cal.getTime();
    		//if the calculation goes off without a hitch, then something is wrong
	    	testInvalidDuration(startDate, finishDate);
	    	assert(false);
	 	} catch(IllegalArgumentException e) {
	  		//we're expecting an IllegalArgumentException here
	  		assert(true);
  		}
    }
    
    private boolean testValidDuration(Date startTime, Date finishTime, long correctDuration) {
    	Trip trip = new Trip();
    	trip.setStarted(startTime);
    	trip.setFinished(finishTime);
    	return correctDuration == TripFunctions.calculateDuration(trip);
    }
    
    private void testInvalidDuration(Date startTime, Date finishTime) throws IllegalArgumentException {
    	Trip trip = new Trip();
    	trip.setStarted(startTime);
    	trip.setFinished(finishTime);
    	TripFunctions.calculateDuration(trip);
    }
    
    @Test
    public void testStatusCalculation() {
    	Calendar calendar = Calendar.getInstance();
    	Date startDate = calendar.getTime();
    	calendar.add(Calendar.MINUTE, 2);
    	Date finishDate = calendar.getTime();
    	
    	//start with an incomplete trip
    	Event event1 = new Event();
		event1.setId("1");
		event1.setDateTimeUTC(startDate);
		event1.setTapType(Event.TAP_TYPE_ON);
		event1.setStopId(Route.STOP_1);
		event1.setCompanyId(Constants.COMPANY_ID);
		event1.setBusId(Constants.BUS_ID);
		event1.setPan(Constants.PAN_AMEX);
		Trip trip1 = TripFunctions.buildTrip(event1, null);
		//trip should be incomplete, and being from Stop 1, $7.30
		assert(Trip.STATUS_INCOMPLETE.equalsIgnoreCase(trip1.getStatus()) && StringUtils.equalsAnyIgnoreCase(trip1.getChargeAmount(), "$7.30"));
		
		//now a cancelled trip (tap on and off from same location)
		Event event2 = new Event();
		event2.setId("2");
		event2.setDateTimeUTC(finishDate);
		event2.setTapType(Event.TAP_TYPE_OFF);
		event2.setStopId(Route.STOP_1);
		event2.setCompanyId(Constants.COMPANY_ID);
		event2.setBusId(Constants.BUS_ID);
		event2.setPan(Constants.PAN_AMEX);
		Trip trip2 = TripFunctions.buildTrip(event1, event2);
		//trip should be incomplete and $0.00
		assert(Trip.STATUS_CANCELLED.equalsIgnoreCase(trip2.getStatus()) && StringUtils.equalsAnyIgnoreCase(trip2.getChargeAmount(), "$0.00"));
		
		//now a complete trip (tap on and off from different location)
		Event event3 = new Event();
		event3.setId("3");
		event3.setDateTimeUTC(finishDate);
		event3.setTapType(Event.TAP_TYPE_OFF);
		event3.setStopId(Route.STOP_2);
		event3.setCompanyId(Constants.COMPANY_ID);
		event3.setBusId(Constants.BUS_ID);
		event3.setPan(Constants.PAN_AMEX);
		Trip trip3 = TripFunctions.buildTrip(event1, event3);
		//trip should be complete, and being from stop 1 to stop 2, $3.25
		assert(Trip.STATUS_COMPLETED.equalsIgnoreCase(trip3.getStatus()) && StringUtils.equalsAnyIgnoreCase(trip3.getChargeAmount(), "$3.25"));
		
		//now try something that should fail
		try {
			//if building the trip goes off without a hitch, then something is wrong
			Trip trip4 = TripFunctions.buildTrip(null, event3);
			assert(trip4 == null);
		} catch(IllegalArgumentException e) {
			assert(true);
		}
    }
    
    @Test
    public void testOutputListLength() {
    	List<Event> eventList = buildSampleEventList();
    	//the number of trips we expect from the output is equal to the number of tap on events in our event set
    	long expectedNumberOfTrips = eventList.stream().filter(event -> {
    		return Event.TAP_TYPE_ON.equalsIgnoreCase(event.getTapType());
    	}).count();
    	try {
    		List<Trip> tripList = EventFunctions.processEventData(eventList);
    		//at this point, with the above tests that specifically test the hard calculations
    			//we can assume that if the list that comes back here exists and is the right size, then the data is correct
    		assert(tripList != null && tripList.size() == expectedNumberOfTrips);
    	} catch(Exception e) {
    		assert(false);
    	}
    }
    
    private List<Event> buildSampleEventList() {
    	List<Event> eventList = new ArrayList<Event>();
    	Calendar daCal = Calendar.getInstance();
		//sequential events in a pair with a duration of 1 minute, from stop1 to stop 2
    	{
    		Event event = new Event();
    		event.setId("1");
    		event.setDateTimeUTC(daCal.getTime());
    		event.setTapType(Event.TAP_TYPE_ON);
    		event.setStopId(Route.STOP_1);
    		event.setCompanyId(Constants.COMPANY_ID);
    		event.setBusId(Constants.BUS_ID);
    		event.setPan(Constants.PAN_AMEX);
    		eventList.add(event);
    	}
    	{
    		daCal.add(Calendar.MINUTE, 1);
    		Event event = new Event();
    		event.setId("2");
    		event.setDateTimeUTC(daCal.getTime());
    		event.setTapType(Event.TAP_TYPE_OFF);
    		event.setStopId(Route.STOP_2);
    		event.setCompanyId(Constants.COMPANY_ID);
    		event.setBusId(Constants.BUS_ID);
    		event.setPan(Constants.PAN_AMEX);
    		eventList.add(event);
    	}
    	//now a pair of non-sequential events
    		//pair 1 (MASTERCARD_1) will be from Stop2 to Stop3 in 2 minutes
    	{
    		Event event = new Event();
    		event.setId("3");
    		event.setDateTimeUTC(daCal.getTime());
    		event.setTapType(Event.TAP_TYPE_ON);
    		event.setStopId(Route.STOP_2);
    		event.setCompanyId(Constants.COMPANY_ID);
    		event.setBusId(Constants.BUS_ID);
    		event.setPan(Constants.PAN_MASTERCARD_1);
    		eventList.add(event);
    	}
    		//pair 2 (MASTERCARD_2) will be from Stop1 to Stop3 in 3 minutes
    	{
    		Event event = new Event();
    		event.setId("4");
    		event.setDateTimeUTC(daCal.getTime());
    		event.setTapType(Event.TAP_TYPE_ON);
    		event.setStopId(Route.STOP_1);
    		event.setCompanyId(Constants.COMPANY_ID);
    		event.setBusId(Constants.BUS_ID);
    		event.setPan(Constants.PAN_MASTERCARD_2);
    		eventList.add(event);
    	}
    	{
    		daCal.add(Calendar.MINUTE, 2);
    		Event event = new Event();
    		event.setId("5");
    		event.setDateTimeUTC(daCal.getTime());
    		event.setTapType(Event.TAP_TYPE_OFF);
    		event.setStopId(Route.STOP_3);
    		event.setCompanyId(Constants.COMPANY_ID);
    		event.setBusId(Constants.BUS_ID);
    		event.setPan(Constants.PAN_MASTERCARD_1);
    		eventList.add(event);
    	}
    	{
    		daCal.add(Calendar.MINUTE, 1);
    		Event event = new Event();
    		event.setId("6");
    		event.setDateTimeUTC(daCal.getTime());
    		event.setTapType(Event.TAP_TYPE_OFF);
    		event.setStopId(Route.STOP_3);
    		event.setCompanyId(Constants.COMPANY_ID);
    		event.setBusId(Constants.BUS_ID);
    		event.setPan(Constants.PAN_MASTERCARD_2);
    		eventList.add(event);
    	}
    	//now an event that doen't have a TAP OFF event
    	{
    		Event event = new Event();
    		event.setId("7");
    		event.setDateTimeUTC(daCal.getTime());
    		event.setTapType(Event.TAP_TYPE_ON);
    		event.setStopId(Route.STOP_1);
    		event.setCompanyId(Constants.COMPANY_ID);
    		event.setBusId(Constants.BUS_ID);
    		event.setPan(Constants.PAN_DINERS_1);
    		eventList.add(event);
    	}
    	//now a TAP OFF event that doen't have a TAP ON event (this event will end up nowhere)
    	{
    		Event event = new Event();
    		event.setId("8");
    		event.setDateTimeUTC(daCal.getTime());
    		event.setTapType(Event.TAP_TYPE_OFF);
    		event.setStopId(Route.STOP_2);
    		event.setCompanyId(Constants.COMPANY_ID);
    		event.setBusId(Constants.BUS_ID);
    		event.setPan(Constants.PAN_DINERS_2);
    		eventList.add(event);
    	}
    	return eventList;
    }
}
