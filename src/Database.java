
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sensors.AirTempSensor;
import sensors.ExternalTempSensor;
import sensors.HeatFluxSensor;
import sensors.Sensor;

public class Database {
	static Connection connection;

	public Database() {
		String DRIVER_CLASS = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://helios.csesalford.com:3306/sta302_spat";
		//helios credentials
		String url = "jdbc:mysql://helios.csesalford.com/sta302_spat";
		String user = "sta302";
		String pass = "Fosters2003";
		try {
			Class.forName(DRIVER_CLASS);
			connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.exit(1);
		} catch (Exception exception) {
			System.err.println("Cannot load " + DRIVER_CLASS);
			System.exit(1);
		}
	}

	public void addSensor(Sensor sensor) {
		try {
			if (sensor instanceof ExternalTempSensor) {
				insertIntoExtTemp((ExternalTempSensor) sensor);
			} else if (sensor instanceof AirTempSensor) {
				insertIntoAirTemp((AirTempSensor) sensor);
			} else if (sensor instanceof HeatFluxSensor) {
				insertIntoHeatFlux((HeatFluxSensor) sensor);
			}
			System.out.println(sensor.getSensorName() + "added");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertIntoHeatFlux(HeatFluxSensor sensor) throws SQLException {
		String sql = "INSERT INTO Heat_Flux_Sensor VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, sensor.getSensorID());
		ps.setString(2, sensor.getSensorType());
		ps.setString(3, sensor.getSensorName());
		ps.setTimestamp(4, sensor.getTimestamp());
		ps.setFloat(5, sensor.getHeatFluxTemp());
		ps.setFloat(6, sensor.getInternalAirTemp());
		ps.setFloat(7, sensor.getInternalWallSurfaceTemp());
		ps.executeUpdate();
	}

	private void insertIntoExtTemp(ExternalTempSensor sensor) throws SQLException {
		String sql = "INSERT INTO External_Temp_Sensor VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, sensor.getSensorID());
		ps.setString(2, sensor.getSensorType());
		ps.setString(3, sensor.getSensorName());
		ps.setTimestamp(4, sensor.getTimestamp());
		ps.setFloat(5, sensor.getExternalSurfaceTemp());
		ps.setFloat(6, sensor.getExternalAirTemp());
		ps.executeUpdate();
	}

	private void insertIntoAirTemp(AirTempSensor sensor) throws SQLException {
		String sql = "INSERT INTO Air_Temp_Sensor(?, ?, ?, ?, ?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, sensor.getSensorID());
		ps.setString(2, sensor.getSensorType());
		ps.setString(3, sensor.getSensorName());
		ps.setTimestamp(4, sensor.getTimestamp());
		ps.setFloat(5, sensor.getAirTemp());
		ps.executeUpdate();
	}
	
	public ArrayList selectFromHeatFlux() throws SQLException
	{
		String sql = "SELECT * FROM Heat_Flux_Sensor";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<String> heatFluxArray = new ArrayList<String>();
		while (rs.next()) 
		{
			String row = rs.getString("Sensor_ID") + "," + rs.getString("Sensor_Name")
			+ "," + rs.getString("Sensor_Type") + "," + rs.getString("Time_Stamp")
			+ "," + rs.getString("Heat_Flux_Data")+ "," + rs.getString("Surface_Temp_Data")
			+ "," + rs.getString("Air_Temp_Data");
			/*HeatFluxSensor sensor = new HeatFluxSensor
					(rs.getString("Sensor_ID"), rs.getString("Sensor_Name"), 
							rs.getString("Sensor_Type"), rs.getTimestamp("Time_Stamp"), 
							rs.getFloat("Heat_Flux_Data"), rs.getFloat("Surface_Temp_Data"),
							rs.getFloat("Air_Temp_Data"));*/
			heatFluxArray.add(row);
		}
		return heatFluxArray;
	}
	
	public ArrayList selectFromExternalTemp() throws SQLException
	{
		String sql = "SELECT * FROM External_Temp_Sensor";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<String> extTempArray = new ArrayList<String>();
		while(rs.next())
		{
			String row = rs.getString("Sensor_ID") + "," + rs.getString("Sensor_Name")
			+ "," + rs.getString("Sensor_Type") + "," + rs.getString("Time_Stamp")
			+ "," + rs.getString("Surface_Temp_Data") + "," + rs.getString("Air_Temp_Data");
			extTempArray.add(row);
		}
		return extTempArray;
	}
	
	public ArrayList selectFromAirTemp() throws SQLException
	{
		String sql = "SELECT * FROM Air_Temp_Sensor";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<String> airTempArray = new ArrayList<String>();
		while(rs.next())
		{
			String row = rs.getString("Sensor_ID") + "," + rs.getString("Sensor_Name")
			+ "," + rs.getString("Sensor_Type") + "," + rs.getString("Time_Stamp")
			+ "," + "," + rs.getString("Air_Temp_Data");
			airTempArray.add(row);
		}
		return airTempArray;
	}
}