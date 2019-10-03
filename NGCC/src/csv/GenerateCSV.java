package csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

public class GenerateCSV {
	
	HashMap<String,String> etudiants;
	int numLength;
	String path = "../export";
	
	public GenerateCSV(HashMap<String,String> map, String s, String p) {
		this.etudiants = map;
		this.numLength = Integer.parseInt(s);
		this.path = path+"/"+p;
	}
	
//	public boolean isValid(String s) {
//		int i = 0;
//		if (s.length() == this.numLength) {
//			while (i < s.length())
//				{
//					   if (s.charAt(i) != '0' && s.charAt(i) == '1' || s.charAt(i) == '2' || s.charAt(i) == '3'
//						|| s.charAt(i) == '4' || s.charAt(i) == '5' || s.charAt(i) == '6' || s.charAt(i) == '7'
//						|| s.charAt(i) == '8' || s.charAt(i) == '9')
//					   		{
//						   		return true;
//						   		
//					   		} else {
//					   			return false;
//					   			break;
//					   		}
//					   i++;
//				}
//		}
//		
//		return true;
//	}
	

	
	
	
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
