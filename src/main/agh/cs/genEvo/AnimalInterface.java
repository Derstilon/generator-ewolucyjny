package agh.cs.genEvo;

public interface AnimalInterface{
    boolean procreateWith(AnimalInterface mate);
    boolean isAlive();
    boolean canBeFirstPartner();
    boolean canBeSecondPartner();
    boolean isHealthy();
    int getEnergy();
    boolean age();
    Genotype getGenes();
    Integer getAgeOfBirth();
    Integer getAgeOfDeath();
    Integer getStartEnergy();
    Integer getMoveEnergy();
    Vector2d getPosition();
    void setPosition(Vector2d newposition);
    void addEnergy(int energy);
    void setPopulationObserver(PopulationObserver worldMap);
    void ChildCreated(AnimalInterface child);
    boolean move();
    boolean move(MapDirection rotation);
}
