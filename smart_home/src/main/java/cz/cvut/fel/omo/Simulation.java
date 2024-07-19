package cz.cvut.fel.omo;

import cz.cvut.fel.omo.device.AbstractDevice;
import cz.cvut.fel.omo.device.documentation.Documentation;
import cz.cvut.fel.omo.house.House;
import cz.cvut.fel.omo.person.Person;

import java.util.ArrayList;

import static cz.cvut.fel.omo.utils.Constatnts.CONFIG_2_PATH;

public class Simulation {
    private House house;

    private void interactWithEnviro() {
        ArrayList<Person> peopleList = this.house.getPeopleList();
        for(Person person : peopleList) {
            person.performActivity(this.house.getRoomList(), this.house.getIterations(), this.house.getGarage());
        }
    }

    private void runMainLoop() {
        // Main loop
        while(this.house.getIterations() > 0) {
            // Interacting with environment --> activities
            interactWithEnviro();
            // Generating events
            this.house.generateEvents();
            // Update consumption in the end of the iteration
            this.house.updateAllConsumptions();
            this.house.setIterations(this.house.getIterations() - 1);
        }

        // Saving consumption
        this.house.saveConsumptionToFile();
        this.house.saveUsageToFile();
        this.house.generateHouseReport();
        this.house.generateEventReport();
    }

    /**
     * Runs the main functionality of the program, including building the house,
     * executing the main loop, and reading documentation for devices.
     */
    public void run() {
        // Building the house
        House.HouseBuilder builder = new House.HouseBuilder(CONFIG_2_PATH);
        this.house = builder.build();
        builder.setPeople(this.house);
        builder.setPets(this.house);
        builder.setEvents(this.house);

        // Loop
        runMainLoop();
        ArrayList<AbstractDevice> devices = house.getDeviceList();
        for(AbstractDevice device : devices) {
            Documentation documentation = device.getDocumentation();
            documentation.readWarrantyCard();
            documentation.readRepairManual();
        }
    }
}
