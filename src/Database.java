
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import sensors.AirTempSensor;
import sensors.ExternalTempSensor;
import sensors.HeatFluxSensor;
import sensors.Sensor;

public class Database 
{
	static Connection connection;
	ArrayList <String> sensors = new ArrayList<String>();
	
	HashMap<String, String> sensorTables = new HashMap<String, String>();
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
		
		// populates sensorTables hashMap with DB table names
		setupHashMapTables();
	}
	
	private void setupHashMapTables() {
		sensorTables.put("heat_flux1", "Heat_Flux_Sensor");
		sensorTables.put("Int Temp", "Air_Temp_Sensor");
		sensorTables.put("Ext Temp", "External_Temp_Sensor");
	}
	
	public void insertSensorIntoTable(Sensor sensor) throws SQLException {
		String sensorName = sensor.getSensorName();
		System.out.println(sensorName);
		// get DB table name for specific sensor type
		String tableName = sensorTables.get(sensorName);
		System.out.println("Table name: " + tableName);
		
		String sqlQueryRemainder = "";
		if (sensorName.equals("heat_flux1")) {
			sqlQueryRemainder = " ,Heat_Flux_Data, Air_Temp_Data, Surface_Temp_Data) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		} else if (sensorName.equals("Ext Temp")) {
			sqlQueryRemainder = ", Surface_Temp_Data, Air_Temp_Data) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
		} else if (sensorName.equals("Int Temp")) {
			sqlQueryRemainder = ", Air_Temp_Data) "
					+ "VALUES (?, ?, ?, ?, ?)";
		}
		System.out.println(sqlQueryRemainder);
		// SQL for columns which all sensors share
		String commonSensorColumnsSQL = "(Sensor_ID, Sensor_Type, Sensor_Name, Time_Stamp";
		// glue the SQL togetha m8
		String sqlQuery = "INSERT INTO " + tableName + commonSensorColumnsSQL + sqlQueryRemainder;
		PreparedStatement ps = connection.prepareStatement(sqlQuery);
		ps.setInt(1, sensor.getSensorID());
		ps.setString(2, sensor.getSensorType());
		ps.setString(3, sensorName);
		ps.setTimestamp(4, sensor.getTimestamp());
		
		// repeating ifs, and some dirty type casting, fix later
		if (sensorName.equals("heat_flux1")) {
			HeatFluxSensor s = ((HeatFluxSensor)sensor);
			ps.setFloat(5, s.getHeatFluxTemp());
			ps.setFloat(6, s.getInternalAirTemp());
			ps.setFloat(7, s.getInternalWallSurfaceTemp());
		} else if (sensorName.equals("Ext Temp")) {
			ExternalTempSensor s = ((ExternalTempSensor)sensor);
			ps.setFloat(5, s.getExternalSurfaceTemp());
			ps.setFloat(6, s.getExternalAirTemp());
		} else if (sensorName.equals("Int Temp")) {
			AirTempSensor s = ((AirTempSensor)sensor);
			ps.setFloat(5, s.getAirTemp());
		}
		System.out.println(ps.toString());
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