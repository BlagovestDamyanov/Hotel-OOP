package CLI;

import CLI.Interfaces.ICommand;

class CloseCommand implements ICommand {
    private FileManager fileManager;

    public CloseCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void execute() {
        fileManager.close();
    }
}