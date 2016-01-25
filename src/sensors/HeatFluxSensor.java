package sensors;

public class HeatFluxSensor extends Sensor {

	private float heatFluxData, surfaceTemp, airTemp;

	public HeatFluxSensor(int sensorID, String sensorName, String sensorType, 
			float heatFluxData, float surfaceTemp, float airTemp) {

		super(sensorID, sensorName, sensorType);
		this.heatFluxData = heatFluxData;
		this.surfaceTemp = surfaceTemp;
		this.airTemp = airTemp;
	}

	public float getHeatFluxData() {
		return heatFluxData;
	}

	public void setHeatFluxData(float heatFluxData) {
		this.heatFluxData = heatFluxData;
	}

	public float getSurfaceTemp() {
		return surfaceTemp;
	}

	public void setSurfaceTemp(float surfaceTemp) {
		this.surfaceTemp = surfaceTemp;
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
		System.out.println("Heat Flux Temperature: " + heatFluxData);
		System.out.println("Internal Wall Surface Temperature: " + surfaceTemp);
		System.out.println("Internal Air Temperature: " + airTemp);

	}

}
