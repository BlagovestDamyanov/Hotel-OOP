package HotelCommands;

import Interfaces.ExecuteCommands;

public class ReportCommand implements ExecuteCommands {
    private HotelManager hotelManager;
    private String data;

    public ReportCommand(HotelManager hotelManager, String data) {
        this.hotelManager = hotelManager;
        this.data=data;
    }

    @Override
    public void execute() {
        hotelManager.report(data);
    }
}
