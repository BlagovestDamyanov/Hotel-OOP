package fileCommands;

import interfaces.executeCommands;

public class openCommand implements executeCommands {
    private fileManager fileManager;
    private String filePath;

    public openCommand(fileManager fileManager, String filePath) {
        this.fileManager = fileManager;
        this.filePath = filePath;
    }

    public void execute() {
        fileManager.open(filePath);
    }
}