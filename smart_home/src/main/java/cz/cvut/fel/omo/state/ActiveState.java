package cz.cvut.fel.omo.state;

import cz.cvut.fel.omo.device.AbstractDevice;

public class ActiveState implements StateOfDevice {
    @Override
    public void turnOn(AbstractDevice device) {
        System.out.println("The device is already turned on.");
    }

    @Override
    public void turnOff(AbstractDevice device) {
        System.out.println("Turning off the device.");
        device.setStateOfDevice(new OffState());
    }

    @Override
    public void idle(AbstractDevice device) {
        System.out.println("The device is now idle.");
        device.setStateOfDevice(new IdleState());
    }

    public double getElectricConsumption() { return 1; }
    public double getWaterConsumption() {
        return 1;
    }
    public double getGasConsumption() {
        return 1;
    }

    @Override
    public String toString() {
        return "ActiveState";
    }
}