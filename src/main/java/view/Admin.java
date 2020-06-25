package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.CardLayout;

public class Admin extends JFrame {

	private JPanel contentPane;
	public CardLayout card;
	
	/**
	 * Launch the application.
	 */
	public Admin() {
		initComponents();
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
	}
	
//	protected void loadData() {
//		AccountDAO accDao = new AccountDAO();
//		DefaultTableModel model = new DefaultTableModel();
//		model.addColumn("UserID");
//		model.addColumn("FullName");
//		model.addColumn("Email");
//		model.addColumn("Privilege");
//		
//		for (Account acc : accDao.getAllAccount()) {
//			model.addRow(new Object[] {
//					acc.getUserID(), acc.getFullName(), acc.getEmail(),
//					acc.getPrivilege() == 1 ? "Admin" : acc.getPrivilege() == 2 ? "Master" : "Officer"
//			});
//		}
//		table.setModel(model);
//	}
}
