package agh.cs.genEvo.src;

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
        //panel.setLayout(new GridBagLayout());
        //GridBagConstraints c = new GridBagConstraints();
        //c.fill = GridBagConstraints.HORIZONTAL;
        //.weighty = 1.0;
        for(String labelText : parameters.labels){
            JLabel label = new JLabel(labelText, JLabel.TRAILING);
            SpinnerNumberModel model = new SpinnerNumberModel(parameters.getDefaultValue(labelText), parameters.getValueSteps(labelText), parameters.getMaxValue(labelText), parameters.getValueSteps(labelText));
            JSpinner spinner = new JSpinner(model);
            label.setLabelFor(spinner);
            //c.gridx = 0;
            //c.weightx = .5;
            //c.gridy = parameters.labels.indexOf(labelText);
            //c.insets = new Insets(0,10,0,0);
            panel.add(label);//, c);
            //c.weightx = 0;
            //c.gridx = 1;
            //c.insets = new Insets(0,0,0,10);
            panel.add(spinner);//, c);
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
        //c.gridy = parameters.labels.size();
        //c.weightx = 1;
        //c.weighty = 1;
        //c.gridx = 1;
        //c.insets = new Insets(0,0,0,10);
        SpringUtilities.makeCompactGrid(panel,
                parameters.labels.size(), 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
        panel.setPreferredSize(new Dimension(300, 350));
        this.add(panel, BorderLayout.CENTER);
        this.add(submit, BorderLayout.SOUTH);
    }
}