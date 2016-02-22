package sensors;

import junit.framework.TestCase;


import org.junit.Before;
import org.junit.Test;

public class HeatFluxSensorTest extends TestCase {
	private HeatFluxSensor hfs;
	
	@Before
	  public void setUp()
	  {
	   hfs= new HeatFluxSensor(3, "Heat Flux Data", "HeatFluxSensor",12.5f,19.8f,26.6f);
	  }

	@Test
	public void testHeatFluxSensor() {
		assertNotNull("Heat Flux Sensor Details",hfs);
		
	}

	@Test
	public void testGetHeatFluxData() {
		hfs.setHeatFluxData(12.5f);
		assertEquals("Heat Flux Temperature",12.5f, hfs.getHeatFluxData());

	}

	@Test
	public void testSetHeatFluxData() {
		hfs.setHeatFluxData(13.5f);
		assertEquals("Heat Flux Temperature",13.5f, hfs.getHeatFluxData());
	}

	@Test
	public void testGetSurfaceTemp() {
		hfs.setSurfaceTemp(19.8f);
		assertEquals("Surface Teamperature doesn't match expected data :",19.8f,
				hfs.getSurfaceTemp());
	}

	@Test
	public void testSetSurfaceTemp() {
		hfs.setSurfaceTemp(20.8f);
		assertEquals("Surface Teamperature doesn't match expected data :",20.8f,
				hfs.getSurfaceTemp());
		}

	@Test
	public void testGetAirTemp() {
		hfs.setAirTemp(26.6f);
		assertEquals("Air Teamperature doesn't match expected data :",26.6f,
				hfs.getAirTemp());
		
	}

	@Test
	public void testSetAirTemp() {
		hfs.setAirTemp(12.5f);
		assertEquals("Air Teamperature doesn't match expected data :",12.5f,
				hfs.getAirTemp());
		}

}
