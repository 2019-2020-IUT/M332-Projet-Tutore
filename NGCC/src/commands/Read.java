package commands;
import java.util.concurrent.Callable;

//import picocli.CommandLine;
import picocli.CommandLine.*;


@Command(
	name = "read",
	version = "Version 1.0",
	sortOptions = false,
	usageHelpWidth = 60,
	header = " --  Nicely Generated and Corrected Copies  -- \n",
	footer = "\n Provided by IUT Info Nice S3T-G4",
	description = "description"		
)


public class Read implements Callable <Void> {
	
	@Option(names= {"-r","--read"}, arity = "0", order = 1, description = "read mode")
	boolean read;
	
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
	
	
	public boolean isCsv(String file) {
		return file.endsWith(".csv");
		
	}

	@Override
	public Void call() throws Exception {
		if(read) {
			System.out.println("Read mode activated ...");
			System.out.println("Update : "+step);
			System.out.println("Verbose : "+vb_level);
			System.out.println("Directory : "+directory_name);
			
			if (isCsv(result_name)) {
				System.out.println("Result : "+result_name);
			}
			else {
				System.out.println("The specified for the result file is invalid");
			}
			
			System.out.println("Source : "+source_path);
		}
		
			return null;
		
	}
}
