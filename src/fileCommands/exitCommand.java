package fileCommands;

import interfaces.executeCommands;

public class exitCommand implements executeCommands {
    private fileManager fileManager;

    public exitCommand(fileManager fileManager) {

        this.fileManager = fileManager;
    }
    public void execute() {
        fileManager.exit();
    }
}