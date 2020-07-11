package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import entity.Complaint;
import entity.Criminal;
import entity.Person;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;

public class IncidentDetailFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textName;
	private JTextField textId;
	private JDateChooser textDate;
	private JTextFieldDateEditor editor;
	private JSpinner timeSpinner;
	private JTextField textPlace;
	private JTextField textDeclarantName;
	private JTextArea textDetail;
	private JTable table;
	private IncidentDetailTableModel tableModel;
	private TableIncidentDetailListener tableListener;
	private JScrollPane jpTable, jpDetail;
	public SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
	public SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
	public SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					IncidentDetailFrame frame = new IncidentDetailFrame();
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
	public IncidentDetailFrame(Complaint inc) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 843, 588);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setTitle("Incident Detail");
		
//		INCIDENT NAME
		JLabel lblName = new JLabel("Incident Name:");	
		textName = new JTextField();
		textName.setColumns(10);
		textName.setText(inc.getName());

//		INCIDENT ID
		JLabel lblID = new JLabel("ID:");
		textId = new JTextField();
		textId.setColumns(10);
		textId.setText(String.valueOf(inc.getId()));
		textId.setEditable(false);

//		INCIDENT DATETIME
		JLabel lblIDate = new JLabel("Date:");	
		textDate = new JDateChooser();
		editor = (JTextFieldDateEditor) textDate.getDateEditor();
		textDate.setDateFormatString("yyyy-MM-dd");
		textDate.setDate(inc.getDatetime());
		
		JLabel lblTime = new JLabel("Time:");
		timeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
		timeSpinner.setEditor(timeEditor);
		Date getDateTime = new java.sql.Timestamp(inc.getDatetime().getTime());
		Pattern pattern = Pattern.compile("..:..:..");
		Matcher matcher = pattern.matcher(getDateTime.toString());
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (matcher.find()) {
					try {
						timeSpinner.setValue(new SimpleDateFormat("HH:mm:ss").parse(matcher.group(0)));
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
//		INCIDENT PLACE
		JLabel lblPlace = new JLabel("Place:");		
		textPlace = new JTextField();
		textPlace.setColumns(10);
		textPlace.setText(inc.getPlace());

//		INCIDENT DECLARANT NAME
		JLabel lblDeclarantName = new JLabel("Declarant Name:");		
		textDeclarantName = new JTextField();
		textDeclarantName.setColumns(10);
		textDeclarantName.setText(inc.getDeclarantName());

//		INCIDENT DETAIL
		JLabel lblDetail = new JLabel("Detail:");		
		textDetail = new JTextArea();	
		textDetail.setLineWrap(true);
		textDetail.setWrapStyleWord(true);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				textDetail.setText(inc.getDetail());
			}
		});
		
		jpDetail = new JScrollPane(textDetail);
		jpDetail.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


// 		INCIDENT LIST OF CRIMINAL
		JLabel lblCriminalTable = new JLabel("List of Criminals:");
		
		tableModel = new IncidentDetailTableModel();
		table = new JTable(tableModel);
		
		jpTable = new JScrollPane(table);
		jpTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		table.setBorder(BorderFactory.createEtchedBorder());
		
		// ALIGN TEXT IN CENTER
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < 8; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnUpdateActionPerformed(e, inc.getId());
			}
		});
		btnUpdate.setFocusPainted(false);
		btnUpdate.setBackground(Color.YELLOW);
		

		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(32)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDeclarantName, GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblName)
								.addComponent(lblID)
								.addComponent(lblIDate)
								.addComponent(lblPlace)
								.addComponent(lblDetail)
								.addComponent(lblCriminalTable))
							.addGap(52)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(textId, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
										.addContainerGap())
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(textDeclarantName, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
													.addComponent(textPlace, Alignment.LEADING)
													.addComponent(textDate, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(textName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
												.addGap(32)
												.addComponent(lblTime)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(timeSpinner, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)))
										.addGap(413)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(jpDetail, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
										.addComponent(jpTable, Alignment.LEADING)
										.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE))
									.addContainerGap())))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblTime)
							.addComponent(timeSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblName)
								.addComponent(textName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(11)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblID)
								.addComponent(textId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblIDate)
								.addComponent(textDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPlace)
						.addComponent(textPlace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDeclarantName)
						.addComponent(textDeclarantName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDetail)
						.addComponent(jpDetail, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCriminalTable)
						.addComponent(jpTable, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE))
					.addGap(30)
					.addComponent(btnUpdate)
					.addGap(27))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void setData(List<Criminal> db) {
		tableModel.setData(db);
	}

	public void refresh() {
		tableModel.fireTableDataChanged();
	}
	
	public void setTableListener(TableIncidentDetailListener tableListener) {
		this.tableListener = tableListener;
	}
	
	protected void btnUpdateActionPerformed(ActionEvent e, int incId) {
		// GET INFO OF COMPLAINT
		String getIncName = textName.getText();
		String getDeclarantName = textDeclarantName.getText();
		String getCompDetail = textDetail.getText();
		String getCompPlace = textPlace.getText();

		Date getDateTime = null;
		Date getDate = textDate.getDate();
		Date getTime = (Date) timeSpinner.getValue();
		String DateTime = sdf0.format(getDate) + " " + sdf1.format(getTime);
		try {
			getDateTime = sdf2.parse(DateTime);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		Complaint incident = new Complaint(incId, getIncName, getDateTime, getCompPlace, getDeclarantName,
				getCompDetail, true);

		tableListener.tableEventUpdated(incident);
	}
}
