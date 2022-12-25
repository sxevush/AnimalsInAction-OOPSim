package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {

    private final Vector2d[] positions;
    private final AbstractWorldMap map;
    private final List<Animal> listAnimals = new ArrayList<>();
    private int worldAge = 10;

    public void setWorldAge(int worldAge) {
        this.worldAge = worldAge;
    }

    public SimulationEngine(AbstractWorldMap map, Vector2d[] positions) {
        this.map = map;
        this.positions = positions;
        for (Vector2d position : positions) {
            Animal animal = new Animal(map, position);
            // TODO place
            listAnimals.add(animal);
            map.place(animal);
        }
    }

    public void run() {
        for (int i = 0; i < worldAge; i++) {
            for (Animal listAnimal : listAnimals) listAnimal.move();
        }
    }
}
