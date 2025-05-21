package models;

import java.time.LocalDate;

/**
 * Represents a booking for a hotel room.
 * Contains details about the booked room number, booking period,
 * number of guests, and an optional note.
 */
public class Booking {
    private LocalDate startDate;
    private LocalDate endDate;
    private int roomNumber;
    private int guests;
    private String note;

    /**
     * Constructs a new Booking instance.
     *
     * @param roomNumber the room number being booked
     * @param startDate the start date of the booking period
     * @param endDate the end date of the booking period
     * @param note additional notes for the booking (e.g., guest requests)
     * @param guests the number of guests for this booking
     */
    public Booking(int roomNumber, LocalDate startDate, LocalDate endDate, String note, int guests) {
        this.roomNumber = roomNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.note = note;
        this.guests = guests;
    }

    /**
     * Returns the booked room number.
     *
     * @return the room number
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Returns the start date of the booking.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Returns the end date of the booking.
     *
     * @return the end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Returns any additional notes related to the booking.
     *
     * @return the booking note
     */
    public String getNote() {
        return note;
    }

    /**
     * Returns the number of guests included in the booking.
     *
     * @return number of guests
     */
    public int getGuests() {
        return guests;
    }

    /**
     * Returns a string representation of the booking details.
     *
     * @return string describing the booking
     */
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
