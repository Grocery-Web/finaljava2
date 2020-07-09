package view;

import java.awt.Color;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Dimension;

import entity.Complaint;
import entity.Criminal;
import entity.Person;

public class ComplaintDetailFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textDeclarantName;
	private JTextArea textCompDetail;
	private JTextField textCompId;
	private JTextField textCompPlace;
	private JRadioButton rdbtnUnverified, rdbtnApproved;
	private ButtonGroup group;
	private JTable table;
	private TableComplaintDetailListener tableListener;
	private JTextField textCompName;
	private ComplaintDetailTableModel tableModel;
	private JPopupMenu popup;
	private JScrollPane jpTable, jpDetail;
	private Complaint complaint;
	private JSpinner timeSpinner;
	private JDateChooser complaintDate;
	private JTextFieldDateEditor editor;
	public JButton btnUpdate, btnSubmit;
	private SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public ComplaintDetailFrame(Complaint cpl) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setTitle("Complaint Details");

		// CREATE COMPONENT
		JLabel lblCompName = new JLabel("Complaint Name");

		JLabel lblCompId = new JLabel("Complaint ID:");

		JLabel lblCompDate = new JLabel("Date:");

		JLabel lblTime = new JLabel("Time:");

		JLabel lblCompPlace = new JLabel("Place:");

		JLabel lblDeclarantName = new JLabel("Declarant Name:");

		JLabel lblCompDetail = new JLabel("Detail:");

		JLabel lblCompStatus = new JLabel("Verify Status:");

		btnSubmit = new JButton("Submit");
		btnSubmit.setFocusPainted(false);
		btnSubmit.setBackground(Color.YELLOW);

		btnUpdate = new JButton("Update");
		btnUpdate.setFocusPainted(false);
		btnUpdate.setBackground(Color.GREEN);

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
		textCompDetail.setEditable(true);
		jpDetail = new JScrollPane(textCompDetail);
		jpDetail.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		complaintDate = new JDateChooser();
		editor = (JTextFieldDateEditor) complaintDate.getDateEditor();
		editor.setEditable(false);
		complaintDate.setDateFormatString("yyyy-MM-dd");
		complaintDate.setDate(cpl.getDatetime());

		timeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
		timeSpinner.setEditor(timeEditor);
		Date getDateTime = new java.sql.Timestamp(cpl.getDatetime().getTime());
		Pattern pattern = Pattern.compile("..:..:..");
		Matcher matcher = pattern.matcher(getDateTime.toString());
		if (matcher.find()) {
			try {
				timeSpinner.setValue(new SimpleDateFormat("HH:mm:ss").parse(matcher.group(0)));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}

		textCompPlace = new JTextField();
		textCompPlace.setColumns(10);
		textCompPlace.setText(cpl.getPlace());

		// RADIO BUTTON GROUP
		rdbtnUnverified = new JRadioButton("Unverified");
		rdbtnApproved = new JRadioButton("Approved");
		rdbtnUnverified.setActionCommand("Unverified");
		rdbtnApproved.setActionCommand("Approved");
		group = new ButtonGroup();
		group.add(rdbtnUnverified);
		group.add(rdbtnApproved);

		// TABLE LIST OF SUSPECT
		JLabel lblSuspectList = new JLabel("List of Suspect:");

		tableModel = new ComplaintDetailTableModel();
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

		// POPUP OPTION PANEL
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

		// BUTTON ACTION LISTENER
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

		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSubmitActionPerformed(e, cpl.getId());
			}
		});

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnUpdateActionPerformed(e, cpl.getId());
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(33)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblCompStatus)
										.addContainerGap())
								.addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane
										.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane
												.createSequentialGroup().addGroup(gl_contentPane
														.createParallelGroup(Alignment.LEADING)
														.addComponent(lblCompName).addComponent(
																lblCompId)
														.addComponent(lblCompDate).addComponent(lblCompPlace)
														.addComponent(lblDeclarantName).addComponent(lblCompDetail))
												.addGap(18)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
														.addComponent(
																jpDetail, GroupLayout.DEFAULT_SIZE, 707,
																Short.MAX_VALUE)
														.addGroup(gl_contentPane.createSequentialGroup()
																.addGroup(gl_contentPane
																		.createParallelGroup(Alignment.LEADING, false)
																		.addComponent(complaintDate,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(textDeclarantName)
																		.addComponent(textCompId)
																		.addComponent(textCompName)
																		.addComponent(textCompPlace))
																.addGap(18)
																.addComponent(
																		lblTime, GroupLayout.PREFERRED_SIZE, 34,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(timeSpinner, GroupLayout.PREFERRED_SIZE,
																		78, GroupLayout.PREFERRED_SIZE))))
										.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblSuspectList)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
														.addGroup(gl_contentPane.createSequentialGroup().addGap(18)
																.addComponent(rdbtnUnverified).addGap(18)
																.addComponent(rdbtnApproved)
																.addPreferredGap(ComponentPlacement.RELATED, 143,
																		Short.MAX_VALUE)
																.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE,
																		199, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE,
																		199, GroupLayout.PREFERRED_SIZE))
														.addGroup(gl_contentPane.createSequentialGroup().addGap(24)
																.addComponent(jpTable, GroupLayout.PREFERRED_SIZE, 700,
																		Short.MAX_VALUE)))))
										.addGap(36)))));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(25)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblCompName).addComponent(
						textCompName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblCompId).addComponent(
						textCompId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblCompDate)
								.addComponent(lblTime).addComponent(timeSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(complaintDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textCompPlace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCompPlace))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textDeclarantName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDeclarantName))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblCompDetail)
						.addComponent(jpDetail, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(lblSuspectList)
						.addComponent(jpTable, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
						.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblCompStatus)
								.addComponent(rdbtnUnverified).addComponent(rdbtnApproved))
						.addGap(10))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnSubmit)
										.addComponent(btnUpdate))
								.addContainerGap()))));
		contentPane.setLayout(gl_contentPane);
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

	protected void btnSubmitActionPerformed(ActionEvent e, int cplId) {
		int action = JOptionPane.showConfirmDialog(null,
				"This action means the complaint will be upgraded to be a serious situation. Do you really want to verify?",
				"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);

		if (action == JOptionPane.OK_OPTION && tableListener != null) {

			// GET INFO OF COMPLAINT
			String getCompName = textCompName.getText();
			String getDeclarantName = textDeclarantName.getText();
			String getCompDetail = textCompDetail.getText();
			String getCompPlace = textCompPlace.getText();

			Date getDateTime = null;
			Date getDate = complaintDate.getDate();
			Date getTime = (Date) timeSpinner.getValue();
			String DateTime = sdf0.format(getDate) + " " + sdf1.format(getTime);
			try {
				getDateTime = sdf2.parse(DateTime);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

			Complaint complaint = new Complaint(cplId, getCompName, getDateTime, getCompPlace, getDeclarantName,
					getCompDetail, true);

			// GET LIST INFO OF CRIMINALS
			Criminal cri = new Criminal();
			HashMap<Person, String> map = tableModel.getListPerson();
			ArrayList<Criminal> list = new ArrayList<Criminal>();

			map.forEach((person, crimeType) -> {
				int rating;

				cri.setPersonalId(person.getPersonalId());
				cri.setComplainttId(cplId);
				cri.setPunishment("in process");
				switch (crimeType) {
				case "Assault and Battery":
					rating = 2;
					break;
				case "Kidnapping":
					rating = 5;
					break;
				case "Homicide":
					rating = 5;
					break;
				case "Rape":
					rating = 3;
					break;
				case "False Imprisonment":
					rating = 3;
					break;
				case "Theft":
					rating = 2;
					break;
				case "Arson":
					rating = 1;
					break;
				case "False Pretenses":
					rating = 4;
					break;
				case "White Collar Crimes":
					rating = 4;
					break;
				case "Receipt of Stolen Goods":
					rating = 1;
					break;
				default:
					rating = 3;
				}
				;
				cri.setRating(rating);
				list.add(cri);
			});
			tableListener.tableEventSubmited(complaint, list);
		}
	}

	protected void btnUpdateActionPerformed(ActionEvent e, int cplId) {
		// GET INFO OF COMPLAINT
		String getCompName = textCompName.getText();
		String getDeclarantName = textDeclarantName.getText();
		String getCompDetail = textCompDetail.getText();
		String getCompPlace = textCompPlace.getText();

		Date getDateTime = null;
		Date getDate = complaintDate.getDate();
		Date getTime = (Date) timeSpinner.getValue();
		String DateTime = sdf0.format(getDate) + " " + sdf1.format(getTime);
		try {
			getDateTime = sdf2.parse(DateTime);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		Complaint complaint = new Complaint(cplId, getCompName, getDateTime, getCompPlace, getDeclarantName,
				getCompDetail, false);

		tableListener.tableEventUpdated(complaint);
	}
}
