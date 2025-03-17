package FileCommands;

import Interfaces.ExecuteCommands;

public class SaveAsCommand implements ExecuteCommands {
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