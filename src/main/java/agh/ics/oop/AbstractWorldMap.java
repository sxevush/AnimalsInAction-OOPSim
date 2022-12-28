package agh.ics.oop;

import java.util.*;

public abstract class AbstractWorldMap {
    protected final int width;
    protected final int height;

    protected final Map<Vector2d, Field> fields = new HashMap<>();

    protected AbstractWorldMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    //todo new plants

    public void place(Animal animal) {
        Vector2d position = animal.getPosition();
        if(objectAt(position) == null) {
            Field field = new Field();
            field.addAnimal(animal);
            fields.put(position, field);
        }
        else{
            fields.get(position).addAnimal(animal);
        }
    }

    public void placePlant(int energy, Vector2d position) {
        if(objectAt(position) == null) {
            Field field = new Field();
            field.addPlant(energy);
            fields.put(position, field);
        }
        else{
            fields.get(position).addPlant(energy);
        }
    }

    public void remove(Animal animal){
        Vector2d position = animal.getPosition();
        fields.get(position).removeAnimal(animal);
        if(fields.get(position).isEmpty()) {
            fields.remove(position);
        }
        fields.get( position ).addDiedAnimal();
    }

    public Object objectAt(Vector2d position) {
        return fields.get(position);
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
