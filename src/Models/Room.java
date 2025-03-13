package Models;

import Models.Interfaces.IRoom;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Room implements IRoom {
    private int roomNumber;
    private int beds;
    private boolean isAvailable;
    protected List<Booking> bookings;

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
    @Override
    public void checkIn(int roomNumber, LocalDate startDate, LocalDate endDate, String note, int numberGuests) {
        bookings.add(new Booking(roomNumber,startDate,endDate,note,numberGuests));
        this.isAvailable = false;
    }

    @Override
    public List<Room> availability(LocalDate date) {
        return List.of();
    }

    @Override
    public void checkOut(int roomNumber) {

    }

    @Override
    public void Report(LocalDate startDate, LocalDate ednDate) {

    }

    @Override
    public Room find(int beds, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public Room importantFind(int beds, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public void unavailable(int roomNumber, LocalDate startDate, LocalDate endDate) {

    }
}
