package FileCommands;

import Interfaces.CommandsCLI;

import java.io.File;
import java.io.IOException;

public class FileManager implements CommandsCLI {
    private File file;
    private boolean isFileOpen = false;
    private String filePath;

    @Override
    public void open(String path) {
        file = new File(path);
        filePath = path;

        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File not found. A new file has been created: " + file.getName());
            } else {
                System.out.println("Successfully opened file: " + file.getName());
            }
            isFileOpen = true;
        } catch (IOException e) {
            System.out.println("Error opening the file: " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void close() {
        if (!isFileOpen) {
            System.out.println("No file is currently open.");
            return;
        }
        isFileOpen = false;
        System.out.println("Successfully closed " + file.getName());
    }

    @Override
    public void save() {
        if (!isFileOpen) {
            System.out.println("No file is currently open.");
            return;
        }
        System.out.println("Successfully saved " + file.getName());
    }

    @Override
    public void saveAs(String newPath) {
        if (!isFileOpen) {
            System.out.println("No file is currently open.");
            return;
        }
        File newFile = new File(newPath);
        try {
            if (file.renameTo(newFile)) {
                file = newFile;
                filePath = newPath;
                System.out.println("Successfully saved " + newFile.getName());
            } else {
                System.out.println("Error saving file.");
            }
        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    @Override
    public void help() {
        System.out.println("The following commands are supported:");
        System.out.println("open <file> - opens <file>");
        System.out.println("close - closes currently opened file");
        System.out.println("save - saves the currently open file");
        System.out.println("saveas <file> - saves the currently open file in <file>");
        System.out.println("help - prints this information");
        System.out.println("exit - exits the program");
    }

    @Override
    public void exit() {
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}