package ui.cell;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import ui.swing.shadow.ShadowRenderer;

public class ActionButton extends JButton {

    private boolean mousePress;
    private Color shadowColor = new Color(170, 170, 170);
    private BufferedImage imageShadow;
    private final Insets shadowSize = new Insets(4, 4, 4, 4);

    public ActionButton() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(3, 3, 3, 3));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                mousePress = true;
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                mousePress = false;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ shadow
        g2.drawImage(imageShadow, 0, 0, null);

        // Màu nền khi nhấn
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height) - (shadowSize.left + shadowSize.right);
        int x = (width - size) / 2;
        int y = (height - size) / 2;

        if (mousePress) {
            g2.setColor(new Color(200, 254, 255));
        } else {
            g2.setColor(new Color(255, 255, 255));
        }

        g2.fill(new Ellipse2D.Double(x, y, size, size));
        g2.dispose();
        super.paintComponent(grphcs);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        createImageShadow();
    }



    private void createImageShadow() {
        int width = getWidth();
        int height = getHeight();
        if (width > 0 && height > 0) {
            imageShadow = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = imageShadow.createGraphics();
            BufferedImage img = createShadow();
            if (img != null) {
                g2.drawImage(img, 0, 0, null);
            }
            g2.dispose();
        }
    }

    private BufferedImage createShadow() {
        int width = getWidth() - (shadowSize.left + shadowSize.right);
        int height = getHeight() - (shadowSize.top + shadowSize.bottom);
        if (width > 0 && height > 0) {
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fill(new Ellipse2D.Double(0, 0, width, height));
            g2.dispose();
            return new ShadowRenderer(5, 0.3f, shadowColor).createShadow(img);
        } else {
            return null;
        }
    }
}
