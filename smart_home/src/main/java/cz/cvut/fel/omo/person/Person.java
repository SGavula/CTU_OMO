package cz.cvut.fel.omo.person;

import cz.cvut.fel.omo.house.Garage;
import cz.cvut.fel.omo.device.AbstractDevice;
import cz.cvut.fel.omo.house.Room;
import cz.cvut.fel.omo.device.CircuitBreakers;
import cz.cvut.fel.omo.device.LightSensor;
import cz.cvut.fel.omo.device.Thermostat;
import cz.cvut.fel.omo.events.Event;
import cz.cvut.fel.omo.events.EventListener;
import cz.cvut.fel.omo.events.EventReportElement;
import cz.cvut.fel.omo.events.EventService;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.util.logging.Logger;

public abstract class Person <T> implements EventListener {
    private static Logger logger = Logger.getLogger(Person.class.getName());
    protected String name;
    protected int age;
    protected int room_loc;
    protected boolean isFree;
    protected int timeForUsingDevice;
    protected AbstractDevice device;
    protected EventService eventService;
    protected boolean sportEquip;
    protected ArrayList<Event> eventList = new ArrayList<>();
    Map<AbstractDevice, Integer> activityReport = new HashMap<AbstractDevice, Integer>();

    protected ArrayList<EventReportElement> eventReportElementList = new ArrayList<>();

    public Person(String name, int age, int room_loc) {
        this.name = name;
        this.age = age;
        this.room_loc = room_loc;
        this.isFree = true;
        this.eventService = new EventService();
    }

    public void print(){
        System.out.print("        ");
        System.out.println(this.name + ": ");
        System.out.printf("            ");
        System.out.println("Age: " + this.age);
    }

    public void printEvents(){
        int eventNum = 0;
        for(EventReportElement eventReportElement :  eventReportElementList){
            eventNum++;
            System.out.printf("        ");
            System.out.println("Event " + eventNum + ": ");
            eventReportElement.print();
        }
    }

    private AbstractDevice getFirstAvailableDevice(ArrayList<AbstractDevice> devices) {
        for(AbstractDevice device : devices) {
            if(device instanceof Thermostat || device instanceof LightSensor || device instanceof CircuitBreakers) {
                continue;
            }

            if(device.isAvail() == true && device.getDurability() > 0) {
                return device;
            }
        }
        return null;
    }


    private void updateActivityReport() {
        // Check if the device is already in the map
        if (this.activityReport.containsKey(this.device)) {
            // If yes, increment the count by 1
            int currentCount = this.activityReport.get(this.device);
            this.activityReport.put(this.device, currentCount + 1);
        } else {
            // If no, add the device to the map with a count of 1
            this.activityReport.put(this.device, 1);
        }
    }

    private void startUsingDevice(Room room, int time, Random random) {
        this.device = getFirstAvailableDevice(room.getDevices());

        if (this.device == null) {
            logger.warning("There are no more available devices in this room.");
            return;
        }

        updateActivityReport();
        room.setNumOfAvailDevices(room.getNumOfAvailDevices() - 1);
        this.device.turnOn();

        int randomTime = random.nextInt(16) + 5;
        this.timeForUsingDevice = time - randomTime;
        this.isFree = false;

        this.device.decreaseDurability();
        System.out.println("Person with name: " + this.name + " started using " + this.device.getType());
    }

    private void goToOtherRoom(ArrayList<Room> rooms, Random random) {
        int upperBound = rooms.size() - 1;
        int randomRoom = random.nextInt(upperBound);

        while(rooms.get(randomRoom).getId() == this.room_loc) {
            randomRoom = random.nextInt(upperBound);
        }
        this.room_loc = rooms.get(randomRoom).getId();

        System.out.println("Person with name: " + this.name + " goes to the " + rooms.get(randomRoom).getType());
    }

    private void goingOut(Garage garage, int randomTime, int time) {
        this.room_loc = garage.getLocationId();
        this.timeForUsingDevice = time - randomTime;
        this.isFree = false;
        String sportEqipType = this.sportEquip ? "skis" : "bikes";
        System.out.println("Person with name: " + this.name + " goes out with " + sportEqipType);
    }

    private void goOut(Garage garage, int time, Random random) {
        int randomTime = random.nextInt(16) + 5;
        this.sportEquip = random.nextBoolean();

        if (this.sportEquip) {
            if (garage.takeSkis()) {
                goingOut(garage, randomTime, time);
            }
        } else {
            if (garage.takeBike()) {
                goingOut(garage, randomTime, time);
            }
        }
    }

    private void selectActivity(Garage garage, ArrayList<Room> rooms, Room room, int time, Random random) {
        double bound = Math.random();

        if (bound < 0.4 && room.getNumOfAvailDevices() != 0) {
            startUsingDevice(room, time, random);
        } else if (bound < 0.5) {
            goToOtherRoom(rooms, random);
        } else {
            goOut(garage, time, random);
        }
    }

    private void handleOccupiedState() {
        if (this.room_loc == 0) {
            System.out.println("Person: " + name + " is outside.");
        } else {
            if (this.device != null && this.device.getDurability() > 0) {
                System.out.println("Person: " + name + " is not free. The person is using device: " + device.getType());
                this.device.decreaseDurability();
            }
        }
    }

    private void handleOutsideState(Garage garage) {
        if (this.sportEquip) {
            garage.decreaseNumOfSkisInUsage();
        } else {
            garage.decreaseNumOfBikesInUsage();
        }
        System.out.println("Person with name: " + this.name + " came home.");
    }

    private void handleInsideState(Room room) {
        room.setNumOfAvailDevices(room.getNumOfAvailDevices() + 1);
        double bound = Math.random();

        if (bound > 0.5) {
            this.device.turnOff();
        } else {
            this.device.idle();
        }

        System.out.println("Person with name: " + this.name + " finish using " + this.device.getType());
    }

    private void handleEndOfActivity(Garage garage, Room room) {
        this.isFree = true;

        if (this.room_loc == 0) {
            handleOutsideState(garage);
        } else {
            handleInsideState(room);
        }
    }

    private void doOrEndActivity(Garage garage, Room room, int time) {
        if (time > timeForUsingDevice) {
            handleOccupiedState();
        } else {
            handleEndOfActivity(garage, room);
        }
    }

    public void performActivity(ArrayList<Room> rooms, int time, Garage garage) {
        Random random = new Random();
        Room room = rooms.get(this.room_loc);

        if (this.isFree) {
            selectActivity(garage, rooms, room, time, random);
        } else {
            doOrEndActivity(garage, room, time);
        }
    }

    /**
     * Generates and executes a random event.
     *
     * If there are no events available, prints a message and exits.
     * Selects a random event from the list, prints its description,
     * subscribes one random target to the event, notifies targets,
     * and saves the event to the report.
     */
    public void generateEvent() {
        Random random = new Random();
        // Get the event
        if (this.eventList.isEmpty()) {
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
            return;
        }

        int randomTargetIndex;

        while(true) {
            randomTargetIndex = random.nextInt(targets.size());
            if(this != targets.get(randomTargetIndex)){
                break;
            }
        }

        eventService.subscribe(targets.get(randomTargetIndex));

        eventService.notifyTargets(randomEvent);

        // Unsubscribe people from the observer
        for(Object person : randomEvent.getTargets()) {
            eventService.unsibscribe(person);
        }

        // Saving the event to the report
        eventReportElementList.add(new EventReportElement(randomEvent, targets.get(randomTargetIndex)));
    }

    public void addEventList(ArrayList<Event> eventList){
        this.eventList.addAll(eventList);
    }

    public void handleEvent(Event event) {}

    public String getName(){
        return this.name;
    }

    public boolean isFree() {
        return isFree;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<Event> eventList){
        this.eventList = eventList;
    }

    public Map<AbstractDevice, Integer> getActivityReport() {
        return activityReport;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", room_loc=" + room_loc +
                '}';
    }
}
