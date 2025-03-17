package FileCommands;

import Interfaces.ExecuteCommands;

public class OpenCommand implements ExecuteCommands {
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