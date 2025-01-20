package ui.glasspanepupup;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class Message extends javax.swing.JPanel {

    public Message() {
        initComponents();
        setOpaque(false);
//        txt.setBackground(new Color(0, 0, 0, 0));
//        txt.setSelectionColor(new Color(48, 170, 63, 200));
//        txt.setOpaque(false);

        btnChangePW.setFont(new java.awt.Font("Segoe UI", 0, 22));
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        g2.dispose();
        super.paintComponent(grphcs);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        btnChangePW = new Button();
        lblPhoneNumber = new javax.swing.JLabel();
        lblEmpID1 = new javax.swing.JLabel();
        lblDOB = new javax.swing.JLabel();
        lblEmpName = new javax.swing.JLabel();
        lblDegree = new javax.swing.JLabel();
        lblGender = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblEmpID_show = new javax.swing.JLabel();
        lblPhoneNumber_show = new javax.swing.JLabel();
        lblEmpName_show = new javax.swing.JLabel();
        lblEmail_show = new javax.swing.JLabel();
        lblDegree_show = new javax.swing.JLabel();
        lblDOB_show = new javax.swing.JLabel();
        lblGender_show = new javax.swing.JLabel();

        setBackground(new java.awt.Color(242, 249, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 25, 25, 25));

        lblTitle.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(80, 80, 80));
        lblTitle.setText("Thông tin nhân viên");

        btnChangePW.setBackground(new java.awt.Color(0, 153, 51));
        btnChangePW.setForeground(new java.awt.Color(255, 255, 255));
        btnChangePW.setText("Đổi mật khẩu");
        btnChangePW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePWActionPerformed(evt);
            }
        });

        lblPhoneNumber.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblPhoneNumber.setText("Số điện thoại:");

        lblEmpID1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblEmpID1.setText("Mã nhân viên: ");

        lblDOB.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblDOB.setText("Ngày sinh:");

        lblEmpName.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblEmpName.setText("Tên nhân viên: ");

        lblDegree.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblDegree.setText("Trình độ:");

        lblGender.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblGender.setText("Giới tính:");

        lblEmail.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblEmail.setText("Email:");

        lblEmpID_show.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblEmpID_show.setText("EP1501");

        lblPhoneNumber_show.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblPhoneNumber_show.setText("0961416115");

        lblEmpName_show.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblEmpName_show.setText("Nguyễn Thị Mỹ Duyên");

        lblEmail_show.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblEmail_show.setText("nguyenmyduyen2702@gmail.com");

        lblDegree_show.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblDegree_show.setText("Đại học");

        lblDOB_show.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblDOB_show.setText("27/02/2004");

        lblGender_show.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblGender_show.setText("Nữ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnChangePW, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(9, 9, 9)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(lblDOB)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(lblEmail)
                                                                                .addGap(1, 1, 1))
                                                                        .addComponent(lblGender)
                                                                        .addComponent(lblDegree)
                                                                        .addComponent(lblPhoneNumber))
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(30, 30, 30)
                                                                                .addComponent(lblEmail_show, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(180, 180, 180)
                                                                                .addComponent(lblGender_show, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(159, 159, 159)
                                                                                .addComponent(lblDegree_show, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(131, 131, 131)
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(lblDOB_show, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(lblPhoneNumber_show, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(6, 6, 6)
                                                                                .addComponent(lblEmpID1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(lblEmpName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(75, 75, 75)
                                                                                .addComponent(lblEmpName_show, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(150, 150, 150)
                                                                                .addComponent(lblEmpID_show, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(174, 174, 174)
                                                .addComponent(lblTitle)))
                                .addContainerGap(97, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(lblTitle)
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblEmpID1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblEmpID_show))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblEmpName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblEmpName_show))
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblPhoneNumber_show))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblDOB)
                                        .addComponent(lblDOB_show))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblGender_show)
                                        .addComponent(lblGender, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblDegree_show)
                                        .addComponent(lblDegree, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblEmail_show)
                                        .addComponent(lblEmail))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                .addComponent(btnChangePW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
    }// </editor-fold>

    private void btnChangePWActionPerformed(java.awt.event.ActionEvent evt) {
        GlassPanePopup.closePopup(0);
        ChangePassword changePassword = new ChangePassword();
        GlassPanePopup.showPopup(changePassword);
    }

    public void eventOK(ActionListener event) {
        btnChangePW.addActionListener(event);
    }

    // Variables declaration - do not modify
    private Button btnChangePW;
    private javax.swing.JLabel lblDOB;
    public javax.swing.JLabel lblDOB_show;
    private javax.swing.JLabel lblDegree;
    public javax.swing.JLabel lblDegree_show;
    private javax.swing.JLabel lblEmail;
    public javax.swing.JLabel lblEmail_show;
    private javax.swing.JLabel lblEmpID1;
    public javax.swing.JLabel lblEmpID_show;
    private javax.swing.JLabel lblEmpName;
    public javax.swing.JLabel lblEmpName_show;
    private javax.swing.JLabel lblGender;
    public javax.swing.JLabel lblGender_show;
    private javax.swing.JLabel lblPhoneNumber;
    public javax.swing.JLabel lblPhoneNumber_show;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration
}
