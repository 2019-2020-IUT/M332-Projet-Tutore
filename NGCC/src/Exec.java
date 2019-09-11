
import commands.*;
import picocli.CommandLine;

public class Exec {

	public static void main(String[] args) {

		try {

			if (args[0].contentEquals("-b") || args[0].contentEquals("--build")) {
				CommandLine cmd = new CommandLine(new Build());
				cmd.execute(args);
				
			} else if (args[0].contentEquals("-r") || args[0].contentEquals("--read")) {
				CommandLine cmd = new CommandLine(new Read());
				cmd.execute(args);
				
			} else if (args[0].contentEquals("-g") || args[0].contentEquals("--generate")) {
				CommandLine cmd = new CommandLine(new Generate());
				cmd.execute(args);
				
			} else if (args[0].contentEquals("-p") || args[0].contentEquals("--produce")) {
				CommandLine cmd = new CommandLine(new Produce());
				cmd.execute(args);
				
			} else if (args[0].contentEquals("-a") || args[0].contentEquals("--analyse")) {
				CommandLine cmd = new CommandLine(new Analyse());
				cmd.execute(args);
				
			} else if (args[0].contentEquals("-e") || args[0].contentEquals("--evaluate")) {
				CommandLine cmd = new CommandLine(new Evaluate());
				cmd.execute(args);
				
			} else if (args[0].contentEquals("help") || args[0].contentEquals("--help")) {
				CommandLine cmd = new CommandLine(new Help());
				cmd.execute(args);
				
			} else {
				System.err.println("NGCC: "+args[0] + " : command not found");
			}

			
		} catch (ArrayIndexOutOfBoundsException e) {
			CommandLine cmd = new CommandLine(new Help());
			System.out.println(cmd.execute(args));
		}

	}

}
