package CLI;

class ExitCommand implements ICommand {
    public void execute() {
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}