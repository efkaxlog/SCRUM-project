import java.sql.Timestamp;
import java.util.Calendar;

import sensors.*;


public class Test {

	public Test() {
		
	}
	
	public void testSensors() {
		Calendar c = Calendar.getInstance();
		java.util.Date now = c.getTime();
		Timestamp timestamp = new Timestamp(now.getTime());
		
		AirTempSensor ats = new AirTempSensor("007", "Some Air Temp Sensor", 
						"Air Temperature Sensor", timestamp, 30.5f);
		ExternalTempSensor ets = new ExternalTempSensor("008", "Some External Temp Sensor", 
				"External Temp Sensor", timestamp, 27.5f, 20.1f);
		HeatFluxSensor hts = new HeatFluxSensor("009", "Heat Flux Sensor", 
				"HeatFluxSensor", timestamp, 20.5f, 17.1f, 77.8f);
		
		ats.printDetails();
		ets.printDetails();
		hts.printDetails();
	}
	
	

}
