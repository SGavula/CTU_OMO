package cz.cvut.fel.omo.device;

import cz.cvut.fel.omo.device.documentation.Documentation;
import cz.cvut.fel.omo.events.Event;

import java.util.ArrayList;
import java.util.Random;

public class CircuitBreakers extends AbstractDevice{
    public CircuitBreakers(String type, int stateOfDevice, int durability, int defaultElectricityCons, int defaultWaterCons, int defaultGasCons, Documentation documentation) {
        super(type, stateOfDevice, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
    }

    public void handleEvent(Event event) {}

    @Override
    public String toString() {
        return "Lamp{" +
                "stateOfDevice=" + stateOfDevice +
                ", durability=" + durability +
                '}';
    }
}
