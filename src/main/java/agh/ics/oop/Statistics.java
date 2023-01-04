package agh.ics.oop;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Statistics {

    private AbstractWorldMap map;
    private SimulationEngine engine;

    public Statistics(AbstractWorldMap map, SimulationEngine engine) {
        this.map = map;
        this.engine = engine;
    }

    public int countAllAnimals() { return engine.getElements().getSize(); }
    public long countAllPlants() { return map.getFieldArrayList().stream().filter(field -> field.getPlant() != 0).count(); }
    public long countFreeFields() { return map.getFieldArrayList().stream().filter(field -> field.getPlant() == 0)
            .filter(field -> field.getAnimals().size() == 0).count(); }

    public String mostPopularGenotype() {
        ArrayList<String> genotypes = new ArrayList<>();
        ArrayList<Animal> animals = engine.getElements().getAnimals();
        if (animals.size() != 0) {
            for (Animal animal : animals) {
                genotypes.add(animal.getGenotype().toString());
            }
            return findMostCommonElement(genotypes);
        }
        return "brak zwierzakow";
    }

    public double avgEnergyLevel() {
        ArrayList<Animal> animals = engine.getElements().getAnimals();
        return Math.round(animals.stream().
                mapToDouble(animal -> (double) animal.getEnergy()).
                average().
                orElse(0.0)*100.0)/100.0;
    }

    public double avgAnimalAge() {
        return Math.round(engine.getElements().
                getAgeOfDiedAnimals().
                stream().
                mapToDouble(i -> (double) i).
                average().
                orElse(0.0)*100.0)/100.0;
    }

    public String findMostCommonElement(ArrayList<String> list) {
        // hashmap to store the frequency of element
        Map<String, Integer> hm = new HashMap<>();

        for (String i : list) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }

        return Collections.max(hm.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    public void makeData(String fileName) throws IOException {
        String[][] data = {{"Most common genotype: ", mostPopularGenotype()},
                {"Number of animals: ", Integer.toString(countAllAnimals())},
                {"Number of plants: ", Long.toString(countAllPlants())},
                {"Free fields on the map: ", Long.toString(countFreeFields())},
                {"Average energy level of alive animal: ", Double.toString(avgEnergyLevel())},
                {"Average age level of dead animals ", Double.toString(avgAnimalAge())},
                {"\n"}
        };
        writeFile(fileName, data);
    }

    public static void writeFile(String fileName, String[][] data) {
        ArrayList<String> out = new ArrayList<>();

        for (String[] series : data) {
            String s = Arrays.toString(series);
            s = s.replace("[", "");
            s = s.replace("]", "");
            out.add(s);
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
            for (String s : out) {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("System nie moze otworzyc pliku. ");
        }
    }

}
