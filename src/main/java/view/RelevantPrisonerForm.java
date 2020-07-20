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

import entity.PrisonList;
import entity.Prisoner;

public class RelevantPrisonerForm extends JDialog {
	private JButton okBtn;
	private JButton cancelBtn;
	private JLabel nameField;
	private FilterPrisonListComboBox prisonBox;
	private FilterComboBox prisonFcb;
	private RelevantPrisonerFormListener revListener;

	public RelevantPrisonerForm(Prisoner pri, List<PrisonList> prisonList) {
		setTitle("Transfer Prisoner To Other Prison");
		okBtn = new JButton("OK");
		cancelBtn = new JButton("Cancel");
		nameField = new JLabel(pri.getName());

		prisonBox = new FilterPrisonListComboBox(prisonList);
		
		layoutControls();

		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrisonList prison = new PrisonList();
				prison.setId(((PrisonList) prisonBox.getSelectedItem()).getId());
				prison.setName(((PrisonList) prisonBox.getSelectedItem()).getName());
				prison.setAddress(((PrisonList) prisonBox.getSelectedItem()).getAddress());
				prison.setCapacity(((PrisonList) prisonBox.getSelectedItem()).getCapacity());
				prison.setQuantity(((PrisonList) prisonBox.getSelectedItem()).getQuantity());
				prison.setImg(((PrisonList) prisonBox.getSelectedItem()).getImg());
				
				if (revListener != null) {
					revListener.prisonerFormEventListener(pri, prison);
				}
			}
		});
		
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		setSize(400, 200);
		setLocationRelativeTo(null);
	}

	private void layoutControls() {
		JPanel controlsPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();

		int space = 15;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		Border titleBorder = BorderFactory.createTitledBorder("Transfer Prisoner");

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
		controlsPanel.add(new JLabel("Available Prison: "), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(prisonBox, gc);
		

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

	public void setFormListener(RelevantPrisonerFormListener revListener) {
		this.revListener = revListener;
	}
}