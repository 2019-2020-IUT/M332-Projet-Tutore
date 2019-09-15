import java.util.concurrent.Callable;
import commands.Read;
import picocli.CommandLine;
import picocli.CommandLine.*;

@Command(
		name = "Exec",
		version = "Version 1.0",
		sortOptions = false,
		usageHelpWidth = 60,
		header = "\n ----  Nicely Generated and Corrected Copies  ----        \n\n" +
				 "  _______    _________________  ________              \n" +											 
				 "  \\      \\  /  _____/\\_   ___ \\\\_   ___ \\       \n"	+											
				 "  /   |   \\/   \\  ___/    \\  \\//    \\  \\/       \n"	+											 
				 " /    |    \\    \\_ \\ \\     \\___\\     \\___      \n"	+			
			     " \\____|__  /\\______  /\\______  /\\______  /        \n"	+										
				 "         \\/        \\/        \\/        \\/         \n"	+
				 "  											        \n" ,
		footer = "\n\n  ---- Provided by IUT Info Nice S3T-G4 ---- \n",
		description = "description"		
	)


public class Exec implements Callable <Void> {

	public static void main(String[] args) throws InterruptedException {
		
		CommandLine cmd = new CommandLine (new Exec())
				.addSubcommand("-r", new Read(System.out))
				.addSubcommand("help", new HelpCommand());
		
		cmd.execute(args);
		
	}

	@Override
	public Void call() throws Exception {
		
		return null;
	}
		
}
