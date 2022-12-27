package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {

    private final Vector2d[] positions;
    private final AbstractWorldMap map;
    private WorldMapElements elements;
    private int worldAge = 10;

    public void setWorldAge(int worldAge) {
        this.worldAge = worldAge;
    }

    public SimulationEngine(AbstractWorldMap map, Vector2d[] positions) {
        this.map = map;
        this.positions = positions;
        this.elements = new WorldMapElements(map);
        for (Vector2d position : positions) {
            Animal animal = new Animal(map, position);
            elements.addAnimal(animal);
            map.place(animal);
        }
    }

    public void run() {
        for (int i = 0; i < worldAge; i++) {
            elements.go();
        }
    }
}
