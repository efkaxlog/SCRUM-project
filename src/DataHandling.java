//import ArduinoConnection.Connection;
import java.util.Scanner;
import sensors.*;

public class DataHandling 
{
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
		
		
		
		
	}
	
	public void parseString(String dataString)
	{
		Scanner read = new Scanner(dataString);
		read.useDelimiter(",");	
		id = read.nextInt();
		sensorName = read.next();
		sensorType = read.next();
		
		if (sensorType.equals("heat_flux1"))
		{
			heatFluxTemp = read.nextFloat();
			internalWallSurfaceTemp = read.nextFloat();
			internalAirTemp = read.nextFloat();
		} else if (sensorType.equals("Ext Temp"))
		{
			externalSurfaceTemp = read.nextFloat(); 
			externalAirTemp = read.nextFloat();
		} else if (sensorType.equals("Int Temp"))
		{
		    //No data
		}
		
	}

}
