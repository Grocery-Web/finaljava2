package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entity.Complaint;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class ComplaintDetail extends JFrame {

	private JPanel contentPane;
	private JTextField textDeclarantName;
	private JTextField textCompDetail;
	private JTextField textCompId;
	private JTextField textCompDate;
	private JTextField textCompPlace;
	private JTable table;
	private ComplaintDetailListener cplDetailListener;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ComplaintDetail frame = new ComplaintDetail();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public ComplaintDetail(Complaint cpl) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblCompId = new JLabel("Complaint ID:");
		
		JLabel lblCompDate = new JLabel("Date:");
		
		JLabel lblCompPlace = new JLabel("Place:");
		
		JLabel lblDeclarantName = new JLabel("Declarant Name:");
		
		JLabel lblCompDetail = new JLabel("Detail:");
		
		JLabel lblCompStatus = new JLabel("Verify Status:");
		
		textDeclarantName = new JTextField();
		textDeclarantName.setColumns(10);
		textDeclarantName.setText(cpl.getDeclarantName());
		
		textCompDetail = new JTextField();
		textCompDetail.setColumns(10);
		textCompDetail.setText(cpl.getDetail());
		
		textCompId = new JTextField();
		textCompId.setColumns(10);
//		textCompId.setInt(cpl.getId());
		
		textCompDate = new JTextField();
		textCompDate.setColumns(10);
//		textCompDate.setText()
		
		textCompPlace = new JTextField();
		textCompPlace.setColumns(10);
		textCompPlace.setText(cpl.getPlace());
		
		JLabel lblCriminalList = new JLabel("List of Criminals:");
		
		table = new JTable();
		
		JRadioButton rdbtnUnverified = new JRadioButton("Unverified");
		
		JRadioButton rdbtnApproved = new JRadioButton("Approved");
		
		if (cpl.isStatus() == false) {
			rdbtnUnverified.setSelected(true);
		} else {
			rdbtnApproved.setSelected(true);
		}
		
		//Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnUnverified);
	    group.add(rdbtnApproved);
		
		JButton btnSubmit = new JButton("Submit");
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(37)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCompStatus)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDeclarantName)
								.addComponent(lblCompId)
								.addComponent(lblCompDate)
								.addComponent(lblCompPlace))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textCompDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textCompId, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
								.addComponent(textDeclarantName, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
								.addComponent(textCompPlace, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCompDetail)
								.addComponent(lblCriminalList))
							.addGap(12)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(table, GroupLayout.PREFERRED_SIZE, 533, GroupLayout.PREFERRED_SIZE)
								.addComponent(textCompDetail, 533, 533, 533)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(rdbtnUnverified)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(rdbtnApproved))))
						.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))
					.addGap(64))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(27)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCompId)
						.addComponent(textCompId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCompDate)
						.addComponent(textCompDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCompPlace)
						.addComponent(textCompPlace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDeclarantName)
						.addComponent(textDeclarantName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(15)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblCompDetail)
							.addGap(68)
							.addComponent(lblCriminalList))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(textCompDetail, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(table, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCompStatus)
						.addComponent(rdbtnUnverified)
						.addComponent(rdbtnApproved))
					.addGap(17)
					.addComponent(btnSubmit)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void setFrameListener(ComplaintDetailListener cplDetailListener) {
		this.cplDetailListener = cplDetailListener;
	}
}
