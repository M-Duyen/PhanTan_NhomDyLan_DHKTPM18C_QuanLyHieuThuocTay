package ui.main;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import dao.Customer_DAO;
import database.ConnectDB;
import entity.Customer;
import ui.table.TableCustom;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class CustomerSearch extends javax.swing.JPanel {

    public CustomerSearch() {
        initComponents();
        ConnectDB.getInstance().connect();
        customer_dao = new Customer_DAO();
        setupTable();
//        testData(tableCustomer);
        TableCustom.apply(scrollPane_tableCustomer, TableCustom.TableType.MULTI_LINE);
        txtearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                searchCustomer();
            }
        });
    }

    private void setupTable() {
        JTableHeader theader = tableCustomer.getTableHeader();
        theader.setFont(new java.awt.Font("Segoe UI", 0, 18));
        tableCustomer.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        if (tableCustomer.getColumnModel().getColumnCount() > 0) {
            tableCustomer.getColumnModel().getColumn(0).setPreferredWidth(150);
            tableCustomer.getColumnModel().getColumn(1).setPreferredWidth(200);
            tableCustomer.getColumnModel().getColumn(2).setPreferredWidth(130);
            tableCustomer.getColumnModel().getColumn(3).setPreferredWidth(90);
            tableCustomer.getColumnModel().getColumn(4).setPreferredWidth(160);
            tableCustomer.getColumnModel().getColumn(5).setPreferredWidth(314);
            tableCustomer.getColumnModel().getColumn(6).setPreferredWidth(70);
            tableCustomer.getColumnModel().getColumn(7).setPreferredWidth(344);
        }
    }
//    private void testData(JTable table) {
//        DefaultTableModel model = (DefaultTableModel) table.getModel();
//        model.addRow(new Object[]{ "Nguyễn Văn A", "0000000000", "Nam", "20-10-2024", "hqn1907@gmail.com", "10", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn B", "0111111111", "Nam","20-10-2024", "firstname.lastname@yourbusinessname.com","30", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn C", "0222222222", "Nữ","20-10-2024", "lastname@yourbusinessname.com", "41", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn D", "0333333333", "Nữ","20-10-2024", "hqn1907@gmail.com","60", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn E", "0444444444", "Nam","20-10-2024", "job@yourbusinessname.com","40", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn F", "0555555555", "Nữ","20-10-2024", "lastname@yourbusinessname.com","70", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn G", "0666666666", "Nam","20-10-2024", "firstname.lastname@yourbusinessname.com","15", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn H", "0777777777", "Nữ","20-10-2024", "hqn1907@gmail.com","56", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn I", "0888888888", "Nam","20-10-2024", "job@yourbusinessname.com","78", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn J", "0999999999", "Nữ","20-10-2024", "job@yourbusinessname.com","16", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn K", "0123456789", "Nữ","20-10-2024", "firstname.lastname@yourbusinessname.com","90", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn L", "0112345678", "Nam","20-10-2024", "hqn1907@gmail.com","28", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn M", "0111234567", "Nữ","20-10-2024", "job@yourbusinessname.com","85", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn N", "0111123456", "Nam","20-10-2024", "firstname.lastname@yourbusinessname.com","67", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn O", "0122345678", "Nữ","20-10-2024", "job@yourbusinessname.com","19", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn P", "0122234567", "Nam","20-10-2024", "hqn1907@gmail.com","7", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn Q", "0122223456", "Nữ","20-10-2024", "lastname@yourbusinessname.com","28", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn R", "0123345678", "Nữ","20-10-2024", "job@yourbusinessname.com","24", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn S", "0123334567", "Nam","20-10-2024", "firstname.lastname@yourbusinessname.com","14", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn T", "0111123456", "Nữ","20-10-2024", "job@yourbusinessname.com","67", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn U", "0122345678", "Nữ","20-10-2024", "hqn1907@gmail.com","19", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn Y", "0122234567", "Nam","20-10-2024", "lastname@yourbusinessname.com","7", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn W", "0122223456", "Nam","20-10-2024", "firstname.lastname@yourbusinessname.com","28", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//        model.addRow(new Object[]{ "Nguyễn Văn X", "0123345678", "Nữ","20-10-2024", "lastname@yourbusinessname.com","24", "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM"});
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the FormEditor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pCenter = new javax.swing.JPanel();
        scrollPane_tableCustomer = new javax.swing.JScrollPane();
        tableCustomer = new JTable();
        lbTitlePane = new javax.swing.JLabel();
        lbTitleTable = new javax.swing.JLabel();
        btnSearch = new ui.button.Button();
        txtearch = new ui.textfield.TextField();

        setPreferredSize(new java.awt.Dimension(1620, 1000));
        setLayout(new java.awt.BorderLayout());

        pCenter.setBackground(new java.awt.Color(242, 249, 255));
        pCenter.setPreferredSize(new java.awt.Dimension(1600, 1000));

        scrollPane_tableCustomer.setBackground(new java.awt.Color(221, 221, 221));
        scrollPane_tableCustomer.setBorder(null);

        tableCustomer.setBackground(new java.awt.Color(221, 221, 221));
        tableCustomer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tableCustomer.setModel(new DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Mã khách hàng","Tên khách hàng", "Số điện thoại", "Giới tính", "Ngày sinh", "Email", "ĐTL", "Địa chỉ"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    true, true, true, true, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCustomer.setGridColor(new java.awt.Color(218, 247, 249));
        scrollPane_tableCustomer.setViewportView(tableCustomer);

        lbTitlePane.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lbTitlePane.setForeground(new java.awt.Color(102, 204, 255));
        lbTitlePane.setText("TÌM KIẾM KHÁCH HÀNG");

        lbTitleTable.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbTitleTable.setForeground(new java.awt.Color(102, 204, 255));
        lbTitleTable.setText("DANH SÁCH KHÁCH HÀNG");

        btnSearch.setBackground(new java.awt.Color(51, 204, 255));
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Tìm kiếm");
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        txtearch.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout pCenterLayout = new javax.swing.GroupLayout(pCenter);
        pCenter.setLayout(pCenterLayout);
        pCenterLayout.setHorizontalGroup(
                pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pCenterLayout.createSequentialGroup()
                                .addContainerGap(87, Short.MAX_VALUE)
                                .addGroup(pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCenterLayout.createSequentialGroup()
                                                .addGroup(pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pCenterLayout.createSequentialGroup()
                                                                .addComponent(txtearch, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(scrollPane_tableCustomer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1458, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(75, 75, 75))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCenterLayout.createSequentialGroup()
                                                .addComponent(lbTitlePane)
                                                .addGap(597, 597, 597))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCenterLayout.createSequentialGroup()
                                                .addComponent(lbTitleTable)
                                                .addGap(645, 645, 645))))
        );
        pCenterLayout.setVerticalGroup(
                pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pCenterLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(lbTitlePane, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74)
                                .addGroup(pCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtearch, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(64, 64, 64)
                                .addComponent(lbTitleTable)
                                .addGap(18, 18, 18)
                                .addComponent(scrollPane_tableCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                                .addGap(114, 114, 114))
        );

        add(pCenter, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void searchCustomer() {
        // Xóa bảng hiện tại và thêm dữ liệu tìm kiếm
        DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
        model.setRowCount(0); // Xóa các dòng hiện tại
        String criteria = txtearch.getText().trim();

        ArrayList<Customer> searchResults = customer_dao.getCustomerByCriterous(criteria);
        if(searchResults.isEmpty()){
            model.addRow(new Object[] {"...","...","...","...","...","...","...","..."});
            // Tùy chọn: Căn chỉnh cột đầu tiên cho thông báo
        }
        for (Customer customer : searchResults) {
            model.addRow(new Object[]{
                    customer.getCustomerID(),
                    customer.getCustomerName(),
                    customer.getPhoneNumber(),
                    customer.isGender() ? "Nam" : "Nữ",
                    customer.getBrithDate(),
                    customer.getEmail(),
                    "",  // Điền ĐTL nếu cần
                    customer.getAddr()
            });
        }
        if(txtearch.getText().isEmpty()){
            model.setRowCount(0); // Xóa các dòng hiện tại
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ui.button.Button btnSearch;
    private javax.swing.JLabel lbTitlePane;
    private javax.swing.JLabel lbTitleTable;
    private javax.swing.JPanel pCenter;
    private javax.swing.JScrollPane scrollPane_tableCustomer;
    private JTable tableCustomer;
    private ui.textfield.TextField txtearch;
    private Customer_DAO customer_dao;
    // End of variables declaration//GEN-END:variables
}
