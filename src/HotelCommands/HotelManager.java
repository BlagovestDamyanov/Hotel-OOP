package HotelCommands;

import FileCommands.FileManager;
import Interfaces.HotelInstructions;
import Models.Booking;
import Models.Room;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HotelManager implements HotelInstructions {
    public Map<Integer, Room> rooms = new HashMap<>();
    private FileManager fileManager;

    public HotelManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void checkIn(String data) {
        if(!fileManager.isFileOpen()){
            System.out.println("No file is currently open.");
            return;
        }
        List<String[]> loadedData = fileManager.checkinList;
        String[] reservations = data.split("\n");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (String reservation : reservations) {
            String[] reservationInfo = reservation.split(" ");
            if (reservationInfo.length < 4) {
                System.out.println("Please enter a valid command!");
                continue;
            }

            int roomNumber = Integer.parseInt(reservationInfo[0]);
            LocalDate startDate, endDate;

            try {
                startDate = LocalDate.parse(reservationInfo[1], dateFormat);
                endDate = LocalDate.parse(reservationInfo[2], dateFormat);
            } catch (Exception e) {
                continue;
            }

            String note = reservationInfo[3];
            Room room = rooms.get(roomNumber);
            if (room == null || isRoomUnavailable(roomNumber, startDate, endDate))
            {
                continue;
            }

            boolean isAvailable = true;
            for (String[] checkinData : loadedData) {
                int bookedRoom = Integer.parseInt(checkinData[0]);
                LocalDate bookedCheckin = LocalDate.parse(checkinData[1], DateTimeFormatter.ofPattern("uuuu-MM-dd"));
                LocalDate bookedCheckout = LocalDate.parse(checkinData[2], DateTimeFormatter.ofPattern("uuuu-MM-dd"));

                if (bookedRoom == roomNumber && !(endDate.isBefore(bookedCheckin) || startDate.isAfter(bookedCheckout))) {
                    System.out.println("The room is not available in this period");
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable) {
                int guests = reservationInfo.length >= 5 ? Integer.parseInt(reservationInfo[4]) : room.getBeds();
                Booking booking = new Booking(roomNumber, startDate, endDate, note, guests);
                room.addBooking(booking);
                room.setAvailable(false);
                fileManager.checkinList.add(new String[]{String.valueOf(roomNumber), startDate.toString(), endDate.toString(), note, String.valueOf(guests)});
                System.out.println("Successfully added a new reservation!");
            }
        }
    }

    @Override
    public void checkout(int roomNumber) {
        if(!fileManager.isFileOpen()){
            System.out.println("No file is currently open.");
            return;
        }
        boolean found = false;
        Iterator<String[]> iterator = fileManager.checkinList.iterator();

        while (iterator.hasNext()) {
            String[] checkinData = iterator.next();
            if (Integer.parseInt(checkinData[0]) == roomNumber) {
                iterator.remove();
                found = true;
            }
        }

        if (found) {
            Room room = rooms.get(roomNumber);
            if (room != null) room.setAvailable(true);
        }
    }

    @Override
    public void report(String data) {
        if(!fileManager.isFileOpen()){
            System.out.println("No file is currently open.");
            return;
        }
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate, endDate;
        String[] reservations = data.split(" ");  // Разделяне по интервали (на един ред)

        if (reservations.length < 2) {
            System.out.println("Please provide start and end dates.");
            return;
        }

        try {
            startDate = LocalDate.parse(reservations[0], dateFormat);  // Първа дата
            endDate = LocalDate.parse(reservations[1], dateFormat);    // Втора дата
        } catch (Exception e) {
            System.out.println("Invalid date format. Use dd-MM-yyyy.");
            return;
        }

        Map<Integer, Integer> roomUsage = new HashMap<>();

        for (String[] checkinData : fileManager.checkinList) {
            int roomNumber = Integer.parseInt(checkinData[0]);
            LocalDate bookedStart = LocalDate.parse(checkinData[1], DateTimeFormatter.ofPattern("uuuu-MM-dd"));
            LocalDate bookedEnd = LocalDate.parse(checkinData[2], DateTimeFormatter.ofPattern("uuuu-MM-dd"));

            // Изчисляване на дните на съвпадение в даденото време
            LocalDate overlapStart = bookedStart.isBefore(startDate) ? startDate : bookedStart;
            LocalDate overlapEnd = bookedEnd.isAfter(endDate) ? endDate : bookedEnd;

            if (!overlapStart.isAfter(overlapEnd)) {
                int daysUsed = (int) ChronoUnit.DAYS.between(overlapStart, overlapEnd) + 1;
                roomUsage.put(roomNumber, roomUsage.getOrDefault(roomNumber, 0) + daysUsed);
            }
        }

        if (roomUsage.isEmpty()) {
            System.out.println("No rooms were used in the given period.");
        } else {
            System.out.println("Room usage report from " + startDate + " to " + endDate + ":");
            for (Map.Entry<Integer, Integer> entry : roomUsage.entrySet()) {
                System.out.println("Room " + entry.getKey() + " was used for " + entry.getValue() + " days.");
            }
        }
    }

    @Override
    public void availability(String date) {
        List<String> availableRooms = new ArrayList<>();
        List<String> occupiedRooms = new ArrayList<>();
        LocalDate targetDate;

        if (date == null || date.isEmpty()) {
            targetDate = LocalDate.now();
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
                targetDate = LocalDate.parse(date, formatter);
            } catch (Exception e) {
                return;
            }
        }

        for (Map.Entry<Integer, Room> entry : rooms.entrySet()) {
            int roomNumber = entry.getKey();
            Room room = entry.getValue();
            boolean isOccupied = false;

            if (isRoomUnavailable(roomNumber, targetDate, targetDate)) {
                occupiedRooms.add("Room " + roomNumber + " is unavailable on " + targetDate);
                continue;
            }

            for (String[] checkinData : fileManager.checkinList) {
                int bookedRoom = Integer.parseInt(checkinData[0]);
                LocalDate checkinDate = LocalDate.parse(checkinData[1], DateTimeFormatter.ofPattern("uuuu-MM-dd"));
                LocalDate checkoutDate = LocalDate.parse(checkinData[2], DateTimeFormatter.ofPattern("uuuu-MM-dd"));

                if (bookedRoom == roomNumber && !targetDate.isBefore(checkinDate) && !targetDate.isAfter(checkoutDate)) {
                    isOccupied = true;
                    break;
                }
            }

            if (isOccupied) occupiedRooms.add("Room " + roomNumber + " is occupied on " + targetDate);
            else availableRooms.add("Room " + roomNumber + " is available on " + targetDate);
        }

        availableRooms.forEach(System.out::println);
        occupiedRooms.forEach(System.out::println);
    }

    @Override
    public void find(String data) {
        if(!fileManager.isFileOpen()){
            System.out.println("No file is currently open.");
            return;
        }
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate, endDate;
        String[] reservations = data.split("\n");

        try {
            startDate = LocalDate.parse(reservations[1], dateFormat);
            endDate = LocalDate.parse(reservations[2], dateFormat);
        } catch (Exception e) {
            return;
        }
        int beds = Integer.parseInt(reservations[0]);
        Room bestRoom = null;

        for (Room room : rooms.values()) {
            if (room.getBeds() < beds || isRoomUnavailable(room.getRoomNumber(), startDate, endDate)) continue;

            boolean isAvailable = true;
            for (String[] checkinData : fileManager.checkinList) {
                int bookedRoom = Integer.parseInt(checkinData[0]);
                LocalDate bookedStart = LocalDate.parse(checkinData[1], DateTimeFormatter.ofPattern("uuuu-MM-dd"));
                LocalDate bookedEnd = LocalDate.parse(checkinData[2], DateTimeFormatter.ofPattern("uuuu-MM-dd"));

                if (bookedRoom == room.getRoomNumber() && !(endDate.isBefore(bookedStart) || startDate.isAfter(bookedEnd))) {
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable && (bestRoom == null || room.getBeds() < bestRoom.getBeds())) {
                bestRoom = room;
            }
        }

        if (bestRoom != null) System.out.println("Found available room: " + bestRoom.toString());
    }

    @Override
    public Room importantFind() {
        return null;
    }

    public void unavailable(String data) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate, endDate;
        String[] reservations = data.split("\n");
        try {
            startDate = LocalDate.parse(reservations[1], dateFormat);
            endDate = LocalDate.parse(reservations[2], dateFormat);
        } catch (Exception e) {
            return;
        }
        int roomNumber = Integer.parseInt(reservations[0]);
        Room room = rooms.get(roomNumber);
        if (room == null) return;

        for (String[] checkinData : fileManager.checkinList) {
            int bookedRoom = Integer.parseInt(checkinData[0]);
            LocalDate bookedStart = LocalDate.parse(checkinData[1], DateTimeFormatter.ofPattern("uuuu-MM-dd"));
            LocalDate bookedEnd = LocalDate.parse(checkinData[2], DateTimeFormatter.ofPattern("uuuu-MM-dd"));

            if (bookedRoom == roomNumber && !(endDate.isBefore(bookedStart) || startDate.isAfter(bookedEnd))) return;
        }

        for (String[] unavailableData : fileManager.unavailableList) {
            int unavailableRoom = Integer.parseInt(unavailableData[0]);
            LocalDate unavailableStart = LocalDate.parse(unavailableData[1], DateTimeFormatter.ofPattern("uuuu-MM-dd"));
            LocalDate unavailableEnd = LocalDate.parse(unavailableData[2], DateTimeFormatter.ofPattern("uuuu-MM-dd"));

            if (unavailableRoom == roomNumber && !(endDate.isBefore(unavailableStart) || startDate.isAfter(unavailableEnd))) return;
        }

        fileManager.unavailableList.add(new String[]{String.valueOf(roomNumber), startDate.toString(), endDate.toString(), reservations[3]});
        room.setAvailable(false);
    }

    private boolean isRoomUnavailable(int roomNumber, LocalDate startDate, LocalDate endDate) {
        for (String[] unavailableData : fileManager.unavailableList) {
            int unavailableRoom = Integer.parseInt(unavailableData[0]);
            LocalDate unavailableStart = LocalDate.parse(unavailableData[1], DateTimeFormatter.ofPattern("uuuu-MM-dd"));
            LocalDate unavailableEnd = LocalDate.parse(unavailableData[2], DateTimeFormatter.ofPattern("uuuu-MM-dd"));

            if (unavailableRoom == roomNumber && !(endDate.isBefore(unavailableStart) || startDate.isAfter(unavailableEnd))) return true;
        }
        return false;
    }
}
