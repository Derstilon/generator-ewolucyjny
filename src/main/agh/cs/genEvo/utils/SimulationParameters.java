package agh.cs.genEvo.utils;

import java.util.ArrayList;

public class SimulationParameters {
    private static final int[] max = {72, 72 ,18 ,18, 1024, 256, 1024, 1024, 256, 1000, 1680};
    private static final int[] defaultVal = {72, 24 ,8 ,4, 256, 4, 64, 24, 2, 250, 1024};
    private static final int[] val = new int[defaultVal.length];
    private static final int[] steps = {4, 4 ,1 ,1, 4, 4, 4, 1, 1, 1, 10};
    public static final ArrayList<String> labels = new ArrayList<String>() {
        {
            add("Width of the map:");
            add("Height of the map:");
            /*add("Size of MapZone:");*/ //4
            add("Count of fertile Zones Horizontally:");
            add("Count of fertile Zones Vertically:");
            add("Startup animals energy:");
            add("Movement animals cost:");
            add("Plant food energy bonus:");
            add("Startup simulation animals:");
            add("Plant food daily spread:");
            add("Simulation duration (1000k):");
            add("Map resolution:");
        }
    };
    public static final ArrayList<String> shortLabels = new ArrayList<String>() {
        {
            add("width");
            add("height");
            add("fertileWidth");
            add("fertileHeight");
            add("startEnergy");
            add("moveEnergy");
            add("plantEnergy");
            add("startAnimals");
            add("plantSpread");
            add("simulTime");
            add("resolution");
        }
    };
    public void setValue(int index, int value){
        val[index] = value - (value%steps[index]);
    }

    //GetValues//
    public int[] getValues(){ return val; }
    private int getValueFrom(int[] array, String label){
        if(labels.contains(label)) {
            return array[labels.indexOf(label)];
        }
        if(shortLabels.contains(label)){
            return array[shortLabels.indexOf(label)];
        }
        return 0;
    }
    public int getMaxValue(String label){
        return getValueFrom(max, label);
    }
    public int getParameterValue(String label){
        return getValueFrom(val, label);
    }
    public int getDefaultValue(String label){
        return getValueFrom(defaultVal, label);
    }
    public int getValueSteps(String label){
        return getValueFrom(steps, label);
    }
    //********//
}
