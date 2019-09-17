package commands;
import java.io.PrintStream;
import java.util.concurrent.Callable;

import picocli.CommandLine;
//import picocli.CommandLine;
import picocli.CommandLine.*;


@Command(
	name = "-g",
	version = "Version 1.0",
	sortOptions = false,
	usageHelpWidth = 60,
	header = "Generate command - Subject and answer generation",
	footer = "",
	description = "\nSubject generation and associated answer with the source document.\n"		
)


public class Generate implements Callable <Void> {
	
	@Spec
	Model.CommandSpec spec;
	
	@Option(names= {"-help"}, arity = "0", order = 1, description = "command help")
	boolean help;
	
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
	
	
//	public boolean isPdf(String file) {
//		return file.endsWith(".pdf");
//		
//	}
	
	public Generate(PrintStream out) {	
	}

	
	@Override
	public Void call() throws Exception {
		
		if(help){
			CommandLine.usage(this.spec, System.out);
		}
		else {
		
			System.out.println("\nGenerate mode activated ...\n");
			System.out.println("Update : "+step               +"\n"+
							   "Verbose : "+vb_level            +"\n"+
							   "Topic : "+topic_name            +"\n"+
							   "Answer : "+answer_name          +"\n"+
							   "Source : "+source_path          +"\n");
		}
			
			return null;
		
	}
}
