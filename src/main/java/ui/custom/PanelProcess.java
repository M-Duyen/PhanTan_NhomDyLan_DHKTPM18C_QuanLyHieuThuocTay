package ui.custom;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class PanelProcess extends JPanel {

	private static final long serialVersionUID = 1L;
	public ui.textfield.TextField_Behind txtDThem;
	public JTable tblThemSP;
	public JPanel pnlHoanTien = new JPanel();
	public JPanel pnlDoiSanPham = new JPanel();
	public JScrollPane cpThemSP = new JScrollPane();
	public ui.checkbox.JCheckBoxCustom chkTXacNhan;
	public ui.button.Button btnDThem, btnD, btnT;
	public JLabel lbltxtTS_SPC, lbltxtTTongTien, lbltxtTPhiHoanTien, lbltxtTTongHoanTien, lblDTienBD, lblDTienQuyDoi, lbltxtChiPhiThem, lbltxtTienHoanThem;

	public PanelProcess() {
		setLayout(null);
		setSize(730, 600);
		setBackground(new Color(255, 255, 255));
		pnlHoanTien.setBackground(new Color(255, 255, 255));
		pnlHoanTien.setBounds(0, 0, 730, 600);
		add(pnlHoanTien);
		pnlHoanTien.setLayout(null);
		
		JLabel lblLuuY = new JLabel("Lưu ý: Trường hợp hoàn tiền chỉ chấp nhận trong trường hợp giao nhầm thuốc");
		lblLuuY.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblLuuY.setBounds(10, 11, 685, 27);
		pnlHoanTien.add(lblLuuY);
		
		JLabel lblNewLabel_1 = new JLabel("Số sản phẩm đã chọn:");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(39, 87, 286, 27);
		pnlHoanTien.add(lblNewLabel_1);
		
		lbltxtTS_SPC = new JLabel("---");
		lbltxtTS_SPC.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lbltxtTS_SPC.setBounds(359, 87, 62, 27);
		pnlHoanTien.add(lbltxtTS_SPC);
		
		lbltxtTTongTien = new JLabel("---");
		lbltxtTTongTien.setForeground(new Color(255, 0, 0));
		lbltxtTTongTien.setFont(new Font("Segoe UI", Font.ITALIC, 22));
		lbltxtTTongTien.setBounds(359, 174, 347, 27);
		pnlHoanTien.add(lbltxtTTongTien);
		
		JLabel lblNewLabel_1_2 = new JLabel("Tổng tiền sản phẩm:");
		lblNewLabel_1_2.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_2.setBounds(39, 174, 286, 27);
		pnlHoanTien.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Phí hoàn tiền:");
		lblNewLabel_1_2_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_2_1.setBounds(39, 255, 286, 27);
		pnlHoanTien.add(lblNewLabel_1_2_1);
		
		lbltxtTPhiHoanTien = new JLabel("---");
		lbltxtTPhiHoanTien.setForeground(Color.RED);
		lbltxtTPhiHoanTien.setFont(new Font("Segoe UI", Font.ITALIC, 22));
		lbltxtTPhiHoanTien.setBounds(359, 255, 347, 27);
		pnlHoanTien.add(lbltxtTPhiHoanTien);
		
		JLabel lblNewLabel_1_2_2 = new JLabel("Tổng tiền hoàn:");
		lblNewLabel_1_2_2.setBackground(new Color(255, 255, 255));
		lblNewLabel_1_2_2.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_2_2.setBounds(39, 344, 286, 27);
		pnlHoanTien.add(lblNewLabel_1_2_2);
		
		lbltxtTTongHoanTien = new JLabel("---");
		lbltxtTTongHoanTien.setForeground(Color.RED);
		lbltxtTTongHoanTien.setFont(new Font("Segoe UI", Font.ITALIC, 26));
		lbltxtTTongHoanTien.setBounds(359, 342, 347, 27);
		pnlHoanTien.add(lbltxtTTongHoanTien);
		
		btnT = new ui.button.Button("Xác nhận hoàn");
		btnT.setBackground(new Color(102, 204, 255));
		btnT.setForeground(new Color(255, 255, 255));
		btnT.setShadowColor(new java.awt.Color(102, 204, 255));
		btnT.setFont(new Font("Segoe UI", 1, 18));
		btnT.setBounds(473, 525, 218, 52);
		btnT.setBorderPainted(false);
		pnlHoanTien.add(btnT);
		
		chkTXacNhan = new ui.checkbox.JCheckBoxCustom();
		chkTXacNhan.setBackground(new Color(102, 204, 255));
		chkTXacNhan.setText("Xác nhận đây là hóa đơn hoàn với lý do giao nhầm thuốc cho khách hàng.");
		chkTXacNhan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		chkTXacNhan.setBounds(39, 456, 685, 35);
		pnlHoanTien.add(chkTXacNhan);
		
		pnlDoiSanPham.setBackground(new Color(255, 255, 255));
		pnlDoiSanPham.setBounds(0, -20, 730, 600);
		add(pnlDoiSanPham);
		pnlDoiSanPham.setLayout(null);
		
		JLabel lblNewLabel_1_3 = new JLabel("Nhập hoặc quét mã sản phẩm quy đổi:");
		lblNewLabel_1_3.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_3.setBounds(10, 32, 363, 27);
		pnlDoiSanPham.add(lblNewLabel_1_3);
		
		txtDThem = new ui.textfield.TextField_Behind();
		txtDThem.setBounds(37, 70, 291, 31);
		pnlDoiSanPham.add(txtDThem);
		txtDThem.setColumns(10);
		
		btnDThem = new ui.button.Button();
		btnDThem.setBackground(new java.awt.Color(102, 204, 255));
		btnDThem.setIcon(new ImageIcon("src/main/java/ui/button/add.png"));
		btnDThem.setMargin(new Insets(5, 5, 5, 5));
		btnDThem = new ui.button.Button("");
		btnDThem.setIcon(new ImageIcon("src/main/java/ui/custom/icons8-plus-32.png"));
		btnDThem.setMargin(new Insets(5, 5, 5, 5));
		btnDThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDThem.setBounds(370, 60, 50, 50);
		pnlDoiSanPham.add(btnDThem);
		
		cpThemSP.setBounds(10, 112, 710, 229);
		pnlDoiSanPham.add(cpThemSP);
		
		tblThemSP = new JTable();
		tblThemSP.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tblThemSP.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"STT", "M\u00E3 s\u1EA3n ph\u1EA9m", "T\u00EAn s\u1EA3n ph\u1EA9m", "S\u1ED1 l\u01B0\u1EE3ng", "Gi\u00E1 b\u00E1n", "Th\u00E0nh ti\u1EC1n"
			}
		));
		tblThemSP.getColumnModel().getColumn(0).setPreferredWidth(15);
		tblThemSP.getColumnModel().getColumn(2).setPreferredWidth(88);
		cpThemSP.setViewportView(tblThemSP);
		
		JLabel lblNewLabel_1_2_3 = new JLabel("Tổng tiền sản phẩm ban đầu:");
		lblNewLabel_1_2_3.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_2_3.setBounds(20, 362, 286, 27);
		pnlDoiSanPham.add(lblNewLabel_1_2_3);
		
		lblDTienBD = new JLabel("---");
		lblDTienBD.setBackground(new Color(255, 0, 0));
		lblDTienBD.setForeground(Color.RED);
		lblDTienBD.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		lblDTienBD.setBounds(397, 362, 296, 27);
		pnlDoiSanPham.add(lblDTienBD);
		
		JLabel lblNewLabel_1_2_3_1 = new JLabel("Tổng tiền sản phẩm quy đổi:");
		lblNewLabel_1_2_3_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_2_3_1.setBounds(20, 409, 286, 27);
		pnlDoiSanPham.add(lblNewLabel_1_2_3_1);
		
		lblDTienQuyDoi = new JLabel("---");
		lblDTienQuyDoi.setBackground(new Color(255, 0, 0));
		lblDTienQuyDoi.setForeground(Color.RED);
		lblDTienQuyDoi.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		lblDTienQuyDoi.setBounds(397, 409, 309, 27);
		pnlDoiSanPham.add(lblDTienQuyDoi);
		
		JLabel lblNewLabel_1_2_3_1_1 = new JLabel("Chi phí thu thêm (bao gồm 10% thuế):");
		lblNewLabel_1_2_3_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_2_3_1_1.setBounds(20, 461, 340, 27);
		pnlDoiSanPham.add(lblNewLabel_1_2_3_1_1);
		
		lbltxtChiPhiThem = new JLabel("---");
		lbltxtChiPhiThem.setBackground(new Color(255, 0, 0));
		lbltxtChiPhiThem.setForeground(Color.RED);
		lbltxtChiPhiThem.setFont(new Font("Segoe UI", Font.ITALIC, 22));
		lbltxtChiPhiThem.setBounds(397, 461, 296, 27);
		pnlDoiSanPham.add(lbltxtChiPhiThem);
		
		JLabel lblNewLabel_2 = new JLabel("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		lblNewLabel_2.setBounds(10, 348, 710, 14);
		pnlDoiSanPham.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1_2_3_1_1_1 = new JLabel("Tiền hoàn thêm (nếu có): ");
		lblNewLabel_1_2_3_1_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_2_3_1_1_1.setBounds(20, 512, 340, 27);
		pnlDoiSanPham.add(lblNewLabel_1_2_3_1_1_1);
		
		lbltxtTienHoanThem = new JLabel("---");
		lbltxtTienHoanThem.setBackground(new Color(255, 0, 0));
		lbltxtTienHoanThem.setForeground(Color.RED);
		lbltxtTienHoanThem.setFont(new Font("Segoe UI", Font.ITALIC, 22));
		lbltxtTienHoanThem.setBounds(397, 512, 296, 27);
		pnlDoiSanPham.add(lbltxtTienHoanThem);
		
		btnD = new ui.button.Button("Xác nhận thanh toán");
		btnD.setForeground(new Color(255, 255, 255));
		btnD.setBackground(new Color(102, 204, 255));
		btnD.setShadowColor(new java.awt.Color(102, 204, 255));
		btnD.setFont(new Font("Segoe UI", 1, 18));
//		btnD.setBounds(476, 536, 244, 40);
		btnD.setBounds(473, 545, 218, 52);
		pnlDoiSanPham.add(btnD);
	}
//        public static void main(String[] args) {
//        new PanelProcess();
//    }
}
