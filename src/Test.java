import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import sensors.AirTempSensor;
import sensors.ExternalTempSensor;
import sensors.HeatFluxSensor;

public class Test {

	Database db = new Database();

	public Test() throws SQLException {
		
	}
	
	public void testGettingSensorStringsFromDB() throws SQLException {
		ArrayList<String> sensorStrings = new ArrayList<String>();
		db.addSensorStrings("all", sensorStrings);
		for (String data : sensorStrings) {
			System.out.println(data);
		}
	}

	public void testSensorsData() {
		Calendar c = Calendar.getInstance();
		java.util.Date now = c.getTime();
		Timestamp timestamp = new Timestamp(now.getTime());

		AirTempSensor ats = new AirTempSensor(7, "Some Air Temp Sensor", "Air Temperature Sensor", timestamp,
				30.5f);
		ExternalTempSensor ets = new ExternalTempSensor(8, "Some External Temp Sensor", "External Temp Sensor",
				timestamp, 27.5f, 20.1f);
		HeatFluxSensor hts = new HeatFluxSensor(9, "Heat Flux Sensor", "HeatFluxSensor", timestamp, 20.5f, 17.1f,
				77.8f);

		ats.printDetails();
		ets.printDetails();
		hts.printDetails();
	}

}
