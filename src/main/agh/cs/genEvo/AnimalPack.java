package agh.cs.genEvo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

public class AnimalPack implements AnimalGroupInterface{
    PriorityQueue<AnimalInterface> animalGroup;
    Integer packBiggestEnergy;
    AnimalPack(){
        AnimalComparator comparator = new AnimalComparator();
        animalGroup = new PriorityQueue<>(comparator);
    }
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
        //System.out.println("ADDED"+this.size());
    }

    @Override
    public void addAll(Collection<AnimalInterface> elements) {
        if(elements.size()>0)
            animalGroup.addAll(elements);
        //System.out.println("ADDED"+this.size());
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
                    //peeked.add(animalGroup.poll());
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
        //System.out.println("POOLED"+this.size());
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
        //System.out.println("POOLED"+this.size());
        return alfa;
    }

    @Override
    public boolean contains(AnimalInterface element) {
        return animalGroup.contains(element);
    }
}
