package hotelCommands;

import interfaces.executeCommands;

public class checkInCommand implements executeCommands {
    private hotelManager hotelManager;
    private String data;

    public checkInCommand(hotelManager hotelManager, String data) {
        this.hotelManager = hotelManager;
        this.data=data;
    }

    @Override
    public void execute() {
        hotelManager.checkIn(data);
    }
}
