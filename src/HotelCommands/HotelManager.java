package HotelCommands;

import FileCommands.FileManager;
import Interfaces.HotelInstructions;
import Models.Booking;
import Models.Room;
import java.text.SimpleDateFormat;
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
            String[] checkinData = {
                    String.valueOf(roomNumber),
                    dateFormat.format(startDate),
                    dateFormat.format(endDate),
                    note,
                    String.valueOf(guests)
            };
            fileManager.checkinList.add(checkinData);
            System.out.println(room.toString() + "is reserved");
        }else{
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
