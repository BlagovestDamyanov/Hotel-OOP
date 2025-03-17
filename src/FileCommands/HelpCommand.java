package FileCommands;

import CLI.FileManager;
import CLI.Interfaces.CommandsCLI;

public class HelpCommand implements CommandsCLI {
    private FileManager fileManager;

    public HelpCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void execute() {
        fileManager.help();
    }
}