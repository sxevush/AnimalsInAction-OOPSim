package agh.ics.oop;

import java.util.HashSet;
import java.util.Random;

public class SimulationEngine {

    private WorldMapElements elements;
    private AbstractWorldMap map;
    private int worldAge = 10;
    protected int numberOfAnimals = 5;
    private int numberOfPlants = 20;
    private int plantEnergy = 5;
    private HashSet<Vector2d> initialAnimalPositions = new HashSet<>();
    private HashSet<Vector2d> initialPlantPositions = new HashSet<>();

    public void setWorldAge(int worldAge) {
        this.worldAge = worldAge;
    }

    public void setNumberOfAnimals(int numberOfAnimals) {
        this.numberOfAnimals = numberOfAnimals;
    }

    public void setPlantEnergy(int plantEnergy) {
        this.plantEnergy = plantEnergy;
    }

    public void setNumberOfPlants(int numberOfPlants) {
        this.numberOfPlants = numberOfPlants;
    }

    public SimulationEngine(AbstractWorldMap map) {
        this.map = map;
        this.elements = new WorldMapElements(map);
    }

    private void randomizePositions(AbstractWorldMap map, HashSet<Vector2d> positions, int numberOfElements) {
        Random rand = new Random();
        while (positions.size() < numberOfElements) {
            int randomX = rand.nextInt( map.width);
            int randomY = rand.nextInt( map.width);
            positions.add(new Vector2d( randomX, randomY ));
        }
    }

    private void placeAnimals(AbstractWorldMap map) {
        randomizePositions( map, initialAnimalPositions, numberOfAnimals );
        for (Vector2d position : initialAnimalPositions) {
            Animal animal = new Animal( map, position);
            elements.addAnimal(animal);
            map.place(animal);
        }
    }

    private void placePlants(AbstractWorldMap map) {
        randomizePositions( map, initialPlantPositions, numberOfPlants );
        System.out.println( numberOfPlants);
        for (Vector2d position : initialPlantPositions) {
            map.placePlant( plantEnergy, position );
        }
    }



    public void run() {
        placeAnimals( map );
        placePlants( map );
        for (int i = 0; i < worldAge; i++) {
            elements.cleanMap();
            elements.move();
            elements.everyoneEat();
            elements.letsBreed(); //todo nie dziala
        }
    }
}
