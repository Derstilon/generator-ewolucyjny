package agh.cs.genEvo;

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
}
