package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

// Klasa Genotype reprezentuje genotyp zwierzęcia. Posiada listę genów List<Integer> genotype,
// która przechowuje informację o kierunku ruchu zwierzęcia na najbliższej turze.
// Klasa ta zawiera metody służące do tworzenia i mutacji genotypu zwierzęcia.

public class Genotype {
    private List<Integer> genotype = new ArrayList<>();
    private int pointer = 0;
    Random rand = new Random();
    private HashSet<Integer> mutations = new HashSet<>(); // pozwala na niepowtarzajace sie elementy
    int genotypeSize;
    int numberOfMutations;

    // Konstruktor Genotype(int genotypeSize, int numberOfMutations) tworzy nowy,
    // losowy genotyp o długości genotypeSize i numberOfMutations mutacjach.
    public Genotype(int genotypeSize, int numberOfMutations) {
        Random rand = new Random();
        newGenotype( rand, genotypeSize );
        this.genotypeSize = genotypeSize;
        this.numberOfMutations = numberOfMutations;
    }

    // Konstruktor Genotype(Animal parent1, Animal parent2, int genotypeSize, int numberOfMutations)
    // tworzy genotyp potomka na podstawie genotypów rodziców.
    // Genotyp potomka jest tworzony poprzez połączenie fragmentów genotypów rodziców.
    // Następnie następuje mutacja genotypu potomka zgodnie z ustawionym licznikiem numberOfMutations.
    public Genotype(Animal parent1, Animal parent2, int genotypeSize, int numberOfMutations){
        this.genotypeSize = genotypeSize;
        this.numberOfMutations = numberOfMutations;
        int change = 0;
        if (parent1.getEnergy() + parent2.getEnergy() != 0) {
            change = parent1.getEnergy()/(parent1.getEnergy() + parent2.getEnergy())*genotypeSize;
        }

        double probability = Math.random();
        Genotype genotype1 = parent1.getGenotype();
        Genotype genotype2 = parent2.getGenotype();
        //losowy wybór kolejności w jakiej wybieramy fragmenty genów rodziców
        if(probability>0.5){
            genotype1 = parent2.getGenotype();
            genotype2 = parent1.getGenotype();
            change = genotypeSize - change; //jeśli zmieniamy kolejność rodziców, wtedy drugi rodzic przekazuje swoje pierwsze geny, dlatego zmieniamy wartość zmiennej change

        }
        //pierwszy fragment - od zera do change
        for(int i = 0; i<change; i++){
            int gen = genotype1.genotype.get(i);
            this.genotype.add(gen);
        }
        //drugi fragment - od change do końca
        for(int i = change; i<genotypeSize; i++){
            int gen = genotype2.genotype.get(i);
            this.genotype.add(gen);
        }

        mutate();
        this.pointer = rand.nextInt(genotypeSize);
    }

    private void newGenotype(Random rand, int genotypeSize) {
        for (int i = 0 ; i < genotypeSize ; i++) {
            int gen = rand.nextInt(8); // 8 - liczba kierunkow
            this.genotype.add(gen);
        }
    }

    private void mutate() {
        while (mutations.size() < numberOfMutations) {
            int mutation = rand.nextInt(genotypeSize);
            mutations.add(mutation);
        }

        for (Integer mutation : mutations) {
            int gen = genotype.get(mutation);
            double probability = Math.random();
            if (probability > 0.5) {
                if (gen + 1 > 7) {
                    genotype.set(mutation, 0);
                }
                else {
                    genotype.set(mutation, gen + 1);
                }
            } else {
                if (gen - 1 < 0) {
                    genotype.set(mutation, 7);
                } else {
                    genotype.set(mutation, gen - 1);
                }
            }
        }
    }

    // Metoda next() zwraca kierunek ruchu zwierzęcia na najbliższej turze.
    // Kierunek ten jest wybierany na podstawie genu o indeksie pointer,
    // a następnie przesuwana jest wartość pointer. W 20% przypadków wartość pointer
    // jest losowana ponownie, co pozwala na losowe zmiany kierunku ruchu zwierzęcia.
    public Integer next() {
        // nieco szalenstwa - w 20% przypadkow zwierzak przeskakuje do losowego genu
        pointer++;
        pointer = pointer % genotype.size();
        double probability = Math.random();
        if (probability > 0.8) {
            pointer = (int) (Math.random() * genotype.size());
        }
        return this.genotype.get(pointer);
    }

    @Override
    public String toString() {
        return genotype.toString();
    }
}
