package Interfaces;

import Models.Room;

import java.util.List;

public interface HotelInstructions {
    void checkIn(String data);
    void availability(String data);
    void checkout(String data);
    void report(String data);
    void find(String data);
    Room importantFind(String data);
    public void unavailable(String data);
}
