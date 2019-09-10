package commands;
import java.util.concurrent.Callable;

//import picocli.CommandLine;
import picocli.CommandLine.*;


@Command(
	name = "generate",
	version = "Version 1.0",
	sortOptions = false,
	usageHelpWidth = 60,
	header = " --  Nicely Generated and Corrected Copies  -- \n",
	footer = "\n Provided by IUT Info Nice S3T-G4",
	description = "description"		
)


public class Generate implements Callable <Void> {
	
	@Option(names= {"-g","--generate"}, arity = "0", order = 1, description = "generate mode")
	boolean generate;
	
	@Option(names= {"-u"}, arity = "1", order = 2, description = "update mode")
	int step;
	
	@Option(names= {"-v"}, arity = "0..*", order = 3, defaultValue = "1", description ="verbose mode")
	int vb_level;
	
	@Option(names= {"-t"}, arity = "1", order = 4, defaultValue = "topic-sheet.pdf", description ="topic")
	String topic_name;
	
	@Option(names= {"-a"}, arity = "1", order = 5, defaultValue = "answer-sheet.pdf", description ="answer")
	String answer_name;
	
	@Parameters(arity = "0..1", defaultValue = "./source.txt", description ="source path")
	String source_path;
	
	
	public boolean isPdf(String file) {
		return file.endsWith(".pdf");
		
	}

	@Override
	public Void call() throws Exception {
		if(generate) {
			System.out.println("Generate mode activated ...");
			System.out.println("Update : "+step);
			System.out.println("Verbose : "+vb_level);
			System.out.println("Topic : "+topic_name); //isPdf ...
			
			if (isPdf(answer_name)) {
				System.out.println("Answer : "+answer_name);
			}
			else {
				System.out.println("The specified name for the result file is invalid");
				//System.out.println("Answer : "+answer_name+".pdf");
			}
			
			System.out.println("Source : "+source_path);
		}
		
			return null;
		
	}
}
