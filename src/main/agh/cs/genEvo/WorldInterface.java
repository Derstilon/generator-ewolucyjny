package agh.cs.genEvo;

public interface WorldInterface {
    Object objectAt(Vector2d position);
    String biomeAt(Vector2d position);
    //Object foodSourceAt(Vector2d position);
    //Object animalAt(Vector2d position);
    Vector2d[] getBounds();
    String toString();
}
