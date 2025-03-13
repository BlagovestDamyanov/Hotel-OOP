package CLI;

import CLI.Interfaces.ICommand;

class ExitCommand implements ICommand {
    public void execute() {
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}