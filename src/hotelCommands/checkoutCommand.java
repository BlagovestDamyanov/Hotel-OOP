package hotelCommands;

import interfaces.executeCommands;

public class checkoutCommand implements executeCommands {
    private hotelManager hotelManager;
    private String data;

    public checkoutCommand(hotelManager hotelManager, String data) {
        this.hotelManager = hotelManager;
        this.data = data;
    }

    @Override
    public void execute() {
        hotelManager.checkout(data);
    }
}
