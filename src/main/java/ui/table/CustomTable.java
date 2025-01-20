package ui.table;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomTable extends JTable {
    private int round = 20; // Độ cong của góc
    private Color headerColor;

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
        repaint();
    }

    public Color getHeaderColor() {
        return headerColor;
    }

    public void setHeaderColor(Color headerColor) {
        this.headerColor = headerColor;
        repaint();
    }
    
    public CustomTable() {
        setOpaque(false);
        // Mặc định màu header
        this.headerColor = Color.LIGHT_GRAY;

        // Tạo renderer cho header
        JTableHeader header = getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Vẽ màu cho header
                setBackground(headerColor);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        int width = getWidth();
        int height = getHeight();
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, round, round);

        super.paintComponent(g);
        g2.dispose();
    }
}
