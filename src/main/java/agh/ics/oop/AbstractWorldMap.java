package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap {
    protected final int width;
    protected final int height;

    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final Map<Vector2d, Plant> plants = new HashMap<>();

    protected AbstractWorldMap(int width, int height) {
        this.width = width;
        this.height = height;
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

    public abstract void checkBoundaries(Animal animal);

    public String toString() {
        MapVisualizer drawing = new MapVisualizer(this);
        return drawing.draw(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));
    }
}
