package agh.cs.genEvo.managers;

import agh.cs.genEvo.mapElements.WorldMapZone;
import agh.cs.genEvo.utils.Vector2d;

public class PaintManager {
    private ZonesManager manager;
    public PaintManager(ZonesManager manager){
        this.manager = manager;
    }
    public boolean canPaintZone(int i){
        int[] dimensions = manager.getDimensions();
        return i < dimensions[0]*dimensions[1];
    }
    public WorldMapZone getZoneToPaint(WorldMapZone oldZone){
        if(oldZone == null)
            return manager.zoneAt(new Vector2d(0,0));
        return manager.nextZone(oldZone);
    }
}
