package commands;
import java.io.PrintStream;
import java.util.concurrent.Callable;

import picocli.CommandLine;
//import picocli.CommandLine;
import picocli.CommandLine.*;


@Command(
	name = "-e",
	version = "Version 1.0",
	sortOptions = false,
	usageHelpWidth = 60,
	header = "Evaluate command - Mark evaluation ",
	footer = "",
	description = "\nEvaluate each copies' mark based on the analysis performed previously."
				+ " Can be executed several times to take changes into account.\n"		
)


public class Evaluate implements Callable <Void> {
	
	@Spec
	Model.CommandSpec spec;
	
	@Option(names= {"-help"}, arity = "0", order = 1, description = "command help")
	boolean help;
	
	@Option(names= {"-u"}, arity = "1", order = 2, description = "update mode")
	int step;
	
	@Option(names= {"-v"}, arity = "0..*", order = 3, defaultValue = "1", description ="verbose mode")
	int vb_level;
	
	@Option(names= {"-o"}, arity = "1", order = 4, defaultValue = "result.csv", description ="result")
	String result_name;
	
	@Parameters(arity = "0..1", defaultValue = "./source.txt", description ="source path")
	String source_path;
	
	
//	public boolean isCsv(String file) {
//		return file.endsWith(".csv");
//		
//	}
	
	
	public Evaluate(PrintStream out) {	
	}

	
	@Override
	public Void call() throws Exception {
		
		if(help){
			CommandLine.usage(this.spec, System.out);
		}
		else {
		
		System.out.println("\nEvaluate mode activated ...\n");
		System.out.println("Update : "+step               +"\n"+
						   "Verbose : "+vb_level            +"\n"+
						   "Result : "+result_name          +"\n"+
						   "Source : "+source_path          +"\n");
		}
		
			return null;
		
	}
}
