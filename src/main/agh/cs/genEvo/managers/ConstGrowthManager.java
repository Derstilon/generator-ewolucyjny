package agh.cs.genEvo.managers;

import agh.cs.genEvo.observers.GrowthObserver;
import agh.cs.genEvo.utils.Vector2d;
import agh.cs.genEvo.mapElements.WorldMapBiome;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ConstGrowthManager implements GrowthInterface{
    private ZonesManager manager;

    //Constructor//
    public ConstGrowthManager(ZonesManager manager){
        this.manager = manager;
    }
    //***********//

    public Vector2d getRandomPosition(WorldMapBiome inBiome){
        int sectionSize = manager.getSectionSize(inBiome);
        if(sectionSize == 0)
            return null;
        int zoneIndex = ThreadLocalRandom.current().nextInt(0, sectionSize);
        int positionIndex = ThreadLocalRandom.current().nextInt(0, manager.getZoneCapacity());
        Vector2d position = manager.getVector(zoneIndex, positionIndex, inBiome);
        return position;
    }

    //Simulator//
    @Override
    public void simulateGrowth() {
        //System.out.println("SAME SAME");
        for (WorldMapBiome biome : WorldMapBiome.values()){
            Vector2d position = getRandomPosition(biome);
            if(position == null)
                continue;
            if(observers.get(0).positionGrowthSet(position)) {
                observers.get(1).positionGrowthSet(position);
                continue;
            }
            Vector2d nextPosition = position;
            int i = 0;
            int k = manager.getSectionSize(biome)*manager.getZoneCapacity();
            while (i<k){
                nextPosition = manager.nextPosition(nextPosition);
                if(nextPosition != null){
                if(manager.zoneAt(nextPosition).getBiome().equals(biome)){
                    i++;
                    if(observers.get(0).positionGrowthSet(position)) {
                        observers.get(1).positionGrowthSet(position);
                        break;
                    }
                }
                }else{
                    nextPosition = new Vector2d(0,0);
                }
                position  = getRandomPosition(biome);
                if(observers.get(0).positionGrowthSet(position)) {
                    observers.get(1).positionGrowthSet(position);
                    break;
                }
            }
        }
    }
    //*********//

    //ControlledRandomGenerators//
    @Override
    public void simulateGrowth(int index) {
        for (WorldMapBiome biome : WorldMapBiome.values()){
            Vector2d position = getRandomPosition(biome, index);
            if(position == null)
                continue;
            if(observers.get(0).positionGrowthSet(position)) {
                observers.get(1).positionGrowthSet(position);
                continue;
            }
            Vector2d nextPosition = position;
            int i = 0;
            int k = manager.getZoneCapacity()*manager.getDimensions()[0]*manager.getDimensions()[1];
            while (i<k){
                nextPosition = manager.nextPosition(nextPosition);
                if(nextPosition != null){
                    if(manager.zoneAt(nextPosition).getBiome().equals(biome)){
                        i++;
                        if(observers.get(0).positionGrowthSet(position)) {
                            observers.get(1).positionGrowthSet(position);
                            break;
                        }
                    }
                }else{
                    nextPosition = new Vector2d(0,0);
                }
            }
        }
    }
    @Override
    public void plantEatenOnPosition(Vector2d position) {
        plantTable.remove(position);
    }
    @Override
    public Vector2d getRandomPosition(WorldMapBiome inBiome, int index){
        int sectionSize = manager.getSectionSize(inBiome);
        if(sectionSize == 0)
            return null;
        int zoneIndex = index%sectionSize;
        int positionIndex = index%manager.getZoneCapacity();
        Vector2d position = manager.getVector(zoneIndex, positionIndex, inBiome);
        return position;
    }
    //**************************//
}
