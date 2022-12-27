package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class WorldMapElements {
    private List<Animal> animals = new ArrayList<>();
    private List<Plant> plants = new ArrayList<>();
    private AbstractWorldMap map;
    public WorldMapElements(AbstractWorldMap map){
        this.map = map;
    }
    public void addAnimal(Animal animal){
        animals.add(animal);
    }

    public void addPlant(Plant plant){
        plants.add(plant);
    }
    public void go(){
        move();
        eat();
        breed();
    }

    public void move(){
        for (Animal animal : animals) animal.move();
    }
    public void eat(){
        for(Plant plant : plants){

        }
    }
    public void breed(){

    }
}
