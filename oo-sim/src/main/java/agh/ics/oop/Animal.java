package agh.ics.oop;
import java.util.ArrayList;
import java.util.Arrays;
import static java.lang.Math.random;
import java.util.List;

public class Animal {
    private MapDirection orientation;
    private AbstractWorldMap map;
    private Vector2d position;



    private int energy;
    private int age = 0; //licznik tur, które przeżyło zwierzę, a w praktyce pointer na aktualny gen
    private int genotypeSize;
    private List<Integer> genotype = new ArrayList<>();

    public Animal(AbstractWorldMap map, Vector2d startingPosition, int genotypeSize){
        this.map = map;
        this.position = startingPosition;
        this.genotypeSize = genotypeSize;

        List<Integer> genList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
    }

    public Animal(Animal parent1, Animal parent2){
        this.genotypeSize = parent1.genotype.size();
    }

    public void move(MapDirection direction){

    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }
    public Vector2d getPosition(){
        return this.position;
    }

}
