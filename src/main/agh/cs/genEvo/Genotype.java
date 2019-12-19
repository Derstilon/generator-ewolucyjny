package agh.cs.genEvo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Genotype {
    ArrayList<MapDirection> geneList = new ArrayList<MapDirection>() {
        {
            addAll(Arrays.asList(MapDirection.values()));
        }
    };
    Genotype(ArrayList<MapDirection> secondaryGenes){
        geneList.addAll(secondaryGenes);
    }
    Genotype(){
        for(int i = geneList.size(); i < 32; i++){
            geneList.add(MapDirection.values()[ThreadLocalRandom.current().nextInt(0, 7)]);
        }
    }
    ArrayList<MapDirection> getSecondaryGenes(){
        return new ArrayList<>(geneList.subList(8,geneList.size()));
    }
    Genotype RecombinateWith(Genotype mateGenes){
        return new Genotype();
    }
    MapDirection geneticRotation(){
        return geneList.get(ThreadLocalRandom.current().nextInt(0, geneList.size()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genotype genotype = (Genotype) o;
        return geneList.equals(genotype.geneList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(geneList);
    }
}
//al.addAll(arraylist1);
