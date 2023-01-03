package agh.ics.oop;

import java.util.*;

public class WorldMapElements {
    private ArrayList<Animal> animals = new ArrayList<>();
    private AbstractWorldMap map;
    protected HashMap<Vector2d, Field> fields;

    public WorldMapElements(AbstractWorldMap map){
        this.map = map;
        this.fields = map.fields;
    }

    public void addAnimals(ArrayList<Animal> newAnimals) {
        animals.addAll( newAnimals );
    }
    public void addAnimal(Animal animal){
        animals.add(animal);
    }

    public void move() {
        for (Animal animal : animals) {
            animal.move();
        }
    }

    public void cleanMap() {
        for (Animal animal : animals) {
            if (animal.getEnergy() == 0) {
                map.remove( animal );
            }
        }
        animals.removeIf( animal -> animal.getEnergy() == 0 );
    }
    public int getSize(){
        return this.animals.size();
    }
}
