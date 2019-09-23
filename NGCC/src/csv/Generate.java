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

	public static void main(String[] args) {
		addList("1234", "6.");
		addList("96543", "16.8");
		addList("12345678910", "20.");
		start();
	}

	public static void start() {
		createFile();
		generateFile();
	}
	
	public static void start(String p) {
		createFile(p);
		generateFile();
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

			System.out.println("Create File Done!");

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void generateFile() {
		System.out.println(csvList.size());
		System.out.println(csvList.get(0));
		System.out.println(csvList.get(1));
		System.out.println(csvList.get(2));
		for (int i = 0; i < csvList.size(); i++) {
			try (PrintWriter writer = new PrintWriter(new File(path + ".csv"))) {

				StringBuilder sb = new StringBuilder();
				sb.append(csvList.get(i).getStudentNumber());
				sb.append(',');
				sb.append(csvList.get(i).getGrade());
				sb.append('\n');

				writer.println((csvList.get(i).getStudentNumber() + ',' + csvList.get(i).getGrade()).toString()+ System.getProperty("line.separator"));
				
				System.out.println("Write File Done!");

			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
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
