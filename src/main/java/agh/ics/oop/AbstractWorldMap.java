package agh.ics.oop;

import java.util.*;
import java.util.stream.Stream;

public abstract class AbstractWorldMap {
    protected final int width;
    protected final int height;
    private int newPlants;
    private int plantEnergy = 5;
    public int startingAnimalEnergy = 20;
    private int numberOfMutations = 2;
    private int genotypeSize = 10;

    protected final HashMap<Vector2d, Field> fields = new HashMap<>();
    protected ArrayList<Animal> parentsAfterBreeding = new ArrayList<>();


    protected AbstractWorldMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getStartingAnimalEnergy() {
        return startingAnimalEnergy;
    }

    public void setStartingAnimalEnergy(int startingEnergy) {
        this.startingAnimalEnergy = startingEnergy;
    }

    public void setNewPlants(int newPlants) {
        this.newPlants = newPlants;
    }

    public void addNewPlants() {
        Stream<Field> fieldsCollection = fields.values().stream();
        fieldsCollection = fieldsCollection.sorted( Comparator.comparingInt( Field::getNumberOfDiedAnimals ).reversed() );
        List<Field> fieldsWithNewPlants = fieldsCollection.limit( newPlants ).toList();
        for (Field field : fieldsWithNewPlants) {
            placePlant( plantEnergy, field.getPosition() );
        }
    }

    public void place(Animal animal) {
        Vector2d position = animal.getPosition();
        if(objectAt(position) == null) {
            Field field = new Field(this, position);
            field.addAnimal(animal);
            fields.put(position, field);
        }
        else{
            fields.get(position).addAnimal(animal);
        }
    }

    public void placePlant(int energy, Vector2d position) {
        if(objectAt(position) == null) {
            Field field = new Field(this, position);
            field.addPlant(energy);
            fields.put(position, field);
        }
        else{
            fields.get(position).addPlant(energy);
        }
    }

    public void remove(Animal animal){
        Vector2d position = animal.getPosition();
        if (fields.get( position ) != null) {
            fields.get( position ).addDiedAnimal();
            fields.get(position).removeAnimal(animal);
            if(fields.get(position).isEmpty()) {
                fields.remove(position);
            }
        }
    }

    public Object objectAt(Vector2d position) {
        return fields.get(position);
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public abstract void checkBoundaries(Animal animal);

    public void addParentsAfterBreeding(ArrayList<Animal> parentsAfterBreeding) {
        this.parentsAfterBreeding.addAll( parentsAfterBreeding );
    }

    public void setParentsAfterBreeding(ArrayList<Animal> parentsAfterBreeding) {
        this.parentsAfterBreeding = parentsAfterBreeding;
    }

    public ArrayList<Animal> getParentsAfterBreeding() {
        return parentsAfterBreeding;
    }

    public String toString() {
        MapVisualizer drawing = new MapVisualizer(this);
        return drawing.draw(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));
    }

    public void setPlantEnergy(int plantEnergy) {
        this.plantEnergy = plantEnergy;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public int getNumberOfMutations() {
        return numberOfMutations;
    }

    public void setNumberOfMutations(int numberOfMutations) {
        this.numberOfMutations = numberOfMutations;
    }

    public int getGenotypeSize() {
        return genotypeSize;
    }

    public void setGenotypeSize(int genotypeSize) {
        this.genotypeSize = genotypeSize;
    }

    public String getImagePath(Vector2d position){
        if(!this.isOccupied(position)){
            return "src/main/resources/empty.png";
        }
        return fields.get(position).getImagePath();
    }

    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

}
