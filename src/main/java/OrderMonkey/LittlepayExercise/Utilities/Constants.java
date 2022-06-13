package OrderMonkey.LittlepayExercise.Utilities;

import java.util.HashSet;
import java.util.Set;

import OrderMonkey.LittlepayExercise.DataObjects.Route.Route;

public class Constants {
	//initialise the available routes with their cost
	public static final Set<Route> AVAILABLE_ROUTES;
	static {
		Set<Route> rtSet = new HashSet<Route>();
		rtSet.add(new Route("Stop1", "Stop2", 325d));
		rtSet.add(new Route("Stop2", "Stop3", 550d));
		rtSet.add(new Route("Stop1", "Stop3", 730d));
		AVAILABLE_ROUTES = rtSet;
	}
}
