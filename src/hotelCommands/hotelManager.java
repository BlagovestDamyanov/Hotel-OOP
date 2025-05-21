package hotelCommands;

import fileCommands.fileManager;
import interfaces.hotelInstructions;
import models.Booking;
import models.Room;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class hotelManager implements hotelInstructions {
    public Map<Integer, Room> rooms = new HashMap<>();
    private fileManager fileManager;

    public hotelManager(fileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void checkIn(String data) {
        if (!fileManager.isFileOpen()) {
            System.out.println("No file is currently open.");
            return;
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (String reservation : data.split("\n")) {
            String[] info = reservation.split(" ");
            if (info.length < 4) {
                System.out.println("Please enter a valid command!");
                continue;
            }

            try {
                int roomNumber = Integer.parseInt(info[0]);
                LocalDate startDate = LocalDate.parse(info[1], dateFormat);
                LocalDate endDate = LocalDate.parse(info[2], dateFormat);

                if (endDate.isBefore(startDate)) {
                    System.out.println("End date cannot be before start date.");
                    continue;
                }

                String note = info[3];
                Room room = rooms.get(roomNumber);

                if (room == null) {
                    System.out.println("Room number " + roomNumber + " does not exist.");
                    continue;
                }

                if (isRoomUnavailable(roomNumber, startDate, endDate)) {
                    System.out.println("Room number " + roomNumber + " is not available in the given period.");
                    continue;
                }

                if (fileManager.checkinList.stream().anyMatch(checkin ->
                        Integer.parseInt(checkin[0]) == roomNumber &&
                                !(endDate.isBefore(LocalDate.parse(checkin[1])) || startDate.isAfter(LocalDate.parse(checkin[2]))))) {
                    System.out.println("The room is not available in this period.");
                    continue;
                }

                int maxBeds = room.getBeds();
                int guests = info.length >= 5 ? Integer.parseInt(info[4]) : maxBeds;
                if (guests > maxBeds) {
                    System.out.println("The number of guests exceeds the number of beds in the room.");
                    continue;
                }

                room.addBooking(new Booking(roomNumber, startDate, endDate, note, guests));
                room.setAvailable(false);
                fileManager.checkinList.add(new String[]{String.valueOf(roomNumber), startDate.toString(), endDate.toString(), note, String.valueOf(guests)});
                System.out.println("Successfully added a new reservation!");
            } catch (Exception ignored) {}
        }
    }


    @Override
    public void checkout(String data) {
        if (!fileManager.isFileOpen()) {
            System.out.println("No file is currently open.");
            return;
        }

        int roomNumber;
        try {
            roomNumber = Integer.parseInt(data.trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid room number format.");
            return;
        }

        Room room = rooms.get(roomNumber);
        if (room == null) {
            System.out.println("Room " + roomNumber + " does not exist.");
            return;
        }

        List<String[]> updatedCheckinList = new ArrayList<>();
        boolean found = false;
        boolean firstRemoved = false;

        for (String[] checkinData : fileManager.checkinList) {
            if (Integer.parseInt(checkinData[0]) == roomNumber) {
                if (!firstRemoved) {
                    // Премахва първата резервация за тази стая
                    firstRemoved = true;
                    System.out.println("Removed the first reservation for room " + roomNumber);
                } else {
                    updatedCheckinList.add(checkinData);
                }
            } else {
                updatedCheckinList.add(checkinData);
            }
        }

        if (!firstRemoved) {
            System.out.println("Room " + roomNumber + " is not currently occupied.");
            return;
        }

        fileManager.checkinList = updatedCheckinList;
        room.setAvailable(true);
        System.out.println("Room " + roomNumber + " has been successfully checked out.");
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
        if(!fileManager.isFileOpen()){
            System.out.println("No file is currently open.");
            return;
        }
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
        if (!fileManager.isFileOpen()) {
            System.out.println("No file is currently open.");
            return;
        }

        String[] reservations = data.split(" ");
        if (reservations.length < 3) {
            System.out.println("Invalid input! Please enter the required parameters: <beds> <from> <to>");
            return;
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate, endDate;

        try {
            int beds = Integer.parseInt(reservations[0]);
            startDate = LocalDate.parse(reservations[1], dateFormat);
            endDate = LocalDate.parse(reservations[2], dateFormat);

            Room bestRoom = null;
            for (Room room : rooms.values()) {
                if (room.getBeds() < beds || isRoomUnavailable(room.getRoomNumber(), startDate, endDate)) continue;

                boolean isAvailable = fileManager.checkinList.stream().noneMatch(checkin -> {
                    int bookedRoom = Integer.parseInt(checkin[0]);
                    LocalDate bookedStart = LocalDate.parse(checkin[1], DateTimeFormatter.ofPattern("uuuu-MM-dd"));
                    LocalDate bookedEnd = LocalDate.parse(checkin[2], DateTimeFormatter.ofPattern("uuuu-MM-dd"));

                    return bookedRoom == room.getRoomNumber() && !(endDate.isBefore(bookedStart) || startDate.isAfter(bookedEnd));
                });

                if (isAvailable && (bestRoom == null || room.getBeds() < bestRoom.getBeds())) {
                    bestRoom = room;
                }
            }

            if (bestRoom != null) {
                System.out.println("Found available room: " + bestRoom.toString());
            } else {
                System.out.println("No available room found for the given criteria.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format for beds. Please enter a valid integer.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format! Please use dd-MM-yyyy.");
        } catch (Exception e) {
            System.out.println("An error occurred while processing the request.");
        }
    }

    @Override
    public Room importantFind(String data) {
        if (!fileManager.isFileOpen()) {
            System.out.println("No file is currently open.");
            return null;
        }

        String[] params = data.split(" ");
        if (params.length < 3) {
            System.out.println("Invalid input! Please enter the required parameters: <beds> <from> <to>");
            return null;
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate, endDate;
        int beds;

        try {
            beds = Integer.parseInt(params[0]);
            startDate = LocalDate.parse(params[1], dateFormat);
            endDate = LocalDate.parse(params[2], dateFormat);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format for beds. Please enter a valid integer.");
            return null;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format! Please use dd-MM-yyyy.");
            return null;
        }

        Room bestRoom = null;

        for (Room room : rooms.values()) {
            if (room.getBeds() < beds) continue;

            boolean isAvailable = fileManager.checkinList.stream().noneMatch(checkin -> {
                int bookedRoom = Integer.parseInt(checkin[0]);
                LocalDate bookedStart = LocalDate.parse(checkin[1], DateTimeFormatter.ofPattern("uuuu-MM-dd"));
                LocalDate bookedEnd = LocalDate.parse(checkin[2], DateTimeFormatter.ofPattern("uuuu-MM-dd"));

                return bookedRoom == room.getRoomNumber() && !(endDate.isBefore(bookedStart) || startDate.isAfter(bookedEnd));
            });

            if (isAvailable && (bestRoom == null || room.getBeds() < bestRoom.getBeds())) {
                bestRoom = room;
            }
        }

        if (bestRoom != null) {
            System.out.println("Found available room: " + bestRoom);
            return bestRoom;
        }

        return tryRearrangeReservations(beds, startDate, endDate);
    }

    @Override
    public void unavailable(String data) {
        if (!fileManager.isFileOpen()) {
            System.out.println("No file is currently open.");
            return;
        }

        String[] params = data.split(" ");
        if (params.length < 4) {
            System.out.println("Invalid input! Please enter: <room> <from> <to> <note>");
            return;
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate, endDate;
        int roomNumber;

        try {
            roomNumber = Integer.parseInt(params[0]);
            startDate = LocalDate.parse(params[1], dateFormat);
            endDate = LocalDate.parse(params[2], dateFormat);
        } catch (NumberFormatException e) {
            System.out.println("Invalid room number format.");
            return;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format! Please use dd-MM-yyyy.");
            return;
        }

        if (startDate.isAfter(endDate)) {
            System.out.println("Start date must be before or equal to the end date.");
            return;
        }

        Room room = rooms.get(roomNumber);
        if (room == null) {
            System.out.println("Room " + roomNumber + " does not exist.");
            return;
        }

        for (String[] checkinData : fileManager.checkinList) {
            int bookedRoom = Integer.parseInt(checkinData[0]);
            LocalDate bookedStart = LocalDate.parse(checkinData[1], DateTimeFormatter.ofPattern("uuuu-MM-dd"));
            LocalDate bookedEnd = LocalDate.parse(checkinData[2], DateTimeFormatter.ofPattern("uuuu-MM-dd"));

            if (bookedRoom == roomNumber && !(endDate.isBefore(bookedStart) || startDate.isAfter(bookedEnd))) {
                System.out.println("Room " + roomNumber + " is already booked during this period.");
                return;
            }
        }

        for (String[] unavailableData : fileManager.unavailableList) {
            int unavailableRoom = Integer.parseInt(unavailableData[0]);
            LocalDate unavailableStart = LocalDate.parse(unavailableData[1], DateTimeFormatter.ofPattern("uuuu-MM-dd"));
            LocalDate unavailableEnd = LocalDate.parse(unavailableData[2], DateTimeFormatter.ofPattern("uuuu-MM-dd"));

            if (unavailableRoom == roomNumber && !(endDate.isBefore(unavailableStart) || startDate.isAfter(unavailableEnd))) {
                System.out.println("Room " + roomNumber + " is already marked as unavailable during this period.");
                return;
            }
        }

        String note = String.join(" ", Arrays.copyOfRange(params, 3, params.length));
        fileManager.unavailableList.add(new String[]{String.valueOf(roomNumber), startDate.toString(), endDate.toString(), note});
        room.setAvailable(false);
        System.out.println("Room " + roomNumber + " is now unavailable from " + startDate + " to " + endDate + ". Note: " + note);
    }

    private Room tryRearrangeReservations(int beds, LocalDate startDate, LocalDate endDate) {
        // Кандидати за разместване
        List<Room> candidateRooms = new ArrayList<>();

        for (Room room : rooms.values()) {
            if (room.getBeds() >= beds) {
                boolean overlaps = fileManager.checkinList.stream().anyMatch(checkin -> {
                    int bookedRoom = Integer.parseInt(checkin[0]);
                    LocalDate bookedStart = LocalDate.parse(checkin[1]);
                    LocalDate bookedEnd = LocalDate.parse(checkin[2]);

                    return bookedRoom == room.getRoomNumber() &&
                            !(endDate.isBefore(bookedStart) || startDate.isAfter(bookedEnd));
                });

                if (overlaps) candidateRooms.add(room);
            }
        }

        for (Room candidate : candidateRooms) {
            List<String[]> conflictingReservations = new ArrayList<>();

            for (String[] checkin : fileManager.checkinList) {
                int bookedRoom = Integer.parseInt(checkin[0]);
                if (bookedRoom != candidate.getRoomNumber()) continue;

                LocalDate bookedStart = LocalDate.parse(checkin[1]);
                LocalDate bookedEnd = LocalDate.parse(checkin[2]);

                if (!(endDate.isBefore(bookedStart) || startDate.isAfter(bookedEnd))) {
                    conflictingReservations.add(checkin);
                }
            }

            // Ако конфликтират максимум 2 резервации
            if (conflictingReservations.size() <= 2) {
                boolean allMoved = true;

                for (String[] conflict : conflictingReservations) {
                    boolean moved = false;
                    for (Room otherRoom : rooms.values()) {
                        if (otherRoom.getRoomNumber() == candidate.getRoomNumber()) continue;
                        if (otherRoom.getBeds() < Integer.parseInt(conflict[4])) continue;

                        boolean available = fileManager.checkinList.stream().noneMatch(otherCheckin -> {
                            int bookedRoom = Integer.parseInt(otherCheckin[0]);
                            LocalDate otherStart = LocalDate.parse(otherCheckin[1]);
                            LocalDate otherEnd = LocalDate.parse(otherCheckin[2]);

                            return bookedRoom == otherRoom.getRoomNumber() &&
                                    !(LocalDate.parse(conflict[2]).isBefore(otherStart) || LocalDate.parse(conflict[1]).isAfter(otherEnd));
                        });

                        if (available) {
                            // Премести резервацията
                            fileManager.checkinList.remove(conflict);
                            fileManager.checkinList.add(new String[]{
                                    String.valueOf(otherRoom.getRoomNumber()),
                                    conflict[1],
                                    conflict[2],
                                    conflict[3],
                                    conflict[4]
                            });
                            moved = true;
                            break;
                        }
                    }

                    if (!moved) {
                        allMoved = false;
                        break;
                    }
                }

                if (allMoved) {
                    System.out.println("Found room by rearranging guests: " + candidate);
                    return candidate;
                }
            }
        }

        System.out.println("Unable to rearrange guests for important booking.");
        return null;
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
