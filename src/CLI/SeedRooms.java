package CLI;

import HotelCommands.HotelManager;
import Models.Room;

public class SeedRooms {
    public static void seedRooms(HotelManager hotelManager){
        hotelManager.rooms.put(101,new Room(101,2));
        hotelManager.rooms.put(102, new Room(102,2));
        hotelManager.rooms.put(103, new Room(103,4));
    }
}
