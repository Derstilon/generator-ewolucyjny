package agh.cs.genEvo.utils;

import java.util.ArrayList;

public class SimulationParameters {
    private static final int[] max = {72, 24 ,72 ,24, 1024, 256, 1024, 1024, 256, 1000, 1080};
    private static final int[] defaultVal = {72, 24 ,8 ,4, 256, 8, 64, 24, 8, 50, 640};
    private static final int[] val = new int[defaultVal.length];
    private static final int[] steps = {4, 4 ,1 ,1, 4, 4, 4, 4, 4, 1, 10};
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
    public void setValue(int index, int value){
        val[index] = value - (value%steps[index]);
    }

    //GetValues//
    public int[] getValues(){ return val; }
    private int getValueFrom(int[] array, String label){
        return array[labels.indexOf(label)];
    }
    public int getMaxValue(String label){
        return getValueFrom(max, label);
    }
    public int getDefaultValue(String label){
        return getValueFrom(defaultVal, label);
    }
    public int getValueSteps(String label){
        return getValueFrom(steps, label);
    }
    //********//
}
