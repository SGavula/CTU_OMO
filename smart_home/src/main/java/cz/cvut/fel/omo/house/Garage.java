package cz.cvut.fel.omo.house;

import java.util.logging.Logger;

public class Garage {
    private static Logger logger = Logger.getLogger(Garage.class.getName());
    int locationId;
    int numOfSkis;
    int numOfBikes;
    int skisInUsage;
    int bikesInUsage;

    /**
     * Constructs a Garage object with the specified number of skis and bikes.
     *
     * @param numOfSkis  The initial number of skis in the garage.
     * @param numOfBikes The initial number of bikes in the garage.
     */
    public Garage(int numOfSkis, int numOfBikes) {
        this.locationId = 0;
        this.numOfSkis = numOfSkis;
        this.numOfBikes = numOfBikes;
        this.skisInUsage = 0;
        this.bikesInUsage = 0;
    }

    /**
     * Attempts to take one ski from the garage.
     *
     * @return {@code true} if a ski is successfully taken, {@code false} otherwise.
     */
    public boolean takeSkis() {
        if(this.skisInUsage <= this.numOfSkis) {
            this.skisInUsage = this.skisInUsage + 1;
            return true;
        }else {
            logger.info("No more skis.");
            return false;
        }
    }

    /**
     * Attempts to take one bike from the garage.
     *
     * @return {@code true} if a bike is successfully taken, {@code false} otherwise.
     */
    public boolean takeBike() {
        if(this.bikesInUsage <= this.numOfBikes) {
            this.bikesInUsage = this.bikesInUsage + 1;
            return true;
        }else {
            logger.info("No more bikes.");
            return false;
        }
    }

    /**
     * Decreases the count of skis currently in usage.
     */
    public void decreaseNumOfSkisInUsage() {
        if(this.skisInUsage > 0) {
            this.skisInUsage = this.skisInUsage - 1;
        }else {
            logger.info("All skis are back.");
        }
    }

    /**
     * Decreases the count of bikes currently in usage.
     */
    public void decreaseNumOfBikesInUsage() {
        if(this.bikesInUsage > 0) {
            this.bikesInUsage = this.bikesInUsage - 1;
        }else {
            logger.info("All bikes are back..");
        }
    }

    /**
     * Prints information about the garage, including the number of skis and bikes available.
     */
    public void print(){
        System.out.println("Garage: ");
        System.out.printf("    ");
        System.out.println("number of skis: " + numOfSkis);
        System.out.printf("    ");
        System.out.println("number of bikes: " + numOfBikes);
    }

    public int getLocationId() {
        return locationId;
    }

    @Override
    public String toString() {
        return "Garage{" +
                "locationId=" + locationId +
                ", numOfSkis=" + numOfSkis +
                ", numOfBikes=" + numOfBikes +
                '}';
    }
}
