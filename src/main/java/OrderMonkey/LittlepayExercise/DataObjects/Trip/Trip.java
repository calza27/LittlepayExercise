package OrderMonkey.LittlepayExercise.DataObjects.Trip;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import OrderMonkey.LittlepayExercise.DataObjects.Event.Event;

public class Trip {
	public static final String STATUS_COMPLETED = "COMPLETED";
	public static final String STATUS_INCOMPLETE = "INCOMPLETE";
	public static final String STATUS_CANCELLED = "CANCELLED";
	
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
	private Date started;
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
	private Date finished;
	private long durationSecs;
	private String fromStopId;
	private String toStopId;
	private String chargeAmount;
	private String companyId;
	private String busId;
	private String pan;
	private String status;
	
	public Trip() {}
	
	public Trip(Event fromEvent) {
		this.started = fromEvent.getDateTimeUTC();
		this.fromStopId = fromEvent.getStopId();
		this.companyId = fromEvent.getCompanyId();
		this.busId = fromEvent.getBusId();
		this.pan = fromEvent.getPan();
		this.status = STATUS_INCOMPLETE; //initialise to INCOMPLETE
	}

	@JsonProperty("Started")
	public Date getStarted() {
		return started;
	}
	public void setStarted(Date started) {
		this.started = started;
	}
	@JsonProperty("Finished")
	public Date getFinished() {
		return finished;
	}
	public void setFinished(Date finished) {
		this.finished = finished;
	}
	@JsonProperty("DurationSecs")
	public long getDurationSecs() {
		return durationSecs;
	}
	public void setDurationSecs(long durationSecs) {
		this.durationSecs = durationSecs;
	}
	@JsonProperty("FromStopId")
	public String getFromStopId() {
		return fromStopId;
	}
	public void setFromStopId(String fromStopId) {
		this.fromStopId = fromStopId;
	}
	@JsonProperty("ToStopId")
	public String getToStopId() {
		return toStopId;
	}
	public void setToStopId(String toStopId) {
		this.toStopId = toStopId;
	}
	@JsonProperty("ChargeAmount")
	public String getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	@JsonProperty("CompanyId")
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	@JsonProperty("BusID")
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	@JsonProperty("PAN")
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	@JsonProperty("Status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
