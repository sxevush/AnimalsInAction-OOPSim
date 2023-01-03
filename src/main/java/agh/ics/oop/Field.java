package agh.ics.oop;

import java.util.*;

public class Field {
    private int energyToBreed = 5;
    private int minBreedEnergy = 5; //todo wieksze od energyToBreed
    //todo setter stuffedAnimal
    protected Comparator<Animal> animalComparator = (a1, a2) -> {
        if (a1.getEnergy() == a2.getEnergy()) {
            return a1.getAge() - a2.getAge();
        }
        return a1.getEnergy() - a2.getEnergy();
    };
    private PriorityQueue<Animal> animals = new PriorityQueue<>( animalComparator );
    protected int plant;
    private int numberOfDiedAnimals = 0;
    private AbstractWorldMap map;
    private Vector2d position;

    public Field (AbstractWorldMap map) {
        this.map = map;
        this.energyToBreed = map.startingAnimalEnergy/2;
        this.minBreedEnergy = map.minBreedEnergy;
    }

    public String getImagePath() {
        if(this.isEmpty()){
            return "src/main/resources/empty2.png";
        }
        if(animals.size()>0){
            if(animals.size()==1) {
                return "src/main/resources/oneAnimal2.png";
            }
            if(animals.size()==2) {
                return "src/main/resources/twoAnimals.png";
            }
            if(animals.size()==3) {
                return "src/main/resources/threeAnimals.png";
            }
            if(animals.size()==4) {
                return "src/main/resources/fourAnimals.png";
            }
            if(animals.size()==5) {
                return "src/main/resources/fiveAnimals.png";
            }
            if(animals.size()==6) {
                return "src/main/resources/sixAnimals.png";
            }
            return "src/main/resources/manyAnimals.png";
        }
        return "src/main/resources/plant2.png";
    }
    public int getNumberOfDiedAnimals() {
        return numberOfDiedAnimals;
    }
    public PriorityQueue<Animal> getAnimals() {
        return animals;
    }
    public void setAnimals(PriorityQueue<Animal> animals) {
        this.animals = animals;
    }
    public void addDiedAnimal () {
        numberOfDiedAnimals++;
    }
    public void addPlant(int energyValue){
        this.plant = energyValue;
    }
    public void addAnimal(Animal animal){
        animals.add(animal);
    }
    public void removeAnimal(Animal animal){
        animals.remove(animal);
    }

    public boolean isEmpty(){
        return animals.size() == 0 && plant == 0;
    }

    public String toString(){
        if(animals.size() != 0) {
            return String.valueOf( animals.size() );
        }
        if (plant != 0) {
            return "*";
        }
        return " ";
    }

    public void eat() {
        if (animals.peek() != null) {
            animals.peek().modifyEnergy( plant );
            plant = 0;
        }
    }

    public ArrayList<Animal> breed() {
        ArrayList<Animal> parentsAfterBreeding = new ArrayList<>();
        ArrayList<Animal> newAnimals = new ArrayList<>();

        while (animals.size() >= 2){

            Animal parent1 = animals.poll();
            parentsAfterBreeding.add( parent1 );
            Animal parent2 = animals.poll();
            parentsAfterBreeding.add( parent2 );

            assert parent1 != null;
            if (parent1.getEnergy() >= minBreedEnergy && parent1.getAge() > 0) {

                assert parent2 != null;
                if (parent2.getEnergy() >= minBreedEnergy && parent2.getAge() > 0) {

                    Animal newAnimal = new Animal( parent1, parent2, 2 * energyToBreed  );
                    parent1.modifyEnergy( -energyToBreed );
                    parent2.modifyEnergy( -energyToBreed );

                    this.addAnimal( newAnimal );
                    newAnimals.add( newAnimal );
                }
                else break;
            }
            else break;
        }
        animals.addAll( parentsAfterBreeding );
        return newAnimals;
    }
}