package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.AccountDAO;
import entity.Account;

import java.awt.Color;
import java.awt.CardLayout;
import java.awt.Button;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Checkbox;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Admin extends JFrame {

	private JPanel contentPane;
	public CardLayout card;
	public JPanel panelL;
	public JPanel panelR;
	public JScrollPane scrollPane;
	public Button btnLogOut;
	public JTable table;
	public JLabel lblNewLabel;
	public JTextField textField;
	public JLabel lblNewLabel_1;
	public JLabel lblNewLabel_2;
	public JTextField textField_2;
	public JSeparator separator;
	public JPasswordField passwordField;
	public JSeparator separator_1;
	public JSeparator separator_2;
	public JLabel lblNewLabel_3;
	public JTextField textField_1;
	public JSeparator separator_3;
	public JLabel lblNewLabel_4;
	public JComboBox comboBox;
	public Checkbox checkbox;
	public JButton btnNewButton;
	public JButton btnNewButton_1;
	public JButton btnNewButton_2;
	public JLabel lblNewLabel_5;
	public JLabel lblTotalAcc;
	
	/**
	 * Launch the application.
	 */
	public Admin() {
		setResizable(false);
		initComponents();
	}
	
	/**
	 * Create the frame.
	 */
	
	private void initComponents() {
		setTitle("Welcome - Admin Portal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 757, 487);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelL = new JPanel();
		panelL.setBackground(new Color(255, 255, 255));
		panelL.setBounds(0, 0, 266, 459);
		contentPane.add(panelL);
		panelL.setLayout(null);
		
		lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(10, 31, 86, 14);
		panelL.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBorder(null);
		textField.setBounds(10, 56, 180, 20);
		panelL.add(textField);
		textField.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(10, 209, 86, 14);
		panelL.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Full Name");
		lblNewLabel_2.setBounds(10, 87, 86, 14);
		panelL.add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		textField_2.setBorder(null);
		textField_2.setBounds(10, 112, 180, 20);
		panelL.add(textField_2);
		textField_2.setColumns(10);
		
		separator = new JSeparator();
		separator.setBackground(new Color(0, 255, 0));
		separator.setBounds(10, 76, 188, 2);
		panelL.add(separator);
		
		passwordField = new JPasswordField();
		passwordField.setBorder(null);
		passwordField.setBounds(10, 234, 180, 20);
		panelL.add(passwordField);
		
		separator_1 = new JSeparator();
		separator_1.setBackground(new Color(0, 255, 0));
		separator_1.setBounds(10, 254, 188, 2);
		panelL.add(separator_1);
		
		separator_2 = new JSeparator();
		separator_2.setBackground(new Color(0, 255, 0));
		separator_2.setBounds(10, 132, 188, 2);
		panelL.add(separator_2);
		
		lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setBounds(10, 143, 86, 14);
		panelL.add(lblNewLabel_3);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBorder(null);
		textField_1.setBounds(10, 168, 180, 20);
		panelL.add(textField_1);
		
		separator_3 = new JSeparator();
		separator_3.setBackground(new Color(0, 255, 0));
		separator_3.setBounds(10, 188, 188, 2);
		panelL.add(separator_3);
		
		lblNewLabel_4 = new JLabel("Privilege");
		lblNewLabel_4.setBounds(10, 300, 86, 14);
		panelL.add(lblNewLabel_4);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Master", "User"}));
		comboBox.setFocusable(false);
		comboBox.setBounds(10, 325, 188, 22);
		panelL.add(comboBox);
		
		checkbox = new Checkbox("Show / Hide");
		checkbox.setFocusable(false);
		checkbox.setBounds(10, 272, 95, 22);
		panelL.add(checkbox);
		
		btnNewButton = new JButton("Add");
		btnNewButton.setFocusPainted(false);
		btnNewButton.setBounds(10, 378, 89, 23);
		panelL.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Update");
		btnNewButton_1.setFocusPainted(false);
		btnNewButton_1.setBounds(109, 378, 89, 23);
		panelL.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Delete");
		btnNewButton_2.setFocusPainted(false);
		btnNewButton_2.setBounds(10, 412, 89, 23);
		panelL.add(btnNewButton_2);
		
		panelR = new JPanel();
		panelR.setBackground(new Color(248, 248, 255));
		panelR.setBounds(266, 0, 485, 459);
		contentPane.add(panelR);
		panelR.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 465, 388);
		panelR.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnLogOut = new Button("LOG OUT");
		btnLogOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLogOut_mouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnLogOut_mouseExited(e);
			}
		});
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLogOut_actionPerformed(e);
			}
		});
		btnLogOut.setFocusable(false);
		btnLogOut.setFont(new Font("Verdana", Font.BOLD, 12));
		btnLogOut.setForeground(new Color(255, 255, 255));
		btnLogOut.setBackground(new Color(240, 128, 128));
		btnLogOut.setBounds(388, 10, 87, 33);
		panelR.add(btnLogOut);
		
		lblNewLabel_5 = new JLabel("Total accounts :");
		lblNewLabel_5.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_5.setFocusable(false);
		lblNewLabel_5.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		lblNewLabel_5.setBounds(10, 10, 98, 33);
		panelR.add(lblNewLabel_5);
		
		lblTotalAcc = new JLabel("");
		lblTotalAcc.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalAcc.setBounds(118, 10, 84, 33);
		panelR.add(lblTotalAcc);
	}
	
	protected void loadData() {
		AccountDAO accDao = new AccountDAO();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("UserID");
		model.addColumn("FullName");
		model.addColumn("Email");
		model.addColumn("Privilege");
		
		int count = 0;
		for (Account acc : accDao.getAllAccount()) {
			model.addRow(new Object[] {
					acc.getUserID(), acc.getFullName(), acc.getEmail(),
					acc.getPrivilege() == 1 ? "Admin" : acc.getPrivilege() == 2 ? "Master" : "Officer"
			});
			count += 1;
		}
		table.setModel(model);
		lblTotalAcc.setText(Integer.toString(count));
	}
	
	protected void btnLogOut_actionPerformed(ActionEvent e) {
		int choice = JOptionPane.showConfirmDialog(null, "Are you sure want to log out ?", "Log Out", JOptionPane.YES_NO_OPTION);
		if (choice == 0) {
			Login.main(null);
			this.setVisible(false);
		}
	}
	
	protected void btnLogOut_mouseEntered(MouseEvent e) {
		btnLogOut.setBackground(Color.RED);
	}
	
	protected void btnLogOut_mouseExited(MouseEvent e) {
		btnLogOut.setBackground(new Color(240, 128, 128));
	}
}
