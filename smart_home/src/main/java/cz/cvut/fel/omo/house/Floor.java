package cz.cvut.fel.omo.house;

import cz.cvut.fel.omo.device.AbstractDevice;

import java.util.ArrayList;

public class Floor implements HouseComponent {

    private int floorId;

    private ArrayList<Room> roomList = new ArrayList<>();

    /**
     * Constructs a Floor object with the specified floor ID.
     *
     * @param floorId The unique identifier of the floor.
     */
    public Floor(int floorId){
        this.floorId = floorId;
    }

    /**
     * Constructs a Floor object with a pre-existing list of rooms.
     *
     * @param roomList The list of rooms on the floor.
     */
    public Floor(ArrayList<Room> roomList){
        this.roomList = roomList;
    }

    /**
     * Prints information about the floor, including the floor ID and details about each room.
     */
    public void print(){
        System.out.println("Floor " + floorId + ": ");
        for(Room room : this.roomList){
            room.print();
        }
    }

    /**
     * Prints the energy consumption of all rooms on the floor.
     */
    @Override
    public void printConsumption() {
        for(Room room: this.roomList){
//            System.out.println("Room id: " + room.getId());
            room.printConsumption();
        }
    }

    /**
     * Saves the energy consumption of all rooms on the floor to a file.
     */
    @Override
    public void saveConsumptionToFile() {
        for(Room room: this.roomList){
            room.saveConsumptionToFile();
        }
    }

    /**
     * Updates the energy consumption of all rooms on the floor.
     */
    @Override
    public void updateAllConsumptions() {
        for(Room room: this.roomList){
            room.updateAllConsumptions();
        }
    }

    /**
     * Adds a room to the floor.
     *
     * @param room The room to be added to the floor.
     */
    public void addRoom(Room room){
        roomList.add(room);
    }

    /**
     * Retrieves a list of all devices in all rooms on the floor.
     *
     * @return An ArrayList of AbstractDevice objects representing all devices on the floor.
     */
    public ArrayList<AbstractDevice> getDeviceList(){
        ArrayList<AbstractDevice> deviceList = new ArrayList<>();
        for(Room room : roomList) {
            ArrayList<AbstractDevice> tmpDeviceList = room.getDeviceList();
            deviceList.addAll(tmpDeviceList);
        }
        return deviceList;
    }

    /**
     * Retrieves a list of all windows in all rooms on the floor.
     *
     * @return An ArrayList of Window objects representing all windows on the floor.
     */
    public ArrayList<Window> getWindowList(){
        ArrayList<Window> windowList = new ArrayList<>();
        for(Room room : roomList) {
            ArrayList<Window> tmpWindowList = room.getWindowsList();
            windowList.addAll(tmpWindowList);
        }
        return windowList;
    }

    /**
     * Retrieves a list of devices on the floor based on their type.
     *
     * @param typeOfDevice The type of devices to retrieve.
     * @return An ArrayList of AbstractDevice objects matching the specified type on the floor.
     */
    public ArrayList<AbstractDevice> getDeviceListByType(String typeOfDevice){
        ArrayList<AbstractDevice> deviceList = new ArrayList<>();
        for(Room room : roomList) {
            ArrayList<AbstractDevice> tmpDeviceList = room.getDeviceListByType(typeOfDevice);
            deviceList.addAll(tmpDeviceList);
        }
        return deviceList;
    }

    public int getFloorId(){
        return floorId;
    }

    public ArrayList<Room> getRoomList() {
        return this.roomList;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "floorId=" + floorId +
                '}';
    }
}
