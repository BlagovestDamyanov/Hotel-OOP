package FileCommands;

import CLI.FileManager;
import CLI.Interfaces.CommandsCLI;

public class SaveCommand implements CommandsCLI {
    private FileManager fileManager;

    public SaveCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void execute() {
        fileManager.save();
    }
}