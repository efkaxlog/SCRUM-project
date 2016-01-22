package sensors;

import java.sql.Timestamp;

public class HeatFluxSensor extends Sensor {

	private float heatFluxTemp, internalWallSurfaceTemp, internalAirTemp;

	public HeatFluxSensor(int sensorID, String sensorName, String sensorType, float heatFluxTemp,
			float internalWallSurfaceTemp, float internalAirTemp) {

		super(sensorID, sensorName, sensorType);
		this.heatFluxTemp = heatFluxTemp;
		this.internalWallSurfaceTemp = internalWallSurfaceTemp;
		this.internalAirTemp = internalAirTemp;
	}

	public float getHeatFluxTemp() {
		return heatFluxTemp;
	}

	public void setHeatFluxTemp(float heatFluxTemp) {
		this.heatFluxTemp = heatFluxTemp;
	}

	public float getInternalWallSurfaceTemp() {
		return internalWallSurfaceTemp;
	}

	public void setInternalWallSurfaceTemp(float internalWallSurfaceTemp) {
		this.internalWallSurfaceTemp = internalWallSurfaceTemp;
	}

	public float getInternalAirTemp() {
		return internalAirTemp;
	}

	public void setInternalAirTemp(float internalAirTemp) {
		this.internalAirTemp = internalAirTemp;
	}

	public void printDetails() {
		System.out.println("-------------------------");
		System.out.println("Class: " + getClass().getSimpleName());
		super.printDetails();
		System.out.println("Heat Flux Temperature: " + heatFluxTemp);
		System.out.println("Internal Wall Surface Temperature: " + internalWallSurfaceTemp);
		System.out.println("Internal Air Temperature: " + internalAirTemp);

	}

}
