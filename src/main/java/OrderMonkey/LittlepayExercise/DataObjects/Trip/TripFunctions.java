package OrderMonkey.LittlepayExercise.DataObjects.Trip;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import OrderMonkey.LittlepayExercise.DataObjects.Event.Event;
import OrderMonkey.LittlepayExercise.DataObjects.Route.Route;
import OrderMonkey.LittlepayExercise.Utilities.Constants;

public class TripFunctions {
    public static Trip buildTrip(Event startEvent, Event endEvent) {
    	Trip trip = new Trip(startEvent);
    	if(endEvent != null) {
			trip.setFinished(endEvent.getDateTimeUTC());
			trip.setToStopId(endEvent.getStopId());
			trip.setChargeAmount(TripFunctions.constructDollarChargeAmount(trip));
			trip.setDurationSecs(TripFunctions.calculateDuration(trip));
			trip.setStatus(Trip.STATUS_COMPLETED);
			if(StringUtils.equalsIgnoreCase(trip.getFromStopId(), trip.getToStopId())) {
				//if the stop IDs match, then the trip is instead cancelled
				trip.setStatus(Trip.STATUS_CANCELLED);
				trip.setChargeAmount("$0.00");
				trip.setDurationSecs(0);
			}
    	}
		return trip;
    }
    
	public static String constructDollarChargeAmount(Trip trip) {
    	Double amount = calculateChargeAmount(trip);
    	return "$" + (amount/100);
    }
    
	public static Double calculateChargeAmount(Trip trip) {
    	return calculateChargeAmount(trip.getFromStopId(), trip.getToStopId());
    }
    
    private static Double calculateChargeAmount(String fromStopId, String toStopId) {
    	if(StringUtils.isBlank(fromStopId)) {
    		throw new IllegalArgumentException("fromStopId not given! Cannot calculate trip charge!");
    	}
    	if(StringUtils.isBlank(toStopId)) {
    		//toStopId is blank/not given, so we find the most expensive route feature the fromStopId
    		Route expensiveRoute = Constants.AVAILABLE_ROUTES.stream().filter(route -> {
    			return fromStopId.equalsIgnoreCase(route.getFromStopId()) || fromStopId.equalsIgnoreCase(route.getToStopId());
    		}).sorted((r1, r2) -> {
    			return r1.getCharge().compareTo(r2.getCharge());
    		}).findFirst().orElse(null);
    		return expensiveRoute == null ? 0 : expensiveRoute.getCharge();
    	} else {
    		Route expensiveRoute = Constants.AVAILABLE_ROUTES.stream().filter(route -> {
    			return (
					(fromStopId.equalsIgnoreCase(route.getFromStopId()) && toStopId.equalsIgnoreCase(route.getToStopId()))
					|| (toStopId.equalsIgnoreCase(route.getFromStopId()) && fromStopId.equalsIgnoreCase(route.getToStopId()))
				);
    		}).sorted((r1, r2) -> {
    			return r1.getCharge().compareTo(r2.getCharge());
    		}).findFirst().orElse(null);
    		return expensiveRoute == null ? 0 : expensiveRoute.getCharge();
    	}
    }
    
    public static long calculateDuration(Trip trip) {
    	return calculateDuration(trip.getStarted(), trip.getFinished());
    }
    
    private static long calculateDuration(Date started, Date finished) {
    	if(started == null || finished == null) {
    		throw new IllegalArgumentException("fromStopId not given! Cannot calculate trip charge!");
    	}
    	//Date.getTime gives us milliseconds since epoch
    		//we can subtract the start from the finish to get the difference in milliseconds, then divide by 1000 to get seconds
    	return (finished.getTime() - started.getTime())/1000;
    }
}
