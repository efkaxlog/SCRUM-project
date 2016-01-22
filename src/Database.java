
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import sensors.AirTempSensor;
import sensors.ExternalTempSensor;
import sensors.HeatFluxSensor;
import sensors.Sensor;

public class Database {
	static Connection connection;
	HashMap<String, String> sensorTables = new HashMap<String, String>();

	public Database() {
		String DRIVER_CLASS = "com.mysql.jdbc.Driver";
		// helios credentials
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

		// populates sensorTables hashMap with DB table names
		setupHashMapTables();
	}

	/**
	 * populates sensorTables with names of the tables in the DB
	 */
	private void setupHashMapTables() {
		sensorTables.put("heat_flux1", "Heat_Flux_Sensor");
		sensorTables.put("Int Temp", "Air_Temp_Sensor");
		sensorTables.put("Ext Temp", "External_Temp_Sensor");
	}

	/**
	 * 
	 * @return String SQL generated for a particular sensor
	 */
	private String generateInsertSQL(Sensor sensor) {
		String sensorName = sensor.getSensorName();
		// get DB table name for specific sensor type
		String tableName = sensorTables.get(sensorName);
		// SQL for columns which all sensors share
		String commonSensorColumnsSQL = "(Sensor_ID, Sensor_Type, Sensor_Name, timestamp";
		String sqlQueryRemainder = "";
		if (sensorName.equals("heat_flux1")) {
			sqlQueryRemainder = " ,Heat_Flux_Data, Air_Temp_Data, Surface_Temp_Data) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";
		} else if (sensorName.equals("Ext Temp")) {
			sqlQueryRemainder = ", Surface_Temp_Data, Air_Temp_Data) " + "VALUES (?, ?, ?, ?, ?, ?)";
		} else if (sensorName.equals("Int Temp")) {
			sqlQueryRemainder = ", Air_Temp_Data) " + "VALUES (?, ?, ?, ?, ?)";
		}
		// glue the SQL togetha m8
		String sqlQuery = "INSERT INTO " + tableName + commonSensorColumnsSQL + sqlQueryRemainder;
		return sqlQuery;
	}

	public void insertSensorIntoTable(Sensor sensor) throws SQLException {
		String sqlQuery = generateInsertSQL(sensor);
		String sensorName = sensor.getSensorName();
		PreparedStatement ps = connection.prepareStatement(sqlQuery);
		ps.setInt(1, sensor.getSensorID());
		ps.setString(2, sensor.getSensorType());
		ps.setString(3, sensorName);
		ps.setTimestamp(4, sensor.getTimestamp());

		// repeating ifs, and some dirty type casting, fix later
		if (sensorName.equals("heat_flux1")) {
			HeatFluxSensor s = ((HeatFluxSensor) sensor);
			ps.setFloat(5, s.getHeatFluxTemp());
			ps.setFloat(6, s.getInternalAirTemp());
			ps.setFloat(7, s.getInternalWallSurfaceTemp());
		} else if (sensorName.equals("Ext Temp")) {
			ExternalTempSensor s = ((ExternalTempSensor) sensor);
			ps.setFloat(5, s.getExternalSurfaceTemp());
			ps.setFloat(6, s.getExternalAirTemp());
		} else if (sensorName.equals("Int Temp")) {
			AirTempSensor s = ((AirTempSensor) sensor);
			ps.setFloat(5, s.getAirTemp());
		}
		ps.executeUpdate();
	}

	public void addSensorStrings(String tableName, ArrayList<String> sensorStrings) throws SQLException {
		if (tableName.equals("all")) {
			Collection<String> c = sensorTables.values();
			Iterator<String> itr = c.iterator();
			while (itr.hasNext()) {
				String nextTable = (String) itr.next();
				addSensorsStringsFromTable(nextTable, sensorStrings);
			}
		} else {
			addSensorsStringsFromTable(tableName, sensorStrings);
		}
	}

	/**
	 * 
	 * @param tableName
	 * @throws SQLException
	 *             adds delimited, ready for making sensor objects with sensor
	 *             data strings to an Arraylist passed as an argument
	 */
	public void addSensorsStringsFromTable(String tableName, ArrayList<String> sensorStrings) throws SQLException {
		String sql = "SELECT * FROM " + tableName;
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		// for storing table column names
		ArrayList<String> columns = new ArrayList<String>();
		ResultSetMetaData metadata = rs.getMetaData();
		int columnCount = metadata.getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			columns.add(metadata.getColumnName(i));
		}
		while (rs.next()) {
			StringBuilder sb = new StringBuilder();
			String sensorDataString = "";
			for (String columnName : columns) {
				// don't want table primary key
				if (columnName.equals("ID")) {
					continue;
				}
				String value = rs.getString(columnName);
				sensorDataString = sb.append(value + ",").toString();
			}
			// remove last character because it's a comma
			sensorDataString = Utilities.removeLastChar(sensorDataString);
			sensorStrings.add(sensorDataString);

		}

	}
}