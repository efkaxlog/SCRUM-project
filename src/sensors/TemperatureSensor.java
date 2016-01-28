package sensors;

/**
 * @author SPAT Group 1
 * @version 1.2
 */
public class TemperatureSensor extends Sensor {
	
	private float airTemp, surfaceTemp;
	
	public TemperatureSensor(int sensorID, String sensorName, String sensorType, 
			float airTemp, float surfaceTemp) {
		super(sensorID, sensorName, sensorType);
		this.airTemp = airTemp;
		this.surfaceTemp = surfaceTemp;
	}

	/**
	 * Return the value of the air temperature of a Temperature Sensor
	 * @return Air Temperature value
	 */
	public float getAirTemp() {
		return airTemp;
	}
	
	/**
	 * Change the air temperature value of the Temperature Sensor to the parameter value
	 * @param airTemp
	 */
	public void setAirTemp(float airTemp) {
		this.airTemp = airTemp;
	}

	/**
	 * Show the surface temperature data from the Temperature Sensor
	 * @return Surface temperature
	 */
	public float getSurfaceTemp() {
		return surfaceTemp;
	}

	/**
	 * Change the surface temperature value of the Temperature Sensor to the parameter value
	 * @param surfaceTemp
	 */
	public void setSurfaceTemp(float surfaceTemp) {
		this.surfaceTemp = surfaceTemp;
	}
	
	/**
	 * Print the sensor temperature details
	 */
	public void printDetails() {
		System.out.println("-------------------------");
		System.out.println("Class: " + getClass().getSimpleName());
		super.printDetails();
		System.out.println("Air temperature: " + airTemp);
		System.out.println("Surface temperature: " + surfaceTemp);
	}
}
