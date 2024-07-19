package cz.cvut.fel.omo;

import cz.cvut.fel.omo.events.Event;
import cz.cvut.fel.omo.events.EventListener;
import cz.cvut.fel.omo.events.EventReportElement;
import cz.cvut.fel.omo.events.EventService;
import cz.cvut.fel.omo.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Pet <T> implements EventListener {
    private static Logger logger = Logger.getLogger(Pet.class.getName());
    private String name;
    private String type;
    private int room_loc;
    private boolean isFree;
    private EventService eventService = new EventService();
    private ArrayList<Event> eventList = new ArrayList<>();
    private ArrayList<EventReportElement> eventReportElementList = new ArrayList<>();

    /**
     * Constructs a Pet object with the specified name, type, and room location.
     *
     * @param name      The name of the pet.
     * @param type      The type or breed of the pet.
     * @param room_loc  The room location where the pet resides.
     */
    public Pet(String name, String type, int room_loc) {
        this.name = name;
        this.type = type;
        this.room_loc = room_loc;
        this.isFree = true;
        this.eventService = new EventService();
    }

    /**
     * Prints information about the pet, including its name and type.
     */
    public void print(){
        System.out.print("    ");
        System.out.println(this.name + ": ");
        System.out.printf("        ");
        System.out.println("Type: " + this.type);
    }

    /**
     * Generates and executes a random event for the pet.
     */
    public void generateEvent() {
        Random random = new Random();
        // Get the event
        if(this.eventList.isEmpty()) {
            logger.warning("No event to execute.");
            return;
        }

        int randomIndex = random.nextInt(eventList.size());
        Event randomEvent = eventList.get(randomIndex);
        randomEvent.printDescription();

        // Subscribe 1 person
        // Subscribe people to the observer
        ArrayList<Object> targets = randomEvent.getTargets();
        if(targets.isEmpty()) {
            logger.warning("No targets to notify.");
            return;
        }
        int randomTargetIndex = random.nextInt(targets.size());
        eventService.subscribe(targets.get(randomTargetIndex));

        eventService.notifyTargets(randomEvent);

        // Unsubscribe people from the observer
        for(Object person : randomEvent.getTargets()) {
            eventService.unsibscribe(person);
        }

        // Save the event to the report
        eventReportElementList.add(new EventReportElement(randomEvent, targets.get(randomTargetIndex)));
    }

    /**
     * Prints a summary of the events executed by the pet.
     */
    public void printEvents(){
        int eventNum = 0;
        for(EventReportElement eventReportElement :  eventReportElementList){
            eventNum++;
            System.out.printf("        ");
            System.out.println("Event " + eventNum + ": ");
            eventReportElement.print();
        }
    }

    public void addEventList(ArrayList<Event> eventList){
        this.eventList.addAll(eventList);
    }

    @Override
    public void handleEvent(Event event) {}

    public boolean isFree() {
        return isFree;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", room_loc=" + room_loc +
                '}';
    }
}
