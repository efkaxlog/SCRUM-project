package mainPackage;

import java.sql.SQLException;
import java.util.ArrayList;

import sensors.Sensor;

public class Test {

	DataHandler dh = new DataHandler();

	public Test() throws SQLException {
		testGettingSensorsFromFileDB("all");
	}

	public void testGettingSensorsFromFileDB(String sensorName) {
		ArrayList<Sensor> sensors = new ArrayList<Sensor>(
				dh.makeSensorsFromFiles(sensorName));
		for (Sensor s : sensors) {
			s.printDetails();
		}
	}

	public void testGettingSensorStringsFromDB(String tableName)
			throws SQLException {

		ArrayList<Sensor> sensors = new ArrayList<Sensor>(
				dh.makeSensorsFromDB(tableName));
		for (Sensor s : sensors) {
			s.printDetails();
		}
	}
}
