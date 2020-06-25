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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultRowSorter;

import java.awt.Checkbox;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.TextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

public class Admin extends JFrame {

	private JPanel contentPane;
	public CardLayout card;
	public JPanel panelL;
	public JPanel panelR;
	public JScrollPane scrollPane;
	public Button btnLogOut;
	public JTable table;
	public JLabel lblNewLabel;
	public JTextField txtUserID;
	public JLabel lblNewLabel_1;
	public JLabel lblNewLabel_2;
	public JTextField txtFullName;
	public JSeparator separator;
	public JPasswordField passwordField;
	public JSeparator separator_1;
	public JSeparator separator_2;
	public JLabel lblNewLabel_3;
	public JTextField txtEmail;
	public JSeparator separator_3;
	public JLabel lblNewLabel_4;
	public JComboBox comboBox;
	public Checkbox ckbxShowHide;
	public JButton btnAdd;
	public JButton btnUpdate;
	public JButton btnDelete;
	public JLabel lblNewLabel_5;
	public JLabel lblTotalAcc;
	public JTextField txtSearch;
	public JLabel lblAdmin;
	
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
		setBounds(100, 100, 757, 502);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelL = new JPanel();
		panelL.setBackground(new Color(255, 255, 255));
		panelL.setBounds(0, 0, 266, 474);
		contentPane.add(panelL);
		panelL.setLayout(null);
		
		lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(10, 31, 86, 14);
		panelL.add(lblNewLabel);
		
		txtUserID = new JTextField();
		txtUserID.setBorder(null);
		txtUserID.setBounds(10, 56, 180, 20);
		panelL.add(txtUserID);
		txtUserID.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(10, 209, 86, 14);
		panelL.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Full Name");
		lblNewLabel_2.setBounds(10, 87, 86, 14);
		panelL.add(lblNewLabel_2);
		
		txtFullName = new JTextField();
		txtFullName.setBorder(null);
		txtFullName.setBounds(10, 112, 180, 20);
		panelL.add(txtFullName);
		txtFullName.setColumns(10);
		
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
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBorder(null);
		txtEmail.setBounds(10, 168, 180, 20);
		panelL.add(txtEmail);
		
		separator_3 = new JSeparator();
		separator_3.setBackground(new Color(0, 255, 0));
		separator_3.setBounds(10, 188, 188, 2);
		panelL.add(separator_3);
		
		lblNewLabel_4 = new JLabel("Privilege");
		lblNewLabel_4.setBounds(10, 300, 86, 14);
		panelL.add(lblNewLabel_4);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Master", "User", "Admin"}));
		comboBox.setFocusable(false);
		comboBox.setBounds(10, 325, 188, 22);
		panelL.add(comboBox);
		
		ckbxShowHide = new Checkbox("Show / Hide");
		ckbxShowHide.setFocusable(false);
		ckbxShowHide.setBounds(10, 272, 95, 22);
		ckbxShowHide.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() != ItemEvent.SELECTED) {
		            passwordField.setEchoChar("\u2022".toCharArray()[0]);
		        } else {
		            passwordField.setEchoChar((char) 0);
		        }
		    }
		});
		panelL.add(ckbxShowHide);
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAdd_actionPerformed(e);
			}
		});
		btnAdd.setFocusPainted(false);
		btnAdd.setBounds(10, 378, 89, 23);
		panelL.add(btnAdd);
		
		btnUpdate = new JButton("Update");
		btnUpdate.setFocusPainted(false);
		btnUpdate.setBounds(109, 378, 89, 23);
		panelL.add(btnUpdate);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDelete_actionPerformed(e);
			}
		});
		btnDelete.setFocusPainted(false);
		btnDelete.setBounds(10, 412, 89, 23);
		panelL.add(btnDelete);
		
		panelR = new JPanel();
		panelR.setBackground(new Color(248, 248, 255));
		panelR.setBounds(266, 0, 485, 474);
		contentPane.add(panelR);
		panelR.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 105, 485, 369);
		panelR.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table_mouseClicked(e);
			}
		});
		table.setRowHeight(25);
		table.setSelectionBackground(new Color(144, 238, 144));
		table.setBackground(Color.WHITE);
		table.setAutoCreateRowSorter(true);
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
		
		txtSearch = new JTextField();
		txtSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtSearch_actionPerformed(e);
			}
		});
		txtSearch.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Search", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		txtSearch.setBounds(10, 53, 465, 41);
		panelR.add(txtSearch);
		txtSearch.setColumns(10);
	}
	
	protected void loadData() {
		AccountDAO accDao = new AccountDAO();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("UserID");
		model.addColumn("FullName");
		model.addColumn("Email");
		model.addColumn("Privilege");
		
		for (Account acc : accDao.getAllAccount()) {
			model.addRow(new Object[] {
					acc.getUserID(), acc.getFullName(), acc.getEmail(),
					acc.getPrivilege() == 1 ? "Admin" : acc.getPrivilege() == 2 ? "Master" : "User"
			});
		}
		table.setModel(model);
		lblTotalAcc.setText(Integer.toString(table.getRowCount()));
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
	
	protected void btnAdd_actionPerformed(ActionEvent e) {
		Account acc = new Account();
		if (!txtUserID.getText().matches("\\w{4,20}")) {
			JOptionPane.showMessageDialog(null, "Please enter legit userID: 4 - 20 alphanumeric characters", "Invalid input", JOptionPane.ERROR_MESSAGE);
			return;
		}
		acc.setUserID(txtUserID.getText());
		if (!txtFullName.getText().matches("[\\D ]{4,50}")) {
			JOptionPane.showMessageDialog(null, "Please enter a legit name: 4 - 50 alphabet characters", "Invalid input", JOptionPane.ERROR_MESSAGE);
			return;
		}
		acc.setFullName(txtFullName.getText());
		if (!txtEmail.getText().matches("^[a-z][a-z0-9_\\.]{4,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
			JOptionPane.showMessageDialog(null, "Please enter a valid email address", "Invalid input", JOptionPane.ERROR_MESSAGE);
			return;
		}
		acc.setEmail(txtEmail.getText());
		if (new String(passwordField.getPassword()).equals("")) {
			JOptionPane.showMessageDialog(null, "Please input a password", "Empty field", JOptionPane.ERROR_MESSAGE);
			return;
		}
		acc.setPassword(new String(passwordField.getPassword()));
		acc.setPrivilege(comboBox.getSelectedItem() == "Master" ? 2 : 3);
		
		AccountDAO accDao = new AccountDAO();
		accDao.addAccount(acc);
		loadData();
	}
	
	protected void btnDelete_actionPerformed(ActionEvent e) {
		Account acc = new Account();
		if (txtUserID.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter an userID", "Empty input field", JOptionPane.ERROR_MESSAGE);
			return;
		}
		acc.setUserID(txtUserID.getText());
		
		int choice = JOptionPane.showConfirmDialog(null, "Are you sure want to delete this account ?", "Delete", JOptionPane.YES_NO_OPTION);
		if (choice == 0) {
			AccountDAO accDao = new AccountDAO();
			accDao.deleteAccount(acc);
			loadData();
		}
	}
	
	protected void txtSearch_actionPerformed(ActionEvent e) {
		String str = txtSearch.getText();
		DefaultRowSorter<?, ?> sorter = (DefaultRowSorter<?, ?>) table.getRowSorter();
		sorter.setRowFilter(RowFilter.regexFilter(str));
		sorter.setSortKeys(null);
	}
	
	protected void table_mouseClicked(MouseEvent e) {
		int selectRow = table.getSelectedRow();
		txtUserID.setText(table.getValueAt(selectRow, 0).toString());
		txtFullName.setText(table.getValueAt(selectRow, 1).toString());
		txtEmail.setText(table.getValueAt(selectRow, 2).toString());
		comboBox.setSelectedItem(table.getValueAt(selectRow, 3));
	}
}
