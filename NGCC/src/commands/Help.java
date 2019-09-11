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
			 " /    |    \\    \\_ \\ \\     \\___\\     \\___      \n"	+			
		     " \\____|__  /\\______  /\\______  /\\______  /        \n"	+										
			 "         \\/        \\/        \\/        \\/         \n"	+
			 "  											        \n" ,
	footer = "\n  ---- Provided by IUT Info Nice S3T-G4 ---- ",
	description = "description"		
)


public class Help implements Callable <Void> {
	
	@Option(names= {"help", "--help"}, required = true, arity = "0", order = 1, description = "help")
	boolean help;
	

	@Override
	public Void call() throws Exception {
		if(help) {
			System.out.println(
					"\nCommands List :               \n\n"  +
					"-b, --build         build mode    \n"  +
					"-r, --read          read mode     \n"  + 
					"-g, --generate      generate mode \n"  +
					"-p, --produce       produce mode  \n"  +
					"-a, --analyse       analyse mode  \n"  +
					"-e, --evaluate      evaluate mode \n" );
					
		}
		
			return null;
		
	}
}
