package sensors;

import java.sql.Timestamp;

public class AirTempSensor extends Sensor {

	private float airTemp;

	public AirTempSensor(int sensorID, String sensorName, String sensorType, Timestamp timestamp, float airTemp) {

		super(sensorID, sensorName, sensorType, timestamp);
		this.airTemp = airTemp;
	}

	public float getAirTemp() {
		return airTemp;
	}

	public void setAirTemp(float airTemp) {
		this.airTemp = airTemp;
	}

	public void printDetails() {
		System.out.println("-------------------------");
		System.out.println("Class: " + getClass().getSimpleName());
		super.printDetails();
		System.out.println("Air Temperature: " + airTemp);
	}
}
