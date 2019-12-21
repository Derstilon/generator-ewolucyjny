package agh.cs.genEvo.src;
import agh.cs.genEvo.observers.StatsObserver;
import agh.cs.genEvo.utils.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StatisticConsoleFrame extends JFrame implements StatsObserver {
    public JButton simulationToggle;
    public JPanel[] statistics;
    public ArrayList<JLabel>[] animalStatisticsLabels = new ArrayList[2];
    StatisticConsoleFrame(){
        this.setLayout(new GridBagLayout());
        JPanel buttons = new JPanel(new SpringLayout());
        this.simulationToggle = new JButton("Stop");
        this.statistics = new JPanel[2];
        buttons.add(simulationToggle);
        SpringUtilities.makeCompactGrid(buttons, 1,
                buttons.getComponentCount(),
                6, 6, 6, 6);
        this.add(buttons);
        pack();
        setRawStatistics(0);
    }

    @Override
    public boolean statsForAnimalsUpdated(String[] stats, Integer index) {
        ArrayList<JLabel> statsLabels = animalStatisticsLabels[index];
        for(int i = 0; i<statsLabels.size(); i++){
            statsLabels.get(i).setText(stats[i]);
        }
        return true;
    }

    public void setRawStatistics(int index){
        statistics[index] = new JPanel(new SpringLayout());
        statistics[index].setBorder(BorderFactory.createTitledBorder("Simulation "+(index+1)));

        JPanel animalStatistics = new JPanel(new SpringLayout());
        animalStatistics.setBorder(BorderFactory.createTitledBorder("Animal Statistics"));
        ArrayList<JLabel> labelInputs = new ArrayList<>();
        JLabel label1 = new JLabel("Number of animals: ");
        JLabel label2 = new JLabel("0");
        labelInputs.add(label2);
        animalStatistics.add(label1);
        animalStatistics.add(label2);
        label1 = new JLabel("Number of grass: ");
        label2 = new JLabel("0");
        labelInputs.add(label2);
        animalStatistics.add(label1);
        animalStatistics.add(label2);
        label1 = new JLabel("Average energy value: ");
        label2 = new JLabel("0");
        labelInputs.add(label2);
        animalStatistics.add(label1);
        animalStatistics.add(label2);
        label1 = new JLabel("Average lifespan: ");
        label2 = new JLabel("0");
        labelInputs.add(label2);
        animalStatistics.add(label1);
        animalStatistics.add(label2);
        label1 = new JLabel("Average number of children: ");
        label2 = new JLabel("0");
        labelInputs.add(label2);
        animalStatistics.add(label1);
        animalStatistics.add(label2);
        animalStatisticsLabels[index] = labelInputs;
        SpringUtilities.makeCompactGrid(animalStatistics,
                animalStatistics.getComponentCount()/2,
                2,
                6, 6, 6, 6);
        statistics[index].add(animalStatistics);
        SpringUtilities.makeCompactGrid(statistics[index], 1,
                statistics[index].getComponentCount(),
                6, 6, 6, 6);
        this.add(statistics[index]);
    };
}
