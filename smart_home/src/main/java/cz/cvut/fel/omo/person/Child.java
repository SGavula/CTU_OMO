package cz.cvut.fel.omo.person;

import cz.cvut.fel.omo.events.Event;

import java.util.logging.Logger;

public class Child extends Person {
    private static Logger logger = Logger.getLogger(Child.class.getName());

    public Child(String name, int age, int room_loc) {
        super(name, age, room_loc);
    }

    /**
     * Handles the given event based on the child's actions.
     *
     * Prints messages indicating the actions the child takes in response to specific event types.
     * The actions are specific to different event IDs.
     *
     * @param event The event to be handled.
     */
    public void handleEvent(Event event) {
        if(this.isFree() == false) {
            return;
        }
        if(this.name == event.getSource()){
            return;
        }

        switch (event.getId()) {
            case 2:
                System.out.println(this.name + "(Child): feeding the pet with name: " + event.getSource());
                break;

            case 3:
                System.out.println(this.name + "(Child): trying to find the glasses.");
                break;

            case 5:
                System.out.println(this.name + "(Child): trying to find the glasses.");
                break;
            default:
                logger.warning(this.name + ": Child does not know this event. " + event.getDescription());
                break;
        }
    }
}
