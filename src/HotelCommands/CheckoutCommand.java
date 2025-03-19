package HotelCommands;

import Interfaces.ExecuteCommands;

public class CheckoutCommand implements ExecuteCommands {
    private HotelManager hotelManager;
    private String data;

    public CheckoutCommand(HotelManager hotelManager, String data) {
        this.hotelManager = hotelManager;
        this.data = data;
    }

    @Override
    public void execute() {
        hotelManager.checkout(Integer.parseInt(data));
    }
}
