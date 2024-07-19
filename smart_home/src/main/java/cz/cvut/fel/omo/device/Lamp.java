package cz.cvut.fel.omo.device;

import cz.cvut.fel.omo.device.documentation.Documentation;
import cz.cvut.fel.omo.events.Event;
import cz.cvut.fel.omo.person.PersonFactory;

import java.util.logging.Logger;

public class Lamp extends AbstractDevice {
    private static Logger logger = Logger.getLogger(Lamp.class.getName());

    public Lamp(String type, int stateOfDevice, int durability, int defaultElectricityCons, int defaultWaterCons, int defaultGasCons, Documentation documentation) {
        super(type, stateOfDevice, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
    }

    public void handleEvent(Event event) {
        if(this.isAvail() == false) {
            return;
        }

        switch (event.getId()) {
            case 8:
                System.out.println(this.type + ": Turning on the light.");
                this.turnOn();
                break;
            case 9:
                System.out.println(this.type + ": Turning off the light.");
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
        return "Lamp{" +
                "stateOfDevice=" + stateOfDevice +
                ", durability=" + durability +
                '}';
    }
}