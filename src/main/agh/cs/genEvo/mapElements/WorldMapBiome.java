package agh.cs.genEvo.mapElements;

import java.awt.*;

public enum WorldMapBiome {
    CORAL_REEF, WARM_OCEAN, DEEP_OCEAN;
    public String toString(){
        switch(this) {
            case CORAL_REEF : return "█";
            case WARM_OCEAN: return " ";
            case DEEP_OCEAN : return "*";
        }
        return null;
    }
    public Color getColor(){
        switch(this) {
            case CORAL_REEF : return new Color(140, 155, 89);
            case WARM_OCEAN: return new Color(18, 79, 72);
            case DEEP_OCEAN : return new Color(16, 20, 53);
        }
        return null;
    }
}
