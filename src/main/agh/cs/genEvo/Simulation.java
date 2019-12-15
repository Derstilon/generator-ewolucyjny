package agh.cs.genEvo;

public class Simulation {
    static final int width =  90;
    static final int height = 30;
    static final int zoneSize = 5;
    static final int jungleWidth = 8;
    static final int jungleHeight = 4;
    static final int startEnergy = 256;
    static final int moveEnergy = 8;
    static final int plantEnergy = 64;
    public static void main(String[] args) {
        try {
            GenerateSimulation();
        } catch(IllegalArgumentException ex) {
            System.out.println(ex);
        }
    }
    private static void GenerateSimulation(){
        PlantLife plant = new PlantLife(plantEnergy);
        WorldMap ocean = new WorldMap(width, height, zoneSize, WorldMapBiome.WARM_OCEAN, plant);
        ocean.addBiome(jungleWidth, jungleHeight, WorldMapBiome.CORAL_REEF);
        System.out.println(ocean.toString());
        int k = width*height - zoneSize*jungleHeight*jungleWidth;
        for(int i = 0; i < k/2; i++){
            ocean.growthManager.SimulateGrowth();
            System.out.println(ocean.toString());
        }
    }
}
/*  🌱█  */