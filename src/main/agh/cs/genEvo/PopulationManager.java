package agh.cs.genEvo;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

public class PopulationManager {
    private PopulationObserver observer;
    private ZonesManager manager;
    public ArrayList<AnimalInterface> population;
    public Hashtable<Vector2d, AnimalPack> animalTable;
    public Hashtable<Genotype, PriorityQueue<AnimalInterface>> familyTable;
    PopulationManager(PopulationObserver worldMap, ZonesManager manager){
        this.observer = worldMap;
        this.manager = manager;
        this.population = new ArrayList<>();
        this.animalTable = new Hashtable<>();
        this.familyTable = new Hashtable<>();
    }
    public Vector2d getRandomPosition(){
        //System.out.println("SAME SAME");
        int[] dimensions = manager.getDimensions();
        //System.out.println(dimensions[0] + ":" + dimensions[1]);
        int zoneIndex = ThreadLocalRandom.current().nextInt(0, dimensions[0]*dimensions[1]);
        int positionIndex = ThreadLocalRandom.current().nextInt(0, manager.getZoneCapacity());
        //System.out.println(zoneIndex + ":" + positionIndex);
        Vector2d position = manager.getVector(zoneIndex, positionIndex);
        //System.out.println(zoneIndex +" " + positionIndex + " " +position.toString() + " " + sectionSize);
        return position;
    }
    public void GeneratePopulation(Integer count){
        int i = 0;
        while (i<count){
            Vector2d position = getRandomPosition();
            if(observer.positionForChildGenerated(position))
                i++;
        }
    }

    public void simulateMovement(){
        population.forEach(e->{
            //System.out.println(e.getPosition() + " " + animalTable.get(e.getPosition()).peek().getPosition());
            //System.out.println("SAME SAME");
            if(!e.move())
                System.out.println("I'm dead");
        });
    }
    public void simulateExtinction(){
        population.removeIf(e -> {
            if(e.isAlive()){
                return false;
            }else{
                Vector2d position = e.getPosition();
                animalTable.get(position).remove(e);
                return true;
            }
        });
    }
    public void feedAnimals(int energy, AnimalPack pack){
        //AnimalPack pack = animalTable.get(position);
        ArrayList<AnimalInterface> feedingCandidates = pack.peekDominant();
        Integer leftenergy = energy;
        int i = 0;
        while (leftenergy > 0) {
            feedingCandidates.get(i).addEnergy(Math.min(leftenergy, energy / 4));
            leftenergy -= Math.min(leftenergy, energy / 4);
            i++;
            i %= feedingCandidates.size();
        }
        observer.animalsFeedOnPosition(pack.peek().getPosition());
    }
    public void reproduceAnimals(AnimalPack matingPack, AnimalInterface alfa){
        ArrayList<AnimalInterface> partners = new ArrayList<>();
        while(alfa.isHealthy()){
            AnimalInterface partner = matingPack.poolPartner();
            if(partner == null)
                break;
            partners.add(partner);
            if(!alfa.procreateWith(partner))
                break;
                //System.out.println("SAME DZIECI");
        }
        matingPack = animalTable.get(alfa.getPosition());
        matingPack.addAll(partners);
    }

    public void simulateFeast(){
        ArrayList<AnimalPack> feedingPacks = new ArrayList<>();
        findGatherings(feedingPacks, 1);
        for(AnimalPack pack : feedingPacks){
            Vector2d position = pack.peek().getPosition();
            Integer energyToSplit = observer.animalsGathered(position);
            if(energyToSplit>0)
                feedAnimals(energyToSplit, pack);
        }
    }
    public void simulateReproduction(){
        ArrayList<AnimalPack> matingPacks = new ArrayList<>();
        findGatherings(matingPacks, 2);
        for(AnimalPack pack : matingPacks){
            Vector2d position = pack.peek().getPosition();
            AnimalInterface alfa = pack.poolAlfa();
            if(alfa != null) {
                if (pack.peek().isHealthy()) {
                    reproduceAnimals(pack, alfa);
                }
                AnimalPack childPack = animalTable.get(alfa.getPosition());
                childPack.add(alfa);
            }
        }
    }

    private void findGatherings(ArrayList<AnimalPack> gatheredPacks, Integer lowerLimit){
        animalTable.forEach(
                (k, v) -> {
                    if(v != null && v.size()>=lowerLimit) {
                        gatheredPacks.add(v);
                    }
                });
    }
}
