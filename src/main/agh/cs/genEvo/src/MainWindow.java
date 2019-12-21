package agh.cs.genEvo.src;

import agh.cs.genEvo.WorldMap;
import agh.cs.genEvo.mapElements.PlantLife;
import agh.cs.genEvo.mapElements.WorldMapBiome;
import agh.cs.genEvo.mapElements.animalElements.AnimalInterface;
import agh.cs.genEvo.mapElements.animalElements.GenderlessAnimal;
import agh.cs.genEvo.utils.SimulationParameters;
import agh.cs.genEvo.utils.Vector2d;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private static PlantLife plant;
    private static AnimalInterface animal;
    private WorldMap ocean;
    private JPanel aBorderPanel = new JPanel(new BorderLayout());
    private SimulationParameters parameters;
    private MapCanvas canvas;
    private StatisticConsoleFrame console;
    private Integer zoneSize = 4;
    public MainWindow(String name){
        super(name);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void setVisible(boolean flag, SimulationParameters parameters){
        if(flag){
            this.parameters = parameters;
            parameters.setValue(2,Math.min(parameters.getParameterValue("width")/zoneSize-1,parameters.getParameterValue("fertileWidth")));
            parameters.setValue(3,Math.min(parameters.getParameterValue("height")/zoneSize-1,parameters.getParameterValue("fertileHeight")));
            parameters.setValue(7,Math.min(parameters.getParameterValue("startAnimals"),parameters.getParameterValue("width")*parameters.getParameterValue("height")));
            generateSimulation();
            canvas = new MapCanvas(ocean,parameters.getParameterValue("resolution"), parameters.getParameterValue("simulTime"));
            console = new StatisticConsoleFrame();
            configureStatistics();
            console.setVisible(flag);
            canvas.map.statsManager.setStatsObserver(console,0);
            this.add (canvas, BorderLayout.CENTER );
            this.pack();
            super.setVisible(flag);
            canvas.repaint();
            canvas.startSimulation();
        }
    };
    private void configureStatistics(){
        console.simulationToggle.addActionListener(actionEvent -> {
            switch (console.simulationToggle.getText()) {
                case "Stop":
                    canvas.toggleSimulation();
                    console.simulationToggle.setText("Start");
                    break;
                case "Start":
                    canvas.toggleSimulation();
                    console.simulationToggle.setText("Stop");
                    break;
            }
        });
    }
    private void generateSimulation(){
        plant = new PlantLife(parameters.getParameterValue("plantEnergy"));
        animal = new GenderlessAnimal(parameters.getParameterValue("startEnergy"),parameters.getParameterValue("startEnergy"), parameters.getParameterValue("moveEnergy"), new Vector2d(0,0), 0);
        ocean = new WorldMap(parameters.getParameterValue("width"), parameters.getParameterValue("height"), zoneSize, WorldMapBiome.WARM_OCEAN, parameters.getParameterValue("plantSpread"), plant, animal);
        ocean.addBiome(parameters.getParameterValue("fertileWidth"), parameters.getParameterValue("fertileHeight"), WorldMapBiome.CORAL_REEF);
        ocean.populationManager.generatePopulation(parameters.getParameterValue("startAnimals"));
        System.out.println(ocean.toString());
    }
}



/*    private StartPanel startPanel;
    private JButton clearButton;
    private SimulationPanel [] simulationPanels;
    public MainFrame() {
        super("Evolution of moving creatures");
        setSize(600,600);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        startPanel = new StartPanel(this);
        add(startPanel);
        clearButton = new JButton("CLEAR SIMULATIONS");
        add(clearButton);
        clearButton.setVisible(false);
        clearButton.addActionListener(actionEvent -> {
            clearButton.setVisible(false);
            startPanel.setVisible(true);
            for (SimulationPanel simulationPanel : simulationPanels) {
                simulationPanel.willBeClosed();
                remove(simulationPanel);
            }
            pack();
        });
    }

    public void startSimulations(Options options){
        clearButton.setVisible(true);
        simulationPanels = new SimulationPanel[options.values[8]];
        for (int i = 0; i < simulationPanels.length; i++) {
            simulationPanels[i]= new SimulationPanel(options);
            add(simulationPanels[i]);
        }
        pack();
    }*/