package agh.cs.genEvo.utils;
import java.util.Random;

public enum MapDirection {
    NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST;
    private final String[] arrows = {"🡡", "🡥", "🡢", "🡦", "🡣", "🡧", "🡠", "🡤"};

    public MapDirection rotate(MapDirection rotateValue){ return MapDirection.values()[(this.ordinal() + rotateValue.ordinal())%8];}

    public MapDirection random(){
        return MapDirection.values()[new Random().nextInt(MapDirection.values().length)];
    }

    public Vector2d toUnitVector(){
        switch(this) {
            case NORTH : return new Vector2d(0,1);
            case EAST : return new Vector2d(1,0);
            case SOUTH : return new Vector2d(0,-1);
            case WEST : return new Vector2d(-1,0);
            case NORTHEAST : return new Vector2d(1,1);
            case NORTHWEST : return new Vector2d(-1,1);
            case SOUTHEAST : return new Vector2d(1,-1);
            case SOUTHWEST : return new Vector2d(-1,-1);
            default : return new Vector2d(0,0);
        }
    }

    public String toString(){
        return arrows[this.ordinal()];
    }
}
