package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Genotype {
    private List<Integer> genotype = new ArrayList<>();
    private int pointer = 0;
    private HashSet<Integer> mutations = new HashSet<>(); // pozwala na niepowtarzajace sie elementy
    private int numberOfMutations = 2;
    private int genotypeSize = 10; //TODO setter w aplikacji

    public void setNumberOfMutations(int numberOfMutations) {
        this.numberOfMutations = numberOfMutations;
    }

    public void setGenotypeSize(int genotypeSize) {
        this.genotypeSize = genotypeSize;
    }

    public Genotype() {
        Random rand = new Random();

        newGenotype( rand, genotypeSize );
        System.out.println(genotype);
        mutate( rand );
        System.out.println(genotype);
    }

    public Genotype(Animal parent1, Animal parent2){
        int change= (int) parent1.getEnergy()/(parent1.getEnergy() + parent2.getEnergy());

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

        Random rand = new Random();
        this.pointer = rand.nextInt(genotypeSize);

        mutate( rand );

    }

    private void newGenotype(Random rand, int genotypeSize) {
        for (int i = 0 ; i < genotypeSize ; i++) {
            int gen = rand.nextInt(8);
            this.genotype.add(gen);
        }
    }

    private void mutate(Random rand) {
        while (mutations.size() < numberOfMutations) {
            int mutation = rand.nextInt(8);
            mutations.add(mutation);
        }

        for (Integer mutation : mutations) {
            int gen = genotype.get(mutation);
            double probability = Math.random();
            if (probability > 0.5) {
                genotype.set(mutation, gen + 1);
            } else {
                genotype.set(mutation, gen - 1);
            }
        }
    }

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
}
