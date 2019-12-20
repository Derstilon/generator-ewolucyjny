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
    static final int startAnimals = 20;
    static final int plantLifeSpread = 8;
    static PlantLife plant;
    static AnimalInterface animal;
    static WorldMap ocean;
    public static void main(String[] args) {
        int k = width*height - zoneSize*jungleHeight*jungleWidth;
        k=10000;
        try {
            GenerateSimulation();
        } catch(IllegalArgumentException ex) {
            System.out.println(ex);
        }
        ocean.simulateWorld(k);
    }
    private static void GenerateSimulation(){
        plant = new PlantLife(plantEnergy);
        animal = new GenderlessAnimal(startEnergy,startEnergy, moveEnergy, new Vector2d(0,0), 0);
        ocean = new WorldMap(width, height, zoneSize, WorldMapBiome.WARM_OCEAN, plantLifeSpread, plant, animal);
        ocean.addBiome(jungleWidth, jungleHeight, WorldMapBiome.CORAL_REEF);
        ocean.populationManager.GeneratePopulation(startAnimals);
        //System.out.println(ocean.toString());
    }
}
/*  🌱█  */