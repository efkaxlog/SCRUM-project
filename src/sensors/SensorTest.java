package sensors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class SensorTest {

	private Sensor sensor1;

	@Before
	public void setUp() {
		sensor1 = new TemperatureSensor(1, "temperature", "TemperatureSensor",
				12.5f, 33.3f);

	}

	public void testSensor() {
		assertNotNull("sensor Details", sensor1);
	}

	@Test
	public void testGetSensorID() {
		sensor1.setSensorID(1);
		assertEquals("Sensor ID", 1, sensor1.getSensorID());

	}

	@Test
	public void testSetSensorID() {
		sensor1.setSensorID(6);
		assertEquals("Sensor ID", 6, sensor1.getSensorID());
	}

	@Test
	public void testGetSensorName() {
		sensor1.setSensorName("temperature");
		assertEquals("Temperature", "temperature", sensor1.getSensorName());

	}

	@Test
	public void testSetSensorName() {
		sensor1.setSensorName("New temperature");
		assertEquals("Temperature", "New temperature", sensor1.getSensorName());
	}

	@Test
	public void testGetSensorType() {
		sensor1.setSensorType("TemperatureSensor");
		assertEquals("Sensor Type", "TemperatureSensor",
				sensor1.getSensorType());

	}

	@Test
	public void testSetSensorType() {
		sensor1.setSensorType("TemperatureSensor");
		assertEquals("New Sensor Type", "TemperatureSensor",
				sensor1.getSensorType());
	}

}
