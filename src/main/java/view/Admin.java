package view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Cursor;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Button;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import dao.AccountDAO;
import entity.Account;

import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import java.awt.Checkbox;

public class Admin extends JFrame {

	private JPanel contentPane;
	public JPanel panelLeft;
	public JPanel tabHome;
	public JPanel tabAddAcc;
	public JPanel tabUpdateAcc;
	public JPanel tabDeleteAcc;
	public JPanel tabLogOut;
	public JLabel lblHome;
	public JLabel lblAddAcc;
	public JLabel lblUpdateAcc;
	public JLabel lblDeleteAcc;
	public JLabel lblLogOut;
	public JPanel panelRight;
	public JPanel cardHome;
	public JPanel cardAddAcc;
	public JPanel cardUpdateAcc;
	public JPanel cardDeleteAcc;
	public CardLayout card;
	public JScrollPane scrollPane;
	public JTable table;
	public Label label;
	public Button button;
	public JLabel lblWarning;
	public JTextField textField;
	public JLabel lblAvatar;
	public JButton btnReload;
	public JLabel lblNewLabel;
	public JLabel lblNewLabel_1;
	public JTextField textField_1;
	public JSeparator separator;
	public JLabel lblNewLabel_2;
	public JTextField textField_2;
	public JSeparator separator_1;
	public JLabel lblNewLabel_3;
	public JTextField textField_3;
	public JSeparator separator_2;
	public JLabel lblNewLabel_4;
	public JPasswordField passwordField;
	public JSeparator separator_3;
	public JLabel lblNewLabel_5;
	public JComboBox comboBox;
	public JButton btnNewButton;
	public Checkbox checkbox;
	
	/**
	 * Launch the application.
	 */
	public Admin() {
		initComponents();
		card = (CardLayout) (panelRight.getLayout());
	}
	
	/**
	 * Create the frame.
	 */
	
	private void initComponents() {
		setResizable(false);
		setTitle("Welcome - Admin Portal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 715, 466);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelLeft = new JPanel();
		panelLeft.setBackground(SystemColor.textHighlight);
		panelLeft.setBounds(0, 0, 226, 438);
		contentPane.add(panelLeft);
		panelLeft.setLayout(null);
		
		tabHome = new JPanel();
		tabHome.setName("tabHome");
		tabHome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tabHome.addMouseListener(new PanelButtonMouseAdapter(tabHome));
		tabHome.setBackground(SystemColor.textHighlight);
		tabHome.setBounds(0, 108, 226, 57);
		panelLeft.add(tabHome);
		tabHome.setLayout(null);
		
		lblHome = new JLabel("HOME");
		lblHome.setFont(new Font("Arial", Font.BOLD, 12));
		lblHome.setForeground(Color.WHITE);
		lblHome.setHorizontalTextPosition(SwingConstants.CENTER);
		lblHome.setHorizontalAlignment(SwingConstants.LEFT);
		lblHome.setBounds(68, 11, 46, 35);
		tabHome.add(lblHome);
		
		tabAddAcc = new JPanel();
		tabAddAcc.setName("tabAddAcc");
		tabAddAcc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tabAddAcc.addMouseListener(new PanelButtonMouseAdapter(tabAddAcc));
		tabAddAcc.setBackground(SystemColor.textHighlight);
		tabAddAcc.setBounds(0, 164, 226, 57);
		panelLeft.add(tabAddAcc);
		tabAddAcc.setLayout(null);
		
		lblAddAcc = new JLabel("ADD ACCOUNT");
		lblAddAcc.setFont(new Font("Arial", Font.BOLD, 12));
		lblAddAcc.setForeground(Color.WHITE);
		lblAddAcc.setHorizontalTextPosition(SwingConstants.CENTER);
		lblAddAcc.setHorizontalAlignment(SwingConstants.LEFT);
		lblAddAcc.setBounds(68, 11, 98, 35);
		tabAddAcc.add(lblAddAcc);
		
		tabUpdateAcc = new JPanel();
		tabUpdateAcc.setName("tabUpdateAcc");
		tabUpdateAcc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tabUpdateAcc.addMouseListener(new PanelButtonMouseAdapter(tabUpdateAcc));
		tabUpdateAcc.setBackground(SystemColor.textHighlight);
		tabUpdateAcc.setBounds(0, 221, 226, 57);
		panelLeft.add(tabUpdateAcc);
		tabUpdateAcc.setLayout(null);
		
		lblUpdateAcc = new JLabel("UPDATE ACCOUNT");
		lblUpdateAcc.setFont(new Font("Arial", Font.BOLD, 12));
		lblUpdateAcc.setForeground(Color.WHITE);
		lblUpdateAcc.setHorizontalAlignment(SwingConstants.LEFT);
		lblUpdateAcc.setHorizontalTextPosition(SwingConstants.CENTER);
		lblUpdateAcc.setBounds(68, 11, 114, 35);
		tabUpdateAcc.add(lblUpdateAcc);
		
		tabDeleteAcc = new JPanel();
		tabDeleteAcc.setName("tabDeleteAcc");
		tabDeleteAcc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tabDeleteAcc.addMouseListener(new PanelButtonMouseAdapter(tabDeleteAcc));
		tabDeleteAcc.setBackground(SystemColor.textHighlight);
		tabDeleteAcc.setBounds(0, 278, 226, 57);
		panelLeft.add(tabDeleteAcc);
		tabDeleteAcc.setLayout(null);
		
		lblDeleteAcc = new JLabel("DELETE ACCOUNT");
		lblDeleteAcc.setFont(new Font("Arial", Font.BOLD, 12));
		lblDeleteAcc.setForeground(Color.WHITE);
		lblDeleteAcc.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDeleteAcc.setHorizontalAlignment(SwingConstants.LEFT);
		lblDeleteAcc.setBounds(68, 11, 110, 35);
		tabDeleteAcc.add(lblDeleteAcc);
		
		tabLogOut = new JPanel();
		tabLogOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tabLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tabLogOut_mouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tabLogOut_mouseExited(e);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				tabLogOut_mouseClicked(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				tabLogOut_mousePressed(e);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				tabLogOut_mouseReleased(e);
			}
		});
		tabLogOut.setBackground(new Color(250, 128, 114));
		tabLogOut.setBounds(0, 335, 226, 57);
		panelLeft.add(tabLogOut);
		tabLogOut.setLayout(null);
		
		lblLogOut = new JLabel("LOG OUT");
		lblLogOut.setFont(new Font("Arial", Font.BOLD, 12));
		lblLogOut.setForeground(Color.WHITE);
		lblLogOut.setHorizontalAlignment(SwingConstants.LEFT);
		lblLogOut.setHorizontalTextPosition(SwingConstants.CENTER);
		lblLogOut.setBounds(68, 11, 72, 35);
		tabLogOut.add(lblLogOut);
		
		lblAvatar = new JLabel("");
		lblAvatar.setBounds(83, 11, 64, 64);
		ImageIcon img = new ImageIcon(getClass().getResource("/images/administrator.png"));
		lblAvatar.setIcon(img);
		panelLeft.add(lblAvatar);
		
		cardHome = new JPanel();
		cardHome.setFocusable(false);
		cardHome.setBackground(new Color(248, 248, 255));
		
		cardAddAcc = new JPanel();
		cardAddAcc.setBackground(Color.WHITE);
		
		cardUpdateAcc = new JPanel();
		cardUpdateAcc.setBackground(Color.WHITE);
		
		cardDeleteAcc = new JPanel();
		cardDeleteAcc.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "Danger Zone", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		cardDeleteAcc.setBackground(new Color(255, 245, 238));
		
		card = new CardLayout();
		panelRight = new JPanel(card);
		panelRight.setBounds(225, 0, 484, 438);
		contentPane.add(panelRight);
		
		panelRight.add(cardHome, "cardHome");
		cardHome.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 107, 464, 212);
		cardHome.add(scrollPane);
		
		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(25);
		scrollPane.setViewportView(table);
		
		btnReload = new JButton("Reload Data");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnReload_actionPerformed(e);
			}
		});
		btnReload.setFocusPainted(false);
		btnReload.setBounds(10, 39, 109, 23);
		cardHome.add(btnReload);
		panelRight.add(cardAddAcc, "cardAddAcc");
		cardAddAcc.setLayout(null);
		
		lblNewLabel = new JLabel("ADD NEW ACCOUNT");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(17, 11, 166, 30);
		cardAddAcc.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("UserID");
		lblNewLabel_1.setBounds(30, 98, 46, 14);
		cardAddAcc.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBackground(Color.WHITE);
		textField_1.setBorder(null);
		textField_1.setBounds(137, 98, 133, 20);
		cardAddAcc.add(textField_1);
		textField_1.setColumns(10);
		
		separator = new JSeparator();
		separator.setBounds(137, 119, 133, 2);
		cardAddAcc.add(separator);
		
		lblNewLabel_2 = new JLabel("Full Name");
		lblNewLabel_2.setBounds(30, 139, 60, 14);
		cardAddAcc.add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		textField_2.setBackground(Color.WHITE);
		textField_2.setBorder(null);
		textField_2.setColumns(10);
		textField_2.setBounds(137, 130, 133, 20);
		cardAddAcc.add(textField_2);
		
		separator_1 = new JSeparator();
		separator_1.setBounds(137, 151, 133, 2);
		cardAddAcc.add(separator_1);
		
		lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setBounds(30, 179, 60, 14);
		cardAddAcc.add(lblNewLabel_3);
		
		textField_3 = new JTextField();
		textField_3.setBackground(Color.WHITE);
		textField_3.setBorder(null);
		textField_3.setColumns(10);
		textField_3.setBounds(137, 170, 133, 20);
		cardAddAcc.add(textField_3);
		
		separator_2 = new JSeparator();
		separator_2.setBounds(137, 191, 133, 2);
		cardAddAcc.add(separator_2);
		
		lblNewLabel_4 = new JLabel("Password");
		lblNewLabel_4.setBounds(30, 223, 60, 14);
		cardAddAcc.add(lblNewLabel_4);
		
		passwordField = new JPasswordField();
		passwordField.setBackground(Color.WHITE);
		passwordField.setBorder(null);
		passwordField.setBounds(137, 214, 133, 20);
		cardAddAcc.add(passwordField);
		
		separator_3 = new JSeparator();
		separator_3.setBounds(137, 235, 133, 2);
		cardAddAcc.add(separator_3);
		
		lblNewLabel_5 = new JLabel("Privilege");
		lblNewLabel_5.setBounds(30, 292, 60, 14);
		cardAddAcc.add(lblNewLabel_5);
		
		comboBox = new JComboBox();
		comboBox.setFocusable(false);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Admin", "Master", "Officer"}));
		comboBox.setBounds(137, 290, 133, 18);
		cardAddAcc.add(comboBox);
		
		btnNewButton = new JButton("ADD");
		btnNewButton.setFocusPainted(false);
		btnNewButton.setBounds(350, 372, 89, 23);
		cardAddAcc.add(btnNewButton);
		
		checkbox = new Checkbox("Show / Hide");
		checkbox.setFocusable(false);
		checkbox.setBounds(137, 245, 95, 22);
		cardAddAcc.add(checkbox);
		panelRight.add(cardUpdateAcc, "cardUpdateAcc");
		panelRight.add(cardDeleteAcc, "cardDeleteAcc");
		cardDeleteAcc.setLayout(null);
		
		label = new Label("Enter the Username to delete :");
		label.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		label.setFont(new Font("Dialog", Font.BOLD, 12));
		label.setBounds(82, 114, 190, 22);
		cardDeleteAcc.add(label);
		
		button = new Button("Delete");
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setFont(new Font("Dialog", Font.BOLD, 12));
		button.setFocusable(false);
		button.setBackground(new Color(255, 69, 0));
		button.setForeground(Color.WHITE);
		button.setBounds(82, 199, 190, 33);
		cardDeleteAcc.add(button);
		
		lblWarning = new JLabel("");
		lblWarning.setHorizontalTextPosition(SwingConstants.CENTER);
		lblWarning.setHorizontalAlignment(SwingConstants.CENTER);
		lblWarning.setBounds(30, 199, 46, 33);
		ImageIcon img1 = new ImageIcon(getClass().getResource("/images/warning-shield-32.png"));
		lblWarning.setIcon(img1);
		cardDeleteAcc.add(lblWarning);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setBounds(82, 148, 190, 33);
		cardDeleteAcc.add(textField);
		textField.setColumns(10);
	}
	
	private class PanelButtonMouseAdapter extends MouseAdapter {
		JPanel panel;
		public PanelButtonMouseAdapter(JPanel panel) {
			this.panel = panel;
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			panel.setBackground(new Color(0, 102, 255));
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			panel.setBackground(SystemColor.textHighlight);
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			panel.setBorder(new BevelBorder(BevelBorder.RAISED)); 
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			panel.setBorder(new EmptyBorder(1, 1, 1, 1));
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			switch (panel.getName()) {
				case "tabHome": {
					card.show(panelRight, "cardHome");
					break;
				}
				
				case "tabAddAcc": {
					card.show(panelRight, "cardAddAcc");
					break;
				}
				
				case "tabUpdateAcc": {
					card.show(panelRight, "cardUpdateAcc");
					break;
				}
				
				case "tabDeleteAcc": {
					card.show(panelRight, "cardDeleteAcc");
					break;
				}
			}
		}
	}
	
	protected void tabLogOut_mouseEntered(MouseEvent e) {
		tabLogOut.setBackground(Color.RED);
	}
	
	protected void tabLogOut_mouseExited(MouseEvent e) {
		tabLogOut.setBackground(new Color(250, 128, 114));
	}
	
	protected void tabLogOut_mousePressed(MouseEvent e) {
		tabLogOut.setBorder(new BevelBorder(BevelBorder.RAISED));
	}
	
	protected void tabLogOut_mouseReleased(MouseEvent e) {
		tabLogOut.setBorder(new EmptyBorder(1, 1, 1, 1));
	}
	
	protected void tabLogOut_mouseClicked(MouseEvent e) {
		int input = JOptionPane.showConfirmDialog(null, "Are you sure want to log out ?", "Log Out", JOptionPane.YES_NO_OPTION);
		if (input == 0) {
			Login.main(null);
			this.setVisible(false);
		}
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
					acc.getPrivilege() == 1 ? "Admin" : acc.getPrivilege() == 2 ? "Master" : "Officer"
			});
		}
		table.setModel(model);
	}
	
	protected void btnReload_actionPerformed(ActionEvent e) {
		loadData();
	}
}
