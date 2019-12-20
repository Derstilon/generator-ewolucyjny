package agh.cs.genEvo.mapElements.animalElements;

import agh.cs.genEvo.utils.MapDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        geneList.addAll(randomizeSecondaryGenes());
    }
    private ArrayList<MapDirection> randomizeSecondaryGenes(){
        ArrayList<MapDirection> secondaryGenes = new ArrayList<>();
        for(int i = geneList.size(); i < 32; i++){
            secondaryGenes.add(MapDirection.values()[ThreadLocalRandom.current().nextInt(0, 8)]);
            Collections.sort(secondaryGenes);
        }
        return secondaryGenes;
    }
    public ArrayList<MapDirection> getSecondaryGenes(){
        return new ArrayList<>(geneList.subList(8,geneList.size()));
    }
    public Genotype RecombinateWith(Genotype mateGenes){
        Integer cut1 = ThreadLocalRandom.current().nextInt(6, 10);
        Integer cut2 = ThreadLocalRandom.current().nextInt(6, 10);
        ArrayList<MapDirection> childSecondaryGenes = new ArrayList<>();
        childSecondaryGenes.addAll(mateGenes.getSecondaryGenes().subList(0,cut1));
        childSecondaryGenes.addAll(this.getSecondaryGenes().subList(cut1,cut1+cut2));
        childSecondaryGenes.addAll(mateGenes.getSecondaryGenes().subList(cut1+cut2,mateGenes.getSecondaryGenes().size()));
        Collections.sort(childSecondaryGenes);
        return new Genotype(childSecondaryGenes);
    }
    public MapDirection geneticRotation(){
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
