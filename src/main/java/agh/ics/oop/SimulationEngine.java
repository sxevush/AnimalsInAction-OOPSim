package agh.ics.oop;

import agh.ics.oop.gui.App;

import java.util.*;

public class SimulationEngine implements Runnable {

    private WorldMapElements elements;
    private AbstractWorldMap map;
    private int worldAge = 10;
    protected int numberOfAnimals = 5;
    private int numberOfPlants = 20;
    private int moveDelay = 0;
    private App app;
    private boolean stopped = false;
    private boolean paused = false;

    private ArrayList<Vector2d> initialAnimalPositions = new ArrayList<>(); // moga sie powtarzac pozycje
    private HashSet<Vector2d> initialPlantPositions = new HashSet<>(); // nie moga sie powtyarzac pozycje

    public SimulationEngine(AbstractWorldMap map, App app) {
        this.map = map;
        this.elements = new WorldMapElements(map);
        this.app = app;
    }

    public int getWorldAge() { return worldAge; }
    public WorldMapElements getElements() { return elements; }
    public void setWorldAge(int worldAge) {
        this.worldAge = worldAge;
    }
    public void setNumberOfAnimals(int numberOfAnimals) { this.numberOfAnimals = numberOfAnimals; }
    public void setNumberOfPlants(int numberOfPlants) { this.numberOfPlants = numberOfPlants; }
    public void setMoveDelay(int delay){
        this.moveDelay = delay;
    }

    private void randomizePositions(AbstractWorldMap map, Collection<Vector2d> positions, int numberOfElements) {
        Random rand = new Random();
        while (positions.size() < numberOfElements) {
            int randomX = rand.nextInt( map.width);
            int randomY = rand.nextInt( map.height);
            positions.add(new Vector2d( randomX, randomY ));
        }
    }

    private void placeAnimals(AbstractWorldMap map) {
        randomizePositions( map, initialAnimalPositions, numberOfAnimals );
        for (Vector2d position : initialAnimalPositions) {
            Animal animal = new Animal( map, position, map.getStartingAnimalEnergy());
            elements.addAnimal(animal);
            map.place(animal);
        }
    }

    private void placePlants(AbstractWorldMap map) {
        randomizePositions( map, initialPlantPositions, numberOfPlants );
        for (Vector2d position : initialPlantPositions) {
            map.placePlant( map.getPlantEnergy(), position );
        }
    }

    public void run() {
        placeAnimals( map );
        placePlants( map );
        for (int i = 0; i < worldAge; i++) {
            if (!stopped) {
                try {
                    while(paused){System.out.print("");}
                    Thread.sleep(moveDelay);
                    elements.cleanMap();
                    elements.move();
                    everyoneEat();
                    everyoneBreed();
                    map.addNewPlants();
                    app.refresh();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else break;
        }


    }
    public void stop(){
        this.stopped = true;
    }
    public void pause(){
        this.paused = !paused;
    }

    public void everyoneEat() {
        map.fields.values().forEach( Field::eat );
    }

    public void everyoneBreed() {
        map.fields.values().forEach( field -> {
            ArrayList<Animal> newAnimals = field.breed();
            elements.addAnimals( newAnimals );
        } );
    }
}
