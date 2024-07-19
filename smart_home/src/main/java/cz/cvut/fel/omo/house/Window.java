package cz.cvut.fel.omo.house;

import cz.cvut.fel.omo.events.Event;
import cz.cvut.fel.omo.events.EventListener;

import java.util.logging.Logger;

public class Window <T> implements EventListener {
    private static Logger logger = Logger.getLogger(Window.class.getName());
    private boolean areBlindsOpened;

    /**
     * Constructs a Window object with the specified initial state of blinds.
     *
     * @param areBlindsOpened {@code true} if the blinds are initially opened, {@code false} otherwise.
     */
    public Window(boolean areBlindsOpened) {
        this.areBlindsOpened = areBlindsOpened;
    }

    /**
     * Prints the current state of the window blinds.
     */
    public void print() {
        if(this.areBlindsOpened) {
            System.out.println("          Window has opened blinds");
        } else {
            System.out.println("          Window has closed blinds");
        }
    }

    /**
     * Handles the incoming event to control the window blinds.
     *
     * @param event The event to be handled.
     */
    @Override
    public void handleEvent(Event event) {
        switch (event.getId()) {
            case 12:
                if(this.areBlindsOpened == true) {
                    this.areBlindsOpened = false;
                    System.out.println("Closing blinds.");
                }
                break;
            case 13:
                if(this.areBlindsOpened == false) {
                    this.areBlindsOpened = true;
                    System.out.println("Opening blinds.");
                }
                break;
            default:
                logger.warning("House does not know this event.");
                break;
        }
    }
}
