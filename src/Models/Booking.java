package Models;

import java.time.LocalDate;
import java.util.Date;

public class Booking {
    private Date startDate;
    private Date endDate;
    private int roomNumber;
    private int guests;
    private String note;

    public Booking(int roomNumber, Date startDate, Date endDate, String note, int guests) {
        this.roomNumber = roomNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.note = note;
        this.guests = guests;
    }
    public int getRoomNumber() {
        return roomNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getNote() {
        return note;
    }

    public int getGuests() {
        return guests;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", roomNumber=" + roomNumber +
                ", guests=" + guests +
                ", note='" + note + '\'' +
                '}';
    }
}
