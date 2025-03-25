package Interfaces;

import Models.Room;

import java.util.List;

public interface HotelInstructions {
    void checkIn(String data);
    void availability(String data);
    void checkout(int roomNumber);
    void report(String data);
    void find(String data);
    Room importantFind();
    public void unavailable(String data);
}
