
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sensors.*;


public class Database 
{
	static Connection connection;
	
	public Database(Sensor sensor) 
	{
		sortSensors(sensor);
	}
	
	//connection
	public static void main(String[] args)
	{
		String DRIVER_CLASS = "com.mysql.jdbc.Driver";
		//helios
		String url = "jdbc:mysql://helios.csesalford.com/sta302_spat"; //change to spat
		String user = "sta302";
		String pass = "Fosters2003";
		try
		{
			Class.forName(DRIVER_CLASS);
			connection = DriverManager.getConnection(url, user, pass);
			//use for returning not needed here
/*			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery("SELECT * FROM UserList");
			while(results.next())
			{
				//data from ssp database - change to SPAT
				String username = results.getString("Username");
				String password = results.getString("Password");
				String address = results.getString("Address");
				String mobile = results.getString("Mobile");
				String city = results.getString("City");
				//show database results
				System.out.println("User: " + username);
				System.out.println("Pass: " + password);
				System.out.println("Address: " + address);
				System.out.println("City: " + city);
				System.out.println("Mobile: " + mobile);
				System.out.println();
			}
			results.close();
			statement.close();
			connection.close();
		}*/
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
			System.exit(1);
		}
		catch (Exception exception)
		{
			System.err.println("Cannot load " + DRIVER_CLASS);
			System.exit(1);
		}
	}
	
	private void sortSensors(Sensor sensor) 
	{
		if(sensor instanceof ExternalTempSensor) 
		{
			insertIntoExtTemp(sensor);
		} 
		else if(sensor instanceof AirTempSensor) 
		{
			insertIntoAirTemp(sensor);
		} 
		else if(sensor instanceof HeatFluxSensor)
		{
			insertIntoHeatFlux(sensor);
		}
	}
	
	private void insertIntoHeatFlux(Sensor sensor)
	{
		String sql = "INSERT INTO Heat_Flux_Sensor VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, sensor.getSensorID());
		ps.setString(2, sensor.getSensorType());
		ps.setString(3, sensor.getSensorName());
		ps.setString(4, sensor.getTimestamp());
		ps.setString(5, sensor.getHeatFluxTemp());
		ps.setString(6, sensor.getInternalAirTemp());
		ps.setString(7, sensor.getInternalWallSurfaceTemp());
		ps.executeUpdate();
	}
	
	private void insertIntoExtTemp(Sensor sensor)
	{
		String sql = "INSERT INTO External_Temp_Sensor VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, sensor.getSensorID());
		ps.setString(2, sensor.getSensorType());
		ps.setString(3, sensor.getSensorName());
		ps.setString(4, sensor.getTimestamp());
		ps.setString(5, sensor.getExternalSurfaceTemp());
		ps.setString(6, sensor.getExternalAirTemp());
		ps.executeUpdate();
	}
	
	private void insertIntoAirTemp(Sensor sensor)
	{
		String sql = "INSERT INTO Air_Temp_Sensor(?, ?, ?, ?, ?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, sensor.getSensorID());
		ps.setString(2, sensor.getSensorType());
		ps.setString(3, sensor.getSensorName());
		ps.setString(4, sensor.getTimestamp());
		ps.setString(5, sensor.getAirTemp());
		ps.executeUpdate();
	}
}