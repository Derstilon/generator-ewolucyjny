package agh.cs.genEvo;
import java.util.ArrayList;
import java.util.Hashtable;

public class ZonesManager {
    private WorldMapZone[][] zones;
    private int zoneSize;
    private int[] dimensions;
    private Hashtable<WorldMapBiome, Integer> biomeSections = new Hashtable<>();
    //private ArrayList<Integer> sections = new ArrayList<Integer>();
    //private ArrayList<WorldMapBiome> biomes = new ArrayList<WorldMapBiome>();

    ZonesManager(int size, WorldMapBiome defaultBiome, Vector2d origin, Vector2d bound){
        zoneSize = size;
        zones = new WorldMapZone[1+ (bound.x-origin.x)/zoneSize][1+ (bound.y-origin.y)/zoneSize];
        dimensions = new int[]{zones.length, zones[0].length};
        Vector2d vSize = new Vector2d(zoneSize-1, zoneSize-1);
        Vector2d xSize = new Vector2d(zoneSize, 0);
        Vector2d ySize = new Vector2d(0, zoneSize);
        Vector2d v1x1 = new Vector2d();
        Vector2d tmp = new Vector2d(-zoneSize, 0);
        for(int i = 0; i<zones.length; i++) {
            tmp = tmp.add(xSize);
            for (int j = 0; j < zones[i].length; j++) {
                Vector2d a = tmp;
                Vector2d b = a.add(vSize);
                zones[i][j] = new WorldMapZone(a, b, zoneSize, defaultBiome);
                tmp = tmp.add(ySize);
            }
            tmp = new Vector2d(tmp.x, 0);
        }
        addBiomeSection(defaultBiome, dimensions[0]*dimensions[1]);
    }

    private void ChangeBiome(int width, int height, WorldMapBiome newBiome){
        WorldMapBiome oldBiome = zones[width][height].getBiome();
        int oldBiomeSize = biomeSections.get(oldBiome);
        oldBiomeSize--;
        biomeSections.put(oldBiome, oldBiomeSize);
        zones[width][height].setBiome(newBiome);
        int newBiomeSize;
        if(biomeSections.containsKey(newBiome))
            newBiomeSize = biomeSections.get(newBiome);
        else newBiomeSize = 0;
        newBiomeSize++;
        biomeSections.put(newBiome, newBiomeSize);
    }

    private void addBiomeSection(WorldMapBiome biome, int size){
        biomeSections.put(biome, size);
    }

    public boolean setNewBiome(int width, int height, WorldMapBiome newBiome){
        if((dimensions[0]-width)%2 != 0 || (dimensions[1]-height)%2 != 0 )
            return false;
        else
            for(int j = 0; j < height; j++){
                for(int i = 0; i < width; i++){
                    ChangeBiome((dimensions[0]-width)/2+i,(dimensions[1]-height)/2+j,newBiome);
                }
            }
        return true;
    }

    public int getZoneCapacity() {
        return zoneSize*zoneSize;
    }

    public int[] getDimensions(){
        return dimensions;
    }

    public Integer getSectionSize(WorldMapBiome thisBiome){
        //System.out.println("SAME SAME");
        Integer sectionSize = biomeSections.get(thisBiome);
        //System.out.println(sectionSize);
        if(sectionSize != null)
            return sectionSize;
        return 0;
    }

    public Vector2d getVector(int zoneIndex, int positionIndex){
        return zones[zoneIndex%dimensions[0]][zoneIndex/dimensions[0]].getVector(positionIndex);
    }

    public Vector2d getVector(int zoneIndex, int positionIndex, WorldMapBiome thisBiome){
        //System.out.println("SAME SAME");
        int tmpIndex = zoneIndex;
        //System.out.println("zoneIndex: " + zoneIndex);
        int i = 0;
        do{
            WorldMapZone tmpZone = zones[i%dimensions[0]][i/dimensions[0]];
            if(!tmpZone.getBiome().equals(thisBiome)) {
                tmpIndex++;
            }
            i++;
            //System.out.println("[i,tmpIndex]: [" + i + "," + tmpIndex + "]");
        }while(i <= tmpIndex && i < dimensions[0]*dimensions[1]);
        //System.out.println("zones[" + tmpIndex%dimensions[0] + "]["+ tmpIndex/dimensions[0] +"]");
        return zones[tmpIndex%dimensions[0]][tmpIndex/dimensions[0]].getVector(positionIndex);
    }

    public WorldMapZone zoneAt(Vector2d position) {
        int x = position.x/zoneSize;
        int y = position.y/zoneSize;
        return zones[x][y];
    }
    public WorldMapZone nextZone(WorldMapZone oldZone){
        //System.out.println("SAME SAME");
        Vector2d origin = oldZone.getVector(0);
        if(dimensions[0]-1>origin.x/zoneSize)
            return zoneAt(origin.add(new Vector2d(zoneSize,0)));
        else if(dimensions[1]-1>origin.y/zoneSize)
            return zoneAt(new Vector2d(0,origin.y+zoneSize));
        else
            return null;

    }

    public Vector2d nextPosition(Vector2d position){
        WorldMapZone zone = zoneAt(position);
        Vector2d newPosition = zone.nextPosition(position);
        if(newPosition == null)
            zone = nextZone(zone);
        else
            return newPosition;
        if(zone == null)
            return null;
        else
            return zone.getVector(0);
    }
}
