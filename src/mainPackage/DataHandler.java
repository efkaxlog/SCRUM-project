package mainPackage;

import java.sql.SQLException;
import java.sql.Timestamp;
//import ArduinoConnection.Connection;
import java.util.ArrayList;
import java.util.Scanner;

import database.Database;
import database.FlatFileDatabase;
import sensors.AirTempSensor;
import sensors.ExternalTempSensor;
import sensors.HeatFluxSensor;
import sensors.Sensor;

public class DataHandler {
	// Connection connection;
	Database database;
	FlatFileDatabase fileDatabase;

	public DataHandler() {
		database = new Database();
		fileDatabase = new FlatFileDatabase();
	}

	// fetches the data from the database class
	public ArrayList<Sensor> makeSensorsFromDB(String tableName) {
		boolean isDataFromArduino = false;
		ArrayList<String> sensorDataStrings = new ArrayList<String>();
		try {
			database.addSensorStrings(tableName, sensorDataStrings);
		} catch (SQLException e) {
			System.out.println("Failed to retrieve sensors data " + "strings from database. (table name: " + tableName);
			e.printStackTrace();
		}

		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		if (sensorDataStrings != null) {
			for (String row : sensorDataStrings) {
				sensors.add(makeSensor(row, isDataFromArduino));
			}
		}
		return sensors;
	}
	
	public ArrayList<Sensor> makeSensorsFromFiles(String sensorName) {
		ArrayList<String> sensorDataStrings = new ArrayList<String>();
		fileDatabase.addSensorStrings(sensorName, sensorDataStrings);
		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		boolean isDataFromArduino = false;
		for (String sensorData : sensorDataStrings) {
			sensors.add(makeSensor(sensorData, isDataFromArduino));
		}
		return sensors;	
	}

	public void handleObject(String dataString, boolean isDataFromArduino) {
//		Sensor sensor = makeSensor(dataString, isDataFromArduino);
//		fileDatabase.insertSensorData(sensor.getSensorName(), dataString);
		try {
			database.insertSensorIntoTable(makeSensor(dataString, isDataFromArduino));
		} catch (SQLException e) {
			System.out.println("Failed to insert sensor into table.");
			System.out.println("Data string: " + dataString);
			e.printStackTrace();
		}
	}

	public Sensor makeSensor(String dataString, boolean isDataFromArduino) {
		Sensor sensor;
		Scanner scanner = new Scanner(dataString);
		scanner.useDelimiter(",");
		int id = scanner.nextInt();
		String sensorType = scanner.next();
		String sensorName = scanner.next();

		if (sensorName.equals("heat_flux1")) {
			float heatFluxTemp = scanner.nextFloat();
			float internalWallSurfaceTemp = scanner.nextFloat();
			float airTempData = scanner.nextFloat();

			sensor = new HeatFluxSensor(id, sensorName, sensorType, heatFluxTemp, internalWallSurfaceTemp, airTempData);

		} else if (sensorName.equals("Ext Temp")) {
			float externalSurfaceTemp = scanner.nextFloat();
			float airTempData = scanner.nextFloat();
			sensor = new ExternalTempSensor(id, sensorName, sensorType, externalSurfaceTemp, airTempData);

		} else {
			float airTempData = scanner.nextFloat();
			sensor = new AirTempSensor(id, sensorName, sensorType, airTempData);
			scanner.next(); // QUICK FIX FOR BUG, DELETE LATER
		}
		
		Timestamp ts;
		if (isDataFromArduino) {
			ts = Utilities.getCurrentTimestamp();
		} else {
			ts = Utilities.getTimestampFromString(scanner.next());
		}
		sensor.setTimestamp(ts);
		scanner.close();
		sensor.printDetails();
		return sensor;
	}

}
