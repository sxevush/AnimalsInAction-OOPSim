package agh.ics.oop;

import java.util.*;

public abstract class AbstractWorldMap {
    protected final int width;
    protected final int height;
    private int newPlants;
    private int plantEnergy = 5;
    public int startingAnimalEnergy = 20;
    private int numberOfMutations = 2;
    private int genotypeSize = 10;

    protected HashMap<Vector2d, Field> fields = new HashMap<>();
    protected ArrayList<Field> fieldArrayList = new ArrayList<>();


    protected AbstractWorldMap(int width, int height) {
        this.width = width;
        this.height = height;
        createMap( width, height );
    }

    public void createMap (int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Field field = new Field(this);
                fields.put( new Vector2d( x, y ), field);
                fieldArrayList.add(field);
            }
        }
        Collections.shuffle( fieldArrayList );
    }

    public void addNewPlants() {
        fieldArrayList.stream()
                .filter( field -> field.plant == 0 )
                .sorted(Comparator.comparingInt( Field::getNumberOfDiedAnimals )
                        .reversed())
                        .limit( newPlants )
                        .forEach( field -> field.addPlant( plantEnergy ) );
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



    public void place(Animal animal) {
        Vector2d position = animal.getPosition();
        if(objectAt(position) == null) {
            Field field = new Field(this);
            field.addAnimal(animal);
            fields.put(position, field);
        }
        else{
            fields.get(position).addAnimal(animal);
        }
    }

    public void placePlant(int energy, Vector2d position) {
        fields.get(position).addPlant(energy);
    }

    public void remove(Animal animal){
        Vector2d position = animal.getPosition();
        fields.get(position).addDiedAnimal();
        fields.get(position).removeAnimal(animal);
    }

    public Object objectAt(Vector2d position) {
        return fields.get(position);
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public abstract void checkBoundaries(Animal animal);

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

    public int getHeight() {
        return height;
    }
    public int getWidth(){
        return width;
    }

    public String getImagePath(Vector2d position){
        return fields.get(position).getImagePath();
    }

}
