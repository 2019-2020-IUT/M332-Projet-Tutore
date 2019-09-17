package commands;
import java.io.PrintStream;
import java.util.concurrent.Callable;

import picocli.CommandLine;
//import picocli.CommandLine;
import picocli.CommandLine.*;


@Command(
	name = "-b",
	version = "Version 1.0",
	sortOptions = false,
	usageHelpWidth = 60,
	header = "Build command - Create standard test copy",
	footer = "",
	description = "\nCreate a standard test copy accepted by the application.\n"		
)


public class Build implements Callable <Void> {
	
	@Spec
	Model.CommandSpec spec;
	
	@Option(names= {"-help"}, arity = "0", order = 1, description = "command help")
	boolean help;
	
	@Option(names= {"-u"}, arity = "1", order = 2, description = "update mode")
	int step;
	
	@Option(names= {"-v"}, arity = "0..1", defaultValue = "1", order = 3, description ="verbose mode")
	int vb_level;
	
	@Option(names= {"-a"}, arity = "1", order = 4, defaultValue = "answer-sheet.pdf", description ="answer")
	String answer_name;
	
	@Parameters(arity = "0..1", defaultValue = "./source.txt", description ="source path")
	String source_path;
	
	
	public Build(PrintStream out) {
	}

	
	@Override
	public Void call() throws Exception {
		
		if(help){
			CommandLine.usage(this.spec, System.out);
		}
		else {
		
		System.out.println("\nBuild mode activated ...\n");
		System.out.println("Update : "+step               +"\n"+
							"Verbose : "+vb_level           +"\n"+
							"Directory : "+answer_name      +"\n"+
							"Source : "+source_path         +"\n");

		}
			return null;
		
	}
}
