package interfaces;

import models.Room;

public interface hotelInstructions {
    void checkIn(String data);
    void availability(String data);
    void checkout(String data);
    void report(String data);
    void find(String data);
    Room importantFind(String data);
    public void unavailable(String data);
}
