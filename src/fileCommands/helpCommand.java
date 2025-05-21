package fileCommands;

import interfaces.executeCommands;

public class helpCommand implements executeCommands {
    private fileManager fileManager;

    public helpCommand(fileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void execute() {
        fileManager.help();
    }
}