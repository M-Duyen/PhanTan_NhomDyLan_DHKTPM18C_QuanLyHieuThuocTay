package ui.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import ui.swing.shadow.ShadowRenderer;

public class PanelRound extends JPanel {

    private int roundTopLeft = 0;
    private int roundTopRight = 0;
    private int roundBottomLeft = 0;
    private int roundBottomRight = 0;
    private Color shadowColor = new Color(170, 170, 170);
    private BufferedImage imageShadow;
    private int shadowSize = 10;  // Điều chỉnh kích thước shadow

    // Thêm padding để tạo không gian cho shadow hiển thị
    private final Insets shadowInsets = new Insets(shadowSize, shadowSize, shadowSize, shadowSize);

    // Getter và setter cho shadowSize
    public int getShadowSize() {
        return shadowSize;
    }

    public void setShadowSize(int shadowSize) {
        this.shadowSize = shadowSize;
        setBorder(new EmptyBorder(shadowSize, shadowSize, shadowSize, shadowSize));  // Cập nhật border khi shadowSize thay đổi
        createImageShadow();
        repaint();
    }

    // Getter và setter cho shadowColor
    public Color getShadowColor() {
        return shadowColor;
    }

    public void setShadowColor(Color shadowColor) {
        this.shadowColor = shadowColor;
        createImageShadow();
        repaint();
    }

    public int getRoundTopLeft() {
        return roundTopLeft;
    }

    public void setRoundTopLeft(int roundTopLeft) {
        this.roundTopLeft = roundTopLeft;
        repaint();
    }

    public int getRoundTopRight() {
        return roundTopRight;
    }

    public void setRoundTopRight(int roundTopRight) {
        this.roundTopRight = roundTopRight;
        repaint();
    }

    public int getRoundBottomLeft() {
        return roundBottomLeft;
    }

    public void setRoundBottomLeft(int roundBottomLeft) {
        this.roundBottomLeft = roundBottomLeft;
        repaint();
    }

    public int getRoundBottomRight() {
        return roundBottomRight;
    }

    public void setRoundBottomRight(int roundBottomRight) {
        this.roundBottomRight = roundBottomRight;
        repaint();
    }

    public PanelRound() {
        setOpaque(false);
        setBorder(new EmptyBorder(shadowSize, shadowSize, shadowSize, shadowSize));  // Đặt border ban đầu
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ shadow
        g2.drawImage(imageShadow, 0, 0, null);

        // Vẽ panel với kích thước và khoảng trống để hiển thị shadow
        int width = getWidth() - shadowInsets.left - shadowInsets.right;
        int height = getHeight() - shadowInsets.top - shadowInsets.bottom;
        int x = shadowInsets.left;
        int y = shadowInsets.top;

        g2.setColor(getBackground());
        Area area = new Area(createRoundTopLeft(x, y, width, height));
        if (roundTopRight > 0) {
            area.intersect(new Area(createRoundTopRight(x, y, width, height)));
        }
        if (roundBottomLeft > 0) {
            area.intersect(new Area(createRoundBottomLeft(x, y, width, height)));
        }
        if (roundBottomRight > 0) {
            area.intersect(new Area(createRoundBottomRight(x, y, width, height)));
        }

        g2.fill(area);
        g2.dispose();
        super.paint(g);
    }

    private void createImageShadow() {
        int width = getWidth();
        int height = getHeight();
        if (width > 0 && height > 0) {
            imageShadow = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = imageShadow.createGraphics();
            BufferedImage img = createShadow();
            if (img != null) {
                g2.drawImage(createShadow(), 0, 0, null);
            }
            g2.dispose();
        }
    }

    private BufferedImage createShadow() {
        int width = getWidth() - shadowInsets.left - shadowInsets.right;
        int height = getHeight() - shadowInsets.top - shadowInsets.bottom;
        if (width > 0 && height > 0) {
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Tạo khu vực bo góc
            Area area = new Area(createRoundTopLeft(0, 0, width, height));
            if (roundTopRight > 0) {
                area.intersect(new Area(createRoundTopRight(0, 0, width, height)));
            }
            if (roundBottomLeft > 0) {
                area.intersect(new Area(createRoundBottomLeft(0, 0, width, height)));
            }
            if (roundBottomRight > 0) {
                area.intersect(new Area(createRoundBottomRight(0, 0, width, height)));
            }

            g2.fill(area);
            g2.dispose();

            // Tạo shadow từ hình ảnh panel
            return new ShadowRenderer(shadowSize, 0.3f, shadowColor).createShadow(img);
        }
        return null;
    }

    private Shape createRoundTopRight(int x, int y, int width, int height) {
        int roundX = Math.min(width, roundTopRight);
        int roundY = Math.min(height, roundTopRight);
        Area area = new Area(new RoundRectangle2D.Double(x, y, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(x, y, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(x, y + roundY / 2, width, height - roundY / 2)));
        return area;
    }

    private Shape createRoundTopLeft(int x, int y, int width, int height) {
        int roundX = Math.min(width, roundTopLeft);
        int roundY = Math.min(height, roundTopLeft);
        Area area = new Area(new RoundRectangle2D.Double(x, y, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(x + roundX / 2, y, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(x, y + roundY / 2, width, height - roundY / 2)));
        return area;
    }

    private Shape createRoundBottomLeft(int x, int y, int width, int height) {
        int roundX = Math.min(width, roundBottomLeft);
        int roundY = Math.min(height, roundBottomLeft);
        Area area = new Area(new RoundRectangle2D.Double(x, y, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(x + roundX / 2, y, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(x, y, width, height - roundY / 2)));
        return area;
    }

    private Shape createRoundBottomRight(int x, int y, int width, int height) {
        int roundX = Math.min(width, roundBottomRight);
        int roundY = Math.min(height, roundBottomRight);
        Area area = new Area(new RoundRectangle2D.Double(x, y, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(x, y, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(x, y, width, height - roundY / 2)));
        return area;
    }
}
