package cz.cvut.fel.omo.events;

import java.util.ArrayList;

public class WeatherEvent <T> extends Event {
    public WeatherEvent(int id, String description, String source, ArrayList targets) {
        super(id, description, source, targets);
    }
}
