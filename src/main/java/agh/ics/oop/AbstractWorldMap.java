package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap {
    private final int size;
    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final Map<Vector2d, Animal> plants = new HashMap<>();

    protected AbstractWorldMap(int size) {
        this.size = size;
    }
    public void place(Animal animal) {
        animals.put(animal.getPosition(), animal);
    }

    public Object objectAt(Vector2d position) {
        Object foundObject = animals.get(position);
        if (foundObject  == null) {
            return plants.get(position);
        }
        return foundObject;
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }
}
