package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hotel room with a specific number of beds and booking information.
 * Tracks availability and bookings associated with this room.
 */
public class Room {
    private int roomNumber;
    private int beds;
    private boolean isAvailable;
    protected List<Booking> bookings;

    /**
     * Checks if the room is available on a given date.
     *
     * @param date the date to check availability for
     * @return true if the room is not booked on the given date, false otherwise
     */
    public boolean isAvailableOn(LocalDate date) {
        for (Booking booking : bookings) {
            if (!date.isBefore(booking.getStartDate()) && !date.isAfter(booking.getEndDate())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a string representation of the room, including its number, bed count,
     * availability status, and current bookings.
     *
     * @return string describing the room details
     */
    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", beds=" + beds +
                ", isAvailable=" + isAvailable +
                ", bookings=" + bookings.toString() +
                '}';
    }

    /**
     * Constructs a new Room instance.
     *
     * @param roomNumber the number identifying the room
     * @param beds the number of beds in the room
     */
    public Room(int roomNumber, int beds) {
        this.roomNumber = roomNumber;
        this.beds = beds;
        this.isAvailable = true;
        this.bookings = new ArrayList<>();
    }

    /**
     * Returns the room number.
     *
     * @return the room number
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Returns the number of beds in the room.
     *
     * @return the bed count
     */
    public int getBeds() {
        return beds;
    }

    /**
     * Sets the availability status of the room.
     *
     * @param available true if the room is available, false otherwise
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * Adds a booking to this room and marks the room as unavailable.
     *
     * @param booking the booking to add
     */
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
        this.isAvailable = false;
    }

    /**
     * Returns the current availability status of the room.
     *
     * @return true if the room is available, false otherwise
     */
    public boolean isAvailable() {
        return isAvailable;
    }
}
