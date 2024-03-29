package agh.cs.genEvo.mapElements.animalElements;

import agh.cs.genEvo.comparators.AnimalComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

public class AnimalPack implements AnimalGroupInterface{
    PriorityQueue<AnimalInterface> animalGroup;

    //Constructor//
    public AnimalPack(){
        AnimalComparator comparator = new AnimalComparator();
        animalGroup = new PriorityQueue<>(comparator);
    }
    //***********//

    @Override
    public Integer size() {
        return animalGroup.size();
    }

    @Override
    public void remove(AnimalInterface element) {
        animalGroup.remove(element);
    }

    @Override
    public void add(AnimalInterface element) {
        animalGroup.add(element);
    }

    @Override
    public void addAll(Collection<AnimalInterface> elements) {
        if(elements.size()>0)
            animalGroup.addAll(elements);
    }

    @Override
    public AnimalInterface peek() {
       return animalGroup.peek();
    }

    @Override
    public ArrayList<AnimalInterface> peekDominant() {
        ArrayList<AnimalInterface> peeked = new ArrayList<>();
        if(animalGroup.size()>0) {
            do {
                peeked.add(animalGroup.poll());
            } while (animalGroup.size() > 0 && animalGroup.peek().getEnergy() == peeked.get(0).getEnergy());
            this.addAll(peeked);
        }
        return peeked;
    }

    @Override
    public AnimalInterface poolPartner() {
        ArrayList<AnimalInterface> peeked = new ArrayList<>();
        AnimalInterface partner = null;
        if(animalGroup.size()>0) {
            do {
                if (!animalGroup.peek().isHealthy()) {
                    break;
                }
                if (!animalGroup.peek().canBeSecondPartner()) {
                    peeked.add(animalGroup.poll());
                    continue;
                }
                partner = animalGroup.poll();
                break;
            } while (animalGroup.size() > 0);
            this.addAll(peeked);
        }
        return partner;
    }

    @Override
    public AnimalInterface poolAlfa() {
        ArrayList<AnimalInterface> peeked = new ArrayList<>();
        AnimalInterface alfa = null;
        if(animalGroup.size()>0) {
            do {
                if (!animalGroup.peek().isHealthy()) {
                    peeked.add(animalGroup.poll());
                    break;
                }
                if (!animalGroup.peek().canBeFirstPartner()) {
                    peeked.add(animalGroup.poll());
                    continue;
                }
                alfa = animalGroup.poll();
                break;
            } while (animalGroup.size() > 0);
            this.addAll(peeked);
        }
        return alfa;
    }

    @Override
    public boolean contains(AnimalInterface element) {
        return animalGroup.contains(element);
    }
}
