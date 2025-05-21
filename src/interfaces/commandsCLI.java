package interfaces;

public interface commandsCLI {
    void open(String path);
    void close();
    void save();
    void saveAs(String newPath);
    void help();
    void exit();
    void load();
}
