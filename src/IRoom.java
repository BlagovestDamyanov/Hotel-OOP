import java.util.Date;
import java.util.List;

public interface IRoom {
    void checkIn(int roomNumber, Date startDate, Date endDate, String note, int numberGuests);
    List<Room> availability(Date date);
    void checkOut(int roomNumber);
    void Report(Date startDate, Date ednDate);
    Room find(int beds , Date startDate, Date endDate);
    Room importantFind(int beds , Date startDate, Date endDate);
    void unavailable(int roomNumber, Date startDate, Date endDate);
}
