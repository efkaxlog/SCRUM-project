package mainPackage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


import sensors.TemperatureSensor;

public class UtilitiesTest {
	  
	@Test
	public void testUtilities() {

	}

	@Test
	public void testRemoveLastChar() {
		new Utilities();
		Utilities.removeLastChar("A");
	}

	
	@Test
	public void testWrite() {
		new Utilities().write(null);
		
	}

}
