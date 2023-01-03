package agh.ics.oop;

import java.util.PriorityQueue;
import java.util.Random;

public class Animal {
    Random random = new Random();
    private MapDirection orientation = MapDirection.values()[random.nextInt(MapDirection.values().length)];
    private AbstractWorldMap map;
    private Vector2d position;
    private int energy;
    private int age = 0; //licznik tur, które przeżyło zwierzę
    private Genotype genotype;


    public Animal(AbstractWorldMap map, Vector2d startingPosition, int startingEnergy){
        this.map = map;
        this.position = startingPosition;
        this.genotype = new Genotype(map.getGenotypeSize(), map.getNumberOfMutations() );
        this.energy = startingEnergy;
    }

    public Animal(Animal parent1, Animal parent2, int startingEnergy){
        this.map = parent1.map;
        this.position = parent1.position;
        this.genotype = new Genotype(parent1, parent2, map.getGenotypeSize(), map.getNumberOfMutations());
        this.energy = startingEnergy;
        //wylosowanie startowej orientacji
        Random rand = new Random();
        int turn = rand.nextInt(8);
        for (int i = 0 ; i < turn ; i++) {
            orientation = orientation.next();
        }

    }
    public void setPosition(Vector2d position) {
        this.position = position;
    }
    public void setOrientation(MapDirection orientation) { this.orientation = orientation; }
    public Vector2d getPosition(){
        return this.position;
    }
    public MapDirection getOrientation() {
        return orientation;
    }
    public int getEnergy(){
        return energy;
    }
    public int getAge(){ return age;}
    public Genotype getGenotype(){
        return genotype;
    }


    public void move() {
        Vector2d oldPosition = position;
        Integer turn = this.genotype.next();

        for (int i = 0 ; i < turn ; i++) {
            orientation = orientation.next();
        }

        position = position.add( orientation.toUnitVector() );
        map.checkBoundaries( this );
        positionChanged( oldPosition ); // TODO OBSERVER

        energy--;
        age++;

    }

    public void modifyEnergy(int energy){
        this.energy += energy; //można również odejmować energię przy pomocy tej funkcji dając wartość ujemną jako argument
    }

    public void positionChanged(Vector2d oldPosition) {
        PriorityQueue<Animal> animals = map.fields.get( oldPosition ).getAnimals();
        if (animals.remove(this)) {
            map.fields.get( oldPosition ).setAnimals( animals );
        }
        map.place(this);
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }
}
