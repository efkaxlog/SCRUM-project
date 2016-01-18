package sensors;

import java.sql.Timestamp;

public class Sensor {
	
	private String sensorID, sensorName, sensorType; 
	private Timestamp timestamp;
	
	public Sensor(String sensorID, String sensorName, 
			String sensorType, Timestamp timestamp) {
		this.sensorID = sensorID;
		this.sensorName = sensorName;
		this.sensorType = sensorType;
		this.timestamp = timestamp;
	}

	protected String getSensorID() {
		return sensorID;
	}

	protected void setSensorID(String sensorID) {
		this.sensorID = sensorID;
	}

	protected String getSensorName() {
		return sensorName;
	}

	protected void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	protected String getSensorType() {
		return sensorType;
	}

	protected void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	protected Timestamp getTimestamp() {
		return timestamp;
	}

	protected void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * for debugging and testing
	 */
	protected void printDetails() {
		System.out.printf("ID: %s\n"
						+ "Name: %s\n"
						+ "Type: %s\n"
						+ "Timestamp: %s\n",
						sensorID, sensorName, sensorType, timestamp.toString());
	}

}
