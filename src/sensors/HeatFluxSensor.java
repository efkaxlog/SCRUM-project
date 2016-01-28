package sensors;

/**
 * @author SPAT Group 1
 * @version 1.2
 */
public class HeatFluxSensor extends Sensor {

	private float heatFluxData, surfaceTemp, airTemp;

	public HeatFluxSensor(int sensorID, String sensorName, String sensorType, 
			float heatFluxData, float surfaceTemp, float airTemp) {

		super(sensorID, sensorName, sensorType);
		this.heatFluxData = heatFluxData;
		this.surfaceTemp = surfaceTemp;
		this.airTemp = airTemp;
	}

	/**
	 * Show the temperature of a Heat Flux Sensor
	 * @return Heat Flux Sensor temperature
	 */
	public float getHeatFluxData() {
		return heatFluxData;
	}

	/**
	 * Change the heat flux temperature value of the Heat Flux Sensor to the parameter value
	 * @param heatFluxData
	 */
	public void setHeatFluxData(float heatFluxData) {
		this.heatFluxData = heatFluxData;
	}

	/**
	 * Show the surface temperature data from the Heat Flux Sensor
	 * @return Surface temperature
	 */
	public float getSurfaceTemp() {
		return surfaceTemp;
	}
	
	/**
	 * Change the surface temperature value of the Heat Flux Sensor to the parameter value
	 * @param surfaceTemp
	 */
	public void setSurfaceTemp(float surfaceTemp) {
		this.surfaceTemp = surfaceTemp;
	}

	/**
	 * Return the value of the air temperature of a Heat Flux Sensor
	 * @return Air Temperature value
	 */
	public float getAirTemp() {
		return airTemp;
	}

	/**
	 * Change the air temperature value of the Heat Flux Sensor to the parameter value
	 * @param airTemp
	 */
	public void setAirTemp(float airTemp) {
		this.airTemp = airTemp;
	}

	/**
	 * Print the sensor temperature details
	 */
	public void printDetails() {
		System.out.println("-------------------------");
		System.out.println("Class: " + getClass().getSimpleName());
		super.printDetails();
		System.out.println("Heat Flux Temperature: " + heatFluxData);
		System.out.println("Internal Wall Surface Temperature: " + surfaceTemp);
		System.out.println("Internal Air Temperature: " + airTemp);
	}
}