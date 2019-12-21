package agh.cs.genEvo;

import agh.cs.genEvo.JFrames.ConfigurationPanel;
import agh.cs.genEvo.JFrames.MainWindow;

public class Simulation {
    static MainWindow main;
    static ConfigurationPanel conf;
    public static void main(String[] args) {
        main = new MainWindow("Generator Ewolucyjny");
        conf = new ConfigurationPanel("Generator Ewolucyjny", main);
    }
}