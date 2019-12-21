package agh.cs.genEvo.managers;

import agh.cs.genEvo.WorldMap;
import agh.cs.genEvo.mapElements.animalElements.AnimalInterface;
import agh.cs.genEvo.observers.GrowthObserver;
import agh.cs.genEvo.observers.PopulationObserver;
import agh.cs.genEvo.observers.StatsObserver;
import agh.cs.genEvo.utils.Vector2d;

public class StatsManager implements GrowthObserver, PopulationObserver {
    private StatsObserver observer;
    private Integer observerIndex;
    private ZonesManager manager;
    private WorldMap map;
    private Integer startEnergy;
    private Integer animalNumber=0;
    private Integer plantNumber=0;
    private Integer countDead=0;
    private Float sumEnergy=0f;
    private Float sumLifespan=0f;
    private Float countChildren=0f;

    public StatsManager(WorldMap map, Integer animalStartEnergy){
        this.map = map;
        this.startEnergy = animalStartEnergy;
    }

    public void updateStats(){
        countChildren = 0f;
        for(AnimalInterface animal : map.populationManager.population){
            countChildren+=animal.getChildrenAmount();
        }
        String[] stats = new String[]{
            animalNumber.toString(),
            plantNumber.toString(),
            String.format("%.2f", (sumEnergy/animalNumber)),
            String.format("%.2f", (sumLifespan/animalNumber)),
            String.format("%.2f", (countChildren/animalNumber)),
        };

        observer.statsForAnimalsUpdated(stats, observerIndex);
    }

    public void setStatsObserver(StatsObserver observer, Integer index){
        this.observer = observer;
        this.observerIndex = index;
    }

    @Override
    public boolean positionGrowthSet(Vector2d position) {
        plantNumber++;
        return false;
    }

    @Override
    public boolean positionForChildGenerated(Vector2d position) {
        animalNumber++;
        sumEnergy+=startEnergy;
        return false;
    }

    @Override
    public boolean childGenerated(AnimalInterface child) {
        animalNumber++;
        return false;
    }

    @Override
    public Integer animalsGathered(Vector2d position) {
        return null;
    }

    @Override
    public void animalsFeedOnPosition(Vector2d position) {
        plantNumber--;
    }

    @Override
    public boolean animalMoved(Vector2d oldposition, AnimalInterface animal) {
        sumEnergy-=animal.getMoveEnergy();
        return false;
    }

    @Override
    public boolean animalDied(AnimalInterface animal) {
        animalNumber--;
        countDead++;
        sumLifespan+=(animal.getAgeOfDeath()-animal.getAgeOfBirth());
        return true;
    }
}
