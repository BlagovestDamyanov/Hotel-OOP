package CLI;

class CloseCommand implements ICommand {
    private FileManager fileManager;

    public CloseCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void execute() {
        fileManager.close();
    }
}