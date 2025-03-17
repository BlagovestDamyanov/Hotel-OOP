package FileCommands;

import Interfaces.CommandsCLI;
import Interfaces.ExecuteCommands;

public class ExitCommand implements ExecuteCommands {
    private FileManager fileManager;

    public ExitCommand(FileManager fileManager) {

        this.fileManager = fileManager;
    }
    public void execute() {
        fileManager.exit();
    }
}