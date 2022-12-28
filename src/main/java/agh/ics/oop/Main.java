package agh.ics.oop;

public class Main {

    public static void main(String[] args) {
        AbstractWorldMap map = new Hell(10, 15);
        SimulationEngine engine = new SimulationEngine(map);
        engine.setNumberOfPlants( 10 );
        engine.setWorldAge( 8 );
        engine.setNumberOfAnimals( 5 );
        engine.run();
        System.out.print(map);
    }

}
