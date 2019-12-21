package agh.cs.genEvo.managers;

import agh.cs.genEvo.mapElements.animalElements.AnimalInterface;
import agh.cs.genEvo.mapElements.animalElements.AnimalPack;
import agh.cs.genEvo.mapElements.animalElements.Genotype;
import agh.cs.genEvo.observers.PopulationObserver;
import agh.cs.genEvo.utils.Vector2d;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

public class PopulationManager {
    public ArrayList<PopulationObserver> observers;
    private ZonesManager manager;
    public ArrayList<AnimalInterface> population;
    public Hashtable<Vector2d, AnimalPack> animalTable;
    public Hashtable<Genotype, PriorityQueue<AnimalInterface>> familyTable;

    //Constructors//
    public PopulationManager(ZonesManager manager){
        this.manager = manager;
        this.population = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.animalTable = new Hashtable<>();
        this.familyTable = new Hashtable<>();
    }
    //************//

    public Vector2d getRandomPosition(){
        int[] dimensions = manager.getDimensions();
        int zoneIndex = ThreadLocalRandom.current().nextInt(0, dimensions[0]*dimensions[1]);
        int positionIndex = ThreadLocalRandom.current().nextInt(0, manager.getZoneCapacity());
        Vector2d position = manager.getVector(zoneIndex, positionIndex);
        return position;
    }

    public void generatePopulation(Integer count){
        int i = 0;
        while (i<count){
            Vector2d position = getRandomPosition();
            if(observers.get(0).positionForChildGenerated(position)) {
                i++;
                observers.get(1).positionForChildGenerated(position);
            }
        }
    }

    public boolean bigDie(){
        return population.size() == 0;
    }

    public void feedAnimals(int energy, AnimalPack pack){
        ArrayList<AnimalInterface> feedingCandidates = pack.peekDominant();
        Integer leftenergy = energy;
        int i = 0;
        while (leftenergy > 0) {
            feedingCandidates.get(i).addEnergy(Math.min(leftenergy, energy / 4));
            leftenergy -= Math.min(leftenergy, energy / 4);
            i++;
            i %= feedingCandidates.size();
        }
        observers.get(0).animalsFeedOnPosition(pack.peek().getPosition());
        observers.get(1).animalsFeedOnPosition(pack.peek().getPosition());
    }

    public void reproduceAnimals(AnimalPack matingPack, AnimalInterface alfa){
        ArrayList<AnimalInterface> partners = new ArrayList<>();
        while(alfa.isHealthy()){
            AnimalInterface partner = matingPack.poolPartner();
            if(partner == null)
                break;
            partners.add(partner);
            observers.get(1).childGenerated(partner);
            if(!alfa.procreateWith(partner))
                break;
        }
        matingPack = animalTable.get(alfa.getPosition());
        matingPack.addAll(partners);
    }

    private void findGatherings(ArrayList<AnimalPack> gatheredPacks, Integer lowerLimit){
        animalTable.forEach(
                (k, v) -> {
                    if(v != null && v.size()>=lowerLimit) {
                        gatheredPacks.add(v);
                    }
                });
    }
    
    //Simulators//
    public void simulateFeast(){
        ArrayList<AnimalPack> feedingPacks = new ArrayList<>();
        findGatherings(feedingPacks, 1);
        for(AnimalPack pack : feedingPacks){
            Vector2d position = pack.peek().getPosition();
            Integer energyToSplit = observers.get(0).animalsGathered(position);
            if(energyToSplit>0)
                feedAnimals(energyToSplit, pack);
        }
    }
    public void simulateMovement(){
        population.forEach(e->{
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
                observers.get(0).animalDied(e);
                observers.get(1).animalDied(e);
                return true;
            }
        });
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
    //**********//

}
