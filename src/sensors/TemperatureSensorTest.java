package sensors;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class TemperatureSensorTest extends TestCase {

	private TemperatureSensor ats1;

	@Before
	public void setUp() {
		ats1 = new TemperatureSensor(1, "Some temperature",
				"TemperatureSensor", 20.5f, 15.3f);
	}

	@Test
	public void testTemperatureSensor() {
		assertNotNull("Details", ats1);
	}

	@Test
	public void testGetAirTemp() {
		ats1.setAirTemp(20.5f);
		assertEquals("Air Temperature", 20.5f, ats1.getAirTemp());

	}

	@Test
	public void testSetAirTemp() {
		ats1.setAirTemp(11.5f);
		assertEquals("Air temperature", 11.5f, ats1.getAirTemp());

	}

	@Test
	public void testGetSurfaceTemp() {
		ats1.setSurfaceTemp(15.3f);
		assertEquals("Surface temperature", 15.3f, ats1.getSurfaceTemp());
	}

	@Test
	public void testSetSurfaceTemp() {
		ats1.setSurfaceTemp(12.3f);
		assertEquals("Surface temperature", 12.3f, ats1.getSurfaceTemp());

	}

	@Test
	public void testSensor() {
		assertNotNull("Different sensor Details", ats1);

	}

	@Test
	public void testGetSensorID() {
		ats1.setSensorID(1);
		assertEquals(1, ats1.getSensorID());
	}

	@Test
	public void testSetSensorID() {
		ats1.setSensorID(2);
		assertEquals(2, ats1.getSensorID());
	}

	@Test
	public void testGetSensorName() {
		ats1.setSensorName("Some Temperature");
		assertEquals("Some Temperature", ats1.getSensorName());
	}

	@Test
	public void testSetSensorName() {
		ats1.setSensorName("new temperature");
		assertEquals("new temperature", ats1.getSensorName());
	}

	@Test
	public void testGetSensorType() {
		ats1.setSensorType("TemperatureSensor");
		assertEquals("Sensor Type", "TemperatureSensor", ats1.getSensorType());
	}

	@Test
	public void testSetSensorType() {
		ats1.setSensorType("TemperatureSensor");
		assertEquals("Sensor Type", "TemperatureSensor", ats1.getSensorType());
	}

}
