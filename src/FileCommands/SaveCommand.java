package FileCommands;

import Interfaces.ExecuteCommands;

public class SaveCommand implements ExecuteCommands {
    private FileManager fileManager;

    public SaveCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void execute() {
        fileManager.save();
    }
}