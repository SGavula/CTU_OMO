package cz.cvut.fel.omo.device;

public class Consumption {
    private double electricityConsumption = 0;
    private double waterConsumption = 0;
    private double gasConsumption = 0;

    public double getElectricityConsumption() {
        return electricityConsumption;
    }

    public void setElectricityConsumption(double electricityConsumption) {
        this.electricityConsumption = electricityConsumption;
    }

    public double getWaterConsumption() {
        return waterConsumption;
    }
    public void setWaterConsumption(double waterConsumption) {
        this.waterConsumption = waterConsumption;
    }

    public double getGasConsumption() {
        return gasConsumption;
    }

    public void setGasConsumption(double gasConsumption) {
        this.gasConsumption = gasConsumption;
    }
}
