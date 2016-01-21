
//import ArduinoConnection.Connection;
import java.util.Calendar;
import java.util.Scanner;
import sensors.*;
import java.sql.Timestamp;

public class DataHandling {
	// Connection connection;
	Database database;

	public DataHandling() {
		database = new Database();
	}

	public void handleObject(String dataString) {
		database.addSensor(makeSensor(dataString));

	}

	public Sensor makeSensor(String dataString) {
		Sensor sensor;

		Scanner scanner = new Scanner(dataString);
		scanner.useDelimiter(",");
		int id = scanner.nextInt();
		String sensorName = scanner.next();
		String sensorType = scanner.next();
		float airTempData = scanner.nextFloat();
		Timestamp timestamp = getCurrentTimestamp();

		if (sensorType.equals("heat_flux1")) {
			float heatFluxTemp = scanner.nextFloat();
			float internalWallSurfaceTemp = scanner.nextFloat();
			sensor = new HeatFluxSensor(id, sensorName, sensorType, timestamp, 
					heatFluxTemp, internalWallSurfaceTemp, airTempData);
		} else if (sensorType.equals("Ext Temp")) {
			float externalSurfaceTemp = scanner.nextFloat();
			sensor = new ExternalTempSensor(id, sensorName, sensorType,
					timestamp, externalSurfaceTemp, airTempData);
		} else {
			sensor = new AirTempSensor(id, sensorName, sensorType, timestamp, airTempData);
		}
		System.out.println(sensor.getSensorName() + "made");
		return sensor;
	}
	
	private Timestamp getCurrentTimestamp() {
		Calendar c = Calendar.getInstance();
		java.util.Date now = c.getTime();
		return new Timestamp(now.getTime());
	}
}
