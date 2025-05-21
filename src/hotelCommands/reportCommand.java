package hotelCommands;

import interfaces.executeCommands;

public class reportCommand implements executeCommands {
    private hotelManager hotelManager;
    private String data;

    public reportCommand(hotelManager hotelManager, String data) {
        this.hotelManager = hotelManager;
        this.data=data;
    }

    @Override
    public void execute() {
        hotelManager.report(data);
    }
}
