package mainPackage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import sensors.HeatFluxSensor;
import sensors.Sensor;

public abstract class Utilities {

	/**
	 * returns string without it's last character
	 */
	public static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}

	public static Timestamp getCurrentTimestamp() {
		Calendar c = Calendar.getInstance();
		java.util.Date now = c.getTime();
		return new Timestamp(now.getTime());
	}

	public static Timestamp getTimestampFromString(String timestampString) {
		String pattern = "yyyy-MM-dd hh:mm:ss.SSS";
		Timestamp timestamp = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			Date parsedDate = dateFormat.parse(timestampString);
			timestamp = new Timestamp(parsedDate.getTime());
		} catch (Exception e) {
			System.out.println("Failed converting string to timestamp");
			e.printStackTrace();
		}
		return timestamp;
	}
	
	private static ArrayList<String> prepareSensorDataForCsv(ArrayList<Sensor> sensors) {
		ArrayList<String> dataStrings = new ArrayList<String>();
		for (Sensor s : sensors) {
			StringBuilder sb = new StringBuilder();
			String dataString = "";
			sb.append(
					s.getTimestamp() + "," + s.getSensorID() + "," + 
					s.getSensorName() + "," + s.getSensorType() + "," +
					s.getAirTemp() + "," + s.getSurfaceTemp());
			if (s.getSensorType().equals("HFT")) {
				HeatFluxSensor hfs = (HeatFluxSensor) s;
				sb.append("," + hfs.getHeatFluxData());
			}
			dataString = sb.toString();
			System.out.println(dataString);
			dataStrings.add(dataString);
		}
		return dataStrings;	
	}
	
	public static void exportToCsv(ArrayList<Sensor> sensors, File file) {
		ArrayList<String> dataStrings = prepareSensorDataForCsv(sensors);
		try {
			// add csv extension if the file hasn't got one
			if (!file.getName().endsWith(".csv")) {
				file = new File(file.getAbsolutePath() + ".csv");
			}
			FileWriter writer = new FileWriter(file);
			for (String data : dataStrings) {
				writer.append(data);
				writer.append("\n");
			}
			writer.flush();
			writer.close();
		}
		catch (IOException e) {
			System.out.println("Error while exporting to CSV");
			e.printStackTrace();
		}
	}
}
