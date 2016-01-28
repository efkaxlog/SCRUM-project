package mainPackage;

import java.sql.SQLException;
import java.sql.Timestamp;
//import ArduinoConnection.Connection;
import java.util.ArrayList;
import java.util.Scanner;

import database.Database;
import database.FlatFileDatabase;
import sensors.HeatFluxSensor;
import sensors.Sensor;
import sensors.TemperatureSensor;

/**
 * @author SPAT Group 1
 * @version 1.2
 */
public class DataHandler 
{
	//Connection connection;
	Database database;
	FlatFileDatabase fileDatabase;

	public DataHandler() 
	{
		database = new Database();
		fileDatabase = new FlatFileDatabase();
	}

	/**
	 * Takes data from the database and turns it in to a Sensor object
	 * @param tableName
	 * @return values stored within an ArrayList
	 */
	public ArrayList<Sensor> makeSensorsFromDB(String tableName) 
	{
		boolean isDataFromArduino = false;
		ArrayList<String> sensorDataStrings = new ArrayList<String>();
		try 
		{
			database.addSensorStrings(tableName, sensorDataStrings);
		} 
		catch (SQLException e) 
		{
			System.out.println("Failed to retrieve sensors data " 
								+ "strings from database. (table name: " + tableName);
			e.printStackTrace();
		}
		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		if (sensorDataStrings != null) 
		{
			for (String row : sensorDataStrings) 
			{
				sensors.add(makeSensor(row, isDataFromArduino));
			}
		}
		return sensors;
	}
	
	/**
	 * add description here
	 * @param sensorName
	 * @return values stored within an ArrayList
	 */
	public ArrayList<Sensor> makeSensorsFromFiles(String sensorName) 
	{
		ArrayList<String> sensorDataStrings = new ArrayList<String>();
		fileDatabase.addSensorStrings(sensorName, sensorDataStrings);
		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		boolean isDataFromArduino = false;
		for (String sensorData : sensorDataStrings) 
		{
			sensors.add(makeSensor(sensorData, isDataFromArduino));
		}
		return sensors;	
	}
	
	/**
	 * add description here
	 * @param dataString
	 * @param isDataFromArduino
	 */
	public void handleObject(String dataString, boolean isDataFromArduino) 
	{
		Sensor sensor = makeSensor(dataString, isDataFromArduino);
		fileDatabase.insertSensorData(sensor.getSensorName(), dataString);
	}

	/**
	 * add description here
	 * @param dataString
	 * @param isDataFromArduino
	 * @return values stored within an ArrayList
	 */
	public Sensor makeSensor(String dataString, boolean isDataFromArduino) 
	{
		Sensor sensor;
		Scanner scanner = new Scanner(dataString);
		scanner.useDelimiter(",");
		int id = scanner.nextInt();
		String sensorType = scanner.next();
		String sensorName = scanner.next();

		if (sensorType.equals("HFT")) 
		{
			float heatFluxData = scanner.nextFloat();
			float surfaceTemp = scanner.nextFloat();
			float airTemp = scanner.nextFloat();
			sensor = new HeatFluxSensor(id, sensorName, sensorType, heatFluxData, 
										surfaceTemp, airTemp);
		} 
		else 
		{
			float airTemp = scanner.nextFloat();
			float surfaceTemp = scanner.nextFloat();
			sensor = new TemperatureSensor(id, sensorName, sensorType, airTemp, surfaceTemp);
		}
		
		Timestamp ts;
		if (isDataFromArduino) 
		{
			ts = Utilities.getCurrentTimestamp();
		} 
		else 
		{
			ts = Utilities.getTimestampFromString(scanner.next());
		}
		sensor.setTimestamp(ts);
		scanner.close();
		sensor.printDetails();
		return sensor;
	}
}
