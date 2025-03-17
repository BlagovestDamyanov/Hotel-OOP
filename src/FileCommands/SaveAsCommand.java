package FileCommands;

import CLI.FileManager;
import CLI.Interfaces.CommandsCLI;

public class SaveAsCommand implements CommandsCLI {
    private FileManager fileManager;
    private String newPath;

    public SaveAsCommand(FileManager fileManager, String newPath) {
        this.fileManager = fileManager;
        this.newPath = newPath;
    }

    public void execute() {
        fileManager.saveAs(newPath);
    }
}