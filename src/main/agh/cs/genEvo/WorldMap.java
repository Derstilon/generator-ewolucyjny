package agh.cs.genEvo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class WorldMap implements WorldInterface, GrowthObserver {
    private final Vector2d origin;
    private final Vector2d bound;
    private final PlantInterface defaultPlant;
    private final WorldMapBiome defaultBiome;
    public GrowthInterface growthManager;
    private Hashtable<Integer, PlantLife> plantTable = new Hashtable<>();
    private ZonesManager zonesManager;
    //private Vector<>;

    //Constructors//
    public WorldMap(int width, int height, int size, WorldMapBiome biome, PlantInterface plant){
        if(width%size != 0 || height%size != 0)
            throw new IllegalArgumentException(size + " jest błędnym rozmiarem strefy mapy");
        origin = new Vector2d(0,0);
        bound = new Vector2d(width, height).subtract(new Vector2d(1,1));
        defaultBiome = biome;
        defaultPlant = plant;
        zonesManager = new ZonesManager(size, biome, origin, bound);
        growthManager = new ConstGrowthManager(this, zonesManager);
    }
    //************//

    public void addBiome(int width, int height, WorldMapBiome biome){
        if(!zonesManager.setNewBiome(width,height,biome))
            throw new IllegalArgumentException("Strefa '" + biome + "' nie może zostać wyśrodkowana");
    }

    public Vector2d[] getBounds(){
        return new Vector2d[] {origin, bound};
    }

    public WorldMapBiome biomeAt(Vector2d position) {
        WorldMapZone zone = zonesManager.zoneAt(position);
        //if(!zone.getBiome().equals(defaultBiome))
            //System.out.println(zone.getBiome());
            return zone.getBiome();
        //else
        //return null;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if(objectAt(position) == null)
            return false;
        return true;
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object tmp = plantTable.get(position.hashCode());
        return tmp;
    }

    @Override
    public String toString() {
        Vector2d[] bounds = getBounds();
        return new MapVisualizer(this).draw(bounds[0], bounds[1]);
    }

    @Override
    public boolean putPlant(Vector2d position) {
        Object object = objectAt(position);
        if(object == null){
            plantTable.put(position.hashCode(), new PlantLife(defaultPlant));
            return true;
        }
        if(object instanceof PlantLife){
            if(((PlantLife) object).CanGrowHere()) {
                plantTable.put(position.hashCode(), new PlantLife(defaultPlant));
                return true;
            }
        }
        return false;
    }

    //GrowthObserver//
    @Override
    public boolean positionGrowthSet(Vector2d position) {
       if(putPlant(position))
            return true;
        return false;
    }
    //______________//
}
