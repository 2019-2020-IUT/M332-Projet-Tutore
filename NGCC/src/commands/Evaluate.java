package commands;
import java.util.concurrent.Callable;

//import picocli.CommandLine;
import picocli.CommandLine.*;


@Command(
	name = "evaluate",
	version = "Version 1.0",
	sortOptions = false,
	usageHelpWidth = 60,
	header = " --  Nicely Generated and Corrected Copies  -- \n",
	footer = "\n Provided by IUT Info Nice S3T-G4",
	description = "description"		
)


public class Evaluate implements Callable <Void> {
	
	@Option(names= {"-e","--evaluate"}, arity = "0", order = 1, description = "evaluate mode")
	boolean evaluate;
	
	@Option(names= {"-u"}, arity = "1", order = 2, description = "update mode")
	int step;
	
	@Option(names= {"-v"}, arity = "0..*", order = 3, defaultValue = "1", description ="verbose mode")
	int vb_level;
	
	@Option(names= {"-o"}, arity = "1", order = 4, defaultValue = "result.csv", description ="result")
	String result_name;
	
	@Parameters(arity = "0..1", defaultValue = "./source.txt", description ="source path")
	String source_path;
	
	
	public boolean isCsv(String file) {
		return file.endsWith(".csv");
		
	}

	@Override
	public Void call() throws Exception {
		if(evaluate) {
			System.out.println("Generate mode activated ...");
			System.out.println("Update : "+step);
			System.out.println("Verbose : "+vb_level);
			
			if (isCsv(result_name)) {
				System.out.println("Result : "+result_name);
			}
			else {
				System.out.println("The specified name for the result file is invalid");
				//System.out.println("Result : "+result_name+".csv");
			}
			
			System.out.println("Source : "+source_path);
		}
		
			return null;
		
	}
}
