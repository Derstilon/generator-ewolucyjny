package agh.cs.genEvo;

import agh.cs.genEvo.managers.*;
import agh.cs.genEvo.mapElements.*;
import agh.cs.genEvo.mapElements.animalElements.AnimalInterface;
import agh.cs.genEvo.mapElements.animalElements.AnimalPack;
import agh.cs.genEvo.mapElements.animalElements.GenderlessAnimal;
import agh.cs.genEvo.observers.GrowthObserver;
import agh.cs.genEvo.observers.PopulationObserver;
import agh.cs.genEvo.utils.MapVisualizer;
import agh.cs.genEvo.utils.Vector2d;

public class WorldMap implements WorldInterface, GrowthObserver, PopulationObserver {
    private final Vector2d origin;
    private final Vector2d bound;
    private final PlantInterface defaultPlant;
    private final AnimalInterface defaultAnimal;
    private final WorldMapBiome defaultBiome;
    private final Integer plantLifeSpread;
    private Integer worldMapAge;
    public PopulationManager populationManager;
    public GrowthInterface growthManager;
    public PaintManager paintManager;
    private ZonesManager zonesManager;
    public StatsManager statsManager;

    //Constructors//
    public WorldMap(int width, int height, int size, WorldMapBiome biome, Integer spread, PlantInterface plant, AnimalInterface animal){
        if(width%size != 0 || height%size != 0)
            throw new IllegalArgumentException(size + " jest błędnym rozmiarem strefy mapy");
        origin = new Vector2d(0,0);
        bound = new Vector2d(width, height).subtract(new Vector2d(1,1));
        defaultBiome = biome;
        defaultPlant = plant;
        defaultAnimal = animal;
        plantLifeSpread = spread;
        zonesManager = new ZonesManager(size, biome, origin, bound);
        statsManager = new StatsManager(this, defaultAnimal.getStartEnergy());
        growthManager = new ConstGrowthManager(zonesManager);
        setGrowthObserver(this);
        setGrowthObserver(statsManager);
        populationManager = new PopulationManager(zonesManager);
        setPopulationObserver(this);
        setPopulationObserver(statsManager);
        paintManager = new PaintManager(zonesManager);
        worldMapAge = 0;
    }
    //************//

    //SIMULATION//
    @Override
    public void simulateWorld() {
        this.populationManager.simulateExtinction();
        for(int j = 0; j < plantLifeSpread; j++)
            this.growthManager.simulateGrowth();
        this.populationManager.simulateFeast();
        this.populationManager.simulateReproduction();
        this.populationManager.simulateMovement();
        this.increaseAge();
    }
    //______________//

    @Override
    public void increaseAge() {
        worldMapAge++;
    }

    public void addBiome(int width, int height, WorldMapBiome biome){
        if(!zonesManager.setNewBiome(width,height,biome))
            throw new IllegalArgumentException("Strefa '" + biome + "' nie może zostać wyśrodkowana");
    }

    public Vector2d[] getBounds(){
        return new Vector2d[] {origin, bound};
    }

    public WorldMapBiome biomeAt(Vector2d position) {
        WorldMapZone zone = zonesManager.zoneAt(position);
            return zone.getBiome();
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if(plantAt(position) == null)
            return false;
        return true;
    }

    @Override
    public PlantInterface plantAt(Vector2d position) {
        PlantInterface tmp = growthManager.plantTable.get(position);
        return tmp;
    }

    @Override
    public AnimalPack animalsAt(Vector2d position) {
        AnimalPack pack = populationManager.animalTable.get(position);
        if(pack != null && pack.size() == 0)
            return null;
        return pack;
    }

    @Override
    public AnimalInterface animalAt(Vector2d position) {
        AnimalPack pack = animalsAt(position);
        AnimalInterface tmp = null;
        if(pack != null) {
            tmp = pack.peek();
        }
        return tmp;
    }

    @Override
    public String toString() {
        Vector2d[] bounds = getBounds();
        return new MapVisualizer(this).draw(bounds[0], bounds[1]);
    }

    @Override
    public boolean putPlant(Vector2d position) {
        Object object = animalAt(position);
        if(object == null) {
            object = plantAt(position);
            if (object == null) {
                growthManager.plantTable.put(position, new PlantLife(defaultPlant));
                return true;
            }
            if (object instanceof PlantLife) {
                if (((PlantLife) object).CanGrowHere()) {
                    growthManager.plantTable.put(position, new PlantLife(defaultPlant));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean putAnimal(AnimalInterface animal) {
        Vector2d position = animal.getPosition();
        AnimalPack pack = animalsAt(position);
        if(pack==null) {
            pack = new AnimalPack();
            pack.add(animal);
            populationManager.animalTable.put(position, pack);
        }else {
            pack.add(animal);
        }
        return true;
    }

    @Override
    public Vector2d bendPositions(Vector2d newposition) {
        int x = !newposition.follows(origin) && newposition.x != origin.x ? bound.x : !newposition.precedes(bound) && newposition.x != bound.x ? origin.x : newposition.x ;
        int y = !newposition.follows(origin) && newposition.y != origin.y  ? bound.y : !newposition.precedes(bound) && newposition.y != bound.y ? origin.y : newposition.y;
        return new Vector2d(x,y);
    }

    //GrowthObserver//


    @Override
    public void setGrowthObserver(GrowthObserver observer) {
        this.growthManager.observers.add(observer);
    }

    @Override
    public boolean positionGrowthSet(Vector2d position) {
       if(putPlant(position))
            return true;
        return false;
    }
    //______________//


    //PopulationObserver//

    @Override
    public void setPopulationObserver(PopulationObserver observer) {
        this.populationManager.observers.add(observer);
    }

    @Override
    public void animalsFeedOnPosition(Vector2d position) {
        growthManager.plantEatenOnPosition(position);
    }

    @Override
    public boolean animalDied(AnimalInterface animal) {
        return true;
    }

    @Override
    public Integer animalsGathered(Vector2d position) {
        PlantInterface food = plantAt(position);
        if(food == null)
            return 0;
        return food.getEnergyValue();
    }

    @Override
    public boolean childGenerated(AnimalInterface child) {
        this.populationManager.population.add(child);
        child.setPopulationObserver(this);
        if(putAnimal(child)){
            return true;
        }
        return false;
    }

    @Override
    public boolean positionForChildGenerated(Vector2d position) {
        AnimalInterface current = animalAt(position);
        if(current != null)
            return false;
        AnimalInterface child = new GenderlessAnimal(defaultAnimal, position);
        return childGenerated(child);
    }

    @Override
    public boolean animalMoved(Vector2d oldposition, AnimalInterface animal) {
        AnimalPack pack = animalsAt(oldposition);
        Vector2d position = bendPositions(animal.getPosition());
        if(pack != null) {
            if (pack.contains(animal)) {
                pack.remove(animal);
                animal.setPosition(position);
                if (putAnimal(animal)){
                    return true;
                }
            }
        }
        return false;
    }
    //______________//
}
