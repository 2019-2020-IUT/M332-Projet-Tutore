package commands;
import java.util.concurrent.Callable;

//import picocli.CommandLine;
import picocli.CommandLine.*;


@Command(
	name = "help",
	version = "Version 1.0",
	sortOptions = false,
	usageHelpWidth = 60,
	header = " ----  Nicely Generated and Corrected Copies  ----        \n" +
			 "  _______    _________________  ________              \n" +											 
			 "  \\      \\  /  _____/\\_   ___ \\\\_   ___ \\       \n"	+											
			 "  /   |   \\/   \\  ___/    \\  \\//    \\  \\/       \n"	+											 
			 " /    |    \\    \\_\\ \\      \\___\\     \\__       \n"	+			
		     " \\____|__  /\\______  /\\______  /\\______  /        \n"	+										
			 "         \\/        \\/        \\/        \\/         \n"	+
			 "  											        \n" ,
	footer = "\n  ---- Provided by IUT Info Nice S3T-G4 ---- ",
	description = "description"		
)


public class Help implements Callable <Void> {
	
	@Option(names= {"--help"}, required = true, arity = "0", order = 1, description = "help")
	boolean help;
	

	@Override
	public Void call() throws Exception {
		if(help) {
			System.out.println("Commands List : -b, -r, -g, -p, -a, -e");
			
			
		}
		
			return null;
		
	}
}
