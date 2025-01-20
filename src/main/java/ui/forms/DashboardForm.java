package ui.forms;

import com.formdev.flatlaf.FlatClientProperties;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import ui.model.ModelDataPS;
import net.miginfocom.swing.MigLayout;
import raven.chart.bar.HorizontalBarChart;
import raven.chart.data.pie.DefaultPieDataset;
import ui.components.SimpleForm;

public class DashboardForm extends SimpleForm {

    public DashboardForm() {
        init();
    }

    @Override
    public void formRefresh() {
        barChart1.startAnimation();
        barChart2.startAnimation();
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
        createBarChart();
    }

    public static void setFormattedDataset(DefaultPieDataset<String> dataset, HorizontalBarChart barchart) {
        for (String key : dataset.getKeys()) {
            Number value = dataset.getValue(key);
            dataset.setValue(key, value);
        }
        barchart.setDataset(dataset);
    }

    private void createBarChart() {
        // BarChart 1
        barChart1 = new HorizontalBarChart();
        JLabel header1 = new JLabel("Danh sách sản phẩm bán chạy");
        header1.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+1;"
                + "border:0,0,5,0");
        barChart1.setHeader(header1);
        barChart1.setBarColor(Color.decode("#f97316"));
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,#000000,,20");
        panel1.add(barChart1);

        barChart2 = new HorizontalBarChart();
        JLabel header2 = new JLabel("Tình trạng tồn kho của sản phẩm bán chạy");
        header2.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+2;"
                + "border:0,0,5,0");
        barChart2.setHeader(header2);
        barChart2.setBarColor(Color.decode("#10b981"));
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,#000000,,20");
        panel2.add(barChart2);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel1, panel2);
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);
        add(splitPane, "grow, push");
    }

    public static DefaultPieDataset createData(ArrayList<ModelDataPS> list, int type) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        if(type == 0){
            list.forEach(x -> dataset.addValue(x.getProductName(), x.getSold()));
        }else {
            list.forEach(x -> dataset.addValue(x.getProductName(), x.getInStock()));
        }
        return dataset;
    }

    public HorizontalBarChart barChart1;
    public HorizontalBarChart barChart2;
}

