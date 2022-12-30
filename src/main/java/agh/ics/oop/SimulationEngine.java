package agh.ics.oop;

import agh.ics.oop.gui.App;

import java.util.*;

public class SimulationEngine implements Runnable{

    private final WorldMapElements elements;
    private final AbstractWorldMap map;
    private int worldAge = 10;
    protected int numberOfAnimals = 5;
    private int numberOfPlants = 20;

    private final ArrayList<Vector2d> initialAnimalPositions = new ArrayList<>(); // moga sie powtarzac pozycje
    private final HashSet<Vector2d> initialPlantPositions = new HashSet<>(); // nie moga sie powtyarzac pozycje

    //parametry aplikacyjne
    private int moveDelay = 0;

    private App app;

    public SimulationEngine(AbstractWorldMap map) {
        this.map = map;
        this.elements = new WorldMapElements(map);
    }

    public SimulationEngine(AbstractWorldMap map, App app) {
        this.map = map;
        this.elements = new WorldMapElements(map);
        this.app = app;
    }
    public void setWorldAge(int worldAge) {
        this.worldAge = worldAge;
    }

    public void setNumberOfAnimals(int numberOfAnimals) {
        this.numberOfAnimals = numberOfAnimals; //todo wyjatek zeby nie bylo wiecej niz sie da
    }


    public void setNumberOfPlants(int numberOfPlants) {
        this.numberOfPlants = numberOfPlants; //todo wyjatek zeby nie bylo wiecej niz sie da
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

    private void placePlants(AbstractWorldMap map) { // moze mozna dac do AbstractWorldMap?
        randomizePositions( map, initialPlantPositions, numberOfPlants );
        System.out.println( numberOfPlants);
        for (Vector2d position : initialPlantPositions) {
            map.placePlant( map.getPlantEnergy(), position );
        }
    }


    public void run() {
        placeAnimals( map );
        placePlants( map );
        for (int i = 0; i < worldAge; i++) {
            try {
                Thread.sleep(moveDelay);
                elements.cleanMap();
                elements.move();
                elements.everyoneEat();
                elements.letsBreed();
                for (Animal animal : map.getParentsAfterBreeding()) {
                    elements.addAnimal(animal);
                    map.place(animal);
                }
                map.setParentsAfterBreeding( new ArrayList<>() ); // todo brzydkie w chuj - zmienic
                map.addNewPlants();
                app.refresh();


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void setMoveDelay(int delay){
        this.moveDelay = delay;
    }
}
