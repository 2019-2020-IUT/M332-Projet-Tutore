package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Generate {

	private static String path = "grades";
	BufferedReader csvReader;
	private static ArrayList<CSV> csvList = new ArrayList<CSV>();

	public static void start() {
		createFile();
	}
	
	public static void start(String p) {
		createFile(p);
	}
	
	public static void addList(String n, String g) {
		CSV o = new CSV(n, g);
		csvList.add(o);
	}

	public static void createFile(String p) {
		SetPath(p);
		createFile();
	}

	public static void createFile() {
		try (PrintWriter writer = new PrintWriter(new File(path + ".csv"))) {

			StringBuilder sb = new StringBuilder();
			sb.append("Student number");
			sb.append(',');
			sb.append("Grade");
			sb.append('\n');

			writer.write(sb.toString());
			
			for (int i = 0; i < csvList.size(); i++) {

					sb.delete(0, sb.length());
					sb.append(csvList.get(i).getStudentNumber());
					sb.append("; ");
					sb.append(csvList.get(i).getGrade());

					writer.write(sb + System.getProperty("line.separator"));
					
			}
			System.out.println("Create File Done!");

		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	public String getPath() {
		return Generate.path;
	}

	public static void SetPath(String s) {
		path = s;
	}

	public ArrayList<CSV> getCsvList() {
		return csvList;
	}

	public void setCsvList(ArrayList<CSV> csvList) {
		Generate.csvList = csvList;
	}

}
