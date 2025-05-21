package cli;

import fileCommands.*;
import hotelCommands.*;
import interfaces.executeCommands;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class hotelCLI {

    public static void run(){
        Scanner scanner = new Scanner(System.in);
        fileManager fileManager = new fileManager();
        hotelManager hotelManager = new hotelManager(fileManager);

        Map<String, executeCommands> commands = new HashMap<>();
        seedRooms.seedRooms(hotelManager);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2);
            String commandName = parts[0].toLowerCase();
            String argument = parts.length > 1 ? parts[1] : "";

            commands.put("open", new openCommand(fileManager, argument));
            commands.put("close", new closeCommand(fileManager));
            commands.put("save", new saveCommand(fileManager));
            commands.put("saveas", new saveAsCommand(fileManager, argument));
            commands.put("help", new helpCommand(fileManager));
            commands.put("exit", new exitCommand(fileManager));
            commands.put("checkin", new checkInCommand(hotelManager,argument));
            commands.put("availability" , new availabilityCommand(hotelManager,argument));
            commands.put("checkout", new checkoutCommand(hotelManager,argument));
            commands.put("find", new findCommand(hotelManager,argument));
            commands.put("report", new reportCommand(hotelManager,argument));
            commands.put("unavailable", new unavailableCommand(hotelManager,argument));
            commands.put("find!", new importantFindCommand(hotelManager,argument));

            executeCommands command = commands.get(commandName);
            if (command != null) {
                command.execute();
            } else {
                System.out.println("Invalid command. Type 'help' for a list of commands.");
            }
        }
    }

}