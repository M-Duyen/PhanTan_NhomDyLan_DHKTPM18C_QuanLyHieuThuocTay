package ui.custom;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import com.toedter.components.JSpinField;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import java.time.LocalDate;

public class PanelBarStatistical extends JPanel {

	private static final long serialVersionUID = 1L;
	public ui.textfield.TextField txtStart;
	public ui.textfield.TextField txtEnd;
	public ui.button.Button btnSelectStart, btnSelectEnd;
	public ui.spinner.Spinner ycT, ycNam;
	public JPanel pnlThongKeTheoThang, pnlThongKeTuyChinh, pnlThongKeNam;
	public ui.combobox.Combobox cboT;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("deprecation")
	public PanelBarStatistical() {
		setBackground(new Color(255, 255, 255));
		setBounds(100, 100, 1730, 82);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);	
		pnlThongKeTheoThang = new JPanel();
		pnlThongKeTheoThang.setBackground(new Color(255, 255, 255));
		pnlThongKeTheoThang.setBounds(0, 0, 470, 66);
		add(pnlThongKeTheoThang);
		pnlThongKeTheoThang.setLayout(null);
		
		JLabel lblChnThngThng = new JLabel("Chọn tháng  thống kê:");
		lblChnThngThng.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblChnThngThng.setBounds(10, 0, 193, 25);
		pnlThongKeTheoThang.add(lblChnThngThng);
		
		JLabel lblNam_1 = new JLabel("Chọn năm thống kê:");
		lblNam_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNam_1.setBounds(253, 0, 193, 25);
		pnlThongKeTheoThang.add(lblNam_1);

		
		cboT = new ui.combobox.Combobox();
		cboT.setLabeText("");
		cboT.setModel(new DefaultComboBoxModel(new String[] {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"}));
		cboT.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		cboT.setBounds(32, 24, 171, 50);
		pnlThongKeTheoThang.add(cboT);
		
		ycT = new ui.spinner.Spinner();
		ycT.setBounds(277, 24, 114, 48);
		ycT.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		ycT.setValue(LocalDate.now().getYear());
		pnlThongKeTheoThang.add(ycT);
		
		pnlThongKeTuyChinh = new JPanel();
		pnlThongKeTuyChinh.setBackground(new Color(255, 255, 255));
		pnlThongKeTuyChinh.setLayout(null);
		pnlThongKeTuyChinh.setBounds(0, 0, 688, 66);
		add(pnlThongKeTuyChinh);
		
		JLabel lblThngKT = new JLabel("Thống kê từ ngày:");
		lblThngKT.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblThngKT.setBounds(10, 0, 193, 25);
		pnlThongKeTuyChinh.add(lblThngKT);
		
		txtStart = new ui.textfield.TextField();
		txtStart.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtStart.setBounds(30, 24, 209, 40);
		pnlThongKeTuyChinh.add(txtStart);
		txtStart.setColumns(10);
		
		JLabel lblThngKNgy = new JLabel("Thống kê đến ngày:");
		lblThngKNgy.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblThngKNgy.setBounds(335, 0, 193, 25);
		pnlThongKeTuyChinh.add(lblThngKNgy);
		
		txtEnd = new ui.textfield.TextField();
		txtEnd.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtEnd.setColumns(10);
		txtEnd.setBounds(355, 24, 209, 40);
		pnlThongKeTuyChinh.add(txtEnd);
		
		btnSelectStart = new ui.button.Button();
		btnSelectStart.setIcon(new ImageIcon("src/main/java/ui/custom/calendar.png"));
		btnSelectStart.setBounds(260, 24, 35, 31);
		btnSelectStart.setBorderPainted(false);
		btnSelectStart.setFocusPainted(false);
		btnSelectStart.setContentAreaFilled(false);
		pnlThongKeTuyChinh.add(btnSelectStart);
		
		btnSelectEnd = new ui.button.Button();
		btnSelectEnd.setIcon(new ImageIcon("src/main/java/ui/custom/calendar.png"));
		btnSelectEnd.setBounds(580, 24, 35, 31);
		btnSelectEnd.setBorderPainted(false);
		btnSelectEnd.setFocusPainted(false);
		btnSelectEnd.setContentAreaFilled(false);
		pnlThongKeTuyChinh.add(btnSelectEnd);
		
		pnlThongKeNam = new JPanel();
		pnlThongKeNam.setBackground(new Color(255, 255, 255));
		pnlThongKeNam.setBounds(0, 0, 470, 66);
		add(pnlThongKeNam);
		pnlThongKeNam.setLayout(null);
		
		JLabel lblNam = new JLabel("Chọn năm thống kê:");
		lblNam.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNam.setBounds(10, 0, 193, 25);
		pnlThongKeNam.add(lblNam);
		

		ycNam = new ui.spinner.Spinner();
		ycNam.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		ycNam.setBounds(58, 24, 110, 48);
		ycNam.setValue(LocalDate.now().getYear());
		pnlThongKeNam.add(ycNam);
//		cboThongKeTheo.addActionListener(e -> {
//		    String selectedOption = (String) cboThongKeTheo.getSelectedItem();
//		    switch (selectedOption) {
//		        case "Năm":
//		    		pnlThongKeNam.show(true);
//		    		pnlThongKeTuyChinh.show(false);
//		    		pnlThongKeTheoThang.show(false);
//		            break;
//		        case "Tháng":
//		        	pnlThongKeNam.show(false);
//		    		pnlThongKeTuyChinh.show(false);
//		    		pnlThongKeTheoThang.show(true);
//		            break;
//		        case "Tùy chỉnh":
//		        	pnlThongKeNam.show(false);
//		    		pnlThongKeTuyChinh.show(true);
//		    		pnlThongKeTheoThang.show(false);
//		            break;
//		            
//		    }
//		});
//		pnlThongKeNam.show(false);
		pnlThongKeTuyChinh.show(false);
		pnlThongKeTheoThang.show(false);
	}
//        public static void main(String[] args) {
//        new PanelBarStatistical();
//    }
}
