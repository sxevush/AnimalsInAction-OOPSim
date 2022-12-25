package agh.ics.oop;

public class Main {

    public static void main(String[] args) {
        AbstractWorldMap map = new Globe(10, 15);
        Vector2d[] positions = new Vector2d[]{new Vector2d( 2, 2 ), new Vector2d( 3, 4 )};
        SimulationEngine engine = new SimulationEngine(map, positions);
        engine.run();
        System.out.print(map);
    }

}
