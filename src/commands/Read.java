package commands;

import java.io.File;
import java.io.PrintStream;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import config.Config;
import csv.GenerateCSV;
import ocr.GestionnaireCopies;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

@SuppressWarnings("unused")

@Command(name = "-r", version = "Version 1.0", sortOptions = false, usageHelpWidth = 60, header = "Read command - Automatic entry", footer = "", description = "\nAnalyzes all scanned copies (pdf files) provided to recognize student's name and mark.\n")

public class Read implements Callable<Void> {

	public Logger logger = LogManager.getLogger(Read.class);

	@Spec
	Model.CommandSpec spec;

	@Option(names = { "-help" }, arity = "0", order = 1, description = "command help")
	boolean help;

	@Option(names = { "-u" }, arity = "1", order = 2, description = "update mode")
	int step;

	@Option(names = { "-v" }, arity = "0..*", order = 3, defaultValue = "1", description = "verbose mode")
	public int vb_level;

	@Option(names = { "-d" }, arity = "1", order = 4, defaultValue = "copies", description = "directory")
	String directory_name;

	@Option(names = { "-o" }, arity = "1", order = 5, defaultValue = "result.csv", description = "result")
	String result_name;

	@Parameters(arity = "0..1", defaultValue = "config/source.txt", description = "source path")
	String source_path;

	// Flux de sortie si nécéssaire
	public Read(PrintStream out) {
	}

	// Check de l'extension csv (à améliorer)
	public boolean isCsv(String file) {
		return file.endsWith(".csv");

	}

	@Override
	public Void call() throws Exception {

		// Niveau de debug selon verbosite

		if ((this.vb_level >= 0) && (this.vb_level <= 2)) {
			Configurator.setLevel("commands", Level.FATAL);
			Configurator.setLevel("csv", Level.FATAL);
		} else if ((this.vb_level >= 3) && (this.vb_level <= 4)) {
			Configurator.setLevel("commands", Level.ERROR);
			Configurator.setLevel("csv", Level.ERROR);
		} else if ((this.vb_level >= 5) && (this.vb_level <= 6)) {
			Configurator.setLevel("commands", Level.WARN);
			Configurator.setLevel("csv", Level.WARN);
		} else if ((this.vb_level >= 7) && (this.vb_level <= 8)) {
			Configurator.setLevel("commands", Level.INFO);
			Configurator.setLevel("csv", Level.INFO);
		} else {
			Configurator.setLevel("commands", Level.DEBUG);
			Configurator.setLevel("csv", Level.DEBUG);
		}

		// Help genere de la commande

		if (this.help) {
			this.logger.info("Help for 'read' command executed");
			CommandLine.usage(this.spec, System.out);
		} else {

//************* Progress Bar Prototype **************	

//		ProgressBar bar = new ProgressBar();
//
//        System.out.println("\nReading pdf ...\n");
//
//        bar.update(0, 1000);
//        for(int i=0;i<1000;i++) {
//                        // do something!
//        	//imgList.lenght * temps estimé par img
//            for(int j=0;j<10000000;j++)
//                for(int p=0;p<10000000;p++);
//            // update the progress bar
//            bar.update(i, 1000); // On ajoute 100/imgList.length à chaque temps estimé par image
//        }
//
//        System.out.println("\nCopies correction succeed !\n");

//***************************************************

			// Log des parametres

			this.logger.info("Read mode activated");
			this.logger.debug("Update : " + this.step);
			this.logger.debug("Verbose : " + this.vb_level);
			this.logger.debug("Directory : " + this.directory_name);
			this.logger.debug("Result : " + this.result_name);
			this.logger.debug("Source : " + this.source_path);

			if (!this.isCsv(this.result_name)) {
				this.result_name = this.result_name + ".csv";
				this.logger.info("Result file name changed to '" + this.result_name + "'");
			}

			Config config = new Config(this.source_path); // Initialise le fichier de configuration selon le chemin
															// donné
			config.readConfig();

			String filePath = new File("").getAbsolutePath();

			// Instantie l'OCR
			GestionnaireCopies ocr = new GestionnaireCopies(this.directory_name);

			this.logger.debug("CSV initialized with : " + ocr.createHashMapforCSV() + " , "
					+ config.getParam().get("Code") + " , " + this.result_name);

			GenerateCSV csv = new GenerateCSV(ocr.createHashMapforCSV(), config.getParam().get("Code"),
					config.getParam().get("MarkFormat"), this.result_name);

			csv.createFile(); // Génère le fichier csv à partir de la HMap retournée par l'OCR

			// Done !

		}
		return null;
	}
}
