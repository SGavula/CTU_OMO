package cz.cvut.fel.omo.device;

import cz.cvut.fel.omo.events.*;
import cz.cvut.fel.omo.device.documentation.Documentation;
import cz.cvut.fel.omo.person.PersonFactory;
import cz.cvut.fel.omo.state.IdleState;
import cz.cvut.fel.omo.state.OffState;
import cz.cvut.fel.omo.state.ActiveState;
import cz.cvut.fel.omo.state.StateOfDevice;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import static cz.cvut.fel.omo.utils.Constatnts.*;

public abstract class AbstractDevice <T> implements EventListener {
    private static Logger logger = Logger.getLogger(AbstractDevice.class.getName());
    protected String type;
    protected StateOfDevice stateOfDevice;
    protected Consumption consumption;
    protected float durability;
    protected boolean isAvail;
    protected int defaultElectricityCons;
    protected int defaultWaterCons;
    protected int defaultGasCons;
    protected EventService eventService = new EventService();
    protected ArrayList<Event> eventList = new ArrayList<>();
    protected ArrayList<EventReportElement> eventReportElementList = new ArrayList<>();

    protected Documentation documentation;

    public AbstractDevice(String type, int stateOfDevice, int durability, int defaultElectricityCons, int defaultWaterCons, int defaultGasCons, Documentation documentation) {
        this.type = type;
        if (stateOfDevice == 0) {
            this.stateOfDevice = new OffState();
        } else if (stateOfDevice == 1) {
            this.stateOfDevice = new ActiveState();
        } else if (stateOfDevice == 2) {
            this.stateOfDevice = new IdleState();
        } else {
            this.stateOfDevice = new OffState();
            logger.warning("ERROR: Unknown device state. State has been set to OffDeviceState.");
        }
        this.consumption = new Consumption();
        this.defaultElectricityCons = defaultElectricityCons;
        this.defaultWaterCons = defaultWaterCons;
        this.defaultGasCons = defaultGasCons;
        this.durability = durability;
        this.isAvail = true;
        this.documentation = documentation;
    }

    public void turnOn() {
        stateOfDevice.turnOn(this);
        this.isAvail = false;
    }

    public void turnOff() {
        stateOfDevice.turnOff(this);
        this.isAvail = true;
    }

    public void idle() {
        stateOfDevice.idle(this);
        this.isAvail = true;
    }

    public void print(){
        System.out.print("            ");
        System.out.println(this.type);
    }

    /**
     * Prints the type and consumption details of the object.
     */
    public void printConsumption(){
        System.out.println(this.type);
        System.out.println(
                this.getConsumption().getElectricityConsumption() + " " +
                        this.getConsumption().getGasConsumption() + " " +
                        this.getConsumption().getWaterConsumption()
        );
    }

    /**
     * Prints events of a specific type (Weather or Device) for the object.
     *
     * @param typeOfEvent The type of event to print ("Weather" or "Device").
     */
    public void printEvents(String typeOfEvent){
        boolean tmp_weather = false;
        boolean weatherEventAvail = false;
        boolean deviceEventAvail = false;

        if(this.eventReportElementList.isEmpty()){
            return;
        }

        // check if there are any weather events
        for (EventReportElement eventReportElement : eventReportElementList) {
            if (eventReportElement.getEvent() instanceof WeatherEvent) {
                weatherEventAvail = true;
            }
        }

        // check if there are any device events
        for (EventReportElement eventReportElement : eventReportElementList) {
            if (eventReportElement.getEvent() instanceof DeviceEvent) {
                deviceEventAvail = true;
            }
        }

        if(typeOfEvent.equals("Weather")) {
            tmp_weather = true;
            if(!weatherEventAvail){
                return;
            }
        }
        else if(!tmp_weather && !deviceEventAvail){
            return;
        }

        System.out.printf("    ");
        System.out.println(this.getType() + ": ");

        int eventNum = 0;
        for (EventReportElement eventReportElement : eventReportElementList) {
            eventNum++;
            System.out.printf("        ");
            System.out.println("Event " + eventNum + ": ");
            if(tmp_weather && weatherEventAvail) {
                if (eventReportElement.getEvent() instanceof WeatherEvent) {
                    eventReportElement.print();
                    continue;
                }
            }
            else if(deviceEventAvail &&  eventReportElement.getEvent() instanceof DeviceEvent) {
                eventReportElement.print();
            }
        }
    }


    /**
     * Saves consumption details to a file.
     */
    public void saveConsumptionToFile() {
        double totalElecPrice = this.getConsumption().getElectricityConsumption() * ELECTRICITY_PRICE;
        double totalGasPrice = this.getConsumption().getGasConsumption() * GAS_PRICE;
        double totalWaterPrice = this.getConsumption().getWaterConsumption() * WATER_PRICE;

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(CONSUMPTION_FILE_PATH, true));

            // Write content to the file
            writer.write(String.format("%-20s%-28s%-20s%-20s%-28s%-20s%-20s%n",
                    this.type,
                    this.getConsumption().getElectricityConsumption(),
                    this.getConsumption().getGasConsumption(),
                    this.getConsumption().getWaterConsumption(),
                    String.format("%.2f", totalElecPrice) + " $",
                    String.format("%.2f", totalGasPrice) + " $",
                    String.format("%.2f", totalWaterPrice) + " $"
            ));

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double calculateConsumption(double currCons, double defaultCons, double constant) {
        return currCons + (defaultCons * constant);
    }

    /**
     * Updates consumption values based on device states.
     */
    public void updateConsumption() {
        // Update electricity consumption
        double resElectricity = calculateConsumption(this.consumption.getElectricityConsumption(), this.defaultElectricityCons, this.stateOfDevice.getElectricConsumption());
        this.consumption.setElectricityConsumption(resElectricity);
        // Update water consumption
        double resWater = calculateConsumption(this.consumption.getWaterConsumption(), this.defaultWaterCons, this.stateOfDevice.getWaterConsumption());
        this.consumption.setWaterConsumption(resWater);
        // Update gas consumption
        double resGas = calculateConsumption(this.consumption.getGasConsumption(), this.defaultGasCons, this.stateOfDevice.getGasConsumption());
        this.consumption.setGasConsumption(resGas);
    }

    public void decreaseDurability() {
        this.durability = this.durability - DAMAGE;
    }

    public void increaseDurability() {
        Random random = new Random();
        int newDurability = random.nextInt(101) + 60;
        this.durability = this.durability + newDurability;
    }

    /**
     * Generates and executes a random event.
     *
     * If there are no events available, prints a message and exits.
     * Randomly selects an event from the list and executes it.
     * Subscribes and notifies observers for the event, then saves it to the report.
     */
    public void generateEvent() {
        Random random = new Random();
        Event randomEvent = null;

        // Get the event
        if(this.eventList.isEmpty()) {
            logger.warning("No event to execute.");
            return;
        }

        if(this.durability <= 0){
            for(Event event : eventList){
                if(event.getDescription().equals("The device got broken.")){
                    randomEvent = event;
                    break;
                }
            }
        }
        else {
            int randomIndex = random.nextInt(eventList.size());
            randomEvent = eventList.get(randomIndex);
            if(randomEvent.getDescription().equals("The device got broken.")){
                this.durability = 0;
            }
        }

        // Save device to the event
        randomEvent.setDevice(this);
        randomEvent.printDescription();

        // Subscribe people to the observer
        for(Object target : randomEvent.getTargets()) {
            eventService.subscribe(target);
        }
        eventService.notifyTargets(randomEvent);

        // Unsubscribe people from the observer
        for(Object target : randomEvent.getTargets()) {
            eventService.unsibscribe(target);
        }

        // Saving the event to the report
        eventReportElementList.add(new EventReportElement(randomEvent, randomEvent.getTargets()));
    }

    public void eventHandler() {}

    public void addEventList(ArrayList<Event> eventList){
        this.eventList.addAll(eventList);
    }

    public String getType() {
        return type;
    }

    public StateOfDevice getStateOfDevice() {
        return stateOfDevice;
    }

    public void setStateOfDevice(StateOfDevice stateOfDevice) {
        this.stateOfDevice = stateOfDevice;
    }

    public boolean isAvail() {
        return isAvail;
    }

    public void setAvail(boolean avail) {
        isAvail = avail;
    }

    public int getDefaultElectricityCons() {
        return defaultElectricityCons;
    }

    public int getDefaultWaterCons() {
        return defaultWaterCons;
    }

    public int getDefaultGasCons() {
        return defaultGasCons;
    }

    public Consumption getConsumption() {
        return consumption;
    }

    public Documentation getDocumentation() {
        return documentation;
    }

    public float getDurability() {
        return durability;
    }

    public void setEventList(ArrayList<Event> eventList){
        this.eventList = eventList;
    }

    @Override
    public String toString() {
        return "Device{" +
                "type='" + type + '\'' +
                ", stateOfDevice=" + stateOfDevice +
                ", durability=" + durability +
                '}';
    }
}