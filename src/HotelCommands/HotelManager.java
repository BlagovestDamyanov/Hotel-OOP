package HotelCommands;

import FileCommands.FileManager;
import Interfaces.HotelInstructions;
import Models.Booking;
import Models.Room;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");

        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(reservationInfo[1], formatter);
            endDate = LocalDate.parse(reservationInfo[2], formatter);
        } catch (Exception e) {
            System.out.println("Error parsing dates: " + e.getMessage());
            return;
        }

        String note = reservationInfo[3];
        Room room = rooms.get(roomNumber);

        if (room != null && room.isAvailable()) {
            int guests = reservationInfo.length >= 5 ? Integer.parseInt(reservationInfo[4]) : room.getBeds();
            Booking booking = new Booking(roomNumber, startDate, endDate, note, guests);
            room.addBooking(booking);
            room.setAvailable(false);

            // Convert Booking to String[] and add to checkinList
            String[] checkinData = {
                    String.valueOf(roomNumber),
                    startDate.format(DateTimeFormatter.ofPattern("uuuu-MM-dd")),
                    endDate.format(DateTimeFormatter.ofPattern("uuuu-MM-dd")),
                    note,
                    String.valueOf(guests)
            };
            fileManager.checkinList.add(checkinData);

            System.out.println("Room " + roomNumber + " is reserved.");
        } else {
            System.out.println("Room is already reserved.");
        }
    }


    @Override
    public List<String> availability() {
        return List.of();
    }

    @Override
    public void checkout() {

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
