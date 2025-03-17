package Models;
import java.util.ArrayList;
import java.util.List;

public class Room {
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
                ", bookings=" + bookings.toString() +
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

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
        this.isAvailable = false;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}

