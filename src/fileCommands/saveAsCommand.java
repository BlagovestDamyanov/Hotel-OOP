package fileCommands;

import interfaces.executeCommands;

public class saveAsCommand implements executeCommands {
    private fileManager fileManager;
    private String newPath;

    public saveAsCommand(fileManager fileManager, String newPath) {
        this.fileManager = fileManager;
        this.newPath = newPath;
    }

    public void execute() {
        fileManager.saveAs(newPath);
    }
}