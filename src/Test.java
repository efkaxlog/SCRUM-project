import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import sensors.*;

public class Test {

	Database db = new Database();

	public Test() throws SQLException {
		testGettingSensorStringsFromDB("all");
	}

	public void testGettingSensorStringsFromDB(String tableName) throws SQLException {
		DataHandler dh = new DataHandler();
		ArrayList<Sensor> sensors = new ArrayList<Sensor>(dh.makeSensorsFromDB(tableName));
		for (Sensor s : sensors) {
			s.printDetails();
		}
	}
}
