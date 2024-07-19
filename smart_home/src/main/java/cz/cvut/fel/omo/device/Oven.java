package cz.cvut.fel.omo.device;

import cz.cvut.fel.omo.device.documentation.Documentation;
import cz.cvut.fel.omo.events.Event;

public class Oven extends AbstractDevice {
    public Oven(String type, int stateOfDevice, int durability, int defaultElectricityCons, int defaultWaterCons, int defaultGasCons, Documentation documentation) {
        super(type, stateOfDevice, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
    }

    @Override
    public void handleEvent(Event event) {}
}
