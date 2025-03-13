package CLI;

import CLI.Interfaces.ICommand;

class SaveCommand implements ICommand {
    private FileManager fileManager;

    public SaveCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void execute() {
        fileManager.save();
    }
}