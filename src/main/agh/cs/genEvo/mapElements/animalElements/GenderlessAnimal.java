package agh.cs.genEvo.mapElements.animalElements;

import agh.cs.genEvo.utils.MapDirection;
import agh.cs.genEvo.observers.PopulationObserver;
import agh.cs.genEvo.utils.Vector2d;

import java.util.ArrayList;

public class GenderlessAnimal implements AnimalInterface{
    private int energyValue;
    private static Integer startEnergy;
    private static Integer moveEnergy;
    private Integer ageOfBirth;
    private Integer ageOfDeath;
    private PopulationObserver observer;
    private Genotype genes;
    private Integer age;
    private ArrayList<AnimalInterface> children = new ArrayList<>();
    private MapDirection rotation = MapDirection.NORTH;
    private Vector2d position;

    //Constructors//
    public GenderlessAnimal(int value, Integer start, Integer move, Vector2d position, Integer currentAge, Genotype newGenes){
        this.energyValue = value;
        this.startEnergy = start;
        this.moveEnergy = move;
        this.genes = new Genotype(newGenes.getSecondaryGenes());
        this.age = 0;
        this.ageOfBirth = currentAge;
        this.position = position;
        this.rotation = rotation.random();
    }
    public GenderlessAnimal(Integer value, Integer start, Integer move, Vector2d position, Integer currentAge){
        this(value, start, move, position, currentAge, new Genotype());
    }
    public GenderlessAnimal(AnimalInterface template, Vector2d position){
        this(template.getEnergy(),template.getStartEnergy(),template.getMoveEnergy(),position,template.getAgeOfBirth(),new Genotype());
    }
    //____________//

    @Override
    public void consumeEnergy(Integer energyCost) {
        energyValue-=energyCost;
        if(isAlive())
            return;
        this.age++;
        this.ageOfDeath = age + ageOfBirth;
    }

    @Override
    public void setPosition(Vector2d newposition) {
        this.position = newposition;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public void setPopulationObserver(PopulationObserver worldMap) {
        this.observer = worldMap;
    }

    @Override
    public boolean isHealthy() {
        return !(energyValue<startEnergy);
    }

    @Override
    public boolean canBeFirstPartner() {
        return isHealthy();
    }

    @Override
    public boolean canBeSecondPartner() {
        return isHealthy();
    }

    @Override
    public Integer getAgeOfBirth() {
        return ageOfBirth;
    }

    @Override
    public Integer getAgeOfDeath() {
        return ageOfDeath;
    }

    @Override
    public Genotype getGenes() {
        return genes;
    }

    @Override
    public Integer getStartEnergy() {
        return startEnergy;
    }

    @Override
    public Integer getMoveEnergy() {
        return moveEnergy;
    }

    @Override
    public void addEnergy(int energy) {
        this.energyValue += energy;
    }

    @Override
    public boolean age() {
        if(isAlive()) {
            age++;
            return true;
        }
        return false;
    }

    @Override
    public boolean isAlive() {
        return energyValue>0;
    }

    @Override
    public boolean procreateWith(AnimalInterface mate) {
        if(mate.isHealthy()){
            Genotype childGenom = genes.RecombinateWith(mate.getGenes());
            Integer myEnergy = energyValue/4;
            Integer mateEnergy = mate.getEnergy()/4;
            AnimalInterface child = new GenderlessAnimal(myEnergy+mateEnergy,this.startEnergy, this.moveEnergy,this.position,this.ageOfBirth+this.age,childGenom);
            if(observer != null && observer.childGenerated(child)){
                this.consumeEnergy(myEnergy);
                mate.ChildCreated(child);
            }
        }
        return false;
    }

    @Override
    public int getEnergy() {
        return energyValue;
    }

    @Override
    public void ChildCreated(AnimalInterface child) {
        children.add(child);
        Integer myEnergy = energyValue/4;
        this.consumeEnergy(myEnergy);
    }

    @Override
    public String toString() {
        if(isAlive())
            return rotation.toString();
        return "X";
    }

    @Override
    public boolean move() {
        MapDirection direction = rotation.rotate(genes.geneticRotation());
        return move(direction);
    }

    @Override
    public boolean move(MapDirection direction) {
        if(isAlive()) {
            consumeEnergy(moveEnergy);
            rotation = direction;//rotation.rotate(genes.geneticRotation());
            Vector2d oldPosition = new Vector2d(this.position);
            this.position = new Vector2d(this.position.add(rotation.toUnitVector()));
            return (observer.animalMoved(oldPosition, this));
        }
        return false;
    }
}
