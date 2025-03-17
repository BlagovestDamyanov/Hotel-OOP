package Interfaces;

public interface CommandsCLI {
    void open(String path);
    void close();
    void save();
    void saveAs(String newPath);
    void help();
    void exit();
}
