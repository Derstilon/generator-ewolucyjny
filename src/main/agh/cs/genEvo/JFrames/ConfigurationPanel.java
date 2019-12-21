package agh.cs.genEvo.JFrames;

import agh.cs.genEvo.utils.SimulationParameters;
import agh.cs.genEvo.utils.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ConfigurationPanel extends JFrame {
    private static SimulationParameters parameters = new SimulationParameters();
    private ArrayList<JSpinner> inputs;
    private MainWindow main;
    public ConfigurationPanel(String name, MainWindow main){
        super(name);
        this.main = main;
        setResizable(false);
        setupContent();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
    private void setupContent(){
        JPanel panel = new JPanel();
        inputs = new ArrayList<>();
        panel.setLayout(new SpringLayout());
        for(String labelText : parameters.labels){
            JLabel label = new JLabel(labelText, JLabel.TRAILING);
            SpinnerNumberModel model = new SpinnerNumberModel(parameters.getDefaultValue(labelText), parameters.getValueSteps(labelText), parameters.getMaxValue(labelText), parameters.getValueSteps(labelText));
            JSpinner spinner = new JSpinner(model);
            label.setLabelFor(spinner);
            panel.add(label);
            panel.add(spinner);
            inputs.add(spinner);
        }
        JButton submit = new JButton("Start simulation");
        submit.setVerticalTextPosition(AbstractButton.CENTER);
        submit.addActionListener(actionEvent -> {
            setVisible(false);
            inputs.forEach(spinner->{
                try {
                    spinner.commitEdit();
                } catch ( java.text.ParseException e ) {System.out.println(e);}
                parameters.setValue(inputs.indexOf(spinner), (Integer) spinner.getValue());
            });
            main.setVisible(true,parameters);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dispose();
        });
        SpringUtilities.makeCompactGrid(panel,
                parameters.labels.size(), 2,
                6, 6,
                6, 6);
        panel.setPreferredSize(new Dimension(300, 350));
        this.add(panel, BorderLayout.CENTER);
        this.add(submit, BorderLayout.SOUTH);
    }
}