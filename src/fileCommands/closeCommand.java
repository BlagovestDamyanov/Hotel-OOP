package fileCommands;

import interfaces.executeCommands;

public class closeCommand implements executeCommands {
    private fileManager fileManager;

    public closeCommand(fileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void execute() {
        fileManager.close();
    }
}