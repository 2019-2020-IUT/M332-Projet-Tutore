package csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenerateCSV {

	Map<String, String> etudiants;
	int numLength;
	String path = "../export";
	Logger logger = LogManager.getLogger(GenerateCSV.class);

	public GenerateCSV(Map<String, String> map, String length, String pth) {
		this.etudiants = map;
		this.numLength = Integer.parseInt(length);
		this.path = path + "/" + pth;
	}

	// Teste validité du numero etudiant (selon param de la config passé :
	// numLength)

	public boolean isValid(String s) {
		int i = 0;
		logger.debug("Checking string validity");
		if (s.length() == this.numLength) {
			while (i < s.length()) {

				int nb = Character.getNumericValue(s.charAt(i));

				if (nb <= 0 || nb >= 9) {
					logger.fatal("Student id's characters are not recognized");
					return false;
				} else {
					i++;
				}

			}
			logger.debug("String validity ok");
			return true;
		} else {
			logger.fatal("Student id's length is not correct");
			return false;
		}
	}

	public void createFile() {
		try (PrintWriter writer = new PrintWriter(new File(this.path))) {

			logger.info("Creating csv file");
			StringBuilder sb = new StringBuilder();
			sb.append("Student number");
			sb.append(';');
			sb.append("Grade");
			sb.append(System.getProperty("line.separator"));

			writer.write(sb.toString());

			if (!etudiants.isEmpty()) {

				for (String etud : this.etudiants.keySet()) {

					// Si etudiant HashMap est null, pas ecrit
					if (etud != null) {

						//if (this.isValid(etud)) {
							writer.write(etud + ";" + etudiants.get(etud) + System.getProperty("line.separator"));
						//}
//						else {
//							logger.debug("Invalid not added to csv");
//						}

					} else {

						logger.debug("Null id not added to csv");
					}

				}

				logger.info("File creation succeed");
			} else {
				logger.fatal("Students list for csv generation is empty");
			}

		} catch (FileNotFoundException e) {
			logger.fatal(e.getMessage());
		}
	}

}
