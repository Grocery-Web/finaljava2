package view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import entity.*;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class VictimFormPanel extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton okButton, cancelButton;
	public JLabel lblVictimName, lblIncident, lblNewLabel, lblDeadDay, lblDeadTime, lblScene, lblReason;
	public JTextField txtVictimName;
	public FilterComplaintComboBox filterComplaintComboBox;
	public JRadioButton rdbtnAlive, rdbtnDead;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public JPanel panelDeathDetails;
	public JDateChooser deadDay;
	private JTextFieldDateEditor editor;
	public JSpinner deadTime;
	public JTextField txtScene;
	public JScrollPane scrollPane;
	public JTextArea txtReason;
	private TableVictimListener victimListener;
	private String s = Character.toString("\u2713".toCharArray()[0]);
	public JLabel q1, q2, q3, q4, q5;
	public boolean cd1, cd2, cd3, cd4, cd5;
	public SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
	public SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
	public SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * Create the dialog.
	 */
	public VictimFormPanel(Person ps, List<Complaint> list) {
		setResizable(false);
		setTitle("Add new victim");
		setBounds(100, 100, 470, 443);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			buttonPane = new JPanel();
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Victim victim = new Victim();
						victim.setPersonalId(ps.getPersonalId());
						victim.setComplaintID(((Complaint) filterComplaintComboBox.getSelectedItem()).getId());
						if (rdbtnAlive.isSelected()) {
							victim.setAlive(true);
						} else {
							victim.setAlive(false);
							Date deathTime = null;
							String d = sdf0.format(deadDay.getDate());
							String t = sdf1.format(deadTime.getValue());
							try {
								deathTime = sdf2.parse(d + " " + t);
							} catch (Exception e2) {
								e2.printStackTrace();
							}
							victim.setDeathTime(deathTime);
							victim.setDeathPlace(txtScene.getText());
							victim.setDeathReason(txtReason.getText());
						}
						if (victimListener != null) {
							victimListener.linkNewVictim(victim);
						}
					}
				});
				okButton.setFocusPainted(false);
				okButton.setActionCommand("OK");
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setFocusPainted(false);
				cancelButton.setActionCommand("Cancel");
			}
		}
		
		lblVictimName = new JLabel("Victim's name: ");
		
		txtVictimName = new JTextField();
		txtVictimName.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtVictimName.setText(ps.getName());
		txtVictimName.setBorder(null);
		txtVictimName.setEditable(false);
		txtVictimName.setColumns(10);
		
		lblIncident = new JLabel("Incident");
		filterComplaintComboBox = new FilterComplaintComboBox(list);
		q1 = new JLabel();
		filterComplaintComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cd1Check();
				checkUnlock();
			}
		});
		filterComplaintComboBox.setSelectedIndex(-1);
		
		lblNewLabel = new JLabel("Life Status");
		rdbtnAlive = new JRadioButton("Alive");
		rdbtnAlive.setFocusPainted(false);
		buttonGroup.add(rdbtnAlive);
		
		rdbtnDead = new JRadioButton("Dead");
		rdbtnDead.setFocusPainted(false);
		buttonGroup.add(rdbtnDead);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (ps.getAlive()) {
					rdbtnAlive.setSelected(true);
				}
			}
		});
		
		ItemListener lifeStatus = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				AbstractButton btn = (AbstractButton) e.getSource();
				if (btn.getText() == "Dead") {
					deadDay.setEnabled(true);
					deadTime.setEnabled(true);
					txtScene.setEditable(true);
					txtReason.setEditable(true); txtReason.setBackground(SystemColor.WHITE);
				} else {
					deadDay.setEnabled(false);
					deadTime.setEnabled(false);
					txtScene.setEditable(false);
					txtReason.setEditable(false); txtReason.setBackground(SystemColor.menu);
				}
			}
		};
		rdbtnAlive.addItemListener(lifeStatus);
		rdbtnDead.addItemListener(lifeStatus);
		
		panelDeathDetails = new JPanel();
		panelDeathDetails.setBorder(new TitledBorder(null, "Death details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		lblDeadDay = new JLabel("Dead day");
		deadDay = new JDateChooser();
		editor = (JTextFieldDateEditor) deadDay.getDateEditor();
		editor.setEditable(false);
		deadDay.setDateFormatString("yyyy-MM-dd");
		q2 = new JLabel();
		deadDay.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				cd2Check(); cd3Check();
				checkUnlock();
			}
		});
		
		lblDeadTime = new JLabel("Dead time");	
		deadTime = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(deadTime, "HH:mm:ss");
		deadTime.setEditor(timeEditor);
		try {
			deadTime.setValue(sdf1.parse("00:00:00"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		q3 = new JLabel();
		deadTime.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				cd3Check();
				checkUnlock();
			}
		});
		
		lblScene = new JLabel("Scene");
		txtScene = new JTextField();
		txtScene.setColumns(10);
		q4 = new JLabel();
		txtScene.getDocument().addDocumentListener(new DocumentListener() {
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
		
		lblReason = new JLabel("Reason");
		scrollPane = new JScrollPane();
		txtReason = new JTextArea();
		txtReason.setWrapStyleWord(true);
		txtReason.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtReason.setLineWrap(true);
		scrollPane.setViewportView(txtReason);
		q5 = new JLabel();
		txtReason.getDocument().addDocumentListener(new DocumentListener() {
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
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(contentPanel, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
				.addComponent(buttonPane, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(contentPanel, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
		gl_buttonPane.setHorizontalGroup(
			gl_buttonPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPane.createSequentialGroup()
					.addGap(100)
					.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
					.addGap(78))
		);
		gl_buttonPane.setVerticalGroup(
			gl_buttonPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPane.createSequentialGroup()
					.addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(okButton)
						.addComponent(cancelButton))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		GroupLayout gl_panelDeathDetails = new GroupLayout(panelDeathDetails);
		gl_panelDeathDetails.setHorizontalGroup(
			gl_panelDeathDetails.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDeathDetails.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_panelDeathDetails.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lblReason, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblScene, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblDeadTime, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblDeadDay, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
					.addGap(40)
					.addGroup(gl_panelDeathDetails.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelDeathDetails.createSequentialGroup()
							.addGroup(gl_panelDeathDetails.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(deadDay, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txtScene, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
								.addComponent(deadTime, Alignment.LEADING))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelDeathDetails.createParallelGroup(Alignment.LEADING)
								.addComponent(q4, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
								.addComponent(q3, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
								.addComponent(q2, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panelDeathDetails.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(q5, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)))
					.addGap(3))
		);
		gl_panelDeathDetails.setVerticalGroup(
			gl_panelDeathDetails.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDeathDetails.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDeathDetails.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelDeathDetails.createSequentialGroup()
							.addGroup(gl_panelDeathDetails.createParallelGroup(Alignment.LEADING)
								.addComponent(q2, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
								.addGroup(gl_panelDeathDetails.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(lblDeadDay)
									.addComponent(deadDay, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelDeathDetails.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDeadTime)
								.addComponent(deadTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(q3, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelDeathDetails.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelDeathDetails.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblScene)
									.addComponent(txtScene, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(q4, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelDeathDetails.createParallelGroup(Alignment.LEADING)
								.addComponent(lblReason)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_panelDeathDetails.createSequentialGroup()
							.addComponent(q5, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addGap(48))))
		);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(48)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblIncident, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblVictimName, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(rdbtnAlive, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(rdbtnDead, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(txtVictimName, Alignment.LEADING)
										.addComponent(filterComplaintComboBox, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(q1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addPreferredGap(ComponentPlacement.RELATED, 11, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(panelDeathDetails, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVictimName)
						.addComponent(txtVictimName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIncident)
						.addComponent(filterComplaintComboBox, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(q1))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(rdbtnAlive)
						.addComponent(rdbtnDead))
					.addGap(18)
					.addComponent(panelDeathDetails, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
		);
		
		panelDeathDetails.setLayout(gl_panelDeathDetails);
		contentPanel.setLayout(gl_contentPanel);
		buttonPane.setLayout(gl_buttonPane);
		getContentPane().setLayout(groupLayout);
		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
	}
	
	public void cd1Check() {
		if (filterComplaintComboBox.getSelectedIndex() != -1) {
			q1.setText(s); q1.setForeground(new Color(0, 153, 51)); 
			q1.setToolTipText(null);
			cd1 = true;
		} else {
			q1.setText("?"); q1.setForeground(Color.RED); 
			q1.setToolTipText("<html><div style='margin:0 -3 0 -3; padding: 0 3 0 3; background:yellow;'>Please select an incident from list.</div></html>"); 
			cd1 = false;
		}
	}
	
	public void cd2Check() {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal3 = Calendar.getInstance();
		if (deadDay.getDate() != null && filterComplaintComboBox.getSelectedIndex() != -1) {
			cal1.setTime(deadDay.getDate());
			cal3.setTime(((Complaint) filterComplaintComboBox.getSelectedItem()).getDatetime());
			LocalDate date1 = LocalDate.of(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH) + 1, cal1.get(Calendar.DATE));
			LocalDate date2 = LocalDate.now();
			LocalDate date3 = LocalDate.of(cal3.get(Calendar.YEAR), cal3.get(Calendar.MONTH) + 1, cal3.get(Calendar.DATE));
			if (date1.compareTo(date2) <= 0 && date1.compareTo(date3) >= 0) {
				q2.setText(s); q2.setForeground(new Color(0, 153, 51));
				q2.setToolTipText(null);
				cd2 = true;
			} else {
				q2.setText("?"); q2.setForeground(Color.RED);
				q2.setToolTipText("<html><div style='margin:0 -3 0 -3; padding: 0 3 0 3; background:yellow;'>Select a date that is no later than today but no sooner than incident date.</div></html>");
				cd2 = false;
			}
		}
	}
	
	public void cd3Check() {
		Date date1 = null;
		try {
			if (deadDay.getDate() != null && filterComplaintComboBox.getSelectedIndex() != -1) {
				String d = sdf0.format(deadDay.getDate());
				String t = sdf1.format(deadTime.getValue());
				date1 = sdf2.parse(d + " " + t);
				Date date2 = new Date();
				Date date3 = ((Complaint) filterComplaintComboBox.getSelectedItem()).getDatetime();
				if (date1.compareTo(date2) <= 0 && date1.compareTo(date3) >= 0) {
					q3.setText(s); q3.setForeground(new Color(0, 153, 51));
					q3.setToolTipText(null); 
					cd3 = true;
				} else {
					q3.setText("?"); q3.setForeground(Color.RED);
					q3.setToolTipText("<html><div style='margin:0 -3 0 -3; padding: 0 3 0 3; background:yellow;'>Select a time that is no later than current time but no sooner than incident time.</div></html>");
					cd3 = false;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void cd4Check() {
		if (!txtScene.getText().equals("") && txtScene.getText().matches("(.|\\s){2,}")) {
			txtScene.setBorder(new LineBorder(Color.GREEN));
			q4.setText(s); q4.setForeground(new Color(0, 153, 51));
			q4.setToolTipText(null);
			cd4 = true;
		} else {
			txtScene.setBorder(new LineBorder(Color.RED));
			q4.setText("?"); q4.setForeground(Color.RED);
			q4.setToolTipText("<html><div style='margin:0 -3 0 -3; padding: 0 3 0 3; background:yellow;'>Minimum length required is 2 characters.</div></html>");
			cd4 = false;
		}
	}
	
	public void cd5Check() {
		if (!txtReason.getText().equals("") && txtReason.getText().matches("(.|\\s){10,}")) {
			txtReason.setBorder(new LineBorder(Color.GREEN));
			q5.setText(s); q5.setForeground(new Color(0, 153, 51));
			q5.setToolTipText(null);
			cd5 = true;
		} else {
			txtReason.setBorder(new LineBorder(Color.RED));
			q5.setText("?"); q5.setForeground(Color.RED);
			q5.setToolTipText("<html><div style='margin:0 -3 0 -3; padding: 0 3 0 3; background:yellow;'>Minimum length required is 10 characters.</div></html>");
			cd5 = false;
		}
	}
	
	public void checkUnlock() {
		boolean unlock = (cd1 && rdbtnAlive.isSelected()) || (cd1 && rdbtnDead.isSelected() && cd2 && cd3 && cd4 && cd5);
		okButton.setEnabled(unlock);
	}
	
	public void setFormListener(TableVictimListener victimListener) {
		this.victimListener = victimListener;
	}
}
