package HotelCommands;

import Interfaces.ExecuteCommands;

public class CheckInCommand implements ExecuteCommands {
    private HotelManager hotelManager;
    private String data;
    public CheckInCommand(HotelManager hotelManager, String data) {
        this.hotelManager = hotelManager;
        this.data=data;
    }

    @Override
    public void execute() {
        hotelManager.checkIn(data);
    }
}
