package ui.menu;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import static javax.swing.SwingConstants.CENTER;

public class SlideShow extends JLabel {
    private final String[] images = {
        "src/main/java/ui/slider/slide1.jpg",
        "src/main/java/ui/slider/slide2.jpg",
        "src/main/java/ui/slider/slide3.jpg",
        "src/main/java/ui/slider/slide4.png"
    };
    private int currentSlide = 0;
    private Timer timer;

    public SlideShow() {
        initComponents();
        startTimer();  // Bắt đầu timer sau khi khởi tạo
    }

    private void startTimer() {
        timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSlide();
            }
        });
        timer.start();
    }

    public void updateSlide() {
        // Tạo đối tượng ImageIcon từ đường dẫn hình ảnh
        ImageIcon originalIcon = new ImageIcon(images[currentSlide]);

        // Kiểm tra kích thước của JLabel trước khi thay đổi kích thước hình ảnh
        int labelWidth = getWidth();
        int labelHeight = getHeight();

        if (labelWidth > 0 && labelHeight > 0) {
            // Thay đổi kích thước hình ảnh để khớp với kích thước của JLabel
            ImageIcon scaledIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(
                labelWidth, // chiều rộng của JLabel
                labelHeight, // chiều cao của JLabel
                java.awt.Image.SCALE_SMOOTH // cách thay đổi kích thước hình ảnh
            ));
            setIcon(scaledIcon); // Cập nhật hình ảnh cho JLabel
        } else {
//            System.out.println("Kích thước JLabel không hợp lệ: " + labelWidth + "x" + labelHeight);
        }

        // Cập nhật slide
        currentSlide++;
        if (currentSlide >= images.length) {
            currentSlide = 0;
        }
    }

    private void initComponents() {
        this.setHorizontalAlignment(CENTER);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        setFont(new Font("Arial", Font.BOLD, 48));
        setPreferredSize(new Dimension(1400, 700));
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
    }
}