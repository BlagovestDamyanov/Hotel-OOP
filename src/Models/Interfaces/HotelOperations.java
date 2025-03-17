package Models.Interfaces;

import Models.Room;

import java.time.LocalDate;
import java.util.List;

public interface HotelOperations {
    void checkIn(int roomNumber, LocalDate startDate, LocalDate endDate, String note, int numberGuests);
    List<Room> availability(LocalDate date);
    void checkOut(int roomNumber);
    void Report(LocalDate startDate, LocalDate ednDate);
    Room find(int beds , LocalDate startDate, LocalDate endDate);
    Room importantFind(int beds , LocalDate startDate, LocalDate endDate);
    void unavailable(int roomNumber, LocalDate startDate, LocalDate endDate);
}
