package mainPackage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import sensors.HeatFluxSensor;
import sensors.Sensor;

/**
 * @author SPAT Group 1
 * @version 1.2
 */
public abstract class Utilities {
	/**
	 * Removes the last character from a given String.
	 * 
	 * @param str
	 * @return String value with it's last character removed
	 */
	public static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}

	/**
	 * Get's the time stamp from the computer.
	 * 
	 * @return Time stamp of the system.
	 */
	public static Timestamp getCurrentTimestamp() {
		Calendar c = Calendar.getInstance();
		java.util.Date now = c.getTime();
		return new Timestamp(now.getTime());
	}

	/**
	 * add description here
	 * 
	 * @param timestampString
	 * @return Time stamp
	 */
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

	private static ArrayList<String> prepareSensorDataForCsv(
			ArrayList<Sensor> sensors) {
		ArrayList<String> dataStrings = new ArrayList<String>();
		for (Sensor s : sensors) {
			StringBuilder sb = new StringBuilder();
			String dataString = "";
			sb.append(s.getTimestamp() + "," + s.getSensorID() + ","
					+ s.getSensorName() + "," + s.getSensorType() + ","
					+ s.getAirTemp() + "," + s.getSurfaceTemp());
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
		} catch (IOException e) {
			System.out.println("Error while exporting to CSV");
			e.printStackTrace();
		}
	}

	/**
	 * add description here
	 * 
	 * @param dataString
	 * @return Boolean value indicating the validity of the parameter
	 */
	public boolean checkData(String dataString) {
		if (!dataString.isEmpty()) {
			Scanner scanner = new Scanner(dataString);
			scanner.useDelimiter(",");
			int id = scanner.nextInt();
			String sensorType = scanner.next();
			String sensorName = scanner.next();
			if (!(id == 0) || !sensorType.isEmpty() || !sensorName.isEmpty()) {
				if (sensorType.equals("HFT")) {
					float heatFluxData = scanner.nextFloat();
					float surfaceTemp = scanner.nextFloat();
					float airTemp = scanner.nextFloat();
					if (!(heatFluxData == 0f) || !(surfaceTemp == 0f)
							|| !(airTemp == 0f)) {
						return true;
					}

				} else {
					float airTemp = scanner.nextFloat();
					float surfaceTemp = scanner.nextFloat();
					if (!(airTemp == 0f) || !(surfaceTemp == 0f)) {
						return true;
					}
				}

			}
			scanner.close();
		}
		return false;
	}
}
