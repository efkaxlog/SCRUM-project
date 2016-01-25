package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class FlatFileDatabase {
	
	private HashMap<String, String> fileNames;
	private String dataFolder = "data/";
	
	public FlatFileDatabase() {
		fileNames = new HashMap<String, String>();
		setUpFileNames();
	}
	
	private void setUpFileNames() {
		fileNames.put("heat_flux1", "Heat Flux Data.txt");
		fileNames.put("Int Temp", "Internal Temp Data.txt");
		fileNames.put("Ext Temp", "External Temp Data.txt");
	}
	
	private String getSensorFilepath(String sensorName) {
		String filename = dataFolder + fileNames.get(sensorName);
		File file = new File(filename);
		System.out.println(sensorName);
		return file.getAbsolutePath();
	}
	
	public void addSensorStrings(String sensorName, ArrayList<String> sensorStrings) {
		if (sensorName.equals("all")) {
			Collection<String> c = fileNames.keySet();
			Iterator<String> itr = c.iterator();
			while (itr.hasNext()) {
				String nextSensorFile = (String) itr.next();
				addSensorsStringsFromFile(nextSensorFile, sensorStrings);
			}
		} else {
			addSensorsStringsFromFile(sensorName, sensorStrings);
		}
	}

	public void addSensorsStringsFromFile(String sensorName, ArrayList<String> sensorStrings) {
		try {
			File sensorFile = new File(getSensorFilepath(sensorName));
			System.out.println(getSensorFilepath(sensorName));
			FileInputStream fis = new FileInputStream(sensorFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String dataString = null;
			while ((dataString = br.readLine()) != null) {
				sensorStrings.add(dataString);
			}
		} catch (IOException e) {
			System.out.println("Cannot read sensor data from file.");
			e.printStackTrace();
		}
	}
	
	public void insertSensorData(String sensorName, String data) {
		String timestamp = mainPackage.Utilities.getCurrentTimestamp().toString();
		String sensorFilepath = getSensorFilepath(sensorName);
		boolean appendToFile = true;
		try {
			FileWriter writer = new FileWriter(sensorFilepath, appendToFile);
			String sensorData = data + "," + timestamp;
			writer.write(sensorData);
			writer.write("\r\n"); // write new line
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
