package hotelCommands;

import interfaces.executeCommands;

public class findCommand implements executeCommands {
    private hotelManager hotelManager;
    private String data;

    public findCommand(hotelManager hotelManager, String data) {
        this.hotelManager = hotelManager;
        this.data = data;
    }

    @Override
    public void execute() {
        hotelManager.find(data);
    }
}
