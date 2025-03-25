package HotelCommands;

import Interfaces.ExecuteCommands;

public class UnavailableCommand implements ExecuteCommands {
    private HotelManager hotelManager;
    private String data;

    public UnavailableCommand(HotelManager hotelManager, String data) {
        this.hotelManager = hotelManager;
        this.data=data;
    }

    @Override
    public void execute() {
        hotelManager.unavailable(data);
    }
}
