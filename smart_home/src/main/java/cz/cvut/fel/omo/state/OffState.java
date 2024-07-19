package cz.cvut.fel.omo.state;

import cz.cvut.fel.omo.device.AbstractDevice;

public class OffState implements StateOfDevice {
    @Override
    public void turnOn(AbstractDevice device) {
        System.out.println("Turning on the device.");
        device.setStateOfDevice(new ActiveState());
    }

    @Override
    public void turnOff(AbstractDevice device) {
        System.out.println("The device is already turned off.");
    }

    @Override
    public void idle(AbstractDevice device) {
        System.out.println("The device is turned off.");
    }

    public double getElectricConsumption() {
        return 0;
    }
    public double getWaterConsumption() {
        return 0;
    }
    public double getGasConsumption() {
        return 0;
    }

    @Override
    public String toString() {
        return "OffState";
    }
}