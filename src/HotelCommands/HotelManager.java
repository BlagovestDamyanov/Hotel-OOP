package HotelCommands;

import FileCommands.FileManager;
import Interfaces.HotelInstructions;
import Models.Booking;
import Models.Room;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HotelManager implements HotelInstructions {
    public Map<Integer, Room> rooms = new HashMap<>();
    private FileManager fileManager;

    public HotelManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void checkIn(String data) {
        List<String[]> loadedData = fileManager.checkinList;
        String[] reservationInfo = data.split(" ");

        if (reservationInfo.length < 4) {
            System.out.println("Insufficient reservation data.");
            return;
        }

        int roomNumber = Integer.parseInt(reservationInfo[0]);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate, endDate;

        try {
            startDate = LocalDate.parse(reservationInfo[1], dateFormat);
            endDate = LocalDate.parse(reservationInfo[2], dateFormat);
        } catch (Exception e) {
            System.out.println("Invalid date format. Use dd-MM-yyyy.");
            return;
        }

        String note = reservationInfo[3];
        Room room = rooms.get(roomNumber);

        if (room == null) {
            System.out.println("Room does not exist.");
            return;
        }

        boolean isAvailable = true;
        for (String[] checkinData : loadedData) {
            int bookedRoom = Integer.parseInt(checkinData[0]);
            LocalDate bookedCheckin = LocalDate.parse(checkinData[1], DateTimeFormatter.ofPattern("uuuu-MM-dd"));
            LocalDate bookedCheckout = LocalDate.parse(checkinData[2], DateTimeFormatter.ofPattern("uuuu-MM-dd"));

            if (bookedRoom == roomNumber) {
                if (!(startDate.isAfter(bookedCheckout))) {
                    isAvailable = false;
                    break;
                }
            }
        }

        if (isAvailable) {
            int guests = reservationInfo.length >= 5 ? Integer.parseInt(reservationInfo[4]) : room.getBeds();
            Booking booking = new Booking(roomNumber, startDate, endDate, note, guests);
            room.addBooking(booking);
            room.setAvailable(false);
            fileManager.checkinList.add(new String[]{String.valueOf(roomNumber), startDate.toString(), endDate.toString(), note, String.valueOf(guests)});
            System.out.println("Room " + roomNumber + " is reserved.");
        } else {
            System.out.println("Room is already reserved for the selected dates.");
        }
    }

    @Override
    public void availability(String date) {
        List<String> availableRooms = new ArrayList<>();
        List<String> occupiedRooms = new ArrayList<>();
        LocalDate targetDate;

        // Use current date if no date is provided
        if (date == null || date.isEmpty()) {
            targetDate = LocalDate.now();
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
                targetDate = LocalDate.parse(date, formatter);
            } catch (Exception e) {
                System.out.println("Invalid date format. Use dd-MM-yyyy.");
                return;
            }
        }

        // Check each room's availability
        for (Map.Entry<Integer, Room> entry : rooms.entrySet()) {
            int roomNumber = entry.getKey();
            Room room = entry.getValue();
            boolean isOccupied = false;

            for (String[] checkinData : fileManager.checkinList) {
                try {
                    int bookedRoom = Integer.parseInt(checkinData[0]);
                    LocalDate checkinDate = LocalDate.parse(checkinData[1], DateTimeFormatter.ofPattern("uuuu-MM-dd"));
                    LocalDate checkoutDate = LocalDate.parse(checkinData[2], DateTimeFormatter.ofPattern("uuuu-MM-dd"));

                    // If the target date is within the check-in and check-out period, the room is occupied
                    if (bookedRoom == roomNumber && !targetDate.isBefore(checkinDate) && !targetDate.isAfter(checkoutDate)) {
                        isOccupied = true;
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing check-in data: " + e.getMessage());
                }
            }

            if (isOccupied) {
                occupiedRooms.add("Room " + roomNumber + " is occupied on " + targetDate);
            } else {
                availableRooms.add("Room " + roomNumber + " is available on " + targetDate);
            }
        }

        // Print results
        if (availableRooms.isEmpty()) {
            System.out.println("No free rooms on " + targetDate);
        } else {
            for (String freeRoom : availableRooms) {
                System.out.println(freeRoom);
            }
        }

        for (String occupiedRoom : occupiedRooms) {
            System.out.println(occupiedRoom);
        }
    }

    @Override
    public void checkout(int roomNumber) {
        boolean found = false;
        Iterator<String[]> iterator = fileManager.checkinList.iterator();

        while (iterator.hasNext()) {
            String[] checkinData = iterator.next();
            int bookedRoom = Integer.parseInt(checkinData[0]);

            if (bookedRoom == roomNumber) {
                iterator.remove();
                found = true;
            }
        }
        if (found) {
            Room room = rooms.get(roomNumber);
            if (room != null) {
                room.setAvailable(true);
            }
            System.out.println("Room " + roomNumber + " is now available.");
        } else {
            System.out.println("No reservation found for room " + roomNumber);
        }
    }


    @Override
    public void report() {

    }

    @Override
    public Room find() {
        return null;
    }

    @Override
    public Room importantFind() {
        return null;
    }

    @Override
    public void unavailable() {

    }
}
