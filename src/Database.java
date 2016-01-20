
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import sensors.*;

public class Database {
	static Connection connection;

	public Database() {
		String DRIVER_CLASS = "com.mysql.jdbc.Driver";
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
}