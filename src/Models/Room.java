package Models;

import Models.Interfaces.HotelOperations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Room{
    private int roomNumber;
    private int beds;
    private boolean isAvailable;
    protected List<Booking> bookings;

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", beds=" + beds +
                ", isAvailable=" + isAvailable +
                ", bookings=" + bookings +
                '}';
    }

    public Room(int roomNumber, int beds) {
        this.roomNumber = roomNumber;
        this.beds = beds;
        this.isAvailable = true;
        this.bookings = new ArrayList<>();
    }
    public int getRoomNumber() {
        return roomNumber;
    }

    public int getBeds() {
        return beds;
    }


}
