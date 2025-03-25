package HotelCommands;

import Interfaces.ExecuteCommands;

public class FindCommand implements ExecuteCommands {
    private HotelManager hotelManager;
    private String data;

    public FindCommand(HotelManager hotelManager, String data) {
        this.hotelManager = hotelManager;
        this.data = data;
    }

    @Override
    public void execute() {
        hotelManager.find(data);
    }
}
