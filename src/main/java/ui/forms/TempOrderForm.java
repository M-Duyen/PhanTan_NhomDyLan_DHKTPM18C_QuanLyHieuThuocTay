package ui.forms;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.itextpdf.barcodes.qrcode.WriterException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import dao.*;
import database.ConnectDB;
import entity.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import ui.button.Button;
import ui.cell.TableActionCellEditor;
import ui.cell.TableActionCellRender;
import ui.cell.TableActionEvent;
import ui.checkbox.JCheckBoxCustom;
import ui.dialog.Message;
import ui.dialog.ProductConfirm;
import ui.login.Login;
import ui.main.HomePage;
import ui.panel.PanelRound;
import ui.radio_button.RadioButtonCustom;
import ui.tabbed.TabbedForm;
import ui.table.EventCellInputChange;
import ui.table.QtyCellEditor;
import ui.table.TableCustom;
import ui.table.TableScrollButton;
import ui.textfield.TextField_Behind;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TempOrderForm extends TabbedForm {
    private HomePage homePage;
    private DefaultTableModel model;
    public TempOrderForm(){};
    public TempOrderForm(HomePage homePage) {
        this.homePage = homePage;
        initComponents();
        ConnectDB.getInstance().connect();
        OrderDetails_DAO orderDetails_dao = new OrderDetails_DAO();
        Order_DAO order_dao = new Order_DAO();
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                String productID = model.getValueAt(row, 0).toString();
                Product product = Product_DAO.getInstance().getProduct_ByID(productID);

                ProductConfirm productConfirm = new ProductConfirm(homePage, product, true);
                productConfirm.setSelectedComboboxUnit(model.getValueAt(row, 2).toString(), (Integer) model.getValueAt(row, 3));
                homePage.getCreateOrder().openProductConfirm(productConfirm, product, TempOrderForm.this, true);
            }

            @Override
            public void onDelete(int row) {
                if (tableProduct.isEditing()) {
                    tableProduct.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) tableProduct.getModel();
                model.removeRow(row);
                updatePanelNotice();
                updateTXTCustNeedPay();
                updateTXTChange();
                updatePointOrder();
            }

            @Override
            public void onView(int row) {
                //System.out.println("View row : " + row);
            }
        };

        JTableHeader theader = tableProduct.getTableHeader();
        theader.setFont(new Font("Segoe UI", 0, 18));

        setupTable();

        TableCustom.apply(scrollPane, TableCustom.TableType.MULTI_LINE);
        tableProduct.getColumnModel().getColumn(3).setCellEditor(new QtyCellEditor(new EventCellInputChange() {
            @Override
            public void inputChanged() {
                int selectedRow = tableProduct.getEditingRow();
                if (selectedRow >= 0) {
                    if (tableProduct.getCellEditor() != null) {
                        tableProduct.getCellEditor().stopCellEditing();
                    }

                    double quantity = 0;
                    double sellPrice = 0;
                    try {
                        quantity = nf.parse(model.getValueAt(selectedRow, 3).toString()).doubleValue();
                        sellPrice = nf.parse(model.getValueAt(selectedRow, 4).toString()).doubleValue();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    model.setValueAt(df.format(quantity * sellPrice), selectedRow, 5);
                    updatePanelNotice();
                    updateTXTCustNeedPay();
                    updateTXTChange();
                    updatePointOrder();
                }
            }
        }));

        tableProduct.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        tableProduct.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
        tableProduct.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            }
        });
    }

    private void setupTable() {
        JTableHeader theader = tableProduct.getTableHeader();
        theader.setFont(new Font("Segoe UI", 0, 18));
        tableProduct.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        if (tableProduct.getColumnModel().getColumnCount() > 0) {
            tableProduct.getColumnModel().getColumn(0).setPreferredWidth(200);
            tableProduct.getColumnModel().getColumn(1).setPreferredWidth(200);
            tableProduct.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableProduct.getColumnModel().getColumn(3).setPreferredWidth(100);
            tableProduct.getColumnModel().getColumn(4).setPreferredWidth(200);
            tableProduct.getColumnModel().getColumn(5).setPreferredWidth(200);
            tableProduct.getColumnModel().getColumn(6).setPreferredWidth(150);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        btnGroup_PaymentMethod = new ButtonGroup();
        panelRound2 = new PanelRound();
        pnProductOrder = new PanelRound();
        tableScrollButton_Product = new TableScrollButton();
        scrollPane = new JScrollPane();
        tableProduct = new JTable();
        pnOrderInfor = new PanelRound();
        txtTotalDue = new TextField_Behind();
        lbEmployeeName = new JLabel();
        lbTotalDue = new JLabel();
        lbOrderPoint = new JLabel();
        txtPointOrder = new TextField_Behind();
        txtCustPhone = new TextField_Behind();
        lbCustPhone = new JLabel();
        txtCustName = new TextField_Behind();
        lbCustName = new JLabel();
        lbCustPoint = new JLabel();
        txtPoint = new TextField_Behind();
        txtEmployeeName = new TextField_Behind();
        ckbTransPoint = new JCheckBoxCustom();
        lbCustNeededPay = new JLabel();
        txtNeededPay = new TextField_Behind();
        lbDiscountAmount = new JLabel();
        txtDiscount = new TextField_Behind();
        lbCustPay = new JLabel();
        txtCustPay = new TextField_Behind();
        rbCash = new RadioButtonCustom();
        rbCreditCard = new RadioButtonCustom();
        rbTransfer = new RadioButtonCustom();
        btnCheckOut = new Button();
        lblPaymentMethod = new JLabel();
        lblChange = new JLabel();
        txtChange = new TextField_Behind();
        ckbPres = new JCheckBoxCustom();
        pnNotes = new PanelRound();
        txtNote = new TextField_Behind();
        lbTotalDue_Left = new JLabel();
        txtTotalQuantity = new TextField_Behind();
        txtTotalDue_Left = new TextField_Behind();

        setBackground(new Color(242, 249, 255));
        setPreferredSize(new Dimension(1620, 900));
        setLayout(new BorderLayout());

        panelRound2.setBackground(new Color(242, 249, 255));
        panelRound2.setPreferredSize(new Dimension(1600, 880));
        panelRound2.setRoundBottomLeft(20);
        panelRound2.setRoundBottomRight(20);
        panelRound2.setRoundTopLeft(20);
        panelRound2.setRoundTopRight(20);
        panelRound2.setShadowSize(5);

        pnProductOrder.setBackground(new Color(255, 255, 255));
        pnProductOrder.setRoundBottomLeft(30);
        pnProductOrder.setRoundBottomRight(30);
        pnProductOrder.setRoundTopLeft(30);
        pnProductOrder.setRoundTopRight(30);
        pnProductOrder.setShadowColor(new Color(204, 204, 204));
        pnProductOrder.setShadowSize(5);

        tableProduct.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        tableProduct.setModel(model = new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Mã sản phẩm", "Tên sản phẩm", "Đơn vị", "Số lượng", "Giá bán", "Thành tiền", "Khác"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, true, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tableProduct.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        tableProduct.getTableHeader().setReorderingAllowed(false);
        scrollPane.setViewportView(tableProduct);

        tableScrollButton_Product.add(scrollPane, BorderLayout.CENTER);

        GroupLayout pnProductOrderLayout = new GroupLayout(pnProductOrder);
        pnProductOrder.setLayout(pnProductOrderLayout);
        pnProductOrderLayout.setHorizontalGroup(
                pnProductOrderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pnProductOrderLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(tableScrollButton_Product, GroupLayout.PREFERRED_SIZE, 786, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnProductOrderLayout.setVerticalGroup(
                pnProductOrderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pnProductOrderLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(tableScrollButton_Product, GroupLayout.PREFERRED_SIZE, 735, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnOrderInfor.setBackground(new Color(255, 255, 255));
        pnOrderInfor.setForeground(new Color(255, 255, 255));
        pnOrderInfor.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        pnOrderInfor.setRoundBottomLeft(30);
        pnOrderInfor.setRoundBottomRight(30);
        pnOrderInfor.setRoundTopLeft(30);
        pnOrderInfor.setRoundTopRight(30);
        pnOrderInfor.setShadowColor(new Color(204, 204, 204));
        pnOrderInfor.setShadowSize(5);

        txtTotalDue.setHorizontalAlignment(JTextField.RIGHT);
        txtTotalDue.setEnabled(false);
        txtTotalDue.setFont(new Font("Segoe UI", 0, 18)); // NOI18N

        lbEmployeeName.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        lbEmployeeName.setText("Người bán:");

        lbTotalDue.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        lbTotalDue.setText("Tổng tiền hàng:");

        lbOrderPoint.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        lbOrderPoint.setText("Tích điểm:");

        txtPointOrder.setHorizontalAlignment(JTextField.RIGHT);
        txtPointOrder.setEnabled(false);
        txtPointOrder.setFont(new Font("Segoe UI", 0, 18)); // NOI18N

        txtCustPhone.setHorizontalAlignment(JTextField.RIGHT);
        txtCustPhone.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        txtCustPhone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                txtCustPhoneActionPerformed(evt);
            }
        });
        txtCustPhone.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (txtCustPhone.getText().trim().isEmpty()) {
                    txtCustName.setText("");
                    txtPoint.setText("");
                }
            }
        });

        lbCustPhone.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        lbCustPhone.setText("Số điện thoại khách:");

        txtCustName.setEnabled(false);
        txtCustName.setFont(new Font("Segoe UI", 0, 18)); // NOI18N

        lbCustName.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        lbCustName.setText("Tên khách:");

        lbCustPoint.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        lbCustPoint.setText("Điểm tích lũy:");

        txtPoint.setHorizontalAlignment(JTextField.RIGHT);
        txtPoint.setEnabled(false);
        txtPoint.setFont(new Font("Segoe UI", 0, 18)); // NOI18N

        txtEmployeeName.setHorizontalAlignment(JTextField.RIGHT);
        txtEmployeeName.setEnabled(false);
        txtEmployeeName.setFont(new Font("Segoe UI", 0, 18)); // NOI18N

        String role = "";
        if (!Login.checkRole()) {
            Manager manager = Login.getManagerLogin();
            if (manager != null) {
                role = manager.getManagerName();
            }
        } else {
            Employee employee = Login.getEmployeeLogin();
            if (employee != null) {
                role = employee.getEmployeeName();
            }
        }
        txtEmployeeName.setText(role);

        ckbTransPoint.setBackground(new Color(102, 204, 255));
        ckbTransPoint.setText("Đổi điểm");
        ckbTransPoint.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        ckbTransPoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ckbTransPointActionPerformed(e);
            }
        });

        lbCustNeededPay.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        lbCustNeededPay.setText("Khách cần trả:");

        txtNeededPay.setHorizontalAlignment(JTextField.RIGHT);
        //txtNeededPay.setDisabledTextColor(new java.awt.Color(102, 204, 255));
        txtNeededPay.setEnabled(false);
        txtNeededPay.setFont(new Font("Segoe UI", 0, 18)); // NOI18N

        lbDiscountAmount.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        lbDiscountAmount.setText("Tiền giảm:");

        txtDiscount.setHorizontalAlignment(JTextField.RIGHT);
        txtDiscount.setEnabled(false);
        txtDiscount.setFont(new Font("Segoe UI", 0, 18)); // NOI18N

        lbCustPay.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        lbCustPay.setText("Khách thanh toán:");

        txtCustPay.setHorizontalAlignment(JTextField.RIGHT);
        txtCustPay.setEnabled(true);
        txtCustPay.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        txtCustPay.addKeyListener(new KeyListener() {
            private boolean isUpdating = false;

            @Override
            public void keyTyped(KeyEvent e) {
                if (checkTableProduct()) {
                    formatText();
                    updateTXTChange();
                } else {
                    JOptionPane.showMessageDialog(null, "Chưa thêm sản phẩm vào đơn hàng");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (checkTableProduct()) {
                    formatText();
                    updateTXTChange();
                } else {
                    JOptionPane.showMessageDialog(null, "Chưa thêm sản phẩm vào đơn hàng");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (checkTableProduct()) {
                    formatText();
                    updateTXTChange();
                } else {
                    JOptionPane.showMessageDialog(null, "Chưa thêm sản phẩm vào đơn hàng");
                }
            }

            private void formatText() {
                if (isUpdating) return;

                isUpdating = true;
                try {
                    String text = txtCustPay.getText().replace(",", "");

                    if (!text.isEmpty()) {
                        long value = Long.parseLong(text);
                        txtCustPay.setText(NumberFormat.getInstance().format(value));
                    }
                } catch (NumberFormatException ex) {
                } finally {
                    isUpdating = false;
                }
            }

            public boolean checkTableProduct() {
                if (model.getRowCount() == 0) {
                    return false;
                }
                return true;
            }
        });

        ActionListener radioButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbCash.isSelected()) {
                    lblChange.setVisible(true);
                    txtChange.setVisible(true);

                    revalidate();
                    repaint();
                } else {
                    lblChange.setVisible(false);
                    txtChange.setVisible(false);

                    revalidate();
                    repaint();
                }
            }
        };

        rbCash.setBackground(new Color(102, 204, 255));
        btnGroup_PaymentMethod.add(rbCash);
        rbCash.setText("Tiền mặt");
        rbCash.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        rbCash.addActionListener(radioButtonListener);

        rbCreditCard.setBackground(new Color(102, 204, 255));
        btnGroup_PaymentMethod.add(rbCreditCard);
        rbCreditCard.setText("Thẻ");
        rbCreditCard.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        rbCreditCard.addActionListener(radioButtonListener);

        rbTransfer.setBackground(new Color(102, 204, 255));
        btnGroup_PaymentMethod.add(rbTransfer);
        rbTransfer.setText("Chuyển khoản");
        rbTransfer.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        rbTransfer.addActionListener(radioButtonListener);

        btnCheckOut.setBackground(new Color(102, 204, 255));
        btnCheckOut.setForeground(new Color(255, 255, 255));
        btnCheckOut.setText("THANH TOÁN");
        btnCheckOut.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
        btnCheckOut.setRound(20);
        btnCheckOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCheckOutActionPerformed(evt);
            }
        });

        lblPaymentMethod.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        lblPaymentMethod.setText("Phương thức thanh toán:");

        lblChange.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        lblChange.setText("Tiền thừa:");
        lblChange.setVisible(false);

        txtChange.setEnabled(false);
        txtChange.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        txtChange.setVisible(false);

        ckbPres.setBackground(new Color(102, 204, 255));
        ckbPres.setText("Có đơn thuốc");
        ckbPres.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        ckbPres.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ckbPresActionPerformed(evt);
            }
        });

        GroupLayout pnOrderInforLayout = new GroupLayout(pnOrderInfor);
        pnOrderInfor.setLayout(pnOrderInforLayout);
        pnOrderInforLayout.setHorizontalGroup(
                pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pnOrderInforLayout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(rbCash, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(rbCreditCard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(rbTransfer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(329, Short.MAX_VALUE))
                        .addGroup(pnOrderInforLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(pnOrderInforLayout.createSequentialGroup()
                                                .addComponent(ckbPres, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(pnOrderInforLayout.createSequentialGroup()
                                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(pnOrderInforLayout.createSequentialGroup()
                                                                .addComponent(lblChange)
                                                                .addGap(97, 97, 97)
                                                                .addComponent(txtChange, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGroup(GroupLayout.Alignment.TRAILING, pnOrderInforLayout.createSequentialGroup()
                                                                .addGap(0, 490, Short.MAX_VALUE)
                                                                .addComponent(btnCheckOut, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(pnOrderInforLayout.createSequentialGroup()
                                                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lbTotalDue)
                                                                        .addComponent(lbOrderPoint))
                                                                .addGap(42, 42, 42)
                                                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(txtTotalDue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(txtPointOrder, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                        .addGroup(pnOrderInforLayout.createSequentialGroup()
                                                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(ckbTransPoint, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lblPaymentMethod, GroupLayout.Alignment.LEADING))
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addGroup(pnOrderInforLayout.createSequentialGroup()
                                                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lbEmployeeName, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lbCustPhone)
                                                                        .addComponent(lbCustName)
                                                                        .addComponent(lbCustNeededPay)
                                                                        .addComponent(lbDiscountAmount)
                                                                        .addComponent(lbCustPay))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(txtCustPay, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(txtDiscount, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(txtNeededPay, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(txtEmployeeName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addGroup(pnOrderInforLayout.createSequentialGroup()
                                                                                .addComponent(txtCustName, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(35, 35, 35)
                                                                                .addComponent(lbCustPoint)
                                                                                .addGap(26, 26, 26)
                                                                                .addComponent(txtPoint, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                        .addComponent(txtCustPhone, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                                .addGap(20, 20, 20))))
        );
        pnOrderInforLayout.setVerticalGroup(
                pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pnOrderInforLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(ckbPres, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(pnOrderInforLayout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(txtEmployeeName, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                                        .addComponent(lbEmployeeName, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtCustPhone, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbCustPhone, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtCustName, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbCustName, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbCustPoint, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPoint, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(lbTotalDue, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(pnOrderInforLayout.createSequentialGroup()
                                                .addComponent(txtTotalDue, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)))
                                .addGap(20, 20, 20)
                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtPointOrder, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbOrderPoint, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addComponent(ckbTransPoint, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbDiscountAmount, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDiscount, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbCustNeededPay, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNeededPay, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbCustPay, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtCustPay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addComponent(lblPaymentMethod)
                                .addGap(10, 10, 10)
                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(rbCash, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(rbCreditCard, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(rbTransfer, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(pnOrderInforLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblChange)
                                        .addComponent(txtChange, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCheckOut, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))
        );

        pnNotes.setBackground(new Color(255, 255, 255));
        pnNotes.setPreferredSize(new Dimension(840, 90));
        pnNotes.setRoundBottomLeft(30);
        pnNotes.setRoundBottomRight(30);
        pnNotes.setRoundTopLeft(30);
        pnNotes.setRoundTopRight(30);
        pnNotes.setShadowColor(new Color(204, 204, 204));

        txtNote.setForeground(new Color(102, 102, 102));
        txtNote.setText("Ghi chú đơn hàng ...");
        txtNote.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        txtNote.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                txtNoteFocusGained(evt);
            }

            public void focusLost(FocusEvent evt) {
                txtNoteFocusLost(evt);
            }
        });

        lbTotalDue_Left.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        lbTotalDue_Left.setText("Tổng tiền hàng: ");

        txtTotalQuantity.setEnabled(false);
        txtTotalQuantity.setFont(new Font("Segoe UI", 0, 18)); // NOI18N

        txtTotalDue_Left.setEnabled(false);
        txtTotalDue_Left.setFont(new Font("Segoe UI", 0, 18)); // NOI18N

        GroupLayout pnNotesLayout = new GroupLayout(pnNotes);
        pnNotes.setLayout(pnNotesLayout);
        pnNotesLayout.setHorizontalGroup(
                pnNotesLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pnNotesLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(txtNote, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66)
                                .addComponent(lbTotalDue_Left)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotalQuantity, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                                .addComponent(txtTotalDue_Left, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))
        );
        pnNotesLayout.setVerticalGroup(
                pnNotesLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pnNotesLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(pnNotesLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbTotalDue_Left)
                                        .addComponent(txtTotalQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtTotalDue_Left, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNote, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(14, Short.MAX_VALUE))
        );

        GroupLayout panelRound2Layout = new GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
                panelRound2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(panelRound2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(pnNotes, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 834, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pnProductOrder, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addComponent(pnOrderInfor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        panelRound2Layout.setVerticalGroup(
                panelRound2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelRound2Layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(panelRound2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(pnOrderInfor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panelRound2Layout.createSequentialGroup()
                                                .addComponent(pnProductOrder, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(pnNotes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(panelRound2, BorderLayout.CENTER);
    }// </editor-fold>

    private void ckbPresActionPerformed(ActionEvent evt) {
        if (ckbPres.isSelected()) {
            if (homePage != null) {
                homePage.showPres(this);
            }
        }
    }

    public void updateTXTChange() {
        lblChange.setVisible(true);
        txtChange.setVisible(true);
        rbCash.setSelected(true);

        double change = 0, custPay = 0, custNeedPay = 0;
        try {
            String custPayTxt = txtCustPay.getText().trim();
            if (!custPayTxt.isEmpty()) {
                custPay = nf.parse(custPayTxt).doubleValue();
                custNeedPay = nf.parse(txtNeededPay.getText().trim()).doubleValue();
                change = custPay - custNeedPay;
                txtChange.setText(String.valueOf(df.format(change)));
            }
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    //1 điểm = 10 đồng/ 1000 đồng = 1đ -> 1680865.42 / 1000
    private void ckbTransPointActionPerformed(ActionEvent evt) {
        if (!txtCustName.getText().trim().isEmpty()) {
            double discount = 0;
            double totalDue = 0;
            String pointText = null;

            try {
                pointText = txtPoint.getText().trim();
                String totalDueText = txtTotalDue.getText().trim();

                if (!pointText.isEmpty()) {
                    discount = nf.parse(pointText).doubleValue() * 10;
                }
                if (!totalDueText.isEmpty()) {
                    totalDue = nf.parse(totalDueText).doubleValue();
                }

                if (ckbTransPoint.isSelected()) {
                    if (totalDue >= discount) {
                        txtDiscount.setText(df.format(discount));
                        txtNeededPay.setText(df.format(totalDue - discount));
                        updateTXTChange();
                    } else if (totalDue < discount) {
                        txtDiscount.setText(df.format(totalDue));
                        txtNeededPay.setText(df.format(0));
                        updateTXTChange();
                    }
                    updatePointOrder();
                } else {
                    txtDiscount.setText("");
                    txtNeededPay.setText(df.format(totalDue));
                    updatePointOrder();
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Message(homePage, true, "Thông báo", "Chưa nhập số điện thoại khách hàng", "src/main/java/ui/dialog/warning.png").showAlert();
            ckbTransPoint.setSelected(false);
            txtCustPhone.requestFocus();
            updateTXTChange();
        }
    }

    private void txtCustPhoneActionPerformed(ActionEvent evt) {//GEN-FIRST:event_txtCustPhoneActionPerformed
        String phone = txtCustPhone.getText().trim();
        if (!phone.isEmpty()) {
            Customer customer = new Customer_DAO().getCustomer_ByPhone(phone);
            if (customer != null) {
                txtCustName.setText(customer.getCustomerName());
                txtPoint.setText(String.valueOf(df_point.format(customer.getPoint())));
            } else {
                new Message(homePage, true, "Thông báo", "Không tìm thấy khách hàng", "src/main/java/ui/dialog/warning.png").showAlert();
            }
        }
    }//GEN-LAST:event_txtCustPhoneActionPerformed

    private ArrayList<String> getProductIDList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            list.add((String) model.getValueAt(i, 0));
        }
        return list;
    }

    private ArrayList<Integer> getQuantityList() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            list.add((Integer) model.getValueAt(i, 3));
        }
        return list;
    }

    private ArrayList<String> getUnitList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            list.add((String) model.getValueAt(i, 2));
        }
        return list;
    }

    private void btnCheckOutActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCheckOutActionPerformed
        ArrayList<String> productIDList = getProductIDList();
        ArrayList<Integer> quantityList = getQuantityList();
        ArrayList<String> unitNameList = getUnitList();

        if (productIDList.isEmpty()) {
            new Message(homePage, true, "Thông báo", "Chưa thêm sản phẩm vào hóa đơn!", "src/main/java/ui/dialog/warning.png").showAlert();
            return;
        }

        if (txtCustPay.getText().trim().isEmpty() && rbCash.isSelected()) {
            new Message(homePage, true, "Thông báo", "Chưa nhập tiền khách trả!", "src/main/java/ui/dialog/warning.png").showAlert();
            return;
        }

        String orderID = new Order_DAO().createOrderID(Login.getEmployeeLogin().getEmployeeID());
        LocalDateTime orderDate = LocalDateTime.now();
        String shipToAddress = null;

        Enum_PaymentMethod pm = null;
        if (rbCash.isSelected()) {
            pm = Enum_PaymentMethod.CASH;
        } else if (rbTransfer.isSelected()) {
            pm = Enum_PaymentMethod.BANK_TRANSFER;
        } else if (rbCreditCard.isSelected()) {
            pm = Enum_PaymentMethod.CREDIT_CARD;
        } else {
            new Message(homePage, true, "Thông báo", "Chưa chọn phương thức thanh toán!", "src/main/java/ui/dialog/warning.png").showAlert();
        }

        double discount = 0;
        try {
            if (!txtDiscount.getText().trim().isEmpty()) {
                discount = nf.parse(txtDiscount.getText().trim()).intValue();
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Customer customer = null;
        if (!txtCustPhone.getText().trim().isEmpty()) {
            customer = new Customer_DAO().getCustomer_ByPhone(txtCustPhone.getText().trim());
        }

        Employee employee = new Login().getEmployeeLogin();

        Prescription prescription = this.prescription;

        Order order = new Order(orderID, orderDate, shipToAddress, pm, discount, employee, customer, prescription);
        Connection con = null;
        try {
            con = ConnectDB.getConnection();
            con.setAutoCommit(false);

            double point = 0;
            //Update Customer Point nếu có check ckbTransPoint
            if (ckbTransPoint.isSelected()) {
                String pointText = txtPoint.getText().trim();
                String totalDueText = txtTotalDue.getText().trim();
                try {
                    double discount2 = nf.parse(pointText).doubleValue() * 10;
                    double totalDue = nf.parse(totalDueText).doubleValue();
                    if (totalDue < discount2) {
                        point = (discount2 - totalDue) / 10;
                    } else {
                        point = nf.parse(pointText).doubleValue();
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                if (customer != null) {
                    if (!new Customer_DAO().updateCustPoint_Decrease(customer.getPhoneNumber(), point, con)) {
                        con.rollback();
                        new Message(homePage, true, "Thông báo", "Cập nhật giảm điểm tích lũy của khách hàng thất bại!", "src/main/java/ui/dialog/warning.png").showAlert();
                        return;
                    }
                }
            } else {
                try {
                    point = nf.parse(txtPointOrder.getText().trim()).doubleValue();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

            //Add Orders
            if (!new Order_DAO().addOrder(order, con)) {
                con.rollback();
                new Message(homePage, true, "Thông báo", "Thêm đơn hàng thất bại!", "src/main/java/ui/dialog/warning.png").showAlert();
                return;
            }

            //Add OrderDetails
            for (int i = 0; i < productIDList.size(); i++) {
                Product product = new Product_DAO().getProduct_ByID(productIDList.get(i));

                int quantity = quantityList.get(i);
                String unitName = unitNameList.get(i);
                Enum_PackagingUnit unit = Enum_PackagingUnit.fromString(new Unit_DAO().convertDes_ToUnit(unitName));

                OrderDetails od = new OrderDetails(quantity, order, product, unit);
                if (!new OrderDetails_DAO().addOrderDetails(od, con)) {
                    con.rollback();
                    new Message(homePage, true, "Thông báo", "Thêm chi tiết đơn hàng thất bại " + i + " !", "src/main/java/ui/dialog/warning.png").showAlert();
                    return;
                }
                if (!new Product_DAO().updateProductInStock_WithTransaction(product.getProductID(), quantity, unit, false, con)) {
                    con.rollback();
                    new Message(homePage, true, "Thông báo", "Cập nhật số lượng tồn kho thất bại!", "src/main/java/ui/dialog/warning.png").showAlert();
                    return;
                }
            }

            if (customer != null) {
                if (!new Customer_DAO().updateCustPoint_Increase(customer.getPhoneNumber(), point, con)) {
                    new Message(homePage, true, "Thông báo", "Cập nhật tăng điểm tích lũy của khách hàng thất bại!", "src/main/java/ui/dialog/warning.png").showAlert();
                    con.rollback();
                    return;
                }
            }

            con.commit();
            new Message(homePage, true, "Xác nhận", "Thêm đơn hàng thành công!", "src/main/java/ui/dialog/checked.png").showAlert();
            try {
                invoiceOrder(order);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            clearAll();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        }
//        finally {
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException closeEx) {
//                    closeEx.printStackTrace();
//                }
//            }
//        }
    }//GEN-LAST:event_btnCheckOutActionPerformed

    private void txtNoteFocusGained(FocusEvent evt) {//GEN-FIRST:event_txtNoteFocusGained
        if (txtNote.getText().equals("Ghi chú đơn hàng ...")) {
            txtNote.setText("");
        }
    }//GEN-LAST:event_txtNoteFocusGained

    private void txtNoteFocusLost(FocusEvent evt) {//GEN-FIRST:event_txtNoteFocusLost
        if (txtNote.getText().equals("")) {
            txtNote.setText("Ghi chú đơn hàng ...");
        }
    }//GEN-LAST:event_txtNoteFocusLost

    public void addProductRow(Object[] rowData) {
        model.addRow(rowData);
    }

    public void clearAll() {
        model.setRowCount(0);
        txtCustPhone.setText("");
        txtCustName.setText("");
        txtPoint.setText("");
        txtTotalDue.setText("");
        txtPointOrder.setText("");
        txtDiscount.setText("");
        txtNeededPay.setText("");
        txtCustPay.setText("");
        rbCash.setSelected(false);
        rbTransfer.setSelected(false);
        rbCreditCard.setSelected(false);
        lblChange.setVisible(false);
        txtChange.setText("");
        txtChange.setVisible(false);
        ckbTransPoint.setSelected(false);
        txtTotalQuantity.setText("");
        txtTotalDue_Left.setText("");
        txtNote.setText("Ghi chú đơn hàng ...");
    }

    public int findRowByProductID(String productID, Enum_PackagingUnit unitEnum) {
        for (int i = 0; i < model.getRowCount(); i++) {
            String id = (String) model.getValueAt(i, 0);
            String unit = (String) model.getValueAt(i, 2);
            Enum_PackagingUnit unitE = Enum_PackagingUnit.fromString(Unit_DAO.getInstance().getUnit_ByDes(unit).getUnitName());

            if (id.equals(productID) && unitE.equals(unitEnum)) {
                return i;
            }
        }
        return -1;
    }

    public int getQuantityProductInTable(String productID) {
        for (int i = 0; i < model.getRowCount(); i++) {
            String id = (String) model.getValueAt(i, 0);

            if (id.equals(productID)) {
                return (Integer) model.getValueAt(i, 3);
            }
        }
        return -1;
    }

    DecimalFormat df = new DecimalFormat("#,##0.00 VND");
    DecimalFormat df_point = new DecimalFormat("#,##0.00");

    public void updateProductRow(int row, int column, int quantity, double sellPrice) {
        model.setValueAt(quantity, row, column);
        model.setValueAt(df.format(quantity * sellPrice), row, column + 2);
    }

    NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());

    public void updatePanelNotice() {
        int totalQuantity = 0;
        double totalDue = 0;
        double point = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            totalQuantity += (int) model.getValueAt(i, 3);
            try {
                totalDue += nf.parse((String) model.getValueAt(i, 5)).doubleValue();
                point += totalDue / 1000;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        txtTotalQuantity.setText(String.valueOf(totalQuantity));
        txtTotalDue_Left.setText(df.format(totalDue));
        txtTotalDue.setText(df.format(totalDue));
    }

    public void updatePointOrder() {
        double totalDue = 0;
        try {
            totalDue = nf.parse(txtNeededPay.getText().trim()).doubleValue();
            txtPointOrder.setText(df_point.format(totalDue / 1000));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void invoiceOrder(Order order) throws IOException {
        OrderDetails_DAO orderDetails_dao = new OrderDetails_DAO();
        Order_DAO order_dao = new Order_DAO();
        String path = "invoice.pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A6);
        Document document = new Document(pdfDocument);
        PdfFont font = PdfFontFactory.createFont("src/main/font/arial.ttf", "Identity-H");

        float fullWidth[] = {300f};
        float[] columnWidths = {35f, 10f, 200f};
        float[] header = {20f, 150f, 30f, 20f, 50f, 50f};
        float[] footer = {270f, 50f};


//        Paragraph onesp = new Paragraph("\n");

        Table table = new Table(fullWidth);
        table.addCell(getCell10Center("NHÀ THUỐC DNGH", true).setFont(font).setFontSize(10f));
        table.addCell(getCell10Center("12 NVB, Q. Gò Vấp, TP HCM", false).setFont(font));
//        table.addCell(getCell10Center("----------------------",false).setFont(font));
        table.addCell(getCell10Center("HÓA ĐƠN BÁN HÀNG", true).setFontSize(8f).setFont(font));

        Border gb = new SolidBorder(com.itextpdf.kernel.color.Color.BLACK, 1f / 50);
        Table divider = new Table(fullWidth);
        divider.setBorder(gb);

        ArrayList<Order> list = order_dao.getOrderByCriterious(order.getOrderID());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Chuyển đổi LocalDateTime thành String

        String formattedDateTime = order.getOrderDate().format(formatter);


        Table tableInformation = new Table(columnWidths);
        tableInformation.addCell(getCell10Left("Thời gian", false).setFont(font));
        tableInformation.addCell(getCell10Center(":", false));
        tableInformation.addCell(getCell10Left(formattedDateTime, false));

        tableInformation.addCell(getCell10Left("Mã HD", false).setFont(font));
        tableInformation.addCell(getCell10Center(":", false));
        tableInformation.addCell(getCell10Left(order.getOrderID(), false).setFont(font));

        tableInformation.addCell(getCell10Left("Nhân viên", false).setFont(font));
        tableInformation.addCell(getCell10Center(":", false));
        tableInformation.addCell(getCell10Left(order.getEmployee().getEmployeeName(), false).setFont(font));

        String cusName = " ";
        if (order.getCustomer() == null) {
            cusName = "Khách vãng lai";
        } else {
            if (order.getCustomer().getCustomerName() == null) {
                cusName = "Khách vãng lai";
            } else {
                cusName = order.getCustomer().getCustomerName();
            }
        }

        tableInformation.addCell(getCell10Left("Khách hàng",false).setFont(font));
        tableInformation.addCell(getCell10Center(":",false));
        tableInformation.addCell(getCell10Left(cusName,false).setFont(font));

        Table tableHeader = new Table(header);
        tableHeader.addCell(getCell10Center("STT", true).setFont(font).setBorderBottom(new SolidBorder(0)).setBorderTop(new SolidBorder(0)));
        tableHeader.addCell(getCell10Left("Tên sản phẩm", true).setFont(font).setBorderBottom(new SolidBorder(0)).setBorderTop(new SolidBorder(0)));
        tableHeader.addCell(getCell10Right("Đơn vị", true).setFont(font).setBorderBottom(new SolidBorder(0)).setBorderTop(new SolidBorder(0)));
        tableHeader.addCell(getCell10Center("SL", true).setFont(font).setBorderBottom(new SolidBorder(0)).setBorderTop(new SolidBorder(0)));
        tableHeader.addCell(getCell10Right("Giá bán", true).setFont(font).setBorderBottom(new SolidBorder(0)).setBorderTop(new SolidBorder(0)));
        tableHeader.addCell(getCell10Right("Thành tiền", true).setFont(font).setBorderBottom(new SolidBorder(0)).setBorderTop(new SolidBorder(0)));

        ArrayList<OrderDetails> listOD = orderDetails_dao.getOrderDetailListByScri(order.getOrderID());
        Map<String, Double> productPrices = new OrderDetails_DAO().getUnitPricesByOrderID(order.getOrderID());
        List<String> keys = new ArrayList<>(productPrices.keySet());

        for (int i = 0; i <= listOD.size() - 1; i++) {
            OrderDetails detail = listOD.get(i);

            tableHeader.addCell(getCell10Center(String.valueOf(i + 1), false).setFont(font)); // In ra số thứ tự, bắt đầu từ 1
            tableHeader.addCell(getCell10Left(detail.getProduct().getProductName(), false).setFont(font));
            // Lấy phần tử đầu tiên trong Map
//            String unit = productPrices.keySet().iterator().next();
//            Double unitPrice = productPrices.get(unit);
            String unit = keys.get(i);
            Double unitPrice = productPrices.get(unit);
            tableHeader.addCell(getCell10Center(orderDetails_dao.createUnit(unit).getDescription(), false).setFont(font));

            tableHeader.addCell(getCell10Center(String.valueOf(detail.getOrderQuantity()), false).setFont(font));
            tableHeader.addCell(getCell10Right(String.valueOf(unitPrice), false).setFont(font));
            tableHeader.addCell(getCell10Right(String.valueOf(detail.getOrderQuantity() * unitPrice), false).setFont(font));
        }

        Table tableFooter = new Table(footer);
        tableFooter.addCell(getCell10Left("Tổng tiền (Đã bao gồm thuế)", false).setFont(font).setBorderTop(new SolidBorder(0)));
        tableFooter.addCell(getCell10Right(StaticProcess.df.format(order.getTotalDue()), false).setFont(font).setBorderTop(new SolidBorder(0)));

        tableFooter.addCell(getCell10Left("Giảm giá", false).setFont(font));
        tableFooter.addCell(getCell10Right(StaticProcess.df.format(order.getDiscount()), false).setFont(font));

        tableFooter.addCell(getCell10Left("Tổng thanh toán", true).setFont(font).setBorderTop(new SolidBorder(0)));
        tableFooter.addCell(getCell10Right(StaticProcess.df.format(order.getTotalDue() - order.getDiscount()), false).setFont(font).setBorderTop(new SolidBorder(0)));

        tableFooter.addCell(getCell10Left("Phương thức thanh toán ", false).setFont(font));
        tableFooter.addCell(getCell10Right(String.valueOf(order.getPaymentMethod()), false));

        Table tableEnd = new Table(fullWidth);
        tableEnd.addCell(getCell10Center("*Lưu ý: Sản phẩm có thể đổi trả trong 30 ngày !!!",true).setFont(font));
        tableEnd.addCell(getCell10Center("Cảm ơn quý khách",true).setFont(font));

        document.add(table);
        document.add(tableInformation);

        document.add(tableHeader);
        document.add(tableFooter);
        document.add(tableEnd);

        String barcodePath = "src/main/java/ui/barcodeprocess/temp.png";

        try {
            BarcodeGenerator.generateBarcode(order.getOrderID());
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        try {
            ImageData barcodeData = ImageDataFactory.create(barcodePath);
            com.itextpdf.layout.element.Image barcodeImage = new com.itextpdf.layout.element.Image(barcodeData);
            barcodeImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
            barcodeImage.setAutoScale(true);
            document.add(barcodeImage);
        } catch (IOException e) {
            throw new RuntimeException("Không thể chèn mã vạch: " + e.getMessage());
        }
        document.close();
        printPDF(path);
    }

    static Cell getHeaderTextCell(String textValue) {
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }

    static Cell getHeaderTextCellValue(String textValue) {
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getBillingandShippingCell(String textValue) {
        return new Cell().add(textValue).setFontSize(5f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getCell10Left(String textValue, Boolean isBold) {
        Cell myCell = new Cell().add(textValue).setFontSize(5f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold ? myCell.setBold() : myCell;
    }

    static Cell getCell10Right(String textValue, Boolean isBold) {
        Cell myCell = new Cell().add(textValue).setFontSize(5f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
        return isBold ? myCell.setBold() : myCell;
    }

    static Cell getCell10Center(String textValue, Boolean isBold) {
        Cell myCell = new Cell().add(textValue)
                .setFontSize(5f)
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER); // Căn giữa
        return isBold ? myCell.setBold() : myCell;
    }


    /**
     * Hiển thị hộp thoại trước khi in
     *
     * @param path
     */
    public static void printPDF(String path) {
        try {
            // Đường dẫn đến file PDF
            File pdfFile = new File(path);

            // Mở tài liệu PDF
            PDDocument document = PDDocument.load(pdfFile);

            // Tìm máy in mặc định
            PrinterJob job = PrinterJob.getPrinterJob();
            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();

            if (printService != null) {
                job.setPrintService(printService);

                // Gán nội dung PDF vào công việc in
                job.setPageable(new PDFPageable(document));

                // Hiển thị hộp thoại in
                if (job.printDialog()) {
                    job.print();
                }
            } else {
                System.out.println("Không tìm thấy máy in mặc định.");
            }

            // Đóng tài liệu PDF
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTXTCustNeedPay() {
        double discount = 0;
        double totalDue = 0;
        try {
            String discountText = txtDiscount.getText().trim();

            if (!discountText.isEmpty()) {
                discount = nf.parse(discountText).doubleValue();
                totalDue = nf.parse(txtTotalDue.getText().trim()).doubleValue();

                if (discount != 0) {
                    txtNeededPay.setText(df.format(totalDue - discount));
                } else {
                    txtNeededPay.setText(df.format(totalDue));
                }
            } else {
                txtNeededPay.setText(df.format(nf.parse(txtTotalDue.getText().trim()).doubleValue()));
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private Prescription prescription;

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public void setCkbPres() {
        ckbPres.setSelected(prescription != null);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Button btnCheckOut;
    private ButtonGroup btnGroup_PaymentMethod;
    private JCheckBoxCustom ckbPres;
    private JCheckBoxCustom ckbTransPoint;
    private JLabel lbCustName;
    private JLabel lbCustNeededPay;
    private JLabel lbCustPay;
    private JLabel lbCustPhone;
    private JLabel lbCustPoint;
    private JLabel lbDiscountAmount;
    private JLabel lbEmployeeName;
    private JLabel lbOrderPoint;
    private JLabel lbTotalDue;
    private JLabel lbTotalDue_Left;
    private JLabel lblChange;
    private JLabel lblPaymentMethod;
    private PanelRound panelRound2;
    private PanelRound pnNotes;
    private PanelRound pnOrderInfor;
    private PanelRound pnProductOrder;
    private RadioButtonCustom rbCash;
    private RadioButtonCustom rbCreditCard;
    private RadioButtonCustom rbTransfer;
    private JScrollPane scrollPane;
    private JTable tableProduct;
    private TableScrollButton tableScrollButton_Product;
    private TextField_Behind txtChange;
    private TextField_Behind txtCustName;
    private TextField_Behind txtCustPay;
    private TextField_Behind txtCustPhone;
    private TextField_Behind txtDiscount;
    private TextField_Behind txtEmployeeName;
    private TextField_Behind txtNeededPay;
    private TextField_Behind txtNote;
    private TextField_Behind txtPoint;
    private TextField_Behind txtPointOrder;
    private TextField_Behind txtTotalDue;
    private TextField_Behind txtTotalDue_Left;
    private TextField_Behind txtTotalQuantity;
    // End of variables declaration//GEN-END:variables
//    public static void main(String[] args) throws IOException {
//        ConnectDB.getInstance().connect();
//        Order_DAO order_dao = new Order_DAO();
//        invoiceOrder(order_dao.getOrderByCriterious("OR1112240302001").getFirst());
//    }
}
