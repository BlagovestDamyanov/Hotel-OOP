package CLI;

import java.io.File;
import java.io.IOException;

class FileManager {
    private File file;
    private boolean isFileOpen = false;
    private String filePath;

    public void open(String path) {
        file = new File(path);
        filePath = path;

        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Successfully created new file: " + file.getName());
            } else {
                System.out.println("Successfully opened " + file.getName());
            }
            isFileOpen = true;
        } catch (IOException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }
    }

    public void close() {
        if (!isFileOpen) {
            System.out.println("No file is currently open.");
            return;
        }
        isFileOpen = false;
        System.out.println("Successfully closed " + file.getName());
    }

    public void save() {
        if (!isFileOpen) {
            System.out.println("No file is currently open.");
            return;
        }
        System.out.println("Successfully saved " + file.getName());
    }

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

    public void help() {
        System.out.println("The following commands are supported:");
        System.out.println("open <file> - opens <file>");
        System.out.println("close - closes currently opened file");
        System.out.println("save - saves the currently open file");
        System.out.println("saveas <file> - saves the currently open file in <file>");
        System.out.println("help - prints this information");
        System.out.println("exit - exits the program");
    }
}