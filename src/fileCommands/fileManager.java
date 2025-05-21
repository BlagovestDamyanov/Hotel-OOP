package fileCommands;

import interfaces.commandsCLI;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класът {@code fileManager} предоставя операции за работа с файлове като отваряне,
 * затваряне, запазване и запазване с ново име. Използва се в система с команден ред
 * (CLI) за управление на информация за хотелски стаи.
 * Съхранява данни за настанени гости и временно недостъпни стаи.
 */
public class fileManager implements commandsCLI {

    private File file;
    private boolean isFileOpen = false;
    private String filePath;

    /**
     * Списък с масиви от низове, съдържащ информация за настаняванията.
     */
    public List<String[]> checkinList = new ArrayList<>();

    /**
     * Списък с масиви от низове, съдържащ информация за временно недостъпни стаи.
     */
    public List<String[]> unavailableList = new ArrayList<>();

    /**
     * Проверява дали в момента има отворен файл.
     *
     * @return {@code true} ако има отворен файл, в противен случай {@code false}.
     */
    public boolean isFileOpen() {
        return isFileOpen;
    }

    /**
     * Отваря файл с подадения път. Ако файлът не съществува, се създава нов.
     * Зарежда съдържанието на файла в списъка с настанявания.
     *
     * @param path Път до файла, който ще бъде отворен.
     */
    @Override
    public void open(String path) {
        file = new File(path);
        filePath = path;

        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Файлът не беше намерен. Създаден е нов файл: " + file.getName());
            } else {
                System.out.println("Файлът е успешно отворен: " + file.getName());
                load();
            }
            isFileOpen = true;
        } catch (IOException e) {
            System.out.println("Грешка при отваряне на файла: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Затваря текущо отворения файл.
     */
    @Override
    public void close() {
        if (!isFileOpen) {
            System.out.println("Няма отворен файл.");
            return;
        }
        isFileOpen = false;
        System.out.println("Файлът е успешно затворен: " + file.getName());
    }

    /**
     * Записва текущите данни за настаняванията във файла.
     */
    @Override
    public void save() {
        if (!isFileOpen) {
            System.out.println("Няма отворен файл.");
            return;
        }
        System.out.println("Файлът е успешно запазен: " + file.getName());
        writeToFile(file.getName(), checkinList);
    }

    /**
     * Записва текущите данни във файл с ново име.
     *
     * @param newPath Новият път (име) на файла.
     */
    @Override
    public void saveAs(String newPath) {
        if (!isFileOpen) {
            System.out.println("Няма отворен файл.");
            return;
        }

        File newFile = new File(newPath);
        try {
            if (file.renameTo(newFile)) {
                file = newFile;
                filePath = newPath;
                System.out.println("Успешно запазване като: " + newFile.getName());
                writeToFile(newFile.getName(), checkinList);
            } else {
                System.out.println("Грешка при запазване на файла.");
            }
        } catch (Exception e) {
            System.out.println("Грешка при запазване на файла: " + e.getMessage());
        }
    }

    /**
     * Показва списък с поддържаните команди и тяхното описание.
     */
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

    /**
     * Изход от програмата.
     */
    @Override
    public void exit() {
        System.out.println("Изход от програмата...");
        System.exit(0);
    }

    /**
     * Зарежда съдържанието на файла в списъка с настанявания (checkinList).
     */
    @Override
    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] checkinData = line.split(" ");
                checkinList.add(checkinData);
            }
        } catch (IOException e) {
            System.err.println("Грешка при четене на файл: " + e.getMessage());
        }
    }

    /**
     * Записва подадения списък с данни за настанявания във файл.
     *
     * @param filename     Името на файла, в който да се запише информацията.
     * @param checkinList  Списък с данни за настаняванията.
     */
    public static void writeToFile(String filename, List<String[]> checkinList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String[] checkin : checkinList) {
                writer.write(String.join(" ", checkin));
                writer.newLine();
            }
            System.out.println("Данните са успешно записани във файла: " + filename);
        } catch (IOException e) {
            System.err.println("Грешка при запис във файл: " + e.getMessage());
        }
    }
}
