package agh.cs.genEvo;

public class WorldMapZone {
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private int density;
    private String biome;

    //Constructors//
    public WorldMapZone(Vector2d a, Vector2d b, String zoneBiome){
        density = 0;
        biome = zoneBiome;
        this.lowerLeft = new Vector2d(a);
        this.upperRight = new Vector2d(b);
    }
    //************//


    void setBiome(String zoneBiome){
        biome = zoneBiome;
    }
    String getBiome(){
        return biome;
    }
}
