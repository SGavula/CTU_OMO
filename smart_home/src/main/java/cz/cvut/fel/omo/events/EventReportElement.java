package cz.cvut.fel.omo.events;

import cz.cvut.fel.omo.Pet;
import cz.cvut.fel.omo.house.Window;
import cz.cvut.fel.omo.device.AbstractDevice;
import cz.cvut.fel.omo.person.Person;

import java.util.ArrayList;

public class EventReportElement <T>{
    Event event;
    ArrayList<T> targets = new ArrayList<>();

    public EventReportElement(Event event, ArrayList<T> targets) {
        this.event = event;
        this.targets = targets;
    }

    public EventReportElement(Event event, T target) {
        this.event = event;
        this.targets.add(target);
    }

    /**
     * Prints event details and associated targets.
     */
    public void print(){
        System.out.printf("            ");
        System.out.println(event.getDescription() + ": ");
        for(T target : targets){
            System.out.printf("                ");
            if(target instanceof Person || target instanceof Pet){
                System.out.println(((Person)target).getName());
            }
            else if(target instanceof Pet){
                System.out.println(((Pet)target).getName());
            }
            else if(target instanceof AbstractDevice){
                System.out.println(((AbstractDevice)target).getType());
            }
            else if(target instanceof Window){
                System.out.println("All Windows");
                break;
            }
        }

    }

    public Event getEvent() {
        return event;
    }
}
