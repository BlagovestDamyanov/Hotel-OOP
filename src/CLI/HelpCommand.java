package CLI;

import CLI.Interfaces.ICommand;

class HelpCommand implements ICommand {
    private FileManager fileManager;

    public HelpCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void execute() {
        fileManager.help();
    }
}