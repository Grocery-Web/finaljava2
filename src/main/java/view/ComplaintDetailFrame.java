package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;
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
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

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
	private JSpinner timeSpinner;
	private JDateChooser complaintDate;
	private JTextFieldDateEditor editor;
	private JButton btnUpdate, btnSubmit;
	private String s = Character.toString("\u2713".toCharArray()[0]);
	private JLabel q1, q2, q4, q5, q3, q6;
	private boolean cd1, cd2, cd3, cd4, cd5, cd6, cd7, enabledMouseListener;
	public SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
	public SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
	public SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public JLabel q7;
	
	public ComplaintDetailFrame(Complaint cpl) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 913, 500);
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
		SwingUtilities.invokeLater(new Runnable() {	
			@Override
			public void run() {
				textCompName.setText(cpl.getName());	
			}
		});
		textCompName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				cd1Check();
				checkUnlock();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				cd1Check();
				checkUnlock();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				cd1Check();
				checkUnlock();
			}
		});
		
		textCompId = new JTextField();
		textCompId.setColumns(10);
		textCompId.setText(String.valueOf(cpl.getId()));
		textCompId.setEditable(false);

		textDeclarantName = new JTextField();
		textDeclarantName.setColumns(10);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				textDeclarantName.setText(cpl.getDeclarantName());
			}
		});
		textDeclarantName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				cd5Check();
				checkUnlock();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				cd5Check();
				checkUnlock();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				cd5Check();
				checkUnlock();
			}
		});
		
		textCompDetail = new JTextArea();
		textCompDetail.setColumns(10);
		textCompDetail.setLineWrap(true);
		textCompDetail.setWrapStyleWord(true);
		textCompDetail.setEditable(true);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				textCompDetail.setText(cpl.getDetail());
			}
		});
		textCompDetail.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				cd6Check();
				checkUnlock();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				cd6Check();
				checkUnlock();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				cd6Check();
				checkUnlock();
			}
		});
		jpDetail = new JScrollPane(textCompDetail);
		jpDetail.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		complaintDate = new JDateChooser();
		editor = (JTextFieldDateEditor) complaintDate.getDateEditor();
		editor.setEditable(false);
		complaintDate.setDateFormatString("yyyy-MM-dd");
		complaintDate.setDate(cpl.getDatetime());
		complaintDate.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				cd2Check(); cd3Check();
				checkUnlock();
			}
		});

		timeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
		timeSpinner.setEditor(timeEditor);
		Date getDateTime = new java.sql.Timestamp(cpl.getDatetime().getTime());
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
		timeSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				cd3Check();
				checkUnlock();
			}
		});
		
		textCompPlace = new JTextField();
		textCompPlace.setColumns(10);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				textCompPlace.setText(cpl.getPlace());
			}
		});
		textCompPlace.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				cd4Check();
				checkUnlock();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				cd4Check();
				checkUnlock();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				cd4Check();
				checkUnlock();
			}
		});
		
		q1 = new JLabel();
		q2 = new JLabel();
		q4 = new JLabel();
		q5 = new JLabel();
		q3 = new JLabel();
		q6 = new JLabel();
		q7 = new JLabel("?"); q7.setForeground(Color.RED);
		q7.setToolTipText("Add one or more suspects to verify this complaint.");

		// RADIO BUTTON GROUP
		rdbtnUnverified = new JRadioButton("Unverified");
		rdbtnApproved = new JRadioButton("Approved");
		
		if (cpl.isStatus() == false) {
			rdbtnUnverified.setSelected(true);
		}
		
	    group = new ButtonGroup();
	    group.add(rdbtnUnverified);
	    group.add(rdbtnApproved);
	    
	    ItemListener choice = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				AbstractButton btn = (AbstractButton) e.getSource();
				if (btn.getText() == "Approved") {
					textCompName.setEnabled(false);
					complaintDate.setEnabled(false);
					timeSpinner.setEnabled(false);
					textCompPlace.setEnabled(false);
					textDeclarantName.setEnabled(false);
					textCompDetail.setEnabled(false);
					table.setBackground(new Color(240, 240, 240)); table.setFocusable(false);
					enabledMouseListener = false; table.setRowSelectionAllowed(false);
				} else {
					textCompName.setEnabled(true);
					complaintDate.setEnabled(true);
					timeSpinner.setEnabled(true);
					textCompPlace.setEnabled(true);
					textDeclarantName.setEnabled(true);
					textCompDetail.setEnabled(true);
					table.setBackground(Color.WHITE); table.setFocusable(true);
					enabledMouseListener = true; table.setRowSelectionAllowed(true);
				}
			}
		};
		rdbtnUnverified.addItemListener(choice);
		rdbtnApproved.addItemListener(choice);
	
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
		enabledMouseListener = true;

		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (enabledMouseListener) {
					int row = table.rowAtPoint(e.getPoint());
					table.getSelectionModel().setSelectionInterval(row, row);

					if (e.getButton() == MouseEvent.BUTTON3) {
						// When right click on the row of table, the pop up will be showed up
						popup.show(table, e.getX(), e.getY());
					}
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
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(33)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblCompStatus)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblCompName)
										.addComponent(lblCompId)
										.addComponent(lblCompDate)
										.addComponent(lblCompPlace)
										.addComponent(lblDeclarantName)
										.addComponent(lblCompDetail))
									.addGap(18)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(jpDetail, GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
												.addComponent(complaintDate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(textDeclarantName)
												.addComponent(textCompId)
												.addComponent(textCompName)
												.addComponent(textCompPlace))
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(q5, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
												.addComponent(q4, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addComponent(q2, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
													.addGap(13)
													.addComponent(lblTime, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.UNRELATED)
													.addComponent(timeSpinner, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.UNRELATED)
													.addComponent(q3, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
												.addComponent(q1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)))))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblSuspectList)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(18)
											.addComponent(rdbtnUnverified)
											.addGap(18)
											.addComponent(rdbtnApproved)
											.addPreferredGap(ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
											.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(24)
											.addComponent(jpTable, GroupLayout.PREFERRED_SIZE, 707, Short.MAX_VALUE)))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(q6, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
								.addComponent(q7, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
							.addGap(18))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(25)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCompName)
								.addComponent(textCompName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(q1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCompId)
								.addComponent(textCompId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblCompDate)
									.addComponent(timeSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblTime)
									.addComponent(q2, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
									.addComponent(q3, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
								.addComponent(complaintDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(textCompPlace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblCompPlace))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(textDeclarantName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblDeclarantName)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(q4, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(q5, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCompDetail)
								.addComponent(jpDetail, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSuspectList)
								.addComponent(jpTable, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblCompStatus)
										.addComponent(rdbtnUnverified)
										.addComponent(rdbtnApproved))
									.addGap(10))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnSubmit)
										.addComponent(btnUpdate))
									.addPreferredGap(ComponentPlacement.RELATED))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(216)
							.addComponent(q6, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addGap(85)
							.addComponent(q7, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
		contentPane.setLayout(gl_contentPane);
	}
	
//  FUNCTIONS TO VALIDATE INPUT
	private void cd1Check() {
		if (!textCompName.getText().equals("") && textCompName.getText().matches("(\\d|[a-zA-Z]|\\s){3,50}")) {
			textCompName.setBorder(new LineBorder(Color.GREEN, 1));
			q1.setText(s); q1.setForeground(new Color(0, 153, 51));
	       	q1.setToolTipText(null);
	       	cd1 = true;
		} else {
			textCompName.setBorder(new LineBorder(Color.RED, 1));
			q1.setText("?"); q1.setForeground(Color.RED);
	       	q1.setToolTipText("3 - 50 characters required (alphabetical characters, numbers and spaces).");
	       	cd1 = false;
		}
	}
	
	private void cd2Check() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(complaintDate.getDate());
		LocalDate date1 = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
		LocalDate date2 = LocalDate.now();
		if (date1 != null) {
			if (date1.compareTo(date2) <= 0) {			
				q2.setText(s); q2.setForeground(new Color(0, 153, 51));
				q2.setToolTipText(null); editor.setBorder(new LineBorder(Color.GREEN, 1));
				cd2 = true;
			} else {
				q2.setText("?"); q2.setForeground(Color.RED); editor.setBorder(new LineBorder(Color.RED, 1));
				q2.setToolTipText("Select a date that is no later than today.");
				cd2 = false;
			}
		}
	}
	
	private void cd3Check() {
		Date date1 = null;
		try {
			if (complaintDate.getDate() != null) {
				String d = sdf0.format(complaintDate.getDate());
				String t = sdf1.format(timeSpinner.getValue());
				date1 = sdf2.parse(d + " " + t);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date date2 = new Date();
		if (date1.compareTo(date2) <= 0) {
			q3.setText(s); q3.setForeground(new Color(0, 153, 51)); timeSpinner.getEditor().setBorder(new LineBorder(Color.GREEN));
			q3.setToolTipText(null); 
			cd3 = true;
		} else {
			q3.setText("?"); q3.setForeground(Color.RED); timeSpinner.getEditor().setBorder(new LineBorder(Color.RED));
			q3.setToolTipText("Select a time that is no later than current time.");
			cd3 = false;
		}
	}
	
	private void cd4Check() {
		if (!textCompPlace.getText().equals("") && textCompPlace.getText().matches("(.|\\s){2,4000}")) {
        	textCompPlace.setBorder(new LineBorder(Color.GREEN, 1));
        	q4.setText(s); q4.setForeground(new Color(0, 153, 51));
        	q4.setToolTipText(null);
            cd4 = true;
        } else {
        	textCompPlace.setBorder(new LineBorder(Color.RED, 1));
        	q4.setText("?"); q4.setForeground(Color.RED);
        	q4.setToolTipText("Minimum length required is 2 characters");
        	cd4 = false;
        }
	}
	
	private void cd5Check() {
		if (!textDeclarantName.getText().equals("") && textDeclarantName.getText().matches("[\\D ]{1,50}")) {
        	textDeclarantName.setBorder(new LineBorder(Color.GREEN, 1));
        	q5.setText(s); q5.setForeground(new Color(0, 153, 51));
        	q5.setToolTipText(null);
            cd5 = true;
        } else {
        	textDeclarantName.setBorder(new LineBorder(Color.RED, 1));
        	q5.setText("?"); q5.setForeground(Color.RED);
        	q5.setToolTipText("1 - 50 alphabet and special characters");
        	cd5 = false;
        }
	}
	
	private void cd6Check() {
		if (!textCompDetail.getText().equals("") && textCompDetail.getText().matches("(.|\\s){20,4000}")) {
        	textCompDetail.setBorder(new LineBorder(Color.GREEN, 1));
        	q6.setText(s); q6.setForeground(new Color(0, 153, 51));
        	q6.setToolTipText(null);
        	cd6 = true;
        } else {
        	textCompDetail.setBorder(new LineBorder(Color.RED, 1));
        	q6.setText("?"); q6.setForeground(Color.RED);
        	q6.setToolTipText("Minimum length required is 20 characters");
        	cd6 = false;
        }
	}
	
	private void cd7Check() {
		if (tableModel.getRowCount() > 0) {
			jpTable.setBorder(new LineBorder(new Color(0, 153, 51), 2));
			q7.setText(s); q7.setForeground(new Color(0, 153, 51));
			q7.setToolTipText(null);
			cd7 = true;
		} else {
			jpTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			q7.setText("?"); q7.setForeground(Color.RED);
			q7.setToolTipText("Add one or more suspects to verify this complaint.");
			cd7 = false;
		}
	}
	
	private void checkUnlock() {
		boolean unlock = cd1 && cd2 && cd3 && cd4 && cd5 && cd6 && rdbtnUnverified.isSelected();
		btnUpdate.setEnabled(unlock);
		
		boolean approve = cd1 && cd2 && cd3 && cd4 && cd5 && cd6 && cd7;
		rdbtnApproved.setEnabled(approve);
		
		btnSubmit.setEnabled(rdbtnApproved.isSelected());
	}
	
	public void setData(HashMap<Person, String> db) {
		tableModel.setData(db);
		cd7Check(); checkUnlock();
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
			HashMap<Person, String> map = tableModel.getListPerson();
			List<Criminal> list = new ArrayList<Criminal>();		
			
			for (Map.Entry<Person, String> entry : map.entrySet()) {
				int rating;
				Criminal cri = new Criminal();
				
				cri.setPersonalId(entry.getKey().getPersonalId());
				cri.setComplaintId(cplId);
				cri.setPunishment("in process");
				switch (entry.getValue()) {
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
					rating = 5;
				}
				;
				cri.setRating(rating);
				list.add(cri);
			}
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
