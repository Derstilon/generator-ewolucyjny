package agh.cs.genEvo.comparators;

import agh.cs.genEvo.mapElements.animalElements.AnimalInterface;

import java.util.Comparator;

public class AnimalComparator implements Comparator<AnimalInterface> {
    @Override
    public int compare(AnimalInterface animal1, AnimalInterface animal2) {
        return animal2.getEnergy()-animal1.getEnergy();
    }
}
