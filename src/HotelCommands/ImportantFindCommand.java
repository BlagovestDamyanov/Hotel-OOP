package HotelCommands;

import Interfaces.ExecuteCommands;

public class ImportantFindCommand implements ExecuteCommands {
    private HotelManager hotelManager;
    private String data;

    public ImportantFindCommand(HotelManager hotelManager, String data) {
        this.hotelManager = hotelManager;
        this.data = data;
    }

    @Override
    public void execute() {
        hotelManager.importantFind(data);
    }
}
