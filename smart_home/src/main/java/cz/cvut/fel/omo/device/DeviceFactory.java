package cz.cvut.fel.omo.device;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

import cz.cvut.fel.omo.device.documentation.Documentation;
import cz.cvut.fel.omo.device.documentation.WarrantyCard;
import cz.cvut.fel.omo.person.PersonFactory;

public class DeviceFactory {
    private static Logger logger = Logger.getLogger(PersonFactory.class.getName());
    public static AbstractDevice createDevice(String type, int state, int durability, int defaultElectricityCons, int defaultWaterCons, int defaultGasCons) {
        WarrantyCard warrantyCard = createWarrantyCard(type);
        Documentation documentation = new Documentation(warrantyCard);
        switch (type) {
            case "Mobile":
                logger.info("Creating mobile");
                return new Mobile(type, state, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
            case "Lamp":
                logger.info("Creating lamp");
                return new Lamp(type, state, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
            case "Thermostat":
                logger.info("Creating thermostat");
                return new Thermostat(type, state, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
            case "CircuitBreakers":
                logger.info("Creating circuit breakers");
                return new CircuitBreakers(type, state, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
            case "Refrigerator":
                logger.info("Creating refrigerator");
                return new Refrigerator(type, state, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
            case "Oven":
                logger.info("Creating oven");
                return new Oven(type, state, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
            case "GasStove":
                logger.info("Creating gas stove");
                return new GasStove(type, state, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
            case "CoffeeMaker":
                logger.info("Creating coffee maker");
                return new CoffeeMaker(type, state, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
            case "Dishwasher":
                logger.info("Creating dishwasher");
                return new Dishwasher(type, state, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
            case "SmartTV":
                logger.info("Creating smart TV");
                return new SmartTV(type, state, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
            case "SoundSystem":
                logger.info("Creating sound system");
                return new SoundSystem(type, state, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
            case "GamingConsole":
                logger.info("Creating gaming console");
                return new GamingConsole(type, state, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
            case "Tablet":
                logger.info("Creating tablet");
                return new Tablet(type, state, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
            case "Light Sensor":
                logger.info("Creating light sensor");
                return new LightSensor(type, state, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
            case "Wind Sensor":
                logger.info("Creating wind sensor");
                return new WindSensor(type, state, durability, defaultElectricityCons, defaultWaterCons, defaultGasCons, documentation);
            default:
                logger.warning("ERROR: Unknown device type: " + type);
                return null;
        }
    }

    private static LocalDate generateRandomDate() {
        int startYear = 2015;
        int endYear = 2035;

        Random random = new Random();

        int year = random.nextInt(endYear - startYear + 1) + startYear;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1;

        return LocalDate.of(year, month, day);
    }

    private static String generateRandomManufacturer() {
        String[] manufacturers = {"Manufacturer A", "Manufacturer B", "Manufacturer C", "Manufacturer D"};

        Random random = new Random();
        int randomIndex = random.nextInt(manufacturers.length);
        return manufacturers[randomIndex];
    }

    private static WarrantyCard createWarrantyCard(String type) {
        String productId = UUID.randomUUID().toString();
        LocalDate date = generateRandomDate();
        String manufacture = generateRandomManufacturer();
        return new WarrantyCard(productId, date, type, manufacture);
    }
}