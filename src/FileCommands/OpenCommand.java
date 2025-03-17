package FileCommands;

import CLI.FileManager;
import CLI.Interfaces.CommandsCLI;

public class OpenCommand implements CommandsCLI {
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