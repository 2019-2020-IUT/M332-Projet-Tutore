package commands;
import java.util.concurrent.Callable;

//import picocli.CommandLine;
import picocli.CommandLine.*;


@Command(
	name = "analyse",
	version = "Version 1.0",
	sortOptions = false,
	usageHelpWidth = 60,
	header = " --  Nicely Generated and Corrected Copies  -- \n",
	footer = "\n Provided by IUT Info Nice S3T-G4",
	description = "description"		
)


public class Analyse implements Callable <Void> {
	
	@Option(names= {"-a","--analyse"}, arity = "0", order = 1, description = "analyse mode")
	boolean analyse;
	
	@Option(names= {"-u"}, arity = "1", order = 2, description = "update mode")
	int step;
	
	@Option(names= {"-v"}, arity = "0..*", order = 3, defaultValue = "1", description ="verbose mode")
	int vb_level;
	
	@Option(names= {"-d"}, arity = "1", order = 4, defaultValue = "copies", description ="directory")
	String directory;
	
	@Parameters(arity = "0..1", defaultValue = "./source.txt", description ="source path")
	String source_path;
	
	


	@Override
	public Void call() throws Exception {
		if(analyse) {
			System.out.println("Analyse mode activated ...");
			System.out.println("Update : "+step);
			System.out.println("Verbose : "+vb_level);
			System.out.println("Directory : "+directory);			
			System.out.println("Source : "+source_path);
		}
		
			return null;
		
	}
}
