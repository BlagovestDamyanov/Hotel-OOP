package Models.Interfaces;

public interface IFileController {
    void open(String filename);
    void close();
    void save();
    void saveAs(String filename);
    void help();
    void exit();
}
