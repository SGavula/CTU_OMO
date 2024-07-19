package cz.cvut.fel.omo.device;

import cz.cvut.fel.omo.device.documentation.Documentation;
import cz.cvut.fel.omo.events.Event;

import java.util.logging.Logger;

public class Thermostat extends AbstractDevice {
    private static Logger logger = Logger.getLogger(Thermostat.class.getName());
    public Thermostat(String type, int stateOfDevice, int durability, int defaultElectricityCons, int defaultWaterCons, int defaultGasCons, Documentation documentation) {
        super(type, stateOfDevice, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
    }

    public void handleEvent(Event event) {
        if(this.isAvail() == false) {
            return;
        }

        switch (event.getId()) {
            case 10:
                System.out.println(this.type + ": Turning on the thermostat.");
                this.turnOn();
                break;
            case 11:
                System.out.println(this.type + ": Turning off the thermostat.");
                this.turnOff();
                break;
            case 6:
                System.out.println(this.type + ": Turning off device");
                this.turnOff();
                break;
            default:
                logger.warning(this.type + ": Unknown event: " + event.getDescription());
                break;
        }
    }

    @Override
    public String toString() {
        return "Thermostat{" +
                "stateOfDevice=" + stateOfDevice +
                ", durability=" + durability +
                '}';
    }
}
