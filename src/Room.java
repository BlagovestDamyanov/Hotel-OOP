import java.util.ArrayList;
import java.util.Date;
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
    public void checkIn(int roomNumber, Date startDate, Date endDate, String note, int numberGuests) {

    }

    @Override
    public List<Room> availability(Date date) {
        return List.of();
    }

    @Override
    public void checkOut(int roomNumber) {

    }

    @Override
    public void Report(Date startDate, Date ednDate) {

    }

    @Override
    public Room find(int beds, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public Room importantFind(int beds, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public void unavailable(int roomNumber, Date startDate, Date endDate) {

    }
}
