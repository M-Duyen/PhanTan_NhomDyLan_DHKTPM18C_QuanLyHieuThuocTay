package ui.glasspanepupup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class Notification extends JPanel {

    public Notification() {
        initComponents();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs); // Gọi phương thức của lớp cha trước
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, 0, 0, 15, 15));
        g2.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane1 = new JScrollPane();
        myList1 = new ui.jlist.MyList<>();

        setBackground(new Color(242, 249, 255));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

//        myList1.setModel(new AbstractListModel<String>() {
//            String[] strings = {};
//            public int getSize() { return strings.length; }
//            public String getElementAt(int i) { return strings[i]; }
//        });

        myList1.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        myList1.setPreferredSize(new Dimension(65, 300));
        jScrollPane1.setViewportView(myList1);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(60, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 420, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(80)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    public void eventOK(ActionListener event) {
        // Thêm chức năng xử lý sự kiện OK
    }



    // Biến khai báo - không sửa đổi//GEN-BEGIN:variables
    private JScrollPane jScrollPane1;
    public ui.jlist.MyList<String> myList1;

    public static void main(String[] args) {
        Notification notification = new Notification();
        notification.myList1.addItem("dssds");
    }
    // Kết thúc khai báo biến//GEN-END:variables

}
