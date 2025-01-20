package ui.forms;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;

import ui.model.ModelDataPS_Circle;
import net.miginfocom.swing.MigLayout;
import raven.chart.bar.HorizontalBarChart;
import raven.chart.data.pie.DefaultPieDataset;
import raven.chart.line.LineChart;
import raven.chart.pie.PieChart;
import ui.components.SimpleForm;


public class DashboardForm2 extends SimpleForm {

    public DashboardForm2() {
        init();
    }

    @Override
    public void formRefresh() {
        lineChart.startAnimation();
        pieChart1.startAnimation();
        pieChart2.startAnimation();
    }

    @Override
    public void formInitAndOpen() {
        System.out.println("init and open");
    }

    @Override
    public void formOpen() {
        System.out.println("Open");
    }

    private void init() {
        setLayout(new MigLayout("wrap,fill,gap 10", "fill"));
        setBackground(new Color(255, 255, 255));
        createPieChart();
    }

    private void createPieChart() {
        pieChart1 = new PieChart();
        JLabel header1 = new JLabel("Thống kê theo loại thuốc");
        header1.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+1");
        pieChart1.setHeader(header1);
        pieChart1.getChartColor().addColor(Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"), Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"), Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart1.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        add(pieChart1, "split 3,height 400");
    }


    public DefaultPieDataset createData(ArrayList<ModelDataPS_Circle> list) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        list.forEach(x -> dataset.addValue(x.getName(), x.getCount()));

        return dataset;
    }

    public void setFormattedDataset(DefaultPieDataset<String> dataset, PieChart pieChart) {
        for (String key : dataset.getKeys()) {
            Number value = dataset.getValue(key);
            dataset.setValue(key, value);
        }
        pieChart.setDataset(dataset);
    }

    private LineChart lineChart;
    private HorizontalBarChart barChart1;
    private HorizontalBarChart barChart2;
    public PieChart pieChart1;
    private PieChart pieChart2;
    private PieChart pieChart3;
}
