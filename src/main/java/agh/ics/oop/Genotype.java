package agh.ics.oop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Genotype {
    private List<Integer> genotype = new ArrayList<>();
    private int pointer=0;
    private final int  genotypeSize = 10;

    public Genotype(){
        //TODO setter w aplikacji
        Random rand = new Random();
        for(int i = 0; i<genotypeSize; i++){
            int gen = rand.nextInt(8);
            this.genotype.add(gen);

        }
    }

    public Integer next(){
        pointer++;
        pointer = pointer % genotype.size();
        double probability = Math.random();
        if(probability > 0.8){
            pointer = (int)(Math.random() * genotype.size());
        }
        return this.genotype.get(pointer);
    }
}
