package CLI;

import FileCommands.*;
import HotelCommands.CheckIn;
import HotelCommands.HotelManager;
import Interfaces.ExecuteCommands;
import Models.Room;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HotelCLI {

    public static void run(){
        Scanner scanner = new Scanner(System.in);
        FileManager fileManager = new FileManager();
        HotelManager hotelManager = new HotelManager(fileManager);
        Map<String, ExecuteCommands> commands = new HashMap<>();

        hotelManager.rooms.put(101,new Room(101,2));
        hotelManager.rooms.put(102, new Room(102,2));
        hotelManager.rooms.put(103, new Room(103,4));
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
            commands.put("exit", new ExitCommand(fileManager));
            commands.put("checkin", new CheckIn(hotelManager,argument));

            ExecuteCommands command = commands.get(commandName);
            if (command != null) {
                command.execute();
            } else {
                System.out.println("Invalid command. Type 'help' for a list of commands.");
            }
        }
    }

}
