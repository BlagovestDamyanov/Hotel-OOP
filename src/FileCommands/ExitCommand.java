package FileCommands;

import CLI.Interfaces.CommandsCLI;

public class ExitCommand implements CommandsCLI {
    public void execute() {
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}