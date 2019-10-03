package csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

public class GenerateCSV {
	
	HashMap<String,String> etudiants;
	int numLength;
	String path = "../export";
	
	public GenerateCSV(HashMap<String,String> map, String length, String pth) {
		this.etudiants = map;
		this.numLength = Integer.parseInt(length);
		this.path = path+"/"+pth;
	}
	
	
	// TO DO : exploiter dans generation
	public boolean isValid(String s) {
		int i = 0;
		if (s.length() == this.numLength) {
			while (i < s.length()) {
				
				int nb = Character.getNumericValue(s.charAt(i));
				
				if (nb <= 0 || nb >= 9) {
					System.err.println("Student id's characters are not recognized");
					return false;
				}
				else {
					i++;
				}
				 	
			}
			return true;
		}
		else {
			System.err.println("Student id's length is not correct");
			return false;
		}
	}
	

	
	
	
	public void createFile() {
		try (PrintWriter writer = new PrintWriter(new File(this.path))) {

			StringBuilder sb = new StringBuilder();
			sb.append("Student number");
			sb.append(';');
			sb.append("Grade");
			sb.append(System.getProperty("line.separator"));

			writer.write(sb.toString());
			
			for (String etud : this.etudiants.keySet()) {

					writer.write(etud+";"+etudiants.get(etud)+System.getProperty("line.separator"));
					
			}
			System.out.println("Create File Done!");

		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}
	

}
