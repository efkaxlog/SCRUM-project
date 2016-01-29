package mainPackage;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import sensors.*;

public class Test {
	
	DataHandler dh = new DataHandler();

	public Test() {
		testExportingToCSV();
	}
	
	public void testExportingToCSV() {
		ArrayList<Sensor> sensors = getSensors("all");
		Utilities.exportToCsv(sensors, new File("/home/sta993/newCSVFile.csv"));
	}
	
	public ArrayList<Sensor> getSensors(String sensorType) {
		return dh.makeSensorsFromFiles(sensorType);
	}
	
	public void testGettingSensorsFromFileDB(String sensorName) {
		ArrayList<Sensor> sensors = new ArrayList<Sensor>(dh.makeSensorsFromFiles(sensorName));
		for (Sensor s : sensors) {
			s.printDetails();
		}
	}

	public void testGettingSensorStringsFromDB(String tableName) throws SQLException {
		
		ArrayList<Sensor> sensors = new ArrayList<Sensor>(dh.makeSensorsFromDB(tableName));
		for (Sensor s : sensors) {
			s.printDetails();
		}
	}
}
