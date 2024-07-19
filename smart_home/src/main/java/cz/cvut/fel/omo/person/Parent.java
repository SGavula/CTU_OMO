package cz.cvut.fel.omo.person;

import cz.cvut.fel.omo.device.AbstractDevice;
import cz.cvut.fel.omo.device.documentation.Documentation;
import cz.cvut.fel.omo.events.Event;

import java.util.logging.Logger;

public class Parent extends Person {
    private static Logger logger = Logger.getLogger(Parent.class.getName());

    public Parent(String name, int age, int room_loc) {
        super(name, age, room_loc);
    }

    /**
     * Handles the given event based on the parent's actions.
     *
     * Prints messages indicating the actions the parent takes in response to the event.
     * The actions are specific to different event types.
     *
     * @param event The event to be handled.
     */
    public void handleEvent(Event event) {
        if(this.isFree() == false) {
            System.out.println("Parent with name: " + this.name + " is not free.");
            return;
        }
        if(this.name.equals(event.getSource())){
            return;
        }

        System.out.println("this.name: " + this.name + ", event.source: " + event.getSource() );

        switch (event.getId()) {
            case 0:
                System.out.println(this.name + "(Parent): changing the baby's diaper.");
                break;
            case 1:
                System.out.println(this.name + "(Parent): reading baby a fairytale.");
                break;
            case 2:
                System.out.println(this.name + "(Parent): feeding the pet with name: " + event.getSource());
                break;
            case 3:
                System.out.println(this.name + "(Parent): trying to find the glasses.");
                break;
            case 4:
                System.out.println(this.name + "(Parent): helping with the homework.");
                break;
            case 5:
                System.out.println(this.name + "(Parent): trying to find the glasses.");
                break;
            case 6:
                System.out.println(this.name + "(Parent): resetting the circuit breaker.");
                break;
            case 7:
                System.out.println(this.name + "(Parent): went to find the manual.");
                // Fixing the device
                AbstractDevice device = event.getDevice();
                Documentation documentation = device.getDocumentation();
                documentation.readWarrantyCard();
                documentation.readRepairManual();
                device.increaseDurability();
                break;
            default:
                logger.warning(this.name + ": Parent does not know this event. " + event.getDescription());
                break;
        }
    }
}
