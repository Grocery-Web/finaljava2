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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import entity.Complaint;
import entity.ComplaintDetail;
import entity.Criminal;
import entity.Person;

public class RelevantIncidentForm extends JDialog {
	private JButton okBtn;
	private JButton cancelBtn;
	private JLabel nameField;
	private FilterComplaintComboBox incDetailBox;
	private FilterComboBox punishmentFcb, crimeTypeFcb;
	private RelevantIncidentFormListener revListener;

	public RelevantIncidentForm(Person per, List<Complaint> incidentList) {
		setTitle("Add person to incident");
		okBtn = new JButton("OK");
		cancelBtn = new JButton("Cancel");
		nameField = new JLabel(per.getName());

		incDetailBox = new FilterComplaintComboBox(incidentList);
		
		punishmentFcb = new FilterComboBox(Arrays.asList("administrative sanctions", "imprisoner", "in process"));
		crimeTypeFcb = new FilterComboBox(Arrays.asList("Assault and Battery", "Kidnapping", "Homicide", "Rape", "False Imprisonment",
				"Theft", "Arson", "False Pretenses", "White Collar Crimes", "Receipt of Stolen Goods"));
		
		layoutControls();

		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ComplaintDetail comDetail = new ComplaintDetail();
				
				comDetail.setCompId(((Complaint) incDetailBox.getSelectedItem()).getId());
				comDetail.setPersonId(per.getPersonalId());
				comDetail.setCrimeType((String) crimeTypeFcb.getSelectedItem());		
				
				Criminal cri = new Criminal();
				int rating;
				cri.setPersonalId(comDetail.getPersonId());
				cri.setComplaintId(comDetail.getCompId());
				cri.setPunishment((String) punishmentFcb.getSelectedItem());
				switch ((String) punishmentFcb.getSelectedItem()) {
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
				
				if (revListener != null) {
					revListener.incidentFormEventListener(comDetail, cri);
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
		controlsPanel.add(incDetailBox, gc);
		
		////////////NEXT ROW /////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Punishment Status: "), gc);
	
		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(punishmentFcb, gc);
		
		////////////NEXT ROW /////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Crime Type: "), gc);
	
		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(crimeTypeFcb, gc);

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

	public void setFormListener(RelevantIncidentFormListener revListener) {
		this.revListener = revListener;
	}
}
