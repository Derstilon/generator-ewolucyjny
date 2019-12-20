package agh.cs.genEvo;

import agh.cs.genEvo.mapElements.*;
import agh.cs.genEvo.mapElements.animalElements.AnimalInterface;
import agh.cs.genEvo.mapElements.animalElements.AnimalPack;
import agh.cs.genEvo.mapElements.animalElements.GenderlessAnimal;
import agh.cs.genEvo.utils.MapDirection;
import agh.cs.genEvo.utils.Vector2d;
import org.junit.Test;

public class AnimalsTests {
    PlantLife plant = new PlantLife(20);
    GenderlessAnimal animal = new GenderlessAnimal(200,100,0,new Vector2d(0,0),0);
    WorldMap map = new WorldMap(3,3,1, WorldMapBiome.WARM_OCEAN,3,plant,animal);
    GenderlessAnimal animal1 = new GenderlessAnimal(200,100,0,new Vector2d(0,0),0);
    GenderlessAnimal animal2 = new GenderlessAnimal(200,100,0,new Vector2d(0,2),0);
    @Test
    public void packTest(){
        AnimalPack pack = new AnimalPack();
        pack.add(animal1);
        pack.add(animal2);
        System.out.println(pack.size());
        AnimalInterface animal3 = pack.poolAlfa();
        System.out.println(pack.size());
        AnimalInterface animal4 = pack.poolPartner();
        System.out.println(pack.size());
        pack.add(animal4);
        pack.add(animal3);
        System.out.println(pack.size());
    }
    @Test
    public void reproductionTest(){
        map.childGenerated(animal1);
        map.childGenerated(animal2);
        System.out.println(map.toString());
        animal1.move(MapDirection.NORTH);
        System.out.println(map.toString());
        animal2.move(MapDirection.SOUTH);
        System.out.println(map.toString());
        map.populationManager.simulateReproduction();
        System.out.println(map.toString());
        System.out.println(animal1.getPosition() +":"+ animal2.getPosition() +":"+ map.populationManager.population.get(2).getPosition());
        map.populationManager.simulateMovement();
        System.out.println(map.toString());
    }
    @Test
    public void movementTest(){
        animal1.setPosition(new Vector2d(1,1));
        animal2.setPosition(new Vector2d(1,1));
        map.childGenerated(animal1);
        map.childGenerated(animal2);
        System.out.println(map.toString());
        map.populationManager.simulateReproduction();
        map.populationManager.simulateMovement();
        System.out.println(map.toString());
    }
}
