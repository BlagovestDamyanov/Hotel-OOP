package CLI;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CLI {

    public static void run(){
        Scanner scanner = new Scanner(System.in);
        FileManager fileManager = new FileManager();
        Map<String, ICommand> commands = new HashMap<>();

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2);
            String commandName = parts[0].toLowerCase();
            String argument = parts.length > 1 ? parts[1] : "";

            commands.put("open", new OpenCommand(fileManager, argument));
            commands.put("close", new CloseCommand(fileManager));
            commands.put("save", new SaveCommand(fileManager));
            commands.put("saveas", new SaveAsCommand(fileManager, argument));
            commands.put("help", new HelpCommand(fileManager));
            commands.put("exit", new ExitCommand());

            ICommand command = commands.get(commandName);
            if (command != null) {
                command.execute();
            } else {
                System.out.println("Invalid command. Type 'help' for a list of commands.");
            }
        }
    }

}
