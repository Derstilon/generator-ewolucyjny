package agh.cs.genEvo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MapZoneTest {
    Vector2d origin = new Vector2d(0,0);
    Vector2d bound = new Vector2d(5,5);
    PlantLife plant = new PlantLife(0);
    WorldMap ocean = new WorldMap(6, 6, 2, WorldMapBiome.WARM_OCEAN, plant);
    ZonesManager manager = new ZonesManager(2,WorldMapBiome.WARM_OCEAN,origin,bound);
    @Test
    public void WorldMapZoneTest(){
       manager.setNewBiome(1,1,WorldMapBiome.CORAL_REEF);
       assertEquals(manager.zoneAt(new Vector2d(3,3)).getBiome(),WorldMapBiome.CORAL_REEF);
    }

    @Test
    public void NextPositionTest() {
        Vector2d[] tmp =  new Vector2d[]{ new Vector2d(0, 0), new Vector2d(1, 0), new Vector2d(0, 1), new Vector2d(1, 1)};
        for (int i = 0; i < tmp.length; i++){
                //System.out.println(tmp[0]);
                assertEquals(tmp[0], tmp[i]);
                tmp[0] = manager.nextPosition(tmp[0]);
        }
    }

    @Test
    public void GetVectorTest() {
        ocean = new WorldMap(80, 80, 2, WorldMapBiome.WARM_OCEAN, plant);
        System.out.println(manager.getVector(4, 3, WorldMapBiome.WARM_OCEAN).toString());
        ocean.addBiome(20, 20, WorldMapBiome.CORAL_REEF);
        System.out.println(manager.getVector(4, 3, WorldMapBiome.WARM_OCEAN).toString());
        System.out.println(ocean.toString());
    }

    @Test
    public void GetSectionSizeTest() {
        System.out.println(manager.getSectionSize(WorldMapBiome.WARM_OCEAN));
    }

    @Test
    public void SimulateGrowthTest(){
        ConstGrowthManager growthManager = new ConstGrowthManager(ocean, manager);
        for(int i = 0; i < 3; i++){
            growthManager.SimulateGrowth();
        }
        manager.setNewBiome(1,1,WorldMapBiome.CORAL_REEF);
        for(int i = 0; i < 3; i++){
            growthManager.SimulateGrowth();
        }
    }

    @Test
    public void FinalGrowthTest() {
        ocean = new WorldMap(3, 4, 1, WorldMapBiome.WARM_OCEAN, plant);
        ocean.addBiome(1,2,WorldMapBiome.CORAL_REEF);
        System.out.println(ocean.toString());
        for(int i = 0; i <56; i++){
            ocean.growthManager.SimulateGrowth();
            System.out.println(ocean.toString());
        }
    }

   /* @Test
    public void WorldMapTest(){
        ocean.addBiome(1, 1, WorldMapBiome.CORAL_REEF);
        System.out.println(ocean.toString());
        assertEquals(ocean.biomeAt(new Vector2d(2,2)),WorldMapBiome.CORAL_REEF);

        ocean.positionGrowthSet(0,WorldMapBiome.WARM_OCEAN);
        System.out.println(ocean.toString());
        ocean.positionGrowthSet(2,WorldMapBiome.WARM_OCEAN);
        System.out.println(ocean.toString());
        for(int i = 0; i < 32; i+=2){
            ocean.positionGrowthSet(i,WorldMapBiome.WARM_OCEAN);
        }
        System.out.println(ocean.toString());
    }*/
}
