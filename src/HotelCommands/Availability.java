package HotelCommands;

import Interfaces.ExecuteCommands;

public class Availability implements ExecuteCommands {
    private HotelManager hotelManager;
    private String data;
    public Availability(HotelManager hotelManager,String data) {
        this.hotelManager = hotelManager;
        this.data=data;
    }

    @Override
    public void execute() {
        hotelManager.availability(data);
    }
}
