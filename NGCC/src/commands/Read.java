package commands;

import picocli.CommandLine;
import picocli.CommandLine.*;
import progressbar.ProgressBar;

import java.io.PrintStream;
import java.util.concurrent.Callable;



@SuppressWarnings("unused")

@Command(
	name = "-r",
	version = "Version 1.0",
	sortOptions = false,
	usageHelpWidth = 60,
	header = "Read command",
	footer = "\n Provided by IUT Info Nice S3T-G4",
	description = "description"		
)


public class Read implements Callable <Void> {
	
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
	
	
	public Read(PrintStream out) {
		
		
	}

	public boolean isCsv(String file) {
		return file.endsWith(".csv");
		
	}

	@Override
	public Void call() throws Exception {
		
		
		ProgressBar bar = new ProgressBar();
		
        System.out.println("\nReading pdf ...\n");

        bar.update(0, 1000);
        for(int i=0;i<1000;i++) {
                        // do something!
            for(int j=0;j<10000000;j++)
                for(int p=0;p<10000000;p++);
            // update the progress bar
            bar.update(i, 1000);
        }
        
        System.out.println("\nCopies correction succeed !\n");
		
		
			System.out.println("\nUpdate : "+step               +"\n"+
							   "Verbose : "+vb_level          +"\n"+
							   "Directory : "+directory_name  +"\n"+
							   "Result : "+result_name        +"\n"+
							   "Source : "+source_path        +"\n");
		
			return null;	
	}
}
