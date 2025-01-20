package ui.login;

import com.formdev.flatlaf.util.Animator;
import com.formdev.flatlaf.util.CubicBezierEasing;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class HomeOverlay extends JWindow {

    public PanelOverlay getOverlay() {
        return overlay;
    }

    private PanelOverlay overlay;
    private List<ModelLocation> locations;

    public HomeOverlay(JFrame frame, List<ModelLocation> locations) {
        super(frame);
        this.locations = locations;
        init();
    }

    private void init() {
        setBackground(new Color(0, 0, 0, 0));
        setLayout(new BorderLayout());
        overlay = new PanelOverlay();
        add(overlay);
    }

    public class PanelOverlay extends JPanel {
        public void setEventHomeOverlay(EventHomeOverlay eventHomeOverlay) {
            this.eventHomeOverlay = eventHomeOverlay;
        }

        private MigLayout migLayout;
        private EventHomeOverlay eventHomeOverlay;
        private AnimationType animationType = AnimationType.NONE;
        private Animator animator;
        private Animator loginAnimator;
        private float animate;
        private int index;
        private boolean showLogin;

        public void setIndex(int index) {
            this.index = index;
            ModelLocation location = locations.get(index);
            textTitle.setText(location.getTitle());
            textDescription.setText(location.getDescription());
        }

        public PanelOverlay() {
            init();
        }

        private void init() {
            setOpaque(false); // Thay đổi thành true để cho phép màu nền
//            setBackground(Color.WHITE);
            migLayout = new MigLayout("fill, insets 5 100 5 100", "fill", "[grow 0][]");
            setLayout(migLayout);
            createHeader();
            createLogin();
            JPanel panel = new JPanel(new MigLayout("wrap", "", "[]40[]"));
            panel.setOpaque(false);
            textTitle = new JTextPane();
            textDescription = new JTextPane();

            textTitle.setOpaque(false);
            textTitle.setEditable(false);
            textTitle.setFont(new Font("Segoe UI Black MT", Font.BOLD, 65));

//            Color customColor = Color.decode("#f8931d");
            textDescription.setOpaque(false);
//            textDescription.setBackground(new Color(32,32,32, 160));
            textDescription.setEditable(false);
            textDescription.setFont(new Font("Roboto", Font.BOLD, 40));
//            textDescription.setForeground(customColor);


            panel.add(textTitle);
            panel.add(textDescription);
            add(panel, "width 50%!");
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    runLoginAnimation(false);
                }
            });

            animator = new Animator(50, new Animator.TimingTarget() {
                @Override
                public void timingEvent(float v) {
                    animate = v;
                    repaint();
                }

                @Override
                public void end() {
                    if (animationType == AnimationType.CLOSE_VIDEO) {
                        eventHomeOverlay.onChanged(index);
                        SwingUtilities.invokeLater(() -> {
                            sleep(500);
                            runAnimation(index, AnimationType.SHOW_VIDEO);
                        });
                    } else {
                        animationType = AnimationType.NONE;
                    }
                }
            });

            loginAnimator = new Animator(300, new Animator.TimingTarget() {
                @Override
                public void timingEvent(float v) {
                    float f = showLogin ? v : 2f - v;
                    int x = (int) ((450 + 300) * f);
                    migLayout.setComponentConstraints(panelLogin, "pos 100%-" + x + " 0.5al, w 600, h 600");
                    revalidate();
                }
            });
            animator.setInterpolator(CubicBezierEasing.EASE_IN);
            loginAnimator.setInterpolator(CubicBezierEasing.EASE);
        }

        private void sleep(long l) {
            try {
                Thread.sleep(l);
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        private void createHeader() {
            Font f = new Font("Bodoni MT Black", Font.PLAIN, 23);
            header = new JPanel(new MigLayout("fill", "[]push[][]"));
            header.setOpaque(false);
            JLabel title = new JLabel("Nhóm 06");
            title.setFont(new Font("Bodoni MT Black", Font.BOLD, 60));
            HeaderButton login = new HeaderButton("Login");
            login.setFont(f);

            header.add(title);
            add(header);
            add(header, "wrap");
        }

        private void createLogin() {
            panelLogin = new Login();
            add(panelLogin, "pos 100% 0.5al, w 600, h 600");
        }


        private boolean runAnimation(int index, AnimationType animationType) {
            if (!animator.isRunning()) {
                this.animate = 0;
                this.animationType = animationType;
                this.index = index;
                animator.start();
                return true;
            } else {
                return false;
            }
        }

        private void runLoginAnimation(boolean show) {
            if (showLogin != show) {
                if (!loginAnimator.isRunning()) {
                    showLogin = show;
                    loginAnimator.start();
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (animationType != AnimationType.NONE) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int width = getWidth();
                int height = getHeight();
                g2.setColor(UIManager.getColor("#0595F3FF"));
                Rectangle rec = new Rectangle(0, 0, width, height);
                if (animationType == AnimationType.CLOSE_VIDEO) {
                    g2.setComposite(AlphaComposite.SrcOver.derive(animate));
                    g2.fill(rec);
                } else {
                    Area area = new Area(rec);
                    area.subtract(new Area(createRec(rec)));
                    g2.fill(area);
                }
                g2.dispose();
            }
            super.paintComponent(g);
        }

        private Shape createRec(Rectangle rec) {
            int maxSize = Math.max(rec.width, rec.height);
            float size = maxSize * animate;
            float x = (rec.width - size) * 2;
            float y = (rec.height - size) * 2;

            Ellipse2D ell = new Ellipse2D.Double(x, y, size, size);
            return ell;
        }

        private JPanel header;
        private JTextPane textTitle;
        private JTextPane textDescription;
        private Login panelLogin;
    }

    public enum AnimationType {
        CLOSE_VIDEO, SHOW_VIDEO, NONE
    }

    public void showLoginForm() {
        if (overlay != null) {
            overlay.runLoginAnimation(true);
        }
    }
}
