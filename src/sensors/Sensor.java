package sensors;

import java.sql.Timestamp;

abstract public class Sensor {
	
	private String sensorID, sensorName, sensorType; 
	private Timestamp timestamp;
	
	public Sensor(String sensorID, String sensorName, 
			String sensorType, Timestamp timestamp) {
		this.sensorID = sensorID;
		this.sensorName = sensorName;
		this.sensorType = sensorType;
		this.timestamp = timestamp;
	}

	public String getSensorID() {
		return sensorID;
	}

	public void setSensorID(String sensorID) {
		this.sensorID = sensorID;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * for debugging and testing
	 */
	public void printDetails() {
		System.out.printf("ID: %s\n"
						+ "Name: %s\n"
						+ "Type: %s\n"
						+ "Timestamp: %s\n",
						sensorID, sensorName, sensorType, timestamp.toString());
	}

}
