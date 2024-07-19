package cz.cvut.fel.omo.events;

import cz.cvut.fel.omo.device.AbstractDevice;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public abstract class Event<T> {
    private int id;
    private String description;
    private String source;
    private ArrayList<T> targets = new ArrayList<>();
    private AbstractDevice device;

    public Event(int id, String description, String source, ArrayList<T> targets) {
        this.id = id;
        this.description = description;
        this.source = source;
        this.targets = targets;
    }

    public void printDescription(){
        if(description != null) {
            System.out.println(this.description);
        }
    }

    public int getId() {
        return id;
    }

    public ArrayList<T> getTargets() {
        return targets;
    }

    public String getDescription(){
        return this.description;
    }

    public String getSource(){
        return this.source;
    }

    public AbstractDevice getDevice() {
        return device;
    }

    public void setDevice(AbstractDevice device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "Event{" +
                "description='" + description + '\'' +
                ", source='" + source + '\'' +
                ", targets=" + targets +
                '}';
    }
}

