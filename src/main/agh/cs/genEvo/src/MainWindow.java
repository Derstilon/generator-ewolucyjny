package agh.cs.genEvo.src;

import agh.cs.genEvo.utils.SimulationParameters;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JPanel aBorderPanel = new JPanel(new BorderLayout());
    private SimulationParameters parameters;
    public MainWindow(String name){
        super(name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void setVisible(boolean flag, SimulationParameters parameters){
        if(flag){
            this.parameters = parameters;
            JLabel label = new JLabel(parameters.labels.get(0)+parameters.getValues()[0]);
            this.add(label, BorderLayout.CENTER);
            this.pack();
            super.setVisible(flag);
        }
    };
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