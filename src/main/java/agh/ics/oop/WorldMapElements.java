package agh.ics.oop;

import java.util.*;

public class WorldMapElements {
    private ArrayList<Animal> animals = new ArrayList<>();
    private AbstractWorldMap map;

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public WorldMapElements(AbstractWorldMap map){
        this.map = map;
    }
    public void addAnimal(Animal animal){
        animals.add(animal);
    }

    public void move() {
        for (Animal animal : animals) {
            animal.move();
        }
    }

    public void everyoneEat() {
        for (Animal animal : animals) {
            animal.eat( animal );
        }
    }

    public void cleanMap() {
        animals.removeIf( animal -> animal.getEnergy() == 0 );
        for (Animal animal : animals) {
            if (animal.getEnergy() == 0) {
                map.remove( animal );
            }
        }
    }

    public void letsBreed() {
        for (Animal animal : animals) {
            animal.breed( animal );
        }
    }
}
