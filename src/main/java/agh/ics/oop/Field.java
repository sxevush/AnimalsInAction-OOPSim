package agh.ics.oop;

import java.util.*;

public class Field {
    private int babyStartingEnergy = 5;
    protected Comparator<Animal> animalComparator = (a1, a2) -> {
        if (a1.getEnergy() == a2.getEnergy()) {
            return a1.getAge() - a2.getAge();
        }
        return a1.getEnergy() - a2.getEnergy();
    };
    private PriorityQueue<Animal> animals = new PriorityQueue<>( animalComparator );
    private int plant;
    private int numberOfDiedAnimals = 0;
    private final AbstractWorldMap map;
    private Vector2d position;

    public Field (AbstractWorldMap map, Vector2d position) {
        this.map = map;
        this.position = position;
    }

    public int getNumberOfDiedAnimals() {
        return numberOfDiedAnimals;
    }

    public void setBabyStartingEnergy(int babyStartingEnergy) {
        this.babyStartingEnergy = babyStartingEnergy;
    }

    public void addDiedAnimal () {
        numberOfDiedAnimals++;
    }

    public PriorityQueue<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(PriorityQueue<Animal> animals) {
        this.animals = animals;
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
//            return animals.peek().toString();
            return String.valueOf( animals.size() );
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
            ArrayList<Animal> parentsAfterBreeding = new ArrayList<>(List.of(Objects.requireNonNull( parent1 ), Objects.requireNonNull( parent2 ) ));
            map.addParentsAfterBreeding( new ArrayList<>(parentsAfterBreeding) );
            if(Objects.requireNonNull( parent1 ).getEnergy() >= babyStartingEnergy && parent1.getAge() > 0) {
                if (parent2.getEnergy() >= babyStartingEnergy && parent2.getAge() > 0) {
                    Animal newAnimal = new Animal( parent1, parent2, map.getStartingAnimalEnergy()  );
                    parent1.modifyEnergy( -babyStartingEnergy );
                    parent2.modifyEnergy( -babyStartingEnergy );
                    this.addAnimal( newAnimal );
                    }
                }
            }
        }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public String getImagePath(){
        if(animals.size() > 0){
            return "src/main/resources/animal.png";
        }
        return "src/main/resources/plant.png";
    }


}

