
//import ArduinoConnection.Connection;
import java.util.Calendar;
import java.util.Scanner;
import sensors.*;

import java.sql.SQLException;
import java.sql.Timestamp;

public class DataHandler {
	// Connection connection;
	Database database;

	public DataHandler() {
		database = new Database();
	}

	public void handleObject(String dataString) {
		try {
			database.insertSensorIntoTable(makeSensor(dataString));
		} catch (SQLException e) {
			System.out.println("Failed to insert sensor into table.");
			System.out.println("Data string: " + dataString);
			e.printStackTrace();
		}
	}

	public Sensor makeSensor(String dataString) {
		Sensor sensor;
		Scanner scanner = new Scanner(dataString);
		scanner.useDelimiter(",");
		int id = scanner.nextInt();
		String sensorType = scanner.next();
		String sensorName = scanner.next();
		float airTempData = scanner.nextFloat();
		Timestamp timestamp = getCurrentTimestamp();

		if (sensorName.equals("heat_flux1")) {
			float heatFluxTemp = scanner.nextFloat();
			float internalWallSurfaceTemp = scanner.nextFloat();
			sensor = new HeatFluxSensor(id, sensorName, sensorType, timestamp, 
					heatFluxTemp, internalWallSurfaceTemp, airTempData);
			
		} else if (sensorName.equals("Ext Temp")) {
			float externalSurfaceTemp = scanner.nextFloat();
			sensor = new ExternalTempSensor(id, sensorName, sensorType,
					timestamp, externalSurfaceTemp, airTempData);
			
		} else {
			sensor = new AirTempSensor(id, sensorName, sensorType, timestamp, airTempData);
		}
		return sensor;
	}
	
	private Timestamp getCurrentTimestamp() {
		Calendar c = Calendar.getInstance();
		java.util.Date now = c.getTime();
		return new Timestamp(now.getTime());
	}
}
