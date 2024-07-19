package cz.cvut.fel.omo.events;

import cz.cvut.fel.omo.device.AbstractDevice;
import cz.cvut.fel.omo.person.Person;

import java.util.ArrayList;

public class DeviceEvent<T> extends Event {
    public DeviceEvent(int id, String description, String source, ArrayList<T> targets) {
        super(id, description, source, targets);
    }
}
