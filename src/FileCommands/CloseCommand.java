package FileCommands;

import Interfaces.ExecuteCommands;

public class CloseCommand implements ExecuteCommands {
    private FileManager fileManager;

    public CloseCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void execute() {
        fileManager.close();
    }
}