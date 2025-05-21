package fileCommands;

import interfaces.executeCommands;

public class saveCommand implements executeCommands {
    private fileManager fileManager;

    public saveCommand(fileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void execute() {
        fileManager.save();
    }
}