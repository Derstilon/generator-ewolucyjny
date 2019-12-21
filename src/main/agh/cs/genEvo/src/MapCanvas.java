package agh.cs.genEvo.src;

import agh.cs.genEvo.WorldMap;
import agh.cs.genEvo.mapElements.PlantInterface;
import agh.cs.genEvo.mapElements.WorldMapZone;
import agh.cs.genEvo.mapElements.animalElements.AnimalInterface;
import agh.cs.genEvo.utils.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class MapCanvas extends JPanel {
    public WorldMap map;
    public Integer canvasWidth;
    public Integer canvasHeight;
    public Integer tileSize;
    private static Integer simulationTime;
    private static AtomicReference<Boolean> simulationOngoing = new AtomicReference<>(true);
    public MapCanvas(WorldMap map, int resolution, int simulationTime){
        this.map = map;
        this.simulationTime = simulationTime*1000;
        Vector2d[] bounds = map.getBounds();
        this.tileSize = resolution/(bounds[1].subtract(bounds[0]).x +1);
        this.canvasWidth = tileSize*(bounds[1].subtract(bounds[0]).x +1);
        this.canvasHeight = tileSize*(bounds[1].subtract(bounds[0]).y +1);
    }
    public void startSimulation(){
        Thread simulationLoop = new Thread(() -> {
            try {
                for (int i = 0; i < simulationTime && !map.populationManager.bigDie(); i++) {
                    if (simulationOngoing.get()) {
                        map.simulateWorld();
                        this.repaint();
                        map.statsManager.updateStats();
                        //map.getAnimalStatistics();
                    } else {
                        i--;
                    }
                    Thread.sleep(1000 / 20);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        simulationLoop.start();
    }
    public void toggleSimulation(){
        simulationOngoing.set(!simulationOngoing.get());
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(canvasWidth,canvasHeight);
    }
    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g.create();
        this.paintZones(g2d);
        g2d.dispose();
    }

    private void paintZones(Graphics2D g2d){
        int i = 0;
        WorldMapZone paintZone = null;
        do{
            i++;
            paintZone = map.paintManager.getZoneToPaint(paintZone);
            Vector2d position = paintZone.getPosition();
            Integer zoneSize = paintZone.getSize();
            g2d.setColor(paintZone.getBiome().getColor());
            g2d.fillRect(position.x*tileSize, position.y*tileSize, zoneSize*tileSize,zoneSize*tileSize);
            for(int j = 0; j < zoneSize*zoneSize; j++){
                position = paintZone.getVector(j);
                PlantInterface plant = map.plantAt(position);
                AnimalInterface animal = map.animalAt(position);
                if(plant != null){
                    g2d.setColor(plant.getColor());
                    paintPlant(g2d, position);
                }else if(animal != null){
                    g2d.setColor(animal.getColor());
                    paintAnimal(g2d, position);
                }
            }
        }while(map.paintManager.canPaintZone(i));
    }
    private void  paintPlant(Graphics2D g2d, Vector2d position){
        g2d.fillRect(tileSize*position.x + 1, tileSize*position.y + 1, tileSize-2,tileSize-2);
    }

    private void  paintAnimal(Graphics2D g2d, Vector2d position){
        g2d.fillOval(tileSize*position.x + 2, tileSize*position.y + 2, tileSize-4,tileSize-4);
    }
}
