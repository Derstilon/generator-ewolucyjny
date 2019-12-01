package agh.cs.genEvo;

public enum MapDirection {
    NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST;
    public String toString(){
        switch(this) {
            case NORTH : return "Północ";
            case EAST: return "Wschód";
            case SOUTH : return "Południe";
            case WEST: return "Zachód";
            case NORTHEAST: return "Północny wschód";
            case NORTHWEST: return "Północny zachód";
            case SOUTHEAST: return "Południowy wschód";
            case SOUTHWEST: return "Południowy zachód";
        }
        return null;
    }
    public MapDirection next(){
        return MapDirection.values()[(this.ordinal() +1)%4];
    }
    public MapDirection previous(){
        return MapDirection.values()[(this.ordinal() +3)%4];
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
}
