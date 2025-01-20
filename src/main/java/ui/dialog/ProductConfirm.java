package ui.dialog;

import dao.Product_DAO;
import dao.Unit_DAO;
import entity.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

public class ProductConfirm extends SweetAlert {
    public static Product product;
    public static DecimalFormat df = new DecimalFormat("#,##0.00 VND");
    public static NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
    public boolean flag = false;

    public ProductConfirm(java.awt.Frame parent, Product product, boolean modal) {
        super(parent, modal);
        this.product = product;
        initComponents();

        setInformationProduct();
        updateComboboxUnit();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblProductID = new javax.swing.JLabel();
        lblProductName = new javax.swing.JLabel();
        lblUnit = new javax.swing.JLabel();
        lblQuantity = new javax.swing.JLabel();
        lblSellPprice = new javax.swing.JLabel();
        lblLineTotal = new javax.swing.JLabel();
        lblInStock = new javax.swing.JLabel();
        txtProductID = new ui.textfield.TextField();
        txtProductName = new ui.textfield.TextField();
        txtInStock = new ui.textfield.TextField();
        txtQuantity = new ui.textfield.TextField();
        txtSellPrice = new ui.textfield.TextField();
        txtLineTotal = new ui.textfield.TextField();
        lblTitle = new javax.swing.JLabel();
        cbbUnit = new ui.combo_suggestion.ComboBoxSuggestion();
        btnConfirm = new ui.button.Button();
        btnCancel = new ui.button.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblProductID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblProductID.setText("Mã sản phẩm:");

        lblProductName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblProductName.setText("Tên sản phẩm:");

        lblUnit.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUnit.setText("Đơn vị:");

        lblQuantity.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblQuantity.setText("Số lượng:");

        lblSellPprice.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSellPprice.setText("Giá bán:");

        lblLineTotal.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblLineTotal.setText("Thành tiền:");

        lblInStock.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInStock.setText("Tồn kho:");

        txtProductID.setEnabled(false);
        txtProductID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtProductName.setEnabled(false);
        txtProductName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtInStock.setEnabled(false);
        txtInStock.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtQuantity.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        String quantity = txtQuantity.getText().trim();
        txtQuantity.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                String quantity = txtQuantity.getText().trim();
                String unitName = Unit_DAO.getInstance().convertDes_ToUnit(String.valueOf(cbbUnit.getSelectedItem()));
                PackagingUnit unitEnum = PackagingUnit.fromString(unitName);
                int inStock = Product_DAO.getInstance().getProduct_ByID(product.getProductID()).getInStockByUnit(unitEnum);

                if(!quantity.isEmpty()){
                    if(Integer.parseInt(quantity) > inStock) {
                        txtQuantity.setText("");
                        txtLineTotal.setText("");
                        new Message(StaticProcess.homePage, true, "Thông báo", "Số lượng bán không lớn hơn tồn kho", "src/main/java/ui/dialog/warning.png").showAlert();
                    } else {

                        double sellPrice = 0;
                        try {
                            sellPrice = nf.parse(txtSellPrice.getText().trim()).doubleValue();
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                        txtLineTotal.setText(df.format(Double.parseDouble(txtQuantity.getText().trim()) * sellPrice));
                    }
                } else {
                    txtLineTotal.setText("");
                }

            }
        });

        txtSellPrice.setEnabled(false);
        txtSellPrice.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtLineTotal.setEnabled(false);
        txtLineTotal.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblTitle.setBackground(new java.awt.Color(102, 204, 255));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(102, 204, 255));
        lblTitle.setText("THÔNG TIN CHI TIẾT SẢN PHẨM");

        cbbUnit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Viên", "Vỉ", "Chai", "Lọ", "Tuýp", "Túi", "Ống", "Chai xịt", "Lọ xịt", "Bộ kit", "Thùng", "Hộp" }));
        cbbUnit.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbbUnit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbbUnitActionPerformed(e);
            }
        });

        btnConfirm.setBackground(new java.awt.Color(102, 204, 255));
        btnConfirm.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirm.setText("Xác nhận");
        btnConfirm.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnConfirm.setRound(15);
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(255, 101, 101));
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Hủy bỏ");
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCancel.setRound(15);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitle)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInStock, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSellPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLineTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 188, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProductID)
                    .addComponent(lblInStock)
                    .addComponent(lblLineTotal)
                    .addComponent(lblSellPprice)
                    .addComponent(lblQuantity)
                    .addComponent(lblUnit)
                    .addComponent(lblProductName))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(167, 167, 167))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductID)
                    .addComponent(txtProductID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductName)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUnit)
                    .addComponent(cbbUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInStock)
                    .addComponent(txtInStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQuantity)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSellPprice)
                    .addComponent(txtSellPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLineTotal)
                    .addComponent(txtLineTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbbUnitActionPerformed(ActionEvent e) {
        if(cbbUnit.getSelectedItem() != null) {
            try {
                String description = String.valueOf(cbbUnit.getSelectedItem());
                String unitName = Unit_DAO.getInstance().getUnit_ByDes(description).getUnitName();

                PackagingUnit unitE = PackagingUnit.fromString(unitName);

                txtInStock.setText(String.valueOf(product.getInStockByUnit(unitE)));
                txtQuantity.setText(String.valueOf(1));
                txtSellPrice.setText(df.format(product.getSellPrice(unitE)));

                double sellPrice = nf.parse(txtSellPrice.getText().trim()).doubleValue();

                txtLineTotal.setText(df.format(Double.parseDouble(txtQuantity.getText().trim()) * sellPrice));
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
        if(cbbUnit.getSelectedItem() != null){
            if(!txtQuantity.getText().isEmpty() && (Integer.parseInt(txtQuantity.getText().trim()) > 0)) {
                flag = true;
                closeAlert();
            } else {
                new Message(StaticProcess.homePage, true, "Thông báo", "Số lượng bán phải lớn hơn 0!", "src/main/java/ui/dialog/warning.png").showAlert();
            }
        } else {
            new Message(StaticProcess.homePage, true, "Thông báo", "Chưa chọn đơn vị cho sản phẩm!", "src/main/java/ui/dialog/warning.png").showAlert();
        }
    }//GEN-LAST:event_btnConfirmActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        flag = false;
        closeAlert();
    }//GEN-LAST:event_btnCancelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductConfirm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ProductConfirm dialog = new ProductConfirm(new javax.swing.JFrame(), product, true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    public void setInformationProduct(){
        txtProductID.setText(product.getProductID());
        txtProductName.setText(product.getProductName());
    }
//String orderID, LocalDateTime orderDate, String shipToAddress, Enum_PaymentMethod paymentMethod, double discount, Employee employee, Customer customer, Prescription prescription
    public OrderDetails getOrderDetails(Order or, String orderIDNew){
        return new OrderDetails(Integer.parseInt(txtQuantity.getText().trim()), new Order(orderIDNew, LocalDateTime.now(), or.getShipToAddress(), or.getPaymentMethod(), or.getDiscount(), StaticProcess.empLogin, or.getCustomer(), or.getPrescription()), Product_DAO.getInstance().getProduct_ByID(txtProductID.getText()), PackagingUnit.fromString(Unit_DAO.getInstance().getUnit_ByDes(cbbUnit.getSelectedItem().toString()).getUnitName()));
    }

    public void updateComboboxUnit(){
        ArrayList<Unit> unitList = Unit_DAO.getInstance().getUnit_ByProductID(product.getProductID());
        String[] items = new String[unitList.size() + 1];
        int i = 0;

        for(Unit u : unitList) {
            i++;
            items[i] = u.getDescription();
        }
        cbbUnit.setModel(new DefaultComboBoxModel<String>(items));
    }

    public String getProductID(){
        return txtProductID.getText().trim();
    }

    public int getQuantity(){
        return Integer.parseInt(txtQuantity.getText().trim());
    }

    public double getSellPrice(){
        try {
            return nf.parse(txtSellPrice.getText().trim()).doubleValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public PackagingUnit getEnumUnit(){
        return PackagingUnit.fromString(Unit_DAO.getInstance().getUnit_ByDes(cbbUnit.getSelectedItem().toString()).getUnitName());
    }

    public void setSelectedComboboxUnit(String desUnit, int qty) {
        cbbUnit.setSelectedItem(desUnit);
        txtQuantity.setText(String.valueOf(qty));
        try {
            txtLineTotal.setText(df.format(qty * nf.parse(txtSellPrice.getText().trim()).doubleValue()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ui.button.Button btnCancel;
    private ui.button.Button btnConfirm;
    private ui.combo_suggestion.ComboBoxSuggestion cbbUnit;
    private javax.swing.JLabel lblInStock;
    private javax.swing.JLabel lblLineTotal;
    private javax.swing.JLabel lblProductID;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblSellPprice;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUnit;
    private ui.textfield.TextField txtInStock;
    private ui.textfield.TextField txtLineTotal;
    private ui.textfield.TextField txtProductID;
    private ui.textfield.TextField txtProductName;
    private ui.textfield.TextField txtQuantity;
    private ui.textfield.TextField txtSellPrice;
    // End of variables declaration//GEN-END:variables
}
