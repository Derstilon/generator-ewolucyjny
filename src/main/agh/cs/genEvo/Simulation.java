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
        WorldMap ocean = new WorldMap(width, height, zoneSize, "Warm ocean");
        ocean.addBiome(jungleWidth, jungleHeight, "█ Coral reef");
        System.out.println(ocean.toString());

    }
}
/*  🌱█  */