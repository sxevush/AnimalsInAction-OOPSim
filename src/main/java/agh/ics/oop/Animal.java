package agh.ics.oop;

import java.util.Random;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH; // TODO poczatkowy kierunek
    private AbstractWorldMap map;
    private Vector2d position;


    private int energy;
    private int age = 0; //licznik tur, które przeżyło zwierzę
    private Genotype genotype;


    public final int startingEnergy = 10; //TODO ustawić setter czy cos takiego bo to brzydkie

    public Animal(AbstractWorldMap map, Vector2d startingPosition){
        this.map = map;
        position = startingPosition;
        genotype = new Genotype();
        energy = startingEnergy;
    }

    public Animal(Animal parent1, Animal parent2){
        this.map = parent1.map;
        this.position = parent1.position;
        this.genotype = new Genotype(parent1, parent2);
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


    public void move() {
        // TODO do testu mapy - bedzie zmienione
        Vector2d oldPosition = position;
        Integer turn = this.genotype.next();

        for (int i = 0 ; i < turn ; i++) {
            orientation = orientation.next();
        }

        position = position.add( orientation.toUnitVector() );
        map.checkBoundaries( this );
        positionChanged( oldPosition, position ); // TODO OBSERVER

        energy--;
    }

    public void modifyEnergy(int energy){
        this.energy += energy; //można również odejmować energię przy pomocy tej funkcji dając wartość ujemną jako argument
    }

    @Override
    public String toString() {
        return this.orientation.toString();//TODO zmienic dla kilku
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        map.fields.remove(oldPosition);
        map.place(this);
    }
    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }
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
}
