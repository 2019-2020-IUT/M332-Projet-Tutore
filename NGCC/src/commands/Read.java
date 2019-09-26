package commands;

import picocli.CommandLine;
import picocli.CommandLine.*;
import progressbar.ProgressBar;

import java.io.PrintStream;
import java.util.concurrent.Callable;

import config.Config;
import ocr_orm.ControleurOCR;



@SuppressWarnings("unused")

@Command(
	name = "-r",
	version = "Version 1.0",
	sortOptions = false,
	usageHelpWidth = 60,
	header = "Read command - Automatic entry",
	footer = "",
	description = "\nAnalyzes all scanned copies (pdf files) provided to recognize student's name and mark.\n"		
)




public class Read implements Callable <Void> {
	
	@Spec
	Model.CommandSpec spec;
	
	@Option(names= {"-help"}, arity = "0", order = 1, description = "command help")
	boolean help;
	
	@Option(names= {"-u"}, arity = "1", order = 2, description = "update mode")
	int step;
	
	@Option(names= {"-v"}, arity = "0..*", order = 3, defaultValue = "1", description ="verbose mode")
	int vb_level;
	
	@Option(names= {"-d"}, arity = "1", order = 4, defaultValue = "copies", description ="directory")
	String directory_name;
	
	@Option(names= {"-o"}, arity = "1", order = 5, defaultValue = "result.csv", description ="result")
	String result_name;
	
	@Parameters(arity = "0..1", defaultValue = "./source.txt", description ="source path")
	String source_path;
	
	
	// Flux de sortie si nécéssaire
	public Read(PrintStream out) {
	}

	
//	public boolean isCsv(String file) {
//		return file.endsWith(".csv");
//		
//	}
	

	@Override
	public Void call() throws Exception {
		
		if(help) {
			CommandLine.usage(this.spec, System.out);
		}
		else {
		
			
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
		
			
			System.out.println("\nRead mode activated ...\n");		   // Debug des paramètres à exploiter plus tard
			System.out.println("Update : "+step               +"\n"+
							   "Verbose : "+vb_level            +"\n"+
							   "Directory : "+directory_name    +"\n"+
							   "Result : "+result_name          +"\n"+
							   "Source : "+source_path          +"\n");
		
			
			
//			********VOIR EQUIPE********
			
			
			Config config = new Config(source_path);	//Initialise le fichier de configuration selon le path donné
			ControleurOCR ocr = new ControleurOCR();	//Initialise le controle de l'OCR
			
			
			ocr.setConfig(config); 	//Configure l'OCR en fonction du fichier de configuration initialisé
			
			
			GenerateCSV.createFile(ocr.getNumNote(directory_name),result_name);  //Génère le fichier csv à partir de la HMap retournée par l'OCR
			
			//Done !
			
		}
			return null;	
	}
}
