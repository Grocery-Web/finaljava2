package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import entity.Complaint;

public class ComplaintFormPanel extends JPanel{
	private JDateChooser complaintDate;
	private JTextFieldDateEditor editor;
	private JSpinner timeSpinner;
	private JTextField name;
	private JTextField place;
	private JTextField declarantName;
	private JTextArea detail;
	private JLabel q1, q2, q3, q4, q5, q6;
	private boolean cd1, cd2, cd3, cd4, cd5, cd6;
	private JButton submitBtn;
	private JScrollPane scroll;
	private String s = Character.toString("\u2713".toCharArray()[0]);
	public SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
	public SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
	public SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
//	INTERFACE LISTERNER
	private FormComplaintListener cplListener;
	
	public ComplaintFormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 300;
		setPreferredSize(dim);
		setMinimumSize(dim);
		
		complaintDate = new JDateChooser();
		editor = (JTextFieldDateEditor) complaintDate.getDateEditor();
		editor.setEditable(false);
		complaintDate.setDateFormatString("yyyy-MM-dd");
		complaintDate.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				cd1Check();
				checkUnlock();
			}
		});
		q1 = new JLabel(); q1.setPreferredSize(new Dimension(10, 20));
		
		timeSpinner = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
		timeSpinner.setEditor(timeEditor);
		timeSpinner.setValue(new Date());
		timeSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				cd2Check();
				checkUnlock();
			}
		});
		q2 = new JLabel(); q2.setPreferredSize(new Dimension(10, 20));
		
		name =  new JTextField(10);
		name.getDocument().addDocumentListener(new DocumentListener() {
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
		q6 = new JLabel(); q6.setPreferredSize(new Dimension(10, 20));
		
		place = new JTextField(10);
		place.getDocument().addDocumentListener(new DocumentListener() {			
			@Override
			public void removeUpdate(DocumentEvent e) {
				cd3Check();
				checkUnlock();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				cd3Check();
				checkUnlock();
			}			
			@Override
			public void changedUpdate(DocumentEvent e) {
				cd3Check();
				checkUnlock();
			}
		});
		q3 = new JLabel(); q3.setPreferredSize(new Dimension(10, 20));
		
		declarantName = new JTextField(10);
		declarantName.getDocument().addDocumentListener(new DocumentListener() {			
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
		q4 = new JLabel(); q4.setPreferredSize(new Dimension(10, 20));
		
		detail = new JTextArea(8, 10);
		detail.getDocument().addDocumentListener(new DocumentListener() {	
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
		q5 = new JLabel(); q5.setPreferredSize(new Dimension(10, 20));
		
		submitBtn = new JButton("Add Complaint");
		submitBtn.setMnemonic(KeyEvent.VK_A);
		checkUnlock();
		
//		ADD SCROLL PANE INTO DETAIL AREA
		detail.setBorder(BorderFactory.createEtchedBorder());
		scroll = new JScrollPane(detail,
			                    JScrollPane.VERTICAL_SCROLLBAR_NEVER,
			                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
//		ACTION PERFORM ON SUBMIT BUTTON
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date getDateTime = null;
				Date getDate = complaintDate.getDate();
				Date getTime = (Date) timeSpinner.getValue();
				String DateTime = sdf0.format(getDate) + " " + sdf1.format(getTime);

				try {
					getDateTime = sdf2.parse(DateTime);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				String getName = name.getText();
				String getPlace = place.getText();
				String getDeclarantName = declarantName.getText();
				String getDetails = detail.getText();
				
				Complaint cpt = new Complaint(getName, getDateTime, getPlace, getDeclarantName, getDetails, false);
				if(cplListener != null) {
					cplListener.insertEventListener(cpt);
				}
			}
		});
		
//		SET FORM LAYOUT
		Border innerBorder = BorderFactory.createTitledBorder("Add Complaint");
		Border outerBorder = BorderFactory.createEmptyBorder(4, 4, 4, 4);
		setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
		
		layoutComponents();
	}
	
//	FUNCTIONS TO CHECK INPUT CONDITIONS
	private void cd1Check() {
		Date date1 = complaintDate.getDate();
		Date date2 = new Date();
		if (date1 != null) {
			if (date1.compareTo(date2) <= 0) {				
				q1.setText(s); q1.setForeground(new Color(0, 153, 51));
				q1.setToolTipText(null);
				cd1 = true;
			} else {
				q1.setText("?"); q1.setForeground(Color.RED);
				q1.setToolTipText("Select a date that is no later than today.");
				cd1 = false;
			}
		}
	}
	
	private void cd2Check() {
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
			q2.setText(s); q2.setForeground(new Color(0, 153, 51));
			q2.setToolTipText(null);
			cd2 = true;
		} else {
			q2.setText("?"); q2.setForeground(Color.RED);
			q2.setToolTipText("Select a time that is no later than current time.");
			cd2 = false;
		}
	}
	
	private void cd3Check() {
		if (!place.getText().equals("") && place.getText().matches("(.|\\s){2,4000}")) {
        	place.setBorder(new LineBorder(Color.GREEN, 1));
        	q3.setText(s); q3.setForeground(new Color(0, 153, 51));
        	q3.setToolTipText(null);
            cd3 = true;
        } else {
        	place.setBorder(new LineBorder(Color.RED, 1));
        	q3.setText("?"); q3.setForeground(Color.RED);
        	q3.setToolTipText("Minimum length required is 2 characters");
        	cd3 = false;
        }
	}
	
	private void cd4Check() {
		if (!declarantName.getText().equals("") && declarantName.getText().matches("[\\D ]{1,50}")) {
        	declarantName.setBorder(new LineBorder(Color.GREEN, 1));
        	q4.setText(s); q4.setForeground(new Color(0, 153, 51));
        	q4.setToolTipText(null);
            cd4 = true;
        } else {
        	declarantName.setBorder(new LineBorder(Color.RED, 1));
        	q4.setText("?"); q4.setForeground(Color.RED);
        	q4.setToolTipText("1 - 50 alphabet and special characters");
        	cd4 = false;
        }
	}
	
	private void cd5Check() {
		if (!detail.getText().equals("") && detail.getText().matches("(.|\\s){20,4000}")) {
        	detail.setBorder(new LineBorder(Color.GREEN, 1));
        	q5.setText(s); q5.setForeground(new Color(0, 153, 51));
        	q5.setToolTipText(null);
            cd5 = true;
        } else {
        	detail.setBorder(new LineBorder(Color.RED, 1));
        	q5.setText("?"); q5.setForeground(Color.RED);
        	q5.setToolTipText("Minimum length required is 20 characters");
        	cd5 = false;
        }
	}
	
	private void cd6Check() {
		if (!name.getText().equals("") && name.getText().matches("(\\d|[a-zA-Z]|\\s){3,50}")) {
        	name.setBorder(new LineBorder(Color.GREEN, 1));
        	q6.setText(s); q6.setForeground(new Color(0, 153, 51));
        	q6.setToolTipText(null);
            cd6 = true;
        } else {
        	name.setBorder(new LineBorder(Color.RED, 1));
        	q6.setText("?"); q6.setForeground(Color.RED);
        	q6.setToolTipText("3 - 50 characters required (alphabetical characters, numbers and spaces).");
        	cd6 = false;
        }
	}
	
	private void checkUnlock() {
		boolean unlock = cd1 && cd2 && cd3 && cd4 && cd5 && cd6;
		submitBtn.setEnabled(unlock);
	}
	
	public void layoutComponents() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
//		This form just have one column, therefore we just use weightY to allocate the space between each components
		
		/////////////// NAME ///////////////////
		gc.weighty = 0.1; // assign at least small additional space between each component on Vertical
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Name: "),gc);
		
		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		add(name, gc);
		
		gc.gridx = 2;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		add(q6, gc);
		
		/////////////// DATE ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Date: "),gc);
		
		gc.gridy = 1;
		gc.gridx = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.LINE_START;
		add(complaintDate, gc);
		
		gc.gridx = 2;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		add(q1, gc);
		
		/////////////// TIME ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Time: "), gc);
		
		gc.gridy = 2;
		gc.gridx = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.LINE_START;
		add(timeSpinner, gc);
		
		gc.gridx = 2;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.LINE_END;
		add(q2, gc);
		
		/////////////// PLACE ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Place: "),gc);
		
		gc.gridy = 3;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(place,gc);
		
		gc.gridy = 3;
		gc.gridx = 2;
		gc.anchor = GridBagConstraints.LINE_END;
		add(q3, gc);
		
		/////////////// DECLARANT NAME ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Declarant Name: "),gc);
		
		gc.gridy = 4;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(declarantName,gc);
		
		gc.gridy = 4;
		gc.gridx = 2;
		gc.anchor = GridBagConstraints.LINE_END;
		add(q4, gc);
		
		/////////////// DETAILS ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Details: "),gc);
		
		gc.gridy++;
		gc.gridx = 0;
		gc.gridwidth = 2;
		gc.anchor = GridBagConstraints.LINE_START;
		add(scroll,gc);
		
		gc.gridy = 6;
		gc.gridx = 2;
		gc.anchor = GridBagConstraints.LINE_START;
		add(q5, gc);
		
		/////////////// SUBMIT BUTTON ///////////////////
		gc.weighty = 2; //all additional space below will be distributed to this component on Vertical
		
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(submitBtn,gc);
		
//		End of Edit Form
	}

	public void setFormListener(FormComplaintListener cplListener) {
		this.cplListener = cplListener;
	}
}
