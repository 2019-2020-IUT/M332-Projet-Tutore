package csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class TestCSV {

	@Test
	public void createNullCSV() {
		//We check if a CSV object cannot be created !
		CSV c = new CSV();
		assertEquals(null, c.getStudentNumber());
		assertEquals(.0, c.getGrade());
	}
	
	@Test
	public void checkFalseStudentnumber() {
		//We check if the student number is full composed of numeric digits !
		String s = "056498a198";
		assertEquals(false, CSV.checkStudentNumber(s));
	}
	
	@Test
	public void checkStudentNumberTooShort() {
		//We check if the student number is enought long !
		String s = "";
		assertEquals(false, CSV.checkStudentNumber(s));
	}
	
	
	@Test
	public void checkStudentNumberTooLong() {
		//We check if the student number is enought short !
		String s = "012345678910111213141516";
		assertEquals(false, CSV.checkStudentNumber(s));
	}
	
	@Test
	public void checkCorrectStudentNumber() {
		//We check if the student number is correct !
		String s = "123456789";
		assertEquals(true, CSV.checkStudentNumber(s));
	}

}
