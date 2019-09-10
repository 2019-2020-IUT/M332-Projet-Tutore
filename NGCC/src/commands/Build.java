package commands;
import java.util.concurrent.Callable;

//import picocli.CommandLine;
import picocli.CommandLine.*;


@Command(
	name = "ngcc",
	version = "Version 1.0",
	sortOptions = false,
	usageHelpWidth = 60,
	header = " --  Nicely Generated and Corrected Copies  -- \n",
	footer = "\n Provided by IUT Info Nice S3T-G4",
	description = "description"		
)


public class Build implements Callable <Void> {
	
	@Option(names= {"-b","--build"}, arity = "0", order = 1, description = "build mode")
	boolean build;
	
	@Option(names= {"-u"}, arity = "1", order = 2, description = "update mode")
	int step;
	
	@Option(names= {"-v"}, arity = "0..1", defaultValue = "1", order = 3, description ="verbose mode")
	int vb_level;
	
	@Option(names= {"-a"}, arity = "1", order = 4, defaultValue = "answer-sheet.pdf", description ="answer")
	String answer_name;
	
	@Parameters(arity = "0..1", defaultValue = "./source.txt", description ="source path")
	String source_path;
	
	

	@Override
	public Void call() throws Exception {
		if(build) {
			System.out.println("Build mode activated ...");
			System.out.println("Update : "+step);
			System.out.println("Verbose : "+vb_level);
			System.out.println("Answer : "+answer_name);
			System.out.println("Source : "+source_path);
		}
		
			return null;
		
	}
}
