package FileCommands;

import Interfaces.ExecuteCommands;

public class HelpCommand implements ExecuteCommands {
    private FileManager fileManager;

    public HelpCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void execute() {
        fileManager.help();
    }
}