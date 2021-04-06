package com.luox6.epidemic.gui.components;

import com.luox6.epidemic.gui.GUIController;
import com.luox6.epidemic.gui.models.Collector;
import com.luox6.epidemic.gui.models.UserSetting;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphPanel extends JPanel {
    private GUIController guiController;
    private XYChart chart = new XYChartBuilder().title("Epidemic Simulation").xAxisTitle("Ticks").yAxisTitle("Number").build();
    JPanel chartPanel = new XChartPanel<>(chart);

    public GraphPanel(GUIController guiController) {
        this.guiController = guiController;
        setLayout(new GridLayout(1, 1));
        add(chartPanel);

        double [] initialX = new double[] { 1 ,2, 3, 4, 5};
        double [] initialY = new double[] { 0, 0, 0, 0, 0};
        chart.addSeries("Susceptible Count", initialX, initialY);
        chart.addSeries("Infected Count", initialX, initialY);
        chart.addSeries("Dead Count", initialX, initialY);
        chart.addSeries("Recovered Count", initialX, initialY);

        chart.getStyler().setSeriesColors(new Color[] {
                UserSetting.getSusceptibleColor(),
                UserSetting.getInfectedColor(),
                UserSetting.getDeadColor(),
                UserSetting.getRecoveredColor()
        });
    }

    public void updateData(Collector collector) {
        List<List<Integer>> data = collector.getDataCollection().getAllData();

        chart.updateXYSeries("Susceptible Count", data.get(0), data.get(1), null);
        chart.updateXYSeries("Infected Count", data.get(0), data.get(2), null);
        chart.updateXYSeries("Dead Count", data.get(0), data.get(3), null);
        chart.updateXYSeries("Recovered Count", data.get(0), data.get(4), null);
    }
}
