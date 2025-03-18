package Interfaces;

import Models.Room;

import java.util.List;

public interface HotelInstructions {
    void checkIn(String data);
    void availability(String data);
    void checkout(int roomNumber);
    void report();
    Room find();
    Room importantFind();
    void unavailable();
}
