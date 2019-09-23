package csv;

public class CSV {

	private String studentNumber; // we put here the idendificator number for each students
	private double grade; // we put here the grade of each copies.

	public CSV() {
		this.studentNumber = null;
		this.grade = .0;
	}

	public CSV(String s, double d) {
		if (checkStudentNumber(s)) {
			this.studentNumber = s;
			this.grade = d;
		} else {
			System.err.println(
					"The student number is not correct, please enter a valid number (from 1 to 16 characters)");
		}
	}

	public CSV(String s, String d) {
		if (checkStudentNumber(s)) {
			this.studentNumber = s;
			this.grade = Double.parseDouble(d);
		} else {
			System.err.println(
					"The student number is not correct, please enter a valid number (from 1 to 16 characters)");
		}
	}

	public static boolean checkStudentNumber(String s) {
		int i = 0;
		boolean retour = false;
		if (s.length() >= 1 && s.length() <= 16) {
			while (i < s.length())
				{
					   if (s.charAt(i) == '0' || s.charAt(i) == '1' || s.charAt(i) == '2' || s.charAt(i) == '3'
						|| s.charAt(i) == '4' || s.charAt(i) == '5' || s.charAt(i) == '6' || s.charAt(i) == '7'
						|| s.charAt(i) == '8' || s.charAt(i) == '9')
					   		{
						   		retour = true;
					   		} else {
					   			retour = false;
					   			break;
					   		}
					   i++;
				}
		}
		return retour;
	}

	@Override
	public String toString() {
		return "The student " + getStudentNumber() + " gets a " + getGrade() + " grade !";
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String s) {
		this.studentNumber = s;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double d) {
		this.grade = d;
	}
}
