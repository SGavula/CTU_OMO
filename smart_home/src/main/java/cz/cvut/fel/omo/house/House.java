package cz.cvut.fel.omo.house;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.omo.Pet;
import cz.cvut.fel.omo.device.*;
import cz.cvut.fel.omo.events.*;
import cz.cvut.fel.omo.person.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import static cz.cvut.fel.omo.utils.Constatnts.*;

public class House implements HouseComponent {
    private static Logger logger = Logger.getLogger(House.class.getName());
    private int iterations;
    private ArrayList<Floor> floorList;
    private Garage garage;

    private ArrayList<Person> peopleList = new ArrayList<Person>();

    private ArrayList<Pet> petList = new ArrayList<>();

    public House(ArrayList<Floor> floorList, Garage garage, int iterations){
        this.floorList = floorList;
        this.garage = garage;
        this.iterations = iterations;
    }

    @Override
    public void printConsumption() {
        for(Floor floor: this.floorList){
            floor.printConsumption();
        }
    }

    public void printDevices() {
        ArrayList<AbstractDevice> deviceList = getDeviceList();

        for(AbstractDevice device : deviceList) {
            System.out.println(device);
        }
    }

    /**
     * Prints information about people in the list.
     *
     * Displays parents, children, and babies with their respective details.
     * Prints the total count of people in the list.
     */
    public void printPeople(){
        System.out.println("People: ");
        System.out.printf("    ");

        System.out.println("Parents: ");
        for(Person person : this.peopleList){
            if(person instanceof Parent) {
                person.print();
            }
        }

        System.out.printf("    ");
        System.out.println("Children: ");
        for(Person person : this.peopleList){
            if(person instanceof Child) {
                person.print();
            }
        }

        System.out.printf("    ");
        System.out.println("Babies: ");
        for(Person person : this.peopleList){
            if(person instanceof Baby) {
                person.print();
            }
        }

        System.out.printf("\n    ");
        System.out.println("Total People Count: " + this.peopleList.size());
    }

    public void printPets(){
        System.out.println("Pets: ");
        for(Pet pet : this.petList){
            pet.print();
        }

        System.out.printf("\n    ");
        System.out.println("Total Pet Count: " + this.petList.size());
    }

    public void print(){
        System.out.println("House:");
        for(Floor floor : this.floorList){
            floor.print();
        }
        System.out.println();
        this.garage.print();
    }

    /**
     * Prints events for pets, people, devices, and weather.
     *
     * Displays events for pets, babies, children, parents, devices, and weather.
     */
    public void printEvents(){
        System.out.println("Pet Events:");
        for(Pet pet : this.petList){
            System.out.printf("    ");
            System.out.println(pet.getName() + ": ");
            pet.printEvents();
        }

        System.out.println("\n----------------------------------------------------------------------------------\n");

        System.out.println("Baby Events:");
        for(Person person : this.peopleList){
            if(person instanceof Baby) {
                System.out.printf("    ");
                System.out.println(person.getName() + ": ");
                person.printEvents();
            }
        }

        System.out.println("");

        System.out.println("Child Events:");
        for(Person person : this.peopleList){
            if(person instanceof Child) {
                System.out.printf("    ");
                System.out.println(person.getName() + ": ");
                person.printEvents();
            }
        }

        System.out.println("");

        System.out.println("Parent Events:");
        for(Person person : this.peopleList){
            if(person instanceof Parent) {
                System.out.printf("    ");
                System.out.println(person.getName() + ": ");
                person.printEvents();
            }
        }

        System.out.println("\n----------------------------------------------------------------------------------\n");

        System.out.println("Device Events:");
        for(AbstractDevice device : this.getDeviceList()){
            device.printEvents("Device");
        }

        System.out.println("\n----------------------------------------------------------------------------------\n");

        System.out.println("Weather Events:");
        for(AbstractDevice device : this.getDeviceList()){
            device.printEvents("Weather");
        }
    }

    /**
     * Clears and updates the consumption report file.
     *
     * Clears the existing content in the consumption file and writes the header.
     * Calls the saveConsumptionToFile method for each floor to update their consumption reports.
     */
    @Override
    public void saveConsumptionToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(CONSUMPTION_FILE_PATH, false));
            writer.write("");
            // Write header of table to the file
            writer.write(String.format("%-20s%-28s%-20s%-20s%-28s%-20s%-20s%n",
                    "Type of device",
                    "Electricity consumption",
                    "Gas consumption",
                    "Water consumption",
                    "Total electricity price",
                    "Total gas price",
                    "Total water price"
            ));

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Floor floor: this.floorList){
            floor.saveConsumptionToFile();
        }
    }

    /**
     * Clears and updates the usage report file.
     *
     * Clears the existing content in the usage file and writes the usage details for each person and their associated devices.
     * Calls the saveUsageToFile method for each floor to update their usage reports.
     */
    public void saveUsageToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(USAGE_FILE_PATH, false));
            writer.write("");

            for(Person person : this.peopleList) {
                writer.write(person.getName() + "\n");
                Map<AbstractDevice, Integer> activityReport = person.getActivityReport();
                for (Map.Entry<AbstractDevice, Integer> entry : activityReport.entrySet()) {
                    writer.write("      Device: " + entry.getKey().getType() + ", Usage Count: " + entry.getValue() + "\n");
                }
                writer.write("\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Floor floor: this.floorList){
            floor.saveConsumptionToFile();
        }
    }

    /**
     * Generates and saves a report for the house.
     *
     * Redirects the standard output to a file stream and prints the house details, people, and pets.
     * Prints dividers between sections for better readability in the generated report file.
     */
    public void generateHouseReport() {
        PrintStream originalOut = System.out;

        try {
            PrintStream fileStream = new PrintStream(new FileOutputStream(HOUSE_FILE_PATH));
            System.setOut(fileStream);

            // print house
            print();

            // print devider
            System.out.println("\n----------------------------------------\n");

            // print people and pets
            printPeople();

            System.out.println("\n----------------------------------------\n");

            printPets();

            fileStream.close();
            System.setOut(originalOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAllConsumptions() {
        for(Floor floor: this.floorList){
            floor.updateAllConsumptions();
        }
    }

    public void generateEvents() {
        generatePeopleEvents();
        generatePetEvents();
        generateDeviceEvents();
    }

    /**
     * Generates and saves a report for events.
     *
     * Redirects the standard output to a file stream and prints the event report format.
     * Prints dividers between sections for better readability in the generated report file.
     * Calls the printEvents method to include details about specific events.
     */
    public void generateEventReport() {
        PrintStream originalOut = System.out;

        try {
            PrintStream fileStream = new PrintStream(new FileOutputStream(EVENTS_FILE_PATH));
            System.setOut(fileStream);

            System.out.println("Report format:\n");

            System.out.println("Type of event: ");
            System.out.println("    Source: ");
            System.out.println("        Event number:");
            System.out.println("            Event description:");
            System.out.println("                Who handled the event");

            System.out.println("\n----------------------------------------------------------------------------------\n");

            printEvents();

            fileStream.close();
            System.setOut(originalOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates random events for a subset of people in the list.
     *
     * Chooses a random number of events to generate (up to 5) and randomly selects people from the list
     * to generate events for. The selected people generate events based on their individual behaviors.
     */
    public void generatePeopleEvents() {
        Random random = new Random();
        int upperBound = random.nextInt(5);

        // this is here so the program does not crash for IllegalArgumentException
        if(peopleList.isEmpty()){
            logger.warning("No people.");
            return;
        }

        // Choose random person that will generate the event upperBound (random) times
        for(int i = 0; i < upperBound; i++) {
            int randomPersonInd = random.nextInt(peopleList.size());
            //System.out.println("Random person ind: " + randomPersonInd);
            peopleList.get(randomPersonInd).generateEvent();
        }
    }

    /**
     * Generates random events for a subset of pets in the list.
     * <p>
     * Chooses a random number of events to generate (up to 5) and randomly selects pets from the list
     * to generate events for. The selected pets generate events based on their individual behaviors.
     */
    public void generatePetEvents() {
        Random random = new Random();
        int upperBound = random.nextInt(5);

        // this is here so the program does not crash for IllegalArgumentException
        if(petList.isEmpty()){
            logger.warning("No pets.");
            return;
        }

        // Choose random person that will generate the event upperBound (random) times
        for(int i = 0; i < upperBound; i++) {
            int randomPetIndex = random.nextInt(petList.size());
            petList.get(randomPetIndex).generateEvent();
        }
    }

    /**
     * Generates random events for a subset of devices in the list.
     *
     * Chooses a random number of events to generate (up to 2) and randomly selects devices from the list
     * to generate events for. The selected devices generate events based on their individual behaviors.
     */
    public void generateDeviceEvents() {
        Random random = new Random();
        int upperBound = random.nextInt(2);
        ArrayList<AbstractDevice> devices = this.getDeviceList();

        // this is here so the program does not crash for IllegalArgumentException
        if(devices.isEmpty()){
            logger.warning("No devices.");
            return;
        }

        // Choose random person that will generate the event upperBound (random) times
        for(int i = 0; i < upperBound; i++) {
            int randomDeviceIndex = random.nextInt(devices.size());
            devices.get(randomDeviceIndex).generateEvent();
        }
    }

    public ArrayList<Room> getRoomList() {
        ArrayList<Room> roomList = new ArrayList<>();
        for(Floor floor : floorList) {
            ArrayList<Room> tmpRoomList = floor.getRoomList();
            roomList.addAll(tmpRoomList);
        }
        return roomList;
    }

    public ArrayList<AbstractDevice> getDeviceList(){
        ArrayList<AbstractDevice> deviceList = new ArrayList<>();
        for(Floor floor : floorList) {
            ArrayList<AbstractDevice> tmpDeviceList = floor.getDeviceList();
            deviceList.addAll(tmpDeviceList);
        }
        return deviceList;
    }

    public ArrayList<Window> getWindowList(){
        ArrayList<Window> windowList = new ArrayList<>();
        for(Floor floor : floorList) {
            ArrayList<Window> tmpWindowList = floor.getWindowList();
            windowList.addAll(tmpWindowList);
        }
        return windowList;
    }

    public ArrayList<AbstractDevice> getDeviceListByType(String typeOfDevice){
        ArrayList<AbstractDevice> deviceList = new ArrayList<>();
        for(Floor floor : floorList) {
            ArrayList<AbstractDevice> tmpDeviceList = floor.getDeviceListByType(typeOfDevice);
            deviceList.addAll(tmpDeviceList);
        }
        return deviceList;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public ArrayList<Person> getPeopleList() {
        return peopleList;
    }
    public void setPeopleList(ArrayList<Person> peopleList) {
        this.peopleList = peopleList;
    }


    public ArrayList<Pet> getPetList() {
        return petList;
    }

    public void setPetList(ArrayList<Pet> petList) {
        this.petList = petList;
    }

    public Garage getGarage() {
        return garage;
    }

    public static class HouseBuilder <T> {
        private JsonNode rootNode;
        private JsonNode houseNode;
        private int iterations;
        private int floors;
        private JsonNode rooms;
        private JsonNode devices;
        private JsonNode people;
        private JsonNode pets;
        private ArrayList<Floor> floorList = new ArrayList<>();
        private Garage garage;


        /**
         * Constructs a HouseBuilder object based on the provided JSON configuration file.
         *
         * Loads the configuration from the specified JSON file path, creates floors, rooms, and devices,
         * and organizes them into a hierarchical structure. Additionally, initializes the garage with
         * the specified number of skis and bicycles.
         *
         * @param jsonPath The file path to the JSON configuration file.
         */
        public HouseBuilder(String jsonPath) {
            loadJson(jsonPath);

            for(int i = 0; i < floors; i++){
                Floor floor = new Floor(i);
                floorList.add(floor);
            }

            for(JsonNode roomNode : this.rooms) {
                int id = roomNode.get("id").asInt();
                String type = roomNode.get("type").asText();
                int floor_loc = roomNode.get("floor_loc").asInt();
                int windows = roomNode.get("windows").asInt();

                Room room = new Room(id, type, windows, floor_loc);

                for(JsonNode deviceNode : this.devices){
                    if(deviceNode.get("type") == null ||
                            deviceNode.get("state_of_device")  == null ||
                            deviceNode.get("durability") == null ||
                            deviceNode.get("defaultElectricityConsumption") == null ||
                            deviceNode.get("defaultWaterConsumption") == null ||
                            deviceNode.get("defaultGasConsumption") == null){
                        logger.warning("ERROR: Not valid device input. Device was not created.");
                        continue;
                    }
                    if(id == deviceNode.get("room_loc").asInt()){
                        String deviceType = deviceNode.get("type").asText();
                        int stateOfDevice = deviceNode.get("state_of_device").asInt();
                        int durability = deviceNode.get("durability").asInt();
                        int defaultElectricityCons = deviceNode.get("defaultElectricityConsumption").asInt();
                        int defaultWaterCons = deviceNode.get("defaultWaterConsumption").asInt();
                        int defaultGasCons = deviceNode.get("defaultGasConsumption").asInt();

                        AbstractDevice device = DeviceFactory.createDevice(deviceType, stateOfDevice, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons);
                        if(device == null){
                            continue;
                        }
                        room.addDevice(device);
                        room.setNumOfAvailDevices(room.getNumOfAvailDevices() + 1);
                    }
                }

                for(Floor floor : floorList){
                    if(floor.getFloorId() == (room.getFloorLocation())){
                        floor.addRoom(room);
                        //System.out.println("adding room " + room.getFloorLocation() + " to floor " + floor.getFloorId());
                        break;
                    }
                }

                // Add garage
                int numOfSkis = this.houseNode.get(5).get("number_of_skis").asInt();
                int numOfBikes = this.houseNode.get(5).get("number_of_bicycles").asInt();
                this.garage = new Garage(numOfSkis, numOfBikes);
            }
        }


        private void loadJson(String jsonPath) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();

                // Get json file
                File jsonFile = new File(jsonPath);

                // Read JSON file into a JsonNode
                this.rootNode = objectMapper.readTree(jsonFile);

                // Get iterations of simulation
                this.iterations = this.rootNode.get("iterations").asInt();

                // Accessing house information
                this.houseNode = this.rootNode.get("house");
                this.floors = this.houseNode.get(0).get("floors").asInt();
                this.rooms = this.houseNode.get(1).get("rooms");
                this.devices = this.houseNode.get(2).get("devices");
                this.people = this.houseNode.get(3).get("people");
                this.pets = this.houseNode.get(4).get("pets");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public House build() {
            House house = new House(this.floorList, this.garage, this.iterations);
            return house;
        }

        private ArrayList<Event> createPetEventList(House house, String source){
            ArrayList<Event> eventList = new ArrayList<>();
            ArrayList<Person> parents = new ArrayList<>();
            ArrayList<Person> children = new ArrayList<>();

            ArrayList<Person> target = new ArrayList<>();

            for(Person person : house.getPeopleList()) {
                if(person instanceof Parent) {
                    parents.add(person);
                }
                else if(person instanceof  Child){
                    children.add(person);
                }
            }

            target.addAll(children);
            target.addAll(parents);

            eventList.add(new PetEvent(2, "Pet is hungry.", source, target));

            return eventList;
        }

        private ArrayList<Event> createBabyEventList(House house, String source){
            ArrayList<Event> eventList = new ArrayList<>();
            ArrayList<Person> parents = new ArrayList<>();

            for(Person person : house.getPeopleList()) {
                if(person instanceof Parent) {
                    parents.add(person);
                }
            }

            eventList.add(new PersonEvent(0, "The baby pooped, needs to be changed.", source, parents));
            eventList.add(new PersonEvent(1, "Baby cries, needs entertainment.", source, parents));

            return eventList;
        }

        private ArrayList<Event> createChildEventList(House house, String source){
            ArrayList<Event> eventList = new ArrayList<>();
            ArrayList<Person> parents = new ArrayList<>();
            ArrayList<Person> children = new ArrayList<>();

            ArrayList<Person> target = new ArrayList<>();

            for(Person person : house.getPeopleList()) {
                if(person instanceof Parent) {
                    parents.add(person);
                }
                else if(person instanceof Child){
                    children.add(person);
                }
            }

            target.addAll(children);
            target.addAll(parents);

            eventList.add(new PersonEvent(3,"Child lost glasses, needs help with finding them.", source, target));

            eventList.add(new PersonEvent(4, "Child needs help with homework.", source, parents));

            return eventList;
        }

        private ArrayList<Event> createParentEventList(House house, String source){
            ArrayList<Event> eventList = new ArrayList<>();
            ArrayList<Person> parents = new ArrayList<>();
            ArrayList<Person> children = new ArrayList<>();

            ArrayList<Person> target = new ArrayList<>();

            for(Person person : house.getPeopleList()) {
                if(person instanceof Parent) {
                    parents.add(person);
                }
                else if(person instanceof Child){
                    children.add(person);
                }
            }

            target.addAll(children);
            target.addAll(parents);

            eventList.add(new PersonEvent(5,"Parent lost glasses, needs help with finding them.", source, target));

            return eventList;
        }

        private ArrayList<Event> createLightSensorEventList(House house, String source){
            ArrayList<Event> eventList = new ArrayList<>();

            ArrayList<AbstractDevice> lights = house.getDeviceListByType("Lamp");

            eventList.add(new WeatherEvent(8, "It got cloudy.", source, lights));
            eventList.add(new WeatherEvent(9, "It got sunny.", source, lights));

            return eventList;
        }

        private ArrayList<Event> createThermostatEventList(House house, String source){
            ArrayList<Event> eventList = new ArrayList<>();

            ArrayList<AbstractDevice> thermostats = house.getDeviceListByType("Thermostat");

            eventList.add(new WeatherEvent(10, "The weather got cold.", source, thermostats));
            eventList.add(new WeatherEvent(11, "The weather got hot.", source, thermostats));

            return eventList;
        }

        private <T> ArrayList<Event> createCircuitBreakerEventList(House house, String source){
            ArrayList<Event> eventList = new ArrayList<>();

            ArrayList<AbstractDevice> devices = house.getDeviceList();
            ArrayList<Person> people = house.getPeopleList();
            ArrayList<T> targets = new ArrayList<>();

            for(Person person : people){
                if(person instanceof Parent){
                    targets.add((T)person);
                }
            }

            for(AbstractDevice device : devices){
                if(device instanceof CircuitBreakers || device instanceof Mobile){
                    continue;
                }
                targets.add((T)device);
            }

            eventList.add(new DeviceEvent(6, "Circuit breaker outage.", source, targets));

            return eventList;
        }

        private ArrayList<Event> createDeviceEventList(House house, String source){
            ArrayList<Event> eventList = new ArrayList<>();

            ArrayList<Person> people = house.getPeopleList();
            ArrayList<Person> parents = new ArrayList<>();

            for(Person person : people){
                if(person instanceof Parent){
                    parents.add(person);
                }
            }

            eventList.add(new DeviceEvent(7, "The device got broken.", source, parents));

            return eventList;
        }

        private ArrayList<Event> createWindSensorEventList(House house, String source){
            ArrayList<Event> eventList = new ArrayList<>();

            ArrayList<Window> targets = house.getWindowList();

            eventList.add(new WeatherEvent(12, "A strong wind is blowing.", source, targets));
            eventList.add(new WeatherEvent(13, "No wind blowing.", source, targets));

            return eventList;
        }

        /**
         * Sets events for people, devices, and pets in the given house.
         *
         * Iterates through the lists of people, devices, and pets in the house
         * and assigns events to each based on their types. Creates and adds event lists
         * specific to the individual's type and behavior.
         *
         * @param house The house for which events are being set.
         */
        public void setPeople(House house) {
            ArrayList<Person> peopleList = new ArrayList<Person>();
            for(JsonNode personNode : this.people) {
                if(personNode.get("name") == null || personNode.get("age") == null || personNode.get("room_loc") == null){
                    logger.warning("ERROR: Not valid person input. Person was not created.");
                    continue;
                }
                Person person = PersonFactory.createPerson(
                            personNode.get("name").asText(),
                            personNode.get("age").asInt(),
                            personNode.get("room_loc").asInt()
                );
                peopleList.add(person);
            }
            house.setPeopleList(peopleList);
        }

        public void setPets(House house) {
            ArrayList<Pet> petList = new ArrayList<Pet>();
            for(JsonNode petNode : this.pets) {
                Pet pet = new Pet(
                        petNode.get("name").asText(),
                        petNode.get("type").asText(),
                        petNode.get("room_loc").asInt()
                );
                petList.add(pet);
            }
            house.setPetList(petList);
        }

        /**
         * Sets events for people, devices, and pets in the given house.
         *
         * Iterates through the lists of people, devices, and pets in the house
         * and assigns events to each based on their types. Creates and adds event lists
         * specific to the individual's type and behavior.
         *
         * @param house The house for which events are being set.
         */
        public void setEvents(House house) {
            ArrayList<Person> people = house.getPeopleList();
            ArrayList<AbstractDevice> devices = house.getDeviceList();
            ArrayList<Pet> pets = house.getPetList();

            for(Person person : people) {
                if (person instanceof Baby) {
                    person.addEventList(createBabyEventList(house, person.getName()));
                }
                else if(person instanceof Child){
                    person.addEventList(createChildEventList(house, person.getName()));
                }
                else if(person instanceof Parent){
                    person.addEventList(createParentEventList(house, person.getName()));
                }
            }

            for(AbstractDevice device : devices) {
                if(device instanceof CircuitBreakers){
                    device.addEventList(createCircuitBreakerEventList(house, device.getType()));
                    continue;
                }
                else if (device instanceof Thermostat) {
                    device.addEventList(createThermostatEventList(house, device.getType()));
                }
                else if (device instanceof LightSensor){
                    device.addEventList(createLightSensorEventList(house, device.getType()));
                }
                else if(device instanceof WindSensor){
                    device.addEventList(createWindSensorEventList(house, device.getType()));
                }
                device.addEventList(createDeviceEventList(house, device.getType()));
            }

            for(Pet pet : pets) {
                pet.addEventList(createPetEventList(house, pet.getName()));
            }
        }
    }
}
