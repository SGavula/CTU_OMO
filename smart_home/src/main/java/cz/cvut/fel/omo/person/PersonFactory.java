package cz.cvut.fel.omo.person;

import cz.cvut.fel.omo.Pet;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.logging.Logger;

public class PersonFactory {
    private static Logger logger = Logger.getLogger(PersonFactory.class.getName());
    public static Person createPerson(String name, int age, int room_loc) {
        if(age <= 4) {
            logger.info("Creating a baby");
            return new Baby(name, age, room_loc);
        }
        else if(age < 18) {
            logger.info("Creating a child");
            return new Child(name, age, room_loc);
        }
        else if(age >= 18){
            logger.info("Creating a parent");
            return new Parent(name, age, room_loc);
        }
        logger.warning("ERROR: Not valid person age. Person was not created.");
        return null;
    }
}
