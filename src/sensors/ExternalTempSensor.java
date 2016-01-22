package sensors;

import java.sql.Timestamp;

public class ExternalTempSensor extends Sensor {

	private float externalSurfaceTemp, externalAirTemp;

	public ExternalTempSensor(int sensorID, String sensorName, String sensorType,
			float externalSurfaceTemp, float externalAirTemp) {

		super(sensorID, sensorName, sensorType);
		this.externalSurfaceTemp = externalSurfaceTemp;
		this.externalAirTemp = externalAirTemp;

	}

	public float getExternalSurfaceTemp() {
		return externalSurfaceTemp;
	}

	public void setExternalSurfaceTemp(float externalSurfaceTemp) {
		this.externalSurfaceTemp = externalSurfaceTemp;
	}

	public float getExternalAirTemp() {
		return externalAirTemp;
	}

	public void setExternalAirTemp(float externalAirTemp) {
		this.externalAirTemp = externalAirTemp;
	}

	public void printDetails() {
		System.out.println("-------------------------");
		System.out.println("Class: " + getClass().getSimpleName());
		super.printDetails();
		System.out.println("External Surface Temperature: " + externalSurfaceTemp);
		System.out.println("External Air Temperature: " + externalAirTemp);

	}

}
