package hotelCommands;

import interfaces.executeCommands;

public class unavailableCommand implements executeCommands {
    private hotelManager hotelManager;
    private String data;

    public unavailableCommand(hotelManager hotelManager, String data) {
        this.hotelManager = hotelManager;
        this.data=data;
    }

    @Override
    public void execute() {
        hotelManager.unavailable(data);
    }
}
