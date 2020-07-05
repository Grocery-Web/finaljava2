package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import dao.ComplaintDAO;
import entity.Complaint;
import entity.ComplaintDetail;
import entity.Person;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JButton;


public class ComplaintDetailFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textDeclarantName;
	private JTextArea textCompDetail;
	private JTextField textCompId;
	private JTextField textCompDate;
	private JTextField textCompPlace;
	private JRadioButton rdbtnUnverified, rdbtnApproved;
	private JTable table;
	private ComplaintDetailListener cplDetailListener;
	private TableComplaintDetailListener tableListener;
	private JTextField textCompName;
	private ComplaintDetailTableModel tableModel;
	private JPopupMenu popup;
	private Complaint complaint;

	public ComplaintDetailFrame(Complaint cpl) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 819, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblCompName = new JLabel("Complaint Name");	
		
		JLabel lblCompId = new JLabel("Complaint ID:");
		
		JLabel lblCompDate = new JLabel("Date:");
		
		JLabel lblCompPlace = new JLabel("Place:");
		
		JLabel lblDeclarantName = new JLabel("Declarant Name:");
		
		JLabel lblCompDetail = new JLabel("Detail:");
		
		JLabel lblCompStatus = new JLabel("Verify Status:");
		
		textCompName = new JTextField();
		textCompName.setColumns(10);
		textCompName.setText(cpl.getName());
		
		textCompId = new JTextField();
		textCompId.setColumns(10);
		textCompId.setText(String.valueOf(cpl.getId()));
		textCompId.setEditable(false);
		
		textDeclarantName = new JTextField();
		textDeclarantName.setColumns(10);
		textDeclarantName.setText(cpl.getDeclarantName());
		
		textCompDetail = new JTextArea();
		textCompDetail.setColumns(10);
		textCompDetail.setLineWrap(true);
		textCompDetail.setWrapStyleWord(true);
		textCompDetail.setText(cpl.getDetail());
		JScrollPane jp = new JScrollPane(textCompDetail);
		textCompDetail.setEditable(true);
		
		textCompDate = new JTextField();
		textCompDate.setColumns(10);
		textCompDate.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cpl.getDatetime()));
		
		textCompPlace = new JTextField();
		textCompPlace.setColumns(10);
		textCompPlace.setText(cpl.getPlace());
		
//		TABLE LIST OF SUSPECT
		JLabel lblCriminalList = new JLabel("List of Suspect:");
		
		tableModel = new ComplaintDetailTableModel();
		table = new JTable(tableModel);
		
		table.setBorder(BorderFactory.createEtchedBorder());
//		add(new JScrollPane(table));
		
		// ALIGN TEXT IN CENTER
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < 8; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		// REMOVE PERSON
		popup = new JPopupMenu();
		
		JMenuItem removeItem = new JMenuItem("Remove person");
		popup.add(removeItem);
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);

				if (e.getButton() == MouseEvent.BUTTON3) {
					// When right click on the row of table, the pop up will be showed up
					popup.show(table, e.getX(), e.getY());
				}
			}
		});
		
		removeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow(); // Start from 0
				int id = (int) table.getModel().getValueAt(row, 0);

				int action = JOptionPane.showConfirmDialog(null, "Do you really want to remove this person",
						"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);

				if (action == JOptionPane.OK_OPTION && tableListener != null) {
					tableListener.tableEventDeleted(id);
				}
			}
		});
	
		
// 		RADIO BUTTON GROUP
		rdbtnUnverified = new JRadioButton("Unverified");
		
		rdbtnApproved = new JRadioButton("Approved");
		
		if (cpl.isStatus() == false) {
			rdbtnUnverified.setSelected(true);
		} else {
			rdbtnApproved.setSelected(true);
		}
		
	    ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnUnverified);
	    group.add(rdbtnApproved);
	    
// 		ASSIGN DATA FOR COMPLAINT IN THIS FRAME;
	    complaint = new Complaint();
	    complaint.setId(cpl.getId());
	    complaint.setName(cpl.getName());
	    complaint.setDatetime(cpl.getDatetime());
	    complaint.setDeclarantName(cpl.getDeclarantName());
	    complaint.setDetail(cpl.getDetail());
	    complaint.setPlace(cpl.getPlace());
	    complaint.setStatus(cpl.isStatus());
	    
//		SUMMIT BUTTON
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setForeground(Color.GREEN);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSubmitActionPerformed(e);
			}
		});
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(37)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDeclarantName)
								.addComponent(lblCompDetail)
								.addComponent(lblCompPlace)
								.addComponent(lblCompName)
								.addComponent(lblCompId)
								.addComponent(lblCompDate))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textCompName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textCompId, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
								.addComponent(textCompPlace, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
								.addComponent(textCompDetail, GroupLayout.PREFERRED_SIZE, 533, GroupLayout.PREFERRED_SIZE)
								.addComponent(textDeclarantName, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
								.addComponent(textCompDate, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCriminalList)
								.addComponent(lblCompStatus))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(rdbtnUnverified)
									.addGap(14)
									.addComponent(rdbtnApproved))
								.addComponent(table, GroupLayout.PREFERRED_SIZE, 592, GroupLayout.PREFERRED_SIZE))))
					.addGap(72))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(32, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCompName)
						.addComponent(textCompName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCompId)
						.addComponent(textCompId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
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
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCompDetail)
						.addComponent(textCompDetail, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCriminalList)
						.addComponent(table, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
					.addGap(31)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCompStatus)
						.addComponent(rdbtnApproved)
						.addComponent(rdbtnUnverified))
					.addGap(17)
					.addComponent(btnSubmit)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void setFrameListener(ComplaintDetailListener cplDetailListener) {
		this.cplDetailListener = cplDetailListener;
	}
	
	public void setData(HashMap<Person, String> db) {
		tableModel.setData(db);
	}
	
	public void refresh() {
		tableModel.fireTableDataChanged();
	}
	
	public void setTableListener(TableComplaintDetailListener tableListener) {
		this.tableListener = tableListener;
	}
	
	protected void btnSubmitActionPerformed(ActionEvent e) {
	    complaint.setId(Integer.parseInt(textCompId.getText()));
	    complaint.setName(textCompName.getText());
	    complaint.setDeclarantName(textDeclarantName.getText());
	    complaint.setDetail(textCompDetail.getText());
	    complaint.setPlace(textCompPlace.getText());
	    
	    String datetime = textCompDate.getText();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	      //Parsing the given String to Date object
	    try {
			complaint.setDatetime(formatter.parse(datetime));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    if (rdbtnUnverified.isSelected()) {
	    	complaint.setStatus(false);
	    } else {
	    	complaint.setStatus(true);
	    }
	    
	    ComplaintDAO compDAO = new ComplaintDAO();
	    compDAO.updateComplaintById(complaint.getId(), complaint);
		
		cplDetailListener.updateEventListener(complaint);
	}
}
