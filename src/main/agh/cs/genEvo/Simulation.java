package agh.cs.genEvo;

import agh.cs.genEvo.mapElements.animalElements.AnimalInterface;
import agh.cs.genEvo.mapElements.animalElements.GenderlessAnimal;
import agh.cs.genEvo.mapElements.PlantLife;
import agh.cs.genEvo.mapElements.WorldMapBiome;
import agh.cs.genEvo.src.ConfigurationPanel;
import agh.cs.genEvo.src.MainWindow;
import agh.cs.genEvo.utils.Vector2d;

public class Simulation {
    static MainWindow main;
    static ConfigurationPanel conf;
    public static void main(String[] args) {
        main = new MainWindow("Generator Ewolucyjny");
        conf = new ConfigurationPanel("Generator Ewolucyjny", main);
    }
}
/*  🌱█  */