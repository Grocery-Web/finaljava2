package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import dao.AccountDAO;
import entity.Account;

import javax.swing.JLabel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Checkbox;
import java.awt.Label;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class Login extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JButton btnLogIn;
	public JTextField txtUserID;
	public JLabel lblNewLabel;
	public JLabel lblPassword;
	public JPasswordField passwordField;
	public JLabel lblTop;
	public Checkbox ckbxShowHide;
	public Label labelTopLeft;
	public JLabel lblTopLeft;
	public JLabel lblLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
					Login login = new Login();
					login.setLocationRelativeTo(null);
					login.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("Crime Management System - Log In");
		setResizable(false);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 449);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 248, 255));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 325, 449);
		contentPane.add(panel);
		
		lblTopLeft = new JLabel("");
		ImageIcon img = new ImageIcon(getClass().getResource("/images/police-department.png"));
		lblTopLeft.setIcon(img);
		panel.add(lblTopLeft);
		
		btnLogIn = new JButton("LOG IN");
		btnLogIn.setBorder(null);
		btnLogIn.setFont(new Font("Dialog", Font.BOLD, 12));
		btnLogIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLogIn_mouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnLogIn_mouseExited(e);
			}
		});
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLogIn_actionPerformed(e);
			}
		});
		btnLogIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogIn.setFocusable(false);
		btnLogIn.setForeground(Color.WHITE);
		btnLogIn.setBackground(SystemColor.textHighlight);
		btnLogIn.setBounds(379, 330, 230, 39);
		contentPane.add(btnLogIn);
		
		txtUserID = new JTextField();
		txtUserID.setBounds(379, 147, 230, 32);
		contentPane.add(txtUserID);
		txtUserID.setColumns(10);
		
		lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(379, 122, 65, 14);
		contentPane.add(lblNewLabel);
		
		lblPassword = new JLabel("Password");
		lblPassword.setBounds(379, 205, 65, 14);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(379, 230, 230, 32);
		contentPane.add(passwordField);
		
		lblTop = new JLabel("");
		lblTop.setBounds(463, 39, 64, 64);
		ImageIcon img1 = new ImageIcon(getClass().getResource("/images/police-icon.png"));
		lblTop.setIcon(img1);
		contentPane.add(lblTop);
		
		
		ckbxShowHide = new Checkbox("Show / Hide");
		ckbxShowHide.setFocusable(false);
		ckbxShowHide.setBounds(379, 274, 95, 22);
		ckbxShowHide.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() != ItemEvent.SELECTED) {
		            passwordField.setEchoChar("\u2022".toCharArray()[0]);
		        } else {
		            passwordField.setEchoChar((char) 0);
		        }
		    }
		});
		contentPane.add(ckbxShowHide);
		
		lblLogin = new JLabel("");
		lblLogin.setBounds(379, 302, 230, 14);
		contentPane.add(lblLogin);
		SwingUtilities.getRootPane(contentPane).setDefaultButton(btnLogIn);
	}
	
	// Create function to change color of btnLogIn on mouseEntered and mouseExited
	protected void btnLogIn_mouseEntered(MouseEvent e) {
		btnLogIn.setBackground(new Color(0, 51, 204));
	}
	
	protected void btnLogIn_mouseExited(MouseEvent e) {
		btnLogIn.setBackground(SystemColor.textHighlight);
	}
	
	protected void btnLogIn_actionPerformed(ActionEvent e) {
		Account acc = new Account();
		AccountDAO accDao = new AccountDAO();
		
		acc.setUserID(txtUserID.getText());
		acc.setPassword(new String(passwordField.getPassword()));
		
		var login = accDao.checkAcc(acc);
		
		if (login == null) {
			// INVALID ACCOUNT
			lblLogin.setText("User not found or incorrect password!");
			lblLogin.setForeground(Color.RED);
			
		} else if (login.isCheckLogin()) {
			// ALREADY LOGGED IN
			lblLogin.setText("This account is already logged in!");
			lblLogin.setForeground(Color.RED);
			
		} else if (login.getPrivilege() == 1) {
			// ADMIN
			accDao.updateAccLoginStatus(login);
			Admin admin = new Admin(login);
			admin.setLocationRelativeTo(null);
			admin.setVisible(true);
			this.setVisible(false);
			admin.loadData();
			
		} else if (login.getPrivilege() == 2) {
			// MASTER
			accDao.updateAccLoginStatus(login);
			MainFrame mf = new MainFrame(login);
			mf.setVisible(true);
			this.setVisible(false);
			
		} else if (login.getPrivilege() == 3) {
			// USER
			accDao.updateAccLoginStatus(login);
			MainFrame mf = new MainFrame(login);
			mf.setVisible(true);
			this.setVisible(false);
		}
	}
	
	public int getPrivilege(int privilege) {
		return privilege;
	}
}