package hotelCommands;

import interfaces.executeCommands;

public class availabilityCommand implements executeCommands {
    private hotelManager hotelManager;
    private String data;

    public availabilityCommand(hotelManager hotelManager, String data) {
        this.hotelManager = hotelManager;
        this.data=data;
    }

    @Override
    public void execute() {
        hotelManager.availability(data);
    }
}
