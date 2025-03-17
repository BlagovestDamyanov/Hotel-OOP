package FileCommands;

import CLI.FileManager;
import CLI.Interfaces.CommandsCLI;

public class CloseCommand implements CommandsCLI {
    private FileManager fileManager;

    public CloseCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void execute() {
        fileManager.close();
    }
}