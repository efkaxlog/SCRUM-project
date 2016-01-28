package sensors;

import java.sql.Timestamp;

/**
 * @author SPAT Group 1
 * @version 1.2
 */
abstract public class Sensor {

	private String sensorName, sensorType;
	private int sensorID;
	private Timestamp timestamp;

	public Sensor(int sensorID, String sensorName, String sensorType) {
		this.sensorID = sensorID;
		this.sensorName = sensorName;
		this.sensorType = sensorType;
	}

	/**
	 * Show the sensor's ID value
	 * @return ID of a sensor
	 */
	public int getSensorID() {
		return sensorID;
	}

	/**
	 * Change the sensor's ID to the parameter value
	 * @param sensorID
	 */
	public void setSensorID(int sensorID) {
		this.sensorID = sensorID;
	}
	
	/**
	 * Show the name of a sensor
	 * @return Sensor name
	 */
	public String getSensorName() {
		return sensorName;
	}

	/**
	 * Change the sensor's name to the parameter value
	 * @param sensorName
	 */
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	/**
	 * Show the type of a sensor
	 * @return Sensor type
	 */
	public String getSensorType() {
		return sensorType;
	}

	/**
	 * Change the sensor's type to the parameter value
	 * @param sensorType
	 */
	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	/**
	 * Show the time stamp of a sensor object
	 * @return Time stamp of sensor
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Change the sensor's time stamp to the parameter value
	 * @param timestamp
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * for debugging and testing
	 */
	public void printDetails() {
		System.out.printf("ID: %s\n" + "Name: %s\n" + "Type: %s\n" + "Timestamp: %s\n", sensorID, sensorName,
				sensorType, timestamp.toString());
	}

}
