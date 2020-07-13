package view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entity.Complaint;
import entity.Person;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;

import java.util.List;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class RelevantIncidentForm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton okButton, cancelButton;
	public JLabel lblVictimName, lblIncident, lblNewLabel, lblDeadDay, lblDeadTime, lblScene, lblReason;
	public JTextField txtVictimName;
	public FilterComplaintComboBox filterComplaintComboBox;
	public JRadioButton rdbtnAlive, rdbtnDead;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public JPanel panelDeathDetails;
	public JDateChooser dateChooser;
	private JTextFieldDateEditor editor;
	public JSpinner timeSpinner;
	public JTextField txtScene;
	public JScrollPane scrollPane;
	public JTextArea txtReason;
	private RelevantFormListener revListener;
	
	/**
	 * Create the dialog.
	 */
	public RelevantIncidentForm(Person ps, List<Complaint> list) {
		setResizable(false);
		setTitle("Add new victim");
		setBounds(100, 100, 450, 443);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			buttonPane = new JPanel();
			{
				okButton = new JButton("OK");
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
					dateChooser.setEnabled(true);
					timeSpinner.setEnabled(true);
					txtScene.setEditable(true);
					txtReason.setEditable(true); txtReason.setBackground(SystemColor.WHITE);
				} else {
					dateChooser.setEnabled(false);
					timeSpinner.setEnabled(false);
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
		dateChooser = new JDateChooser();
		editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
		editor.setEditable(false);
		dateChooser.setDateFormatString("yyyy-MM-dd");
		
		lblDeadTime = new JLabel("Dead time");	
		timeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
		timeSpinner.setEditor(timeEditor);
		
		lblScene = new JLabel("Scene");
		txtScene = new JTextField();
		txtScene.setColumns(10);
		
		lblReason = new JLabel("Reason");
		scrollPane = new JScrollPane();
		txtReason = new JTextArea();
		txtReason.setWrapStyleWord(true);
		txtReason.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtReason.setLineWrap(true);
		scrollPane.setViewportView(txtReason);
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, 434, GroupLayout.PREFERRED_SIZE)
						.addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, 434, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(contentPanel, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
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
								.addComponent(timeSpinner, Alignment.LEADING)
								.addComponent(dateChooser, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txtScene, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
							.addGap(177))
						.addGroup(gl_panelDeathDetails.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
							.addContainerGap())))
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
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(rdbtnAlive, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(rdbtnDead, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 120, Short.MAX_VALUE))
								.addGroup(Alignment.LEADING, gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(txtVictimName, Alignment.LEADING)
									.addComponent(filterComplaintComboBox, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(panelDeathDetails, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
						.addComponent(filterComplaintComboBox, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(rdbtnAlive)
						.addComponent(rdbtnDead))
					.addGap(18)
					.addComponent(panelDeathDetails, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
		);
		gl_panelDeathDetails.setVerticalGroup(
			gl_panelDeathDetails.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDeathDetails.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDeathDetails.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblDeadDay)
						.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelDeathDetails.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDeadTime)
						.addComponent(timeSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelDeathDetails.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblScene)
						.addComponent(txtScene, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelDeathDetails.createParallelGroup(Alignment.LEADING)
						.addComponent(lblReason)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		panelDeathDetails.setLayout(gl_panelDeathDetails);
		contentPanel.setLayout(gl_contentPanel);
		buttonPane.setLayout(gl_buttonPane);
		getContentPane().setLayout(groupLayout);
	}
	
	public void setFormListener(RelevantFormListener revListener) {
		this.revListener = revListener;
	}
}
