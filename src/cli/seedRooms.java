package cli;

import hotelCommands.hotelManager;
import models.Room;

public class seedRooms {
    public static void seedRooms(hotelManager hotelManager){
        hotelManager.rooms.put(101,new Room(101,2));
        hotelManager.rooms.put(102, new Room(102,2));
        hotelManager.rooms.put(103, new Room(103,4));
    }
}