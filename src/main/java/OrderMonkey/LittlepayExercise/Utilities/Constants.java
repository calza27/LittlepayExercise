package OrderMonkey.LittlepayExercise.Utilities;

import java.util.HashSet;
import java.util.Set;

import OrderMonkey.LittlepayExercise.DataObjects.Route.Route;

public class Constants {
	//initialise the available routes with their cost
	public static final Set<Route> AVAILABLE_ROUTES;
	static {
		Set<Route> rtSet = new HashSet<Route>();
		rtSet.add(new Route(Route.STOP_1, Route.STOP_2, 325d));
		rtSet.add(new Route(Route.STOP_2, Route.STOP_3, 550d));
		rtSet.add(new Route(Route.STOP_1, Route.STOP_3, 730d));
		AVAILABLE_ROUTES = rtSet;
	}
	//these are only used for the test cases really
	public static final String COMPANY_ID = "Company1";
	public static final String BUS_ID = "Bus37";
	
	//sample PANS for test cases
	public static final String PAN_AMEX = "34343434343434";
	public static final String PAN_DINERS_1 = "36700102000000";
	public static final String PAN_DINERS_2 = "36148900647913";
	public static final String PAN_MASTERCARD_1 = "5555555555554444";
	public static final String PAN_MASTERCARD_2 = "5454545454545454";
	public static final String PAN_VISA_1 = "4444333322221111";
	public static final String PAN_VISA_2 = "4911830000000";
	public static final String PAN_VISA_3 = "4917610000000000";
}
