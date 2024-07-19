package cz.cvut.fel.omo.events;

import cz.cvut.fel.omo.person.Person;

import java.util.ArrayList;

public class PetEvent extends Event{
    public PetEvent(int id, String description, String source, ArrayList<Person> people) {
        super(id, description, source, people);
    }
}
