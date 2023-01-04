package agh.ics.oop;

import agh.ics.oop.gui.App;

import java.util.*;

// SimulationEngine to klasa odpowiedzialna za symulację. Posiada pola
// zawierające elementy mapy, mapę, wiek świata, liczbę zwierząt i roślin,
// opóźnienie ruchu, instancję klasy App oraz flagi określające, czy symulacja została zatrzymana lub zapauzowana.

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

    // Klasa ta posiada także kolekcje przechowujące pozycje początkowe zwierząt i roślin -
    // initialAnimalPositions i initialPlantPositions.
    private ArrayList<Vector2d> initialAnimalPositions = new ArrayList<>(); // mogą się powtarzać pozycje
    private HashSet<Vector2d> initialPlantPositions = new HashSet<>(); // nie mogą się powtarzać pozycje

    public SimulationEngine(AbstractWorldMap map, App app) {
        this.map = map;
        this.elements = new WorldMapElements(map);
        this.app = app;
    }

    public WorldMapElements getElements() { return elements; }
    public void setWorldAge(int worldAge) {
        this.worldAge = worldAge;
    }
    public void setNumberOfAnimals(int numberOfAnimals) { this.numberOfAnimals = numberOfAnimals; }
    public void setNumberOfPlants(int numberOfPlants) { this.numberOfPlants = numberOfPlants; }
    public void setMoveDelay(int delay){
        this.moveDelay = delay;
    }


    // Metoda randomizePositions służy do losowego umieszczenia elementów na mapie.
    private void randomizePositions(AbstractWorldMap map, Collection<Vector2d> positions, int numberOfElements) {
        Random rand = new Random();
        while (positions.size() < numberOfElements) {
            int randomX = rand.nextInt( map.width);
            int randomY = rand.nextInt( map.height);
            positions.add(new Vector2d( randomX, randomY ));
        }
    }

    // Losowo ustawia zwierzęta na mapie (mogą się powtarzać).
    private void placeAnimals(AbstractWorldMap map) {
        randomizePositions( map, initialAnimalPositions, numberOfAnimals );
        for (Vector2d position : initialAnimalPositions) {
            Animal animal = new Animal( map, position, map.getStartingAnimalEnergy());
            elements.addAnimal(animal);
            map.place(animal);
        }
    }

    // Losowo ustawia rośliny na mapie (nie mogą się powtarzać).
    private void placePlants(AbstractWorldMap map) {
        randomizePositions( map, initialPlantPositions, numberOfPlants );
        for (Vector2d position : initialPlantPositions) {
            map.placePlant( map.getPlantEnergy(), position );
        }
    }

    // Metoda run jest główną metodą symulacji - umieszcza zwierzęta i rośliny na mapie,
    // a następnie przez określoną liczbę kroków symulacji (worldAge) wykonuje następujące czynności:
    // sprawdza, czy symulacja nie została zatrzymana,
    // opóźnia ruch o czas określony w polu moveDelay,
    // usuwa martwe elementy z mapy,
    // przesuwa zwierzęta,
    // sprawia, że zwierzęta jedzą,
    // sprawia, że zwierzęta rozmnażają się,
    // dodaje nowe rośliny na mapę,
    // odświeża widok aplikacji.
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

    // Metoda everyoneEat sprawia, że każde zwierzę na mapie je.
    public void everyoneEat() {
        map.fields.values().forEach( Field::eat );
    }

    // Metoda everyoneBreed sprawia, że każde zwierzę rozmnaża się.
    public void everyoneBreed() {
        map.fields.values().forEach( field -> {
            ArrayList<Animal> newAnimals = field.breed();
            elements.addAnimals( newAnimals );
        } );
    }
}
