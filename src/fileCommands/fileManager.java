package fileCommands;

import interfaces.commandsCLI;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class fileManager implements commandsCLI {
    private File file;
    private boolean isFileOpen = false;

    public boolean isFileOpen() {
        return isFileOpen;
    }

    private String filePath;
    public List<String[]> checkinList = new ArrayList<>();
    public List<String[]> unavailableList = new ArrayList<>();
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
                load();
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
        writeToFile(file.getName(),checkinList);
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
                writeToFile(newFile.getName(), checkinList);
            } else {
                System.out.println("Error saving file.");
            }
        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }


    @Override
    public void help() {
        System.out.println("Поддържаните команди са:");
        System.out.println("open <file> - отваря файл <file>");
        System.out.println("close - затваря текущо отворения файл");
        System.out.println("save - запазва текущо отворения файл");
        System.out.println("saveas <file> - запазва текущо отворения файл с ново име <file>");
        System.out.println("help - показва тази помощна информация");
        System.out.println("exit - затваря програмата");

        System.out.println("checkin <стая> <от> <до> <бележка> [<гости>] -");
        System.out.println("    Регистрира гости в стая <стая> от дата <от> до дата <до> с бележка <бележка>.");
        System.out.println("    Параметърът [<гости>] е по избор; ако не е зададен, се приема броят на леглата в стаята.");

        System.out.println("availability [<дата>] -");
        System.out.println("    Показва списък със свободните стаи на дата <дата>; ако не е зададена, се използва текущата дата.");

        System.out.println("checkout <стая> -");
        System.out.println("    Освобождава заета стая с номер <стая>.");

        System.out.println("report <от> <до> -");
        System.out.println("    Показва справка за използването на стаи в периода от <от> до <до>.");

        System.out.println("find <легла> <от> <до> -");
        System.out.println("    Намира свободна стая с поне <легла> легла за периода от <от> до <до>.");
        System.out.println("    При наличие на повече възможности, се предпочитат стаи с по-малко легла.");

        System.out.println("find! <легла> <от> <до> -");
        System.out.println("    Спешно намиране на стая при липса на свободни – допуска се преместване на гости от до две стаи.");

        System.out.println("unavailable <стая> <от> <до> <бележка> -");
        System.out.println("    Обявява стая <стая> за временно недостъпна в периода от <от> до <до> с бележка <бележка>.");
    }


    @Override
    public void exit() {
        System.out.println("Exiting the program...");
        System.exit(0);
    }

    @Override
    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] checkinData = line.split(" ");
                checkinList.add(checkinData);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    public static void writeToFile(String filename, List<String[]> checkinList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String[] checkin : checkinList) {
                writer.write(String.join(" ", checkin));
                writer.newLine();
            }
            System.out.println("Data successfully written to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}