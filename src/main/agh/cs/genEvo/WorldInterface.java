package agh.cs.genEvo;

public interface WorldInterface {
    Object objectAt(Vector2d position);
    WorldMapBiome biomeAt(Vector2d position);
    boolean isOccupied(Vector2d position);
    boolean putPlant(Vector2d position);
    //Object foodSourceAt(Vector2d position);
    //Object animalAt(Vector2d position);
    Vector2d[] getBounds();
    String toString();
}
