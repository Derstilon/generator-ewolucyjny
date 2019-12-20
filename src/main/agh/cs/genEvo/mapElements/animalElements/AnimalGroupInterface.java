package agh.cs.genEvo.mapElements.animalElements;

import java.util.ArrayList;
import java.util.Collection;

public interface AnimalGroupInterface {
    ArrayList<AnimalInterface> peekDominant();
    Integer size();
    void add(AnimalInterface element);
    void addAll(Collection<AnimalInterface> elements);
    AnimalInterface poolAlfa();
    AnimalInterface poolPartner();
    AnimalInterface peek();
    boolean contains(AnimalInterface element);
    void  remove(AnimalInterface element);
}
