package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Cursor;

public class Admin extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
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
		setBounds(100, 100, 696, 466);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(0, 0, 226, 438);
		contentPane.add(panel);
		panel.setLayout(null);
		
		tabHome = new JPanel();
		tabHome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tabHome.addMouseListener(new PanelButtonMouseAdapter(tabHome));
		tabHome.setBackground(SystemColor.textHighlight);
		tabHome.setBounds(0, 99, 226, 57);
		panel.add(tabHome);
		tabHome.setLayout(null);
		
		lblHome = new JLabel("HOME");
		lblHome.setFont(new Font("Arial", Font.BOLD, 12));
		lblHome.setForeground(Color.WHITE);
		lblHome.setHorizontalTextPosition(SwingConstants.CENTER);
		lblHome.setHorizontalAlignment(SwingConstants.LEFT);
		lblHome.setBounds(68, 11, 46, 35);
		tabHome.add(lblHome);
		
		tabAddAcc = new JPanel();
		tabAddAcc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tabAddAcc.addMouseListener(new PanelButtonMouseAdapter(tabAddAcc));
		tabAddAcc.setBackground(SystemColor.textHighlight);
		tabAddAcc.setBounds(0, 155, 226, 57);
		panel.add(tabAddAcc);
		tabAddAcc.setLayout(null);
		
		lblAddAcc = new JLabel("ADD ACCOUNT");
		lblAddAcc.setFont(new Font("Arial", Font.BOLD, 12));
		lblAddAcc.setForeground(Color.WHITE);
		lblAddAcc.setHorizontalTextPosition(SwingConstants.CENTER);
		lblAddAcc.setHorizontalAlignment(SwingConstants.LEFT);
		lblAddAcc.setBounds(68, 11, 98, 35);
		tabAddAcc.add(lblAddAcc);
		
		tabUpdateAcc = new JPanel();
		tabUpdateAcc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tabUpdateAcc.addMouseListener(new PanelButtonMouseAdapter(tabUpdateAcc));
		tabUpdateAcc.setBackground(SystemColor.textHighlight);
		tabUpdateAcc.setBounds(0, 212, 226, 57);
		panel.add(tabUpdateAcc);
		tabUpdateAcc.setLayout(null);
		
		lblUpdateAcc = new JLabel("UPDATE ACCOUNT");
		lblUpdateAcc.setFont(new Font("Arial", Font.BOLD, 12));
		lblUpdateAcc.setForeground(Color.WHITE);
		lblUpdateAcc.setHorizontalAlignment(SwingConstants.LEFT);
		lblUpdateAcc.setHorizontalTextPosition(SwingConstants.CENTER);
		lblUpdateAcc.setBounds(68, 11, 114, 35);
		tabUpdateAcc.add(lblUpdateAcc);
		
		tabDeleteAcc = new JPanel();
		tabDeleteAcc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tabDeleteAcc.addMouseListener(new PanelButtonMouseAdapter(tabDeleteAcc));
		tabDeleteAcc.setBackground(SystemColor.textHighlight);
		tabDeleteAcc.setBounds(0, 269, 226, 57);
		panel.add(tabDeleteAcc);
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
		});
		tabLogOut.setBackground(new Color(250, 128, 114));
		tabLogOut.setBounds(0, 326, 226, 57);
		panel.add(tabLogOut);
		tabLogOut.setLayout(null);
		
		lblLogOut = new JLabel("LOG OUT");
		lblLogOut.setFont(new Font("Arial", Font.BOLD, 12));
		lblLogOut.setForeground(Color.WHITE);
		lblLogOut.setHorizontalAlignment(SwingConstants.LEFT);
		lblLogOut.setHorizontalTextPosition(SwingConstants.CENTER);
		lblLogOut.setBounds(68, 11, 72, 35);
		tabLogOut.add(lblLogOut);
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
		public void mouseClicked(MouseEvent e) {
			panel.setBackground(new Color(0, 102, 255));
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			panel.setBackground(SystemColor.textHighlight);
		}
	}
	
	protected void tabLogOut_mouseEntered(MouseEvent e) {
		tabLogOut.setBackground(Color.RED);
	}
	
	protected void tabLogOut_mouseExited(MouseEvent e) {
		tabLogOut.setBackground(new Color(250, 128, 114));
	}
}
