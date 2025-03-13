package CLI;

import CLI.Interfaces.ICommand;

class SaveAsCommand implements ICommand {
    private FileManager fileManager;
    private String newPath;

    public SaveAsCommand(FileManager fileManager, String newPath) {
        this.fileManager = fileManager;
        this.newPath = newPath;
    }

    public void execute() {
        fileManager.saveAs(newPath);
    }
}