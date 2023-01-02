package agh.ics.oop;

import java.util.*;

public class Field {
    private int energyToBreed = 5; //todo setter energyToBreed
    private int stuffedAnimal = 5; //todo wieksze od energyToBreed
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
    }

    public int getNumberOfDiedAnimals() {
        return numberOfDiedAnimals;
    }

    public void setEnergyToBreed(int energyToBreed) {
        this.energyToBreed = energyToBreed;
    }

    public void setStuffedAnimal(int stuffedAnimal) {
        this.stuffedAnimal = stuffedAnimal;
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
            if (parent1.getEnergy() >= stuffedAnimal && parent1.getAge() > 0) {

                assert parent2 != null;
                if (parent2.getEnergy() >= stuffedAnimal && parent2.getAge() > 0) {

                    Animal newAnimal = new Animal( parent1, parent2, 2 * energyToBreed  );
                    parent1.modifyEnergy( -energyToBreed );
                    parent2.modifyEnergy( -energyToBreed );

                    this.addAnimal( newAnimal );
                    newAnimals.add( newAnimal );
                }
            }
            else break;
        }
        animals.addAll( parentsAfterBreeding );
        return newAnimals;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public String getImagePath() {
        if(this.isEmpty()){
            return "src/main/resources/empty.png";
        }
        if(animals.size()>0){
            if(animals.size()==1) {
                return "src/main/resources/oneAnimal.png";
            }
            return "src/main/resources/animal.png";
        }
        return "src/main/resources/plant.png";
    }
}