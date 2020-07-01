package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import entity.Person;

public class AttachComplaintForm extends JDialog {
	private JButton okBtn;
	private JButton cancelBtn;
	private JTextArea nameField;
	private JComboBox cplDetailBox;
	private FilterComboBox fcb;

	public AttachComplaintForm(Person per) {
		setTitle("Person in Complaints");
		okBtn = new JButton("OK");
		cancelBtn = new JButton("Cancel");

		nameField = new JTextArea();
		nameField.setText(per.getName());
		nameField.setEditable(false);

		cplDetailBox = new JComboBox();
		fcb = new FilterComboBox(Arrays.asList("Assault and Battery", "Kidnapping", "Homicide", "Rape", "False Imprisonment",
				"Theft", "Arson", "False Pretenses", "White Collar Crimes", "Receipt of Stolen Goods"));

		layoutControls();

		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		setSize(340, 250);
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
		controlsPanel.add(new JLabel("Complaint Detail: "), gc);

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

		//////////// NEXT ROW /////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Complaint Detail: "), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(cplDetailBox, gc);

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

}
