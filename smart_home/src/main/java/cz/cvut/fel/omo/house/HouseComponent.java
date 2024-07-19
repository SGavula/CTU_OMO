package cz.cvut.fel.omo.house;

import cz.cvut.fel.omo.device.AbstractDevice;

import java.util.ArrayList;

public interface HouseComponent {
    public void updateAllConsumptions();  // updates consumptions on all devices in house

    public void printConsumption();
    public void saveConsumptionToFile();

    public ArrayList<AbstractDevice> getDeviceList();

    public ArrayList<AbstractDevice> getDeviceListByType(String typeOfDevice);

    public void print();

}
