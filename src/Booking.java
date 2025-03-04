import java.time.LocalDate;
import java.util.Date;

public class Booking {
    private LocalDate startDate;
    private LocalDate endDate;
    private int roomNumber;
    private int guests;
    private String note;

    public Booking(int roomNumber, LocalDate startDate, LocalDate endDate, String note, int guests) {
        this.roomNumber = roomNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.note = note;
        this.guests = guests;
    }
    public int getRoomNumber() {
        return roomNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getNote() {
        return note;
    }

    public int getGuests() {
        return guests;
    }
}
