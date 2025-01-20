package ui.glasspanepupup;

import dao.Account_DAO;
import dao.Login_DAO;
import ui.login.Login;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class ChangePassword extends javax.swing.JPanel {

    public ChangePassword() {
        initComponents();
        setOpaque(false);
        btnChange.setFont(new java.awt.Font("Segoe UI", 0, 22));
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
        lblCurrent = new javax.swing.JLabel();
        lblNew = new javax.swing.JLabel();
        btnChange = new ui.button.Button();
        txtNew = new ui.textfield.TextField();
        txtCurrent = new ui.textfield.TextField();
        lblConfirm = new javax.swing.JLabel();
        txtConfirm = new ui.textfield.TextField();
        lblError = new javax.swing.JLabel();

        setBackground(new java.awt.Color(242, 249, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 25, 25, 25));

        lblTitle.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(80, 80, 80));
        lblTitle.setText("Đổi mật khẩu");

        lblCurrent.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblCurrent.setText("Mật khẩu hiện tại");

        lblNew.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblNew.setText("Mật khẩu mới");

        btnChange.setBackground(new java.awt.Color(5, 146, 57));
        btnChange.setForeground(new java.awt.Color(255, 255, 255));
        btnChange.setText("Cập nhật");
        btnChange.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnChange.setIconTextGap(2);
        btnChange.setPreferredSize(new java.awt.Dimension(64, 64));
        btnChange.setRound(30);
        btnChange.setShadowColor(new java.awt.Color(0, 0, 0));
        btnChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeActionPerformed(evt);
            }
        });

        txtNew.setForeground(new java.awt.Color(153, 153, 153));
        txtNew.setFont(new java.awt.Font("Segoe UI", 0, 18));

        txtCurrent.setForeground(new java.awt.Color(153, 153, 153));
        txtCurrent.requestFocus();
        txtCurrent.setFont(new java.awt.Font("Segoe UI", 0, 18));

        lblConfirm.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblConfirm.setText("Xác nhận mật khẩu");

        txtConfirm.setForeground(new java.awt.Color(153, 153, 153));
        txtConfirm.setFont(new java.awt.Font("Segoe UI", 0, 18));

        lblError.setFont(new java.awt.Font("Segoe UI", 2, 15)); // NOI18N
        lblError.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(180, 180, 180)
                                .addComponent(lblTitle)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(32, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(377, 377, 377)
                                                .addComponent(btnChange, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblNew, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblCurrent)
                                                        .addComponent(txtCurrent, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtNew, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(6, 6, 6)
                                                                .addComponent(lblError)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(lblTitle)
                                .addGap(18, 18, 18)
                                .addComponent(lblCurrent, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCurrent, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(lblNew, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNew, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(lblConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblError)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                .addComponent(btnChange, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>

    private void btnChangePWActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btnChangeActionPerformed(java.awt.event.ActionEvent evt) {
        Login login = new Login();
        Account_DAO account_dao = new Account_DAO();
        Login_DAO login_dao = new Login_DAO();


        if (!txtNew.getText().isEmpty() && !txtConfirm.getText().isEmpty() && !txtCurrent.getText().isEmpty()) {
            if (txtNew.getText().equals(txtConfirm.getText())) {
                ArrayList<String> account = login_dao.login(login.getTxtUsername().getText(), txtCurrent.getText());
                if (account.get(0) != null) {
                    account_dao.updatePasswordByAccountID(account.get(0), txtNew.getText());
                    lblError.setForeground(Color.green);
                    lblError.setText("Cập nhật mật khẩu thành công");

                } else {
                    lblError.setForeground(Color.red);
                    lblError.setText("Mật khẩu hiện tại không đúng");
                }
            } else {
                lblError.setForeground(Color.red);
                lblError.setText("Mật khẩu không khớp");
            }
        } else {
            lblError.setForeground(Color.red);
            lblError.setText("Vui lòng nhập đầy đủ thông tin");
        }


    }

    // Variables declaration - do not modify
    private ui.button.Button btnChange;
    private javax.swing.JLabel lblCurrent;
    private javax.swing.JLabel lblNew;
    private javax.swing.JLabel lblConfirm;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblTitle;
    private ui.textfield.TextField txtConfirm;
    private ui.textfield.TextField txtNew;
    private ui.textfield.TextField txtCurrent;
    // End of variables declaration

}
