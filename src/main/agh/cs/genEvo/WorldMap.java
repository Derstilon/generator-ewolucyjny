package agh.cs.genEvo;

public class WorldMap implements WorldInterface {
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private WorldMapZone[][] zones;
    private final int zoneSize;
    private final String defaultBiome;

    //Constructors//
    public WorldMap(int width, int height, int size, String biome){
        if(width%size != 0 || height%size != 0)
            throw new IllegalArgumentException(size + " jest błędnym rozmiarem strefy mapy");
        lowerLeft = new Vector2d(0,0);
        upperRight = new Vector2d(width, height).subtract(new Vector2d(1,1));
        defaultBiome = biome;
        zoneSize = size;
        GenerateZones();
    }
    private void GenerateZones(){
        zones = new WorldMapZone[1+ upperRight.x/zoneSize][1+ upperRight.y/zoneSize];
        Vector2d vSize = new Vector2d(zoneSize-1, zoneSize-1);
        Vector2d xSize = new Vector2d(zoneSize, 0);
        Vector2d ySize = new Vector2d(0, zoneSize);
        Vector2d v1x1 = new Vector2d();
        Vector2d tmp = new Vector2d(-zoneSize, 0);
        for(int i = 0; i<zones.length; i++){
            tmp = tmp.add(xSize);
            for(int j = 0; j<zones[i].length; j++){
                Vector2d a = tmp;
                Vector2d b = a.add(vSize);
                zones[i][j] = new WorldMapZone(a,b, defaultBiome);
                tmp = tmp.add(ySize);
            }
            tmp = new Vector2d(tmp.x,0);
        }
    }
    //************//

    public void addBiome(int width, int height, String biome){
        System.out.println(zones.length + "%" + width + " " + zones[0].length + "%" + height);
        if((zones.length-width)%2 != 0 || (zones[0].length-height)%2 != 0 )
            throw new IllegalArgumentException("Strefa '" + biome + "' nie może zostać wyśrodkowana");
        for(int i = 0; i < width; i++){
            WorldMapZone[] tmp = zones[(zones.length/2) - (width/2) + i];
            for(int j = 0; j < height; j++){
                tmp[(tmp.length/2) - (height/2) + j].setBiome(biome);
            }
        }
    }

    public Vector2d[] getBounds(){
        return new Vector2d[] {lowerLeft, upperRight};
    }

    public WorldMapZone zoneAt(Vector2d position) {
        int x = position.x/zoneSize;
        int y = position.y/zoneSize;
        return zones[x][y];
    }

    public String biomeAt(Vector2d position) {
        WorldMapZone zone = zoneAt(position);
        if(!zone.getBiome().equals(defaultBiome))
            //System.out.println(zone.getBiome());
            return zone.getBiome();
        else
        return null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return null;
    }

    @Override
    public String toString() {
        Vector2d[] bounds = getBounds();
        return new MapVisualizer(this).draw(bounds[0], bounds[1]);
    }
}
