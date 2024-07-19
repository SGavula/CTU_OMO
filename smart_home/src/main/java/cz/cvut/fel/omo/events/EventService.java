package cz.cvut.fel.omo.events;

import java.util.ArrayList;
import cz.cvut.fel.omo.events.Event;
import cz.cvut.fel.omo.events.EventListener;

public class EventService <T extends EventListener> {
    private final ArrayList<T> targets;
    public EventService() {
        this.targets = new ArrayList<T>();
    }
    public void subscribe(Object target) {
        this.targets.add((T) target);
    }
    public void unsibscribe(Object target) {
        this.targets.remove(target);
    }
    public void notifyTargets(Event event) {
        // Execute the action to event
        targets.forEach(target -> target.handleEvent(event));
    }
}
