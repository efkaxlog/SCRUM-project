
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sensors.AirTempSensor;
import sensors.ExternalTempSensor;
import sensors.HeatFluxSensor;
import sensors.Sensor;

public class Database 
{
	static Connection connection;
	ArrayList <String> sensors = new ArrayList<String>();
	
	public Database()
	{
		String DRIVER_CLASS = "com.mysql.jdbc.Driver";
		//helios credentials
		String url = "jdbc:mysql://helios.csesalford.com/sta302_spat";
		String user = "sta302";
		String pass = "Fosters2003";
		try
		{
			Class.forName(DRIVER_CLASS);
			connection = DriverManager.getConnection(url, user, pass);
		}
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
	
	public void addSensor(Sensor sensor) 
	{
		try
		{
			if(sensor instanceof ExternalTempSensor) 
			{
				insertIntoExtTemp((ExternalTempSensor) sensor);
			} 
			else if(sensor instanceof AirTempSensor) 
			{
				insertIntoAirTemp((AirTempSensor) sensor);
			} 
			else if(sensor instanceof HeatFluxSensor)
			{
				insertIntoHeatFlux((HeatFluxSensor) sensor);
			}
			System.out.println(sensor.getSensorName() + " added");
		} 
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private void insertIntoHeatFlux(HeatFluxSensor sensor) throws SQLException
	{
		String sql = "INSERT INTO Heat_Flux_Sensor"
				+ "(Sensor_ID, Sensor_Type, Sensor_Name, Time_Stamp,"
				+ "Heat_Flux_Data, Air_Temp_Data, Surface_Temp_Data) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, sensor.getSensorID());
		ps.setString(2, sensor.getSensorType());
		ps.setString(3, sensor.getSensorName());
		ps.setTimestamp(4, sensor.getTimestamp());
		ps.setFloat(5, sensor.getHeatFluxTemp());
		ps.setFloat(6, sensor.getInternalAirTemp());
		ps.setFloat(7, sensor.getInternalWallSurfaceTemp());
		System.out.print(ps.toString());
		ps.executeUpdate();
	}
	
	private void insertIntoExtTemp(ExternalTempSensor sensor) throws SQLException
	{
		String sql = "INSERT INTO External_Temp_Sensor"
				+ "(Sensor_ID, Sensor_Type, Sensor_Name, Time_Stamp,"
				+ "Surface_Temp_Data, Air_Temp_Data) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, sensor.getSensorID());
		ps.setString(2, sensor.getSensorType());
		ps.setString(3, sensor.getSensorName());
		ps.setTimestamp(4, sensor.getTimestamp());
		ps.setFloat(5, sensor.getExternalSurfaceTemp());
		ps.setFloat(6, sensor.getExternalAirTemp());
		ps.executeUpdate();
	}
	
	private void insertIntoAirTemp(AirTempSensor sensor) throws SQLException
	{
		String sql = "INSERT INTO Air_Temp_Sensor"
				+ "(Sensor_ID, Sensor_Type, Sensor_Name, Time_Stamp, Air_Temp_Data) "
				+ "VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, sensor.getSensorID());
		ps.setString(2, sensor.getSensorType());
		ps.setString(3, sensor.getSensorName());
		ps.setTimestamp(4, sensor.getTimestamp());
		ps.setFloat(5, sensor.getAirTemp());
		ps.executeUpdate();
	}
	
	public ArrayList returnData() throws SQLException 
	{
		selectFromHeatFlux();
		selectFromExternalTemp();
		selectFromAirTemp();
		return sensors;
	}
	
	public void selectFromHeatFlux() throws SQLException
	{
		String sql = "SELECT * FROM Heat_Flux_Sensor";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) 
		{
			System.out.println("got here");
			String row = rs.getString("Sensor_ID") + "," + rs.getString("Sensor_Name")
			+ "," + rs.getString("Sensor_Type") + "," + rs.getString("Time_Stamp")
			+ "," + rs.getString("Heat_Flux_Data")+ "," + rs.getString("Surface_Temp_Data")
			+ "," + rs.getString("Air_Temp_Data");
			System.out.println(row);
			sensors.add(row);
		}
	}
	
	public void selectFromExternalTemp() throws SQLException
	{
		String sql = "SELECT * FROM External_Temp_Sensor";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			String row = rs.getString("Sensor_ID") + "," + rs.getString("Sensor_Name")
			+ "," + rs.getString("Sensor_Type") + "," + rs.getString("Time_Stamp")
			+ "," + rs.getString("Surface_Temp_Data") + "," + rs.getString("Air_Temp_Data");
			sensors.add(row);
		}
	}
	
	public void selectFromAirTemp() throws SQLException
	{
		String sql = "SELECT * FROM Air_Temp_Sensor";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			String row = rs.getString("Sensor_ID") + "," + rs.getString("Sensor_Name")
			+ "," + rs.getString("Sensor_Type") + "," + rs.getString("Time_Stamp")
			+ "," + "," + rs.getString("Air_Temp_Data");
			sensors.add(row);
		}
	}
}