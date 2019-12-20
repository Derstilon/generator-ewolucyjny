package agh.cs.genEvo.observers;

import agh.cs.genEvo.utils.Vector2d;
import agh.cs.genEvo.mapElements.animalElements.AnimalInterface;

public interface PopulationObserver {
    boolean childGenerated(AnimalInterface child);
    boolean positionForChildGenerated(Vector2d position);
    Integer animalsGathered(Vector2d position);
    boolean animalMoved(Vector2d oldposition, AnimalInterface animal);
    void animalsFeedOnPosition(Vector2d position);

}
