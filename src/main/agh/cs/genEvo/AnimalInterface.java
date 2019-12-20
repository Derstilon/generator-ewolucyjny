package agh.cs.genEvo;

public interface AnimalInterface{
    boolean procreateWith(AnimalInterface mate);
    boolean isAlive();
    boolean canBeFirstPartner();
    boolean canBeSecondPartner();
    boolean isHealthy();
    boolean move();
    boolean move(MapDirection rotation);
    boolean age();

    void consumeEnergy(Integer energyCost);
    void setPosition(Vector2d newposition);
    void addEnergy(int energy);
    void setPopulationObserver(PopulationObserver worldMap);
    void ChildCreated(AnimalInterface child);

    int getEnergy();

    Integer getAgeOfBirth();
    Integer getAgeOfDeath();
    Integer getStartEnergy();
    Integer getMoveEnergy();

    Vector2d getPosition();

    Genotype getGenes();
}
