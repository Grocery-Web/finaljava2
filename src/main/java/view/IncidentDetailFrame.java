package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
import entity.Victim;

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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
	private IncidentDetailVictimTableModel victimTableModel;
	private TableIncidentDetailListener tableListener;
	private JScrollPane jpTable, jpDetail, jpVictimTable;
	private String s = Character.toString("\u2713".toCharArray()[0]);
	private JLabel q1, q2, q4, q5, q3, q6;
	private boolean cd1, cd2, cd3, cd4, cd5, cd6;
	public SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
	public SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
	public SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private JTable tableVictim;
	private JButton btnUpdate;

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
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 868, 726);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setTitle("Incident Detail");
		
//		INCIDENT NAME
		JLabel lblName = new JLabel("Incident Name:");	
		textName = new JTextField();
		textName.setColumns(10);
		SwingUtilities.invokeLater(new Runnable() {	
			@Override
			public void run() {
				textName.setText(inc.getName());	
			}
		});
		textName.getDocument().addDocumentListener(new DocumentListener() {
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
		editor.setEditable(false);
		textDate.setDateFormatString("yyyy-MM-dd");
		textDate.setDate(inc.getDatetime());
		textDate.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				cd2Check(); cd3Check();
				checkUnlock();
			}
		});
		
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
		timeSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				cd3Check();
				checkUnlock();
			}
		});
		
//		INCIDENT PLACE
		JLabel lblPlace = new JLabel("Place:");		
		textPlace = new JTextField();
		textPlace.setColumns(10);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				textPlace.setText(inc.getPlace());
			}
		});
		textPlace.getDocument().addDocumentListener(new DocumentListener() {
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

//		INCIDENT DECLARANT NAME
		JLabel lblDeclarantName = new JLabel("Declarant Name:");		
		textDeclarantName = new JTextField();
		textDeclarantName.setColumns(10);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				textDeclarantName.setText(inc.getDeclarantName());
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
		textDetail.getDocument().addDocumentListener(new DocumentListener() {
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
		
		jpDetail = new JScrollPane(textDetail);
		jpDetail.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


// 		INCIDENT LIST OF CRIMINAL
		JLabel lblCriminalTable = new JLabel("List of Criminals:");
		
		tableModel = new IncidentDetailTableModel();
		table = new JTable(tableModel);
		
		jpTable = new JScrollPane(table);
		jpTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		table.setBorder(BorderFactory.createEtchedBorder());
		

//		LIST OF VICTIM
		JLabel lblVictimTable = new JLabel("List of Victims:");
		
		victimTableModel = new IncidentDetailVictimTableModel();
		tableVictim = new JTable(victimTableModel);
		
		jpVictimTable = new JScrollPane(tableVictim);
		jpVictimTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		tableVictim.setBorder(BorderFactory.createEtchedBorder());

		// ALIGN TEXT IN CENTER
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < 8; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			tableVictim.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		q1 = new JLabel();
		q2 = new JLabel();
		q4 = new JLabel();
		q5 = new JLabel();
		q3 = new JLabel();
		q6 = new JLabel();
		
// 		UPDATE BUTTON		
		btnUpdate = new JButton("Update");
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
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblVictimTable)
							.addContainerGap())
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(lblDeclarantName, GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
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
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(textId, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
										.addContainerGap())
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(textDeclarantName, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
													.addComponent(textPlace, Alignment.LEADING)
													.addComponent(textDate, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(textName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
													.addComponent(q1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
													.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
														.addComponent(q4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(q2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)))
												.addPreferredGap(ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
												.addComponent(lblTime)
												.addGap(18)
												.addComponent(timeSpinner, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(q3, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)))
										.addGap(10)
										.addComponent(q5, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
										.addGap(238))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(jpDetail, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
											.addComponent(jpTable, Alignment.LEADING)
											.addComponent(jpVictimTable, Alignment.LEADING)
											.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(q6, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
										.addContainerGap()))))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblTime)
							.addComponent(timeSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(q3, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(q1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblName)
									.addComponent(textName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(11)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblID)
										.addComponent(textId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblIDate)
										.addComponent(textDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addComponent(q2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPlace)
						.addComponent(textPlace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(q4, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDeclarantName)
						.addComponent(textDeclarantName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(q5, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDetail)
								.addComponent(jpDetail, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCriminalTable)
								.addComponent(jpTable, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblVictimTable)
								.addComponent(jpVictimTable, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(57)
							.addComponent(q6, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(29))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void setData(List<Criminal> db) {
		tableModel.setData(db);
	}
	
	public void setDataVictim(List<Victim> db) {
		victimTableModel.setData(db);
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
	
//  FUNCTIONS TO VALIDATE INPUT
	private void cd1Check() {
		if (!textName.getText().equals("") && textName.getText().matches("(\\d|[a-zA-Z]|\\s){3,50}")) {
			textName.setBorder(new LineBorder(Color.GREEN, 1));
			q1.setText(s); q1.setForeground(new Color(0, 153, 51));
	       	q1.setToolTipText(null);
	       	cd1 = true;
		} else {
			textName.setBorder(new LineBorder(Color.RED, 1));
			q1.setText("?"); q1.setForeground(Color.RED);
	       	q1.setToolTipText("3 - 50 characters required (alphabetical characters, numbers and spaces).");
	       	cd1 = false;
		}
	}
	
	private void cd2Check() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(textDate.getDate());
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
			if (textDate.getDate() != null) {
				String d = sdf0.format(textDate.getDate());
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
		if (!textPlace.getText().equals("") && textPlace.getText().matches("(.|\\s){2,4000}")) {
			textPlace.setBorder(new LineBorder(Color.GREEN, 1));
        	q4.setText(s); q4.setForeground(new Color(0, 153, 51));
        	q4.setToolTipText(null);
            cd4 = true;
        } else {
        	textPlace.setBorder(new LineBorder(Color.RED, 1));
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
		if (!textDetail.getText().equals("") && textDetail.getText().matches("(.|\\s){20,4000}")) {
			textDetail.setBorder(new LineBorder(Color.GREEN, 1));
        	q6.setText(s); q6.setForeground(new Color(0, 153, 51));
        	q6.setToolTipText(null);
        	cd6 = true;
        } else {
        	textDetail.setBorder(new LineBorder(Color.RED, 1));
        	q6.setText("?"); q6.setForeground(Color.RED);
        	q6.setToolTipText("Minimum length required is 20 characters");
        	cd6 = false;
        }
	}
	
	private void checkUnlock() {
		boolean unlock = cd1 && cd2 && cd3 && cd4 && cd5 && cd6;
		btnUpdate.setEnabled(unlock);

	}
}