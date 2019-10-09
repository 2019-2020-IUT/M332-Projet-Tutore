package commands;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;

@Command(
		name = "TestRead",
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
		description = ""		
	)


class TestRead {	

	
	@Test
	void test() {	
		
		Read read = new Read(System.out);
		
		CommandLine cmd = new CommandLine (new TestRead())
				.addSubcommand("-r", read) 			
				.addSubcommand("help", new HelpCommand()); 			
		
		String[] t = {"-r","-v9","-d","pdf","config.txt"};
		cmd.execute(t);
		
		assertEquals("pdf",read.directory_name);
		assertEquals(9,read.vb_level);
		assertEquals("config.txt",read.source_path);
		assertEquals("result.csv",read.result_name);
	}

}
