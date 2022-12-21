package agh.ics.oop;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH; // TODO poczatkowy kierunek
    private AbstractWorldMap map;
    private Vector2d position;


    private int energy;
    private int age = 0; //licznik tur, które przeżyło zwierzę, a w praktyce pointer na aktualny gen
    private int genotypeSize;
    private List<Integer> genotype = new ArrayList<>();

    public Animal(AbstractWorldMap map, Vector2d startingPosition){
        this.map = map;
        this.position = startingPosition;
        List<Integer> genList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public void setOrientation(MapDirection orientation) { this.orientation = orientation; }

    public Animal(Animal parent1, Animal parent2){
        this.genotypeSize = parent1.genotype.size();
    }

    public void move(int direction){
        // do testu mapy - bedzie zmienione
        Vector2d oldPosition = position;
        if (direction == 0) {
            Vector2d unitVector = MapDirection.NORTH.toUnitVector();
            position = position.add(unitVector);
            System.out.println(position);
        }
        map.checkBoundaries(this);
        positionChanged(oldPosition, position);
    }

    @Override
    public String toString() {
        return "$";
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        map.animals.remove(oldPosition);
        map.animals.put(newPosition, this);
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
}
