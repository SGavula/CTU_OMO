package cz.cvut.fel.omo.state;

import cz.cvut.fel.omo.device.AbstractDevice;

public class IdleState implements StateOfDevice {
    @Override
    public void turnOn(AbstractDevice device) {
        System.out.println("The device is waking up.");
        device.setStateOfDevice(new ActiveState());
    }

    @Override
    public void turnOff(AbstractDevice device) {
        System.out.println("Turning off the device");
        device.setStateOfDevice(new OffState());
    }

    @Override
    public void idle(AbstractDevice device) {
        System.out.println("The device is already idle.");
    }

    public double getElectricConsumption() {
        return 0.5;
    }
    public double getWaterConsumption() {
        return 0;
    }

    public double getGasConsumption() {
        return 0.2;
    }

    @Override
    public String toString() {
        return "IdleState";
    }
}