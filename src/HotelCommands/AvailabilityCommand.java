package HotelCommands;

import Interfaces.ExecuteCommands;

public class AvailabilityCommand implements ExecuteCommands {
    private HotelManager hotelManager;
    private String data;
    public AvailabilityCommand(HotelManager hotelManager, String data) {
        this.hotelManager = hotelManager;
        this.data=data;
    }

    @Override
    public void execute() {
        hotelManager.availability(data);
    }
}
