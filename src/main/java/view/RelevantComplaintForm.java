package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import entity.Complaint;
import entity.ComplaintDetail;
import entity.Person;

public class RelevantComplaintForm extends JDialog {
	private JButton okBtn;
	private JButton cancelBtn;
	private JLabel nameField;
	private FilterComplaintComboBox cplDetailBox;
	private FilterComboBox fcb;
	private RelevantFormListener revListener;

	public RelevantComplaintForm(Person per, List<Complaint> listComplaint) {
		setTitle("Person in Complaints");
		okBtn = new JButton("OK");
		cancelBtn = new JButton("Cancel");
		nameField = new JLabel(per.getName());

		cplDetailBox = new FilterComplaintComboBox(listComplaint);
		
		fcb = new FilterComboBox(Arrays.asList("Assault and Battery", "Kidnapping", "Homicide", "Rape", "False Imprisonment",
				"Theft", "Arson", "False Pretenses", "White Collar Crimes", "Receipt of Stolen Goods"));
		
		layoutControls();

		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ComplaintDetail comDetail = new ComplaintDetail();
				
				comDetail.setCompId(((Complaint) cplDetailBox.getSelectedItem()).getId());
				comDetail.setPersonId(per.getPersonalId());
				comDetail.setCrimeType((String) fcb.getSelectedItem());		
				
				if (revListener != null) {
					revListener.formEventListener(comDetail);
				}
			}
		});
		
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		setSize(500, 300);
		setLocationRelativeTo(null);
	}

	private void layoutControls() {
		JPanel controlsPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();

		int space = 15;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		Border titleBorder = BorderFactory.createTitledBorder("Attach Complaint");

		controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));

		controlsPanel.setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		Insets rightPadding = new Insets(0, 0, 0, 15);
		Insets noPadding = new Insets(0, 0, 0, 0);

		//////////// FIRST ROW /////////////////
		gc.gridy = 0;
		gc.weighty = 0.5;
		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Name: "), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(nameField, gc);

		//////////// NEXT ROW /////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Relevant Complaint: "), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(cplDetailBox, gc);
		
		////////////NEXT ROW /////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Crime Type: "), gc);
	
		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(fcb, gc);

		//////////// BUTTONS PANEL /////////////////
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(okBtn, gc);
		buttonsPanel.add(cancelBtn, gc);

		Dimension btnSize = cancelBtn.getPreferredSize();
		okBtn.setPreferredSize(btnSize);

		// Add subs Panel to dialog
		setLayout(new BorderLayout());
		add(controlsPanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
	}

	public void setFormListener(RelevantFormListener revListener) {
		this.revListener = revListener;
	}
}
