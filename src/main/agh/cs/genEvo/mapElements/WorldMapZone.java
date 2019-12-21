package agh.cs.genEvo.mapElements;

import agh.cs.genEvo.utils.Vector2d;

import java.awt.geom.Rectangle2D;
import java.util.Vector;

public class WorldMapZone {
    private final Vector2d origin;
    private final Vector2d bound;
    private final int size;
    private int density;
    private WorldMapBiome biome;

    //Constructors//
    public WorldMapZone(Vector2d a, Vector2d b, int size, WorldMapBiome zoneBiome){
        this.density = 0;
        this.biome = zoneBiome;
        this.origin = new Vector2d(a);
        this.bound = new Vector2d(b);
        this.size = size;
    }
    //************//

    public String toString(){
        return "[" + origin.toString() + "," + bound.toString() + ",'" + biome.toString() + "']";
    }

    public void setBiome(WorldMapBiome zoneBiome){
        biome = zoneBiome;
    }
    public WorldMapBiome getBiome(){
        return biome;
    }
    public int getSize(){
        return size;
    };
    boolean isFilled(){
        return density == size;
    }
    public Vector2d getVector(int index){
        return origin.add(new Vector2d(index%size, index/size));
    }
    public Vector2d nextPosition(Vector2d position){
        Vector2d tmp = position.subtract(origin);
        Vector2d nextPosition = getVector(tmp.y*size+tmp.x +1);
        //System.out.println("Suggested vector: " + nextPosition.toString());
        if(!nextPosition.precedes(bound))
            return null;
        return nextPosition;
    }
    public Rectangle2D getRectangle(){
        Rectangle2D zoneRectangle = null;
        return zoneRectangle;
    }
    public Vector2d getPosition(){return getVector(0);}
}
