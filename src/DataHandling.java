//import ArduinoConnection.Connection;
import java.util.Calendar;
import java.util.Scanner;
import sensors.*;
import sensors.HeatFluxSensor;
import java.sql.Timestamp;

public class DataHandling 

{
	
	public ArduinoConnector arduino;
	
	//Heat flux sensor:
		//Sensor ID, Sensor Name, Sensor type, time stamp, heat flux data, surface temp data, air temp data 
	//External temperature sensor:
	    //Sensor ID, Sensor Name, Sensor type, time stamp, surface temp data, air temp data
    //Air temperature data:
        //Sensor ID, Sensor Name, Sensor type, time stamp, air temp data
	
	
	//String variables
	int id;
	String sensorName;
	String sensorType;
	//TimeStamp timeStamp; needs an import
	float airTempData;
	
	//Heat flux sensor variables
	float heatFluxTemp;
	float internalWallSurfaceTemp;
	float internalAirTemp;
	
	//External temperature sensor variables
	float externalSurfaceTemp; 
	float externalAirTemp;
	
	//Air temperature data variables
	//N/A
	

	public DataHandling()
	{
		
		
		//Needs to be uncommented when classes are added.
		//Connection connection = new Connection();
		//dataString = connection.getStream();
		
		//temp string to simulate data from arduino
		String dataString = "10,Temp,Int Temp,27,89,29,65";
		
		parseString(dataString); 
		
		
		
	}
	
	public Sensor parseString(String dataString)
	{
		//Heat flux sensor:
				//Sensor ID, Sensor Name, Sensor type, time stamp, heat flux data, surface temp data, air temp data 
			//External temperature sensor:
			    //Sensor ID, Sensor Name, Sensor type, time stamp, surface temp data, air temp data
		    //Air temperature data:
		        //Sensor ID, Sensor Name, Sensor type, time stamp, air temp data
			
		Sensor sensor;
		
		
			//String variables
			String id;
			String sensorName;
			String sensorType;
			Timestamp timestamp;
			float airTempData;
			
			//Heat flux sensor variables
			float heatFluxTemp;
			float internalWallSurfaceTemp;
			
			//External temperature sensor variables
			float externalSurfaceTemp; 
			
			//Air temperature data variables
			//N/A
		
		Scanner read = new Scanner(dataString);
		read.useDelimiter(",");	
		id = read.next();
		sensorName = read.next();
		sensorType = read.next();
		airTempData = read.nextFloat();
		Calendar c = Calendar.getInstance();
		java.util.Date now = c.getTime();
	    timestamp = new Timestamp(now.getTime());
		
		if (sensorType.equals("heat_flux1"))
		{
			heatFluxTemp = read.nextFloat();
			internalWallSurfaceTemp = read.nextFloat();
			sensor = new HeatFluxSensor(id, sensorName, sensorType, timestamp, heatFluxTemp, internalWallSurfaceTemp, airTempData);
			
		} else if (sensorType.equals("Ext Temp"))
		{
			externalSurfaceTemp = read.nextFloat();
			sensor = new ExternalTempSensor(id, sensorName, sensorType, timestamp, externalSurfaceTemp, airTempData);
		} else
		{
			sensor = new AirTempSensor(id, sensorName, sensorType, timestamp, airTempData);
		}
		
		return sensor;
	}
	
	public static void recieveDataString(String data) {
		System.out.println("Data handling\n__________");
		System.out.println(data);
	}

}
