package agh.ics.oop;

public class Main {

    public static void main(String[] args) {
        AbstractWorldMap map = new Globe(10, 15);
        SimulationEngine engine = new SimulationEngine(map);
        engine.setNumberOfPlants( 10 ); // todo wyjatki dla za duzych/ujemnych wartosci
        engine.setWorldAge( 1000 );
        engine.setNumberOfAnimals( 100 );
        map.setNewPlants( 5 );
        map.setPlantEnergy( 5 );
        map.setStartingAnimalEnergy( 20 );
        map.setGenotypeSize( 8 );
        map.setNumberOfMutations( 2 );
        engine.run();
        System.out.print(map);
        // todo sprawdzic czy zwierzaki jedza nowe roslinki, ale powinny
        // todo moze poprawic zlozonosc obliczeniowa - program nie jest super szybki, z drugiej strony i tak watki nie wymagaja idealnej zlozonosci
    }

}
