package commands;
import java.io.PrintStream;
import java.util.concurrent.Callable;

import picocli.CommandLine;
//import picocli.CommandLine;
import picocli.CommandLine.*;


@Command(
	name = "-p",
	version = "Version 1.0",
	sortOptions = false,
	usageHelpWidth = 60,
	header = "Produce command - Correction subject generation",
	footer = "\nProduce correction subject associated to the source file.\n",
	description = "description"		
)


public class Produce implements Callable <Void> {
	
	@Spec
	Model.CommandSpec spec;
	
	@Option(names= {"-help"}, arity = "0", order = 1, description = "command help")
	boolean help;
	
	@Option(names= {"-u"}, arity = "1", order = 2, description = "update mode")
	int step;
	
	@Option(names= {"-v"}, arity = "0..*", order = 3, defaultValue = "1", description ="verbose mode")
	int vb_level;
	
	@Option(names= {"-c"}, arity = "1", order = 4, defaultValue = "corrected-sheet.pdf", description ="sheet")
	String sheet_name;
	
	@Parameters(arity = "0..1", defaultValue = "./source.txt", description ="source path")
	String source_path;
	
	
//	public boolean isPdf(String file) {
//		return file.endsWith(".pdf");
//		
//	}
	
	
	public Produce(PrintStream out) {
	}
	

	@Override
	public Void call() throws Exception {
		
		if(help){
			CommandLine.usage(this.spec, System.out);
		}
		else {
		
		System.out.println("\nProduce mode activated ...\n");
		System.out.println("Update : "+step               +"\n"+
						   "Verbose : "+vb_level            +"\n"+
						   "Sheet : "+sheet_name            +"\n"+
						   "Source : "+source_path          +"\n");
		}
		
			return null;
		
	}
}
