package cz.cvut.fel.omo.house;

import cz.cvut.fel.omo.device.AbstractDevice;

import java.util.ArrayList;
import java.util.Random;

public class Room implements HouseComponent {
    private int id;
    private String type;
    private int floorLocation;
    private int numOfAvailDevices = 0;
    private ArrayList<AbstractDevice> devices = new ArrayList<>();
    private ArrayList<Window> windows = new ArrayList<>();

    /**
     * Constructs a Room object with the specified parameters.
     *
     * @param id             The unique identifier of the room.
     * @param type           The type or purpose of the room.
     * @param numOfWindows   The number of windows in the room.
     * @param floorLocation  The floor location of the room in the house.
     */
    public Room(int id, String type, int numOfWindows, int floorLocation) {
        this.id = id;
        this.type = type;
        this.windows = generateWindows(numOfWindows);
        this.floorLocation = floorLocation;
    }

    private ArrayList<Window> generateWindows(int numOfWindows) {
        ArrayList<Window> windows = new ArrayList<>();

        Random random = new Random();

        for(int i = 0; i < numOfWindows; i++) {
            boolean areBlindsOpened = random.nextBoolean();
            Window window = new Window(areBlindsOpened);
            windows.add(window);
        }

        return windows;
    }

    /**
     * Prints the energy consumption of all devices in the room.
     */
    @Override
    public void printConsumption() {
        for(AbstractDevice device: this.devices){
            device.printConsumption();
        }
    }

    /**
     * Saves the energy consumption of all devices in the room to a file.
     */
    @Override
    public void saveConsumptionToFile() {
        for(AbstractDevice device: this.devices){
            device.saveConsumptionToFile();
        }
    }

    /**
     * Updates the energy consumption of all devices in the room.
     */
    @Override
    public void updateAllConsumptions() {
        for(AbstractDevice device: this.devices){
            device.updateConsumption();
        }
    }

    /**
     * Prints information about the room, including the number of windows, their states,
     * and details about devices in the room.
     */
    public void print(){
        System.out.print("    ");
        System.out.println(this.type + ": ");

        System.out.println("        Number of windows: " + this.windows.size());
        for(Window window : this.windows) {
            System.out.printf("    ");
            window.print();
        }

        System.out.println();
        System.out.println("        Devices: ");
        for(AbstractDevice device : this.devices){
            device.print();
        }

    }

    /**
     * Retrieves a list of devices in the room based on their type.
     *
     * @param typeOfDevice The type of devices to retrieve.
     * @return An ArrayList of AbstractDevice objects matching the specified type.
     */
    public ArrayList<AbstractDevice> getDeviceListByType(String typeOfDevice){
        ArrayList<AbstractDevice> deviceList = new ArrayList<>();
        for(AbstractDevice device : this.devices) {
            if(device.getType().equals(typeOfDevice)){
                deviceList.add(device);
            }
        }
        return deviceList;
    }

    public ArrayList<AbstractDevice> getDeviceList(){
        return this.devices;
    }

    public ArrayList<Window> getWindowsList() {
        return windows;
    }

    public void addDevice(AbstractDevice device){
        devices.add(device);
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public int getFloorLocation(){
        return this.floorLocation;
    }

    public ArrayList<AbstractDevice> getDevices() {
        return devices;
    }

    public int getNumOfAvailDevices() {
        return numOfAvailDevices;
    }

    public void setNumOfAvailDevices(int numOfAvailDevices) {
        this.numOfAvailDevices = numOfAvailDevices;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", windows=" + windows +
                ", floorLocation=" + floorLocation +
                '}';
    }
}
