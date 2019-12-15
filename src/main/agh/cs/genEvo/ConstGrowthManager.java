package agh.cs.genEvo;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ConstGrowthManager implements GrowthInterface{
    GrowthObserver observer;
    ZonesManager manager;
    ConstGrowthManager(GrowthObserver worldMap, ZonesManager manager){
        this.observer = worldMap;
        this.manager = manager;
    }

    public Vector2d getRandomPosition(WorldMapBiome inBiome){
        //System.out.println("SAME SAME");
        int sectionSize = manager.getSectionSize(inBiome);
        //System.out.println(sectionSize);
        if(sectionSize == 0)
            return null;
        int zoneIndex = ThreadLocalRandom.current().nextInt(0, sectionSize);
        int positionIndex = ThreadLocalRandom.current().nextInt(0, manager.getZoneCapacity());
        Vector2d position = manager.getVector(zoneIndex, positionIndex, inBiome);
        //System.out.println(zoneIndex +" " + positionIndex + " " +position.toString() + " " + sectionSize);
        return position;
    }

    @Override
    public void SimulateGrowth() {
        //System.out.println("SAME SAME");
        for (WorldMapBiome biome : WorldMapBiome.values()){
            Vector2d position = getRandomPosition(biome);
            if(position == null)
                continue;
            if(observer.positionGrowthSet(position))
                continue;
            Vector2d nextPosition = position;
            int i = 0;
            int k = manager.getSectionSize(biome)*manager.getZoneCapacity();
            while (i<k){
                nextPosition = manager.nextPosition(nextPosition);
                if(nextPosition != null){
                if(manager.zoneAt(nextPosition).getBiome().equals(biome)){
                    i++;
                    if(observer.positionGrowthSet(nextPosition))
                        break;
                }
                }else{
                    nextPosition = new Vector2d(0,0);
                }
                position  = getRandomPosition(biome);
                if(observer.positionGrowthSet(position))
                    break;
            }
        }
    }

    //NotRandomVersion//
    @Override
    public void SimulateGrowth(int index) {
        //System.out.println("SAME SAME");
        for (WorldMapBiome biome : WorldMapBiome.values()){
            Vector2d position = getRandomPosition(biome, index);
            if(position == null)
                continue;
            if(observer.positionGrowthSet(position))
                continue;
            Vector2d nextPosition = position;
            int i = 0;
            int k = manager.getZoneCapacity()*manager.getDimensions()[0]*manager.getDimensions()[1];
            while (i<k){
                nextPosition = manager.nextPosition(nextPosition);
                if(nextPosition != null){
                    if(manager.zoneAt(nextPosition).getBiome().equals(biome)){
                        i++;
                        if(observer.positionGrowthSet(nextPosition))
                            break;
                    }
                }else{
                    nextPosition = new Vector2d(0,0);
                }
                /*position  = getRandomPosition(biome, index);
                if(observer.positionGrowthSet(position))
                    break;
                */
            }
        }
    }

    @Override
    public Vector2d getRandomPosition(WorldMapBiome inBiome, int index){
        //System.out.println("SAME SAME");
        int sectionSize = manager.getSectionSize(inBiome);
        //System.out.println(sectionSize);
        if(sectionSize == 0)
            return null;
        int zoneIndex = index%sectionSize;
        int positionIndex = index%manager.getZoneCapacity();
        Vector2d position = manager.getVector(zoneIndex, positionIndex, inBiome);
        //System.out.println(zoneIndex +" " + positionIndex + " " +position.toString() + " " + sectionSize);
        return position;
    }
}
