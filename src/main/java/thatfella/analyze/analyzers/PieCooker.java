package thatfella.analyze.analyzers;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by ESALE on 05.12.2016.
 */
public class PieCooker {

    private int width;
    private int height;

    public PieDataset createDataForPie(String pt, String nt, String mt, int ps, int ns, int ms) {
        DefaultPieDataset pds = new DefaultPieDataset();
        pds.setValue(pt, (double) ps);
        pds.setValue(nt, (double) ns);
        pds.setValue(mt, (double) ms);

        return pds;
    }

    public JFreeChart drawPie(PieDataset ds, String pieName) throws IOException {

        //Creating a Diagram here via JFreeChart library
        JFreeChart chart = ChartFactory.createPieChart(
                pieName, // chart title
                ds, // data
                true, // include legend
                true,
                true);

        return chart;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }
}


