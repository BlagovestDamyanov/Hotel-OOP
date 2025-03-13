package CLI;

import CLI.Interfaces.ICommand;

class OpenCommand implements ICommand {
    private FileManager fileManager;
    private String filePath;

    public OpenCommand(FileManager fileManager, String filePath) {
        this.fileManager = fileManager;
        this.filePath = filePath;
    }

    public void execute() {
        fileManager.open(filePath);
    }
}