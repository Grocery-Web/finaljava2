package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entity.Complaint;

public class ComplaintDetailFrame extends JFrame {
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	
	private JPanel contentPane;
	private JButton updateBtn;
	private ComplaintDetailListener cplDetailListener;

	public ComplaintDetailFrame(Complaint cpl) {
		updateBtn = new JButton("Update");
		updateBtn.setMnemonic(KeyEvent.VK_U);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
		
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					var complaint = new Complaint(formatter.parse("2001-12-01"), "HCM", "Thang", "aaaa", false);
					cplDetailListener.updateEventListener(complaint);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		contentPane.add(updateBtn, BorderLayout.CENTER);
	}
	
	public void setFrameListener(ComplaintDetailListener cplDetailListener) {
		this.cplDetailListener = cplDetailListener;
	}
}
