package sensors;

public class TemperatureSensor extends Sensor {
	
	private float airTemp, surfaceTemp;
	
	public TemperatureSensor(int sensorID, String sensorName, String sensorType, 
			float airTemp, float surfaceTemp) {
		super(sensorID, sensorName, sensorType);
		this.airTemp = airTemp;
		this.surfaceTemp = surfaceTemp;
	}

	public float getAirTemp() {
		return airTemp;
	}

	public void setAirTemp(float airTemp) {
		this.airTemp = airTemp;
	}

	public float getSurfaceTemp() {
		return surfaceTemp;
	}

	public void setSurfaceTemp(float surfaceTemp) {
		this.surfaceTemp = surfaceTemp;
	}
	
	public void printDetails() {
		System.out.println("-------------------------");
		System.out.println("Class: " + getClass().getSimpleName());
		super.printDetails();
		System.out.println("Air temperature: " + airTemp);
		System.out.println("Surface temperature: " + surfaceTemp);
	}
}
