package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Button;
import java.awt.SystemColor;
import javax.swing.JTextField;

import dao.AccountDAO;
import entity.Account;

import javax.swing.JLabel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
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
	public Button btnLogIn;
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
					Login frame = new Login();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
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
		
		btnLogIn = new Button("LOG IN");
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
		acc.setUserID(txtUserID.getText());
		acc.setPassword(new String(passwordField.getPassword()));
		
		AccountDAO accDao = new AccountDAO();
		int checked = accDao.checkAcc(acc);
		
		switch (checked) {
			case -1: {
				lblLogin.setText("User not found or incorrect password!");
				lblLogin.setForeground(Color.RED);
				break;
			}
			
			case -2: {
				lblLogin.setText("Your account has been disabled.");
				lblLogin.setForeground(Color.RED);
				break;
			}

			case 1: {
				Admin admin = new Admin();
				admin.setLocationRelativeTo(null);
				admin.setVisible(true);
				this.setVisible(false);
				admin.loadData();
				break;
			}
			
			case 2: {
				lblLogin.setText("Welcome Master");
				lblLogin.setForeground(new Color(0, 128, 0));
				break;
			}
			
			case 3: {
				lblLogin.setText(String.format("Welcome %s", acc.getUserID()));
				lblLogin.setForeground(new Color(0, 128, 0));
				break;
			}
		}
	}
	
	
}
