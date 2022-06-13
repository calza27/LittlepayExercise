package OrderMonkey.LittlepayExercise.DataObjects.Route;

public class Route {
	private String fromStopId;
	private String toStopId;
	private Double charge; //storing the charge as a double that represents the number of cents eg. 330 = $3.30
	
	public Route(String fromStopId, String toStopId, double charge) {
		this.fromStopId = fromStopId;
		this.toStopId = toStopId;
		this.charge = charge;
	}
	
	public String getFromStopId() {
		return fromStopId;
	}
	public void setFromStopId(String fromStopId) {
		this.fromStopId = fromStopId;
	}
	public String getToStopId() {
		return toStopId;
	}
	public void setToStopId(String toStopId) {
		this.toStopId = toStopId;
	}
	public Double getCharge() {
		return charge;
	}
	public void setCharge(Double charge) {
		this.charge = charge;
	}
}
