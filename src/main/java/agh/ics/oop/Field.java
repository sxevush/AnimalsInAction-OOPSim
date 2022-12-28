package agh.ics.oop;

import java.util.*;

public class Field {
    protected Comparator<Animal> animalComparator = (a1, a2) -> {
        if (a1.getEnergy() == a2.getEnergy()) {
            return a1.getAge() - a2.getAge();
        }
        return a1.getEnergy() - a2.getEnergy();
    };
    private PriorityQueue<Animal> animals = new PriorityQueue<>( animalComparator );
    private int plant;
    private int numberOfDiedAnimals = 0; //todo nowe roslinki codziennie

    public void addDiedAnimal () {
        numberOfDiedAnimals++;
    }


    public void addAnimal(Animal animal){
        animals.add(animal);
    }
    public void removeAnimal(Animal animal){
        animals.remove(animal);
    }

    public void addPlant(int energyValue){
        this.plant = energyValue;
    }

    public boolean isEmpty(){
        return animals.size() == 0 && plant == 0;
    }

    public String toString(){
        if(animals.size() != 0) {
            return animals.peek().toString();
        }
        if (plant != 0) {
            return "*";
        }
        return " ";
    }

    public void eat() {
        assert animals.peek() != null;
        if (animals.peek() != null) {
            animals.peek().modifyEnergy( plant );
        }
        plant = 0;
    }

    public void breed() {
        if(animals.size() >= 2){
            Animal parent1 = animals.poll();
            Animal parent2 = animals.poll();
            int minEnergy = parent1.startingEnergy/2; //TODO brzydkie trzeba sparametryzowac
            if(parent1.getEnergy() >= minEnergy) {
                assert parent2 != null;
                if (parent2.getEnergy() >= minEnergy) {
                    Animal newAnimal = new Animal( parent1, parent2 );
                    parent1.modifyEnergy( -minEnergy );
                    parent2.modifyEnergy( -minEnergy );
                    this.addAnimal( newAnimal );
                    }
                }
            }
        }

    }

