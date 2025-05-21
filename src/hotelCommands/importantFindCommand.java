package hotelCommands;

import interfaces.executeCommands;

public class importantFindCommand implements executeCommands {
    private hotelManager hotelManager;
    private String data;

    public importantFindCommand(hotelManager hotelManager, String data) {
        this.hotelManager = hotelManager;
        this.data = data;
    }

    @Override
    public void execute() {
        hotelManager.importantFind(data);
    }
}
