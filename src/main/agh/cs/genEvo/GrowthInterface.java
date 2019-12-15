package agh.cs.genEvo;

public interface GrowthInterface {
    void SimulateGrowth();
    Vector2d getRandomPosition(WorldMapBiome inBiome);
    void SimulateGrowth(int index);
    Vector2d getRandomPosition(WorldMapBiome inBiome, int index);
}
