package com.luox6.epidemic.gui.components;

import com.luox6.epidemic.gui.GUIController;
import com.luox6.epidemic.gui.models.Collector;
import com.luox6.epidemic.gui.models.UserSetting;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphPanel extends JPanel {
    private final XYChart chart = new XYChartBuilder().title("Epidemic Simulation").xAxisTitle("Ticks").yAxisTitle("Number").build();
    JPanel chartPanel;

    public GraphPanel() {

        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
        chart.getStyler().setLegendLayout(Styler.LegendLayout.Horizontal);

        chart.getStyler().setSeriesColors(new Color[] {
                UserSetting.getSusceptibleColor(),
                UserSetting.getInfectedColor(),
                UserSetting.getDeadColor(),
                UserSetting.getRecoveredColor()
        });

        chart.getStyler().setZoomEnabled(true);
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setZoomResetByDoubleClick(true);
        chart.getStyler().setZoomResetByButton(true);

        // Placeholder data
        double [] initialX = new double[] { 1 ,2, 3, 4, 5 };
        double [] initialY = new double[] { 0, 0, 0, 0, 0 };
        chart.addSeries("Susceptible Count", initialX, initialY);
        chart.addSeries("Infected Count", initialX, initialY);
        chart.addSeries("Dead Count", initialX, initialY);
        chart.addSeries("Recovered Count", initialX, initialY);

        // Chart need to be fully initialized then put
        // into panel, or there may be paint conflict
        chartPanel = new XChartPanel<>(chart);
        setLayout(new GridLayout(1, 1));
        add(chartPanel);
    }

    /**
     * Update chart and repaint
     * @param collector Collector model
     */
    public void updateData(Collector collector) {
        List<List<Integer>> data = collector.getDataCollection().getAllData();

        chart.updateXYSeries("Susceptible Count", data.get(0), data.get(1), null);
        chart.updateXYSeries("Infected Count", data.get(0), data.get(2), null);
        chart.updateXYSeries("Dead Count", data.get(0), data.get(3), null);
        chart.updateXYSeries("Recovered Count", data.get(0), data.get(4), null);
        chartPanel.revalidate();
        chartPanel.repaint();
    }
}
