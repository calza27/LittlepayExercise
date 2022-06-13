package OrderMonkey.LittlepayExercise.DataObjects.Event;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Event {
	public static final String TAP_TYPE_ON = "ON";
	public static final String TAP_TYPE_OFF = "OFF";
	
	private String id;
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
	private Date dateTimeUTC;
	private String tapType;
	private String stopId;
	private String companyId;
	private String busId;
	private String pan;
	
	public Event() {}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getDateTimeUTC() {
		return dateTimeUTC;
	}
	public void setDateTimeUTC(Date dateTimeUTC) {
		this.dateTimeUTC = dateTimeUTC;
	}
	public String getTapType() {
		return tapType;
	}
	public void setTapType(String tapType) {
		this.tapType = tapType;
	}
	public String getStopId() {
		return stopId;
	}
	public void setStopId(String stopId) {
		this.stopId = stopId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}

	@Override
	public String toString() {
		return this.getId()
			+ " " + this.getDateTimeUTC()
			+ " " + this.getTapType()
			+ " " + this.getStopId()
			+ " " + this.getCompanyId()
			+ " " + this.getBusId()
			+ " " + this.getPan()
		;
	}
}
