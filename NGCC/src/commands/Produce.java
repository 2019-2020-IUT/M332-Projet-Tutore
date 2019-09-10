package commands;
import java.util.concurrent.Callable;

//import picocli.CommandLine;
import picocli.CommandLine.*;


@Command(
	name = "produce",
	version = "Version 1.0",
	sortOptions = false,
	usageHelpWidth = 60,
	header = " --  Nicely Generated and Corrected Copies  -- \n",
	footer = "\n Provided by IUT Info Nice S3T-G4",
	description = "description"		
)


public class Produce implements Callable <Void> {
	
	@Option(names= {"-p","--produce"}, arity = "0", order = 1, description = "produce mode")
	boolean produce;
	
	@Option(names= {"-u"}, arity = "1", order = 2, description = "update mode")
	int step;
	
	@Option(names= {"-v"}, arity = "0..*", order = 3, defaultValue = "1", description ="verbose mode")
	int vb_level;
	
	@Option(names= {"-c"}, arity = "1", order = 4, defaultValue = "corrected-sheet.pdf", description ="sheet")
	String sheet_name;
	
	@Parameters(arity = "0..1", defaultValue = "./source.txt", description ="source path")
	String source_path;
	
	
	public boolean isPdf(String file) {
		return file.endsWith(".pdf");
		
	}

	@Override
	public Void call() throws Exception {
		if(produce) {
			System.out.println("Produce mode activated ...");
			System.out.println("Update : "+step);
			System.out.println("Verbose : "+vb_level);
			
			if (isPdf(sheet_name)) {
				System.out.println("Sheet : "+sheet_name);
			}
			else {
				System.out.println("The specified name for the result file is invalid");
				//System.out.println("Sheet : "+sheet_name+".pdf");
			}
			
			System.out.println("Source : "+source_path);
		}
		
			return null;
		
	}
}
