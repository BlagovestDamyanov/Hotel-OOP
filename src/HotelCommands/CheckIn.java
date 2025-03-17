package HotelCommands;

import Interfaces.ExecuteCommands;

public class CheckIn implements ExecuteCommands {
    private HotelManager hotelManager;
    private String data;
    public CheckIn(HotelManager hotelManager,String data) {
        this.hotelManager = hotelManager;
        this.data=data;
    }

    @Override
    public void execute() {
        hotelManager.checkIn(data);
    }
}
