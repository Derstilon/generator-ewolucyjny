package agh.cs.genEvo.managers;

import agh.cs.genEvo.mapElements.PlantLife;
import agh.cs.genEvo.utils.Vector2d;
import agh.cs.genEvo.mapElements.WorldMapBiome;

import java.util.Hashtable;

public interface GrowthInterface {
    Hashtable<Vector2d, PlantLife> plantTable = new Hashtable<>();
    void plantEatenOnPosition(Vector2d position);
    void simulateGrowth();
    Vector2d getRandomPosition(WorldMapBiome inBiome);
    void simulateGrowth(int index);
    Vector2d getRandomPosition(WorldMapBiome inBiome, int index);
}
