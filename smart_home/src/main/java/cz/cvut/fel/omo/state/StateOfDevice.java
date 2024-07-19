package cz.cvut.fel.omo.state;

import cz.cvut.fel.omo.device.AbstractDevice;

public interface StateOfDevice {
    void turnOn(AbstractDevice device);
    void turnOff(AbstractDevice device);
    void idle(AbstractDevice device);
    double getElectricConsumption();
    double getWaterConsumption();
    double getGasConsumption();
}
