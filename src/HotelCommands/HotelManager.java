package HotelCommands;

import Interfaces.HotelInstructions;
import Models.Booking;
import Models.Room;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelManager implements HotelInstructions {
    public Map<Integer, Room> rooms = new HashMap<>();
    private void saveReservationToFile(int roomNumber, Date startDate, Date endDate, String notes, String guests) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reservations.txt", true))) {
            writer.write("Room " + roomNumber + " from " + startDate + " to " + endDate + ", Notes: " + notes + ", Guests: " + guests);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void checkIn(String data) {
       String [] reservationInfo = data.split(" ");
        if (reservationInfo.length < 4) {
            System.out.println("Insufficient reservation data.");
            return;
        }

       int roomNumber = Integer.parseInt(reservationInfo[0]);
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       Date startDate = null;
       Date endDate = null;
        try {
            startDate = dateFormat.parse(reservationInfo[1]);
            endDate = dateFormat.parse(reservationInfo[2]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String note = reservationInfo[3];
        Room room = rooms.get(roomNumber);

        if(room != null && room.isAvailable()){
            int guests = reservationInfo.length >=5 ? Integer.parseInt(reservationInfo[4]) : room.getBeds();
            Booking booking = new Booking(roomNumber, startDate, endDate, note, guests);
            room.addBooking(booking);
            room.setAvailable(false);
            System.out.println(room.toString() + "is reserved");
        }else{
            return;
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
