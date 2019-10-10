package csv;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestCSV {
	
	Map<String,String> map = new HashMap<String,String>();
	String length = "8";
	String path = "../export/result.csv";
	GenerateCSV csv = new GenerateCSV(map,length,path);
	
	@BeforeEach
	void setUp() {
		map = new HashMap<String,String>();
		length = "8";
		path = "../export/result.csv";
	}
	
	@Test
	void testNotNull() {
		
		map.put("21705239", "17");
		csv = new GenerateCSV(map,length,path);
		
		assertFalse(csv == null);	
	}
	
	@Test
	void testValid() {
	
		map.put("21435712", "9");
		map.put("21705239", "17");
		csv = new GenerateCSV(map,length,path);
		
		for (String etud : csv.etudiants.keySet()) {
			assertTrue(csv.isValid(etud));
		}
		
	}
	
	
	@Test
	void testNotValid() {
	
		map.put("21435", "9");
		map.put("1705239", "17");
		csv = new GenerateCSV(map,length,path);
		
		for (String etud : csv.etudiants.keySet()) {
			assertFalse(csv.isValid(etud));
		}
		
	}
	
	
	@Test
	void testGeneration() {
		
	}

}