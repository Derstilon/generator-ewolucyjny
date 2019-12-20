package agh.cs.genEvo;

import agh.cs.genEvo.mapElements.animalElements.AnimalInterface;
import agh.cs.genEvo.mapElements.animalElements.AnimalPack;
import agh.cs.genEvo.mapElements.PlantInterface;
import agh.cs.genEvo.mapElements.WorldMapBiome;
import agh.cs.genEvo.utils.Vector2d;

public interface WorldInterface {
    PlantInterface plantAt(Vector2d position);
    AnimalInterface animalAt(Vector2d position);
    AnimalPack animalsAt(Vector2d position);
    WorldMapBiome biomeAt(Vector2d position);
    boolean isOccupied(Vector2d position);
    boolean putPlant(Vector2d position);
    boolean putAnimal(AnimalInterface animal);
    void increaseAge();
    Vector2d bendPositions(Vector2d newposition);
    void simulateWorld(Integer k);
    Vector2d[] getBounds();
    String toString();
}
