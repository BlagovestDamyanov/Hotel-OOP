package Interfaces;

import Models.Room;

import java.util.List;

public interface HotelInstructions {
    void checkIn();
    List<String> availability();
    void checkout();
    void report();
    Room find();
    Room importantFind();
    void unavailable();
}
