package ui.glasspanepupup;

import dao.Prescription_DAO;
import entity.Prescription;
import ui.dialog.Confirm;
import ui.dialog.Message;
import ui.forms.TempOrderForm;
import ui.main.HomePage;
import ui.panel.PanelRound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreateOrderWithPres extends PanelRound{
    private HomePage homePage;
    private TempOrderForm tempOrderForm;

    public CreateOrderWithPres(HomePage homePage, TempOrderForm tempOrderForm) {
        this.homePage = homePage;
        this.tempOrderForm = tempOrderForm;
        initComponents();

        this.setRoundBottomLeft(20);
        this.setRoundBottomRight(20);
        this.setRoundTopLeft(20);
        this.setRoundTopRight(20);

//        this.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                System.out.println("Clicked");
//            }
//        });
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        date = new ui.datechooser.DateChooser();
        lblTitle = new javax.swing.JLabel();
        lblPresID = new javax.swing.JLabel();
        lblDiagnosis = new javax.swing.JLabel();
        lblCreateDate = new javax.swing.JLabel();
        lblFacility = new javax.swing.JLabel();
        txtPresID = new ui.textfield.TextField();
        txtCreateDate = new ui.textfield.TextField();
        txtDiagnosis = new ui.textfield.TextField();
        txtFacility = new ui.textfield.TextField();
        btnAdd = new ui.button.Button();
        btnDate = new ui.button.Button();

        date.setForeground(new java.awt.Color(51, 153, 255));
        date.setTextRefernce(txtCreateDate);

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(700, 550));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(102, 204, 255));
        lblTitle.setText("THÔNG TIN ĐƠN KÊ");

        lblPresID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPresID.setText("Mã đơn thuốc:");

        lblDiagnosis.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDiagnosis.setText("Chẩn đoán:");

        lblCreateDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCreateDate.setText("Ngày lập:");

        lblFacility.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblFacility.setText("Cơ sở khám bệnh:");

        txtPresID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtCreateDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtDiagnosis.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtFacility.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnAdd.setBackground(new java.awt.Color(51, 204, 255));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Thêm đơn thuốc");
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAdd.setRound(20);
        btnAdd.setShadowColor(new java.awt.Color(51, 153, 255));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDate.setIcon(new javax.swing.ImageIcon("src/main/java/ui/button/calendar.png")); // NOI18N
        btnDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(225, 225, 225)
                                                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(38, 38, 38)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblPresID)
                                                        .addComponent(lblCreateDate)
                                                        .addComponent(lblDiagnosis)
                                                        .addComponent(lblFacility))
                                                .addGap(35, 35, 35)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(txtCreateDate, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(txtPresID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(txtDiagnosis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(txtFacility, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))))))
                                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(lblTitle)
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblPresID)
                                        .addComponent(txtPresID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblCreateDate)
                                        .addComponent(txtCreateDate, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtDiagnosis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblDiagnosis))
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblFacility)
                                        .addComponent(txtFacility, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30))
        );
    }// </editor-fold>

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        String presID = txtPresID.getText().trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate createDate;
        try {
            createDate = LocalDate.parse(txtCreateDate.getText().trim(), formatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Ngày không hợp lệ! Vui lòng nhập theo định dạng dd-MM-yyyy.");
            return;
        }

        String diagnosis = txtDiagnosis.getText().trim();
        String facility = txtFacility.getText().trim();

        Prescription pres = new Prescription(presID, createDate, diagnosis, facility);
        if(new Prescription_DAO().addPrescription(pres)){
            new Message(homePage, true, "Thông báo", "Thêm đơn thuốc thành công", "src/main/java/ui/dialog/checked.png").showAlert();
            tempOrderForm.setPrescription(pres);
            homePage.closePres();
            tempOrderForm.setCkbPres();
        } else {
            Confirm dialog = new Confirm(homePage, true, "Thông báo", "Thêm đơn thuốc thất bại !!!", "src/main/java/ui/dialog/warning.png", "Tiếp tục", "Hủy bỏ");
            dialog.showAlert();
            int response = dialog.getResponse();
            if (response == 0) {
                homePage.closePres();
                tempOrderForm.setCkbPres();
            }
        }
    }

    private void btnDateActionPerformed(java.awt.event.ActionEvent evt) {
        JDialog dateDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Chọn ngày", Dialog.ModalityType.APPLICATION_MODAL);
        dateDialog.setLayout(new BorderLayout());
        dateDialog.add(date, BorderLayout.CENTER);

        ui.button.Button confirmButton = new ui.button.Button("Chọn");
        confirmButton.setBackground(new java.awt.Color(5, 146, 57));
        confirmButton.setForeground(new java.awt.Color(255, 255, 255));
        confirmButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        confirmButton.setRound(20);
        confirmButton.setShadowColor(new java.awt.Color(51, 153, 255));

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateDialog.dispose();
            }
        });

        dateDialog.add(confirmButton, BorderLayout.SOUTH);
        dateDialog.pack();
        Point location = txtCreateDate.getLocationOnScreen();
        dateDialog.setLocation(location.x + 140, location.y + txtCreateDate.getHeight());

        dateDialog.setVisible(true);
    }

    // Variables declaration - do not modify
    private ui.button.Button btnAdd;
    private ui.button.Button btnDate;
    private ui.datechooser.DateChooser date;
    private javax.swing.JLabel lblCreateDate;
    private javax.swing.JLabel lblDiagnosis;
    private javax.swing.JLabel lblFacility;
    private javax.swing.JLabel lblPresID;
    private javax.swing.JLabel lblTitle;
    private ui.textfield.TextField txtCreateDate;
    private ui.textfield.TextField txtDiagnosis;
    private ui.textfield.TextField txtFacility;
    private ui.textfield.TextField txtPresID;
    // End of variables declaration
}
