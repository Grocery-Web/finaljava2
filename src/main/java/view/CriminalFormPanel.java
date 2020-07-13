package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class CriminalFormPanel extends JPanel{
	private JLabel criminalID;
	private JLabel nameField;
	private JLabel nationality;
	private JLabel dob;
	private JLabel gender;
	private JLabel crimeType;
	private JComboBox punishCombo;
	private JLabel imgLabel;
	
	public CriminalFormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 300;
		setPreferredSize(dim);
		setMinimumSize(dim);
		
		//CREATE COMPONENTS
		criminalID =  new JLabel("criminalID");
		nameField =  new JLabel("nameField");
		nationality =  new JLabel("nationality");
		dob =  new JLabel("dob");
		gender =  new JLabel("gender");
		crimeType = new JLabel("crimeType");
		punishCombo = new JComboBox();
		
		imgLabel =  new JLabel();
		imgLabel.setBorder(BorderFactory.createEtchedBorder());
		imgLabel.setPreferredSize(new Dimension(100, 100));
		
		//Combobox Model
		DefaultComboBoxModel punishModel = new DefaultComboBoxModel();
		punishModel.addElement("in process");
		punishModel.addElement("imprisoner");
		punishModel.addElement("administrative sanctions");
		punishCombo.setModel(punishModel);
		punishCombo.setSelectedIndex(0);
		
		//SET FORM LAYOUT
		Border innerBorder = BorderFactory.createTitledBorder("Criminal Details");
		Border outerBorder = BorderFactory.createEmptyBorder(4, 4, 4, 4);
		setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
		
		layoutComponents();
	}
	
	public void layoutComponents() {
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

//		This form just have one column, therefore we just use weightY to allocate the space between each components
		
		/////////////// AVATAR ///////////////////
		gc.weighty = 0.1; // assign at least small additional space between each component on Vertical
	
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;

		gc.fill = GridBagConstraints.HORIZONTAL;
		add(imgLabel, gc);

		/////////////// CRIMINAL ID ///////////////////
		
//		gc.fill = GridBagConstraints.BOTH;
		gc.gridwidth = 1;
		
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 30);
		add(new JLabel("Criminal ID: "), gc);

		gc.gridx = 1;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(criminalID, gc);

		/////////////// NAME ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 30);
		add(new JLabel("Name: "), gc);

		gc.gridx = 1;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.LINE_START;
		add(nameField, gc);

		/////////////// NATIONALITY ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 30);
		add(new JLabel("Nationality: "), gc);

		gc.gridx = 1;
		gc.gridy = 3;
		gc.anchor = GridBagConstraints.LINE_START;
		add(nationality, gc);

		/////////////// Date of Birth ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 30);
		add(new JLabel("Date of Birth: "), gc);

		gc.gridx = 1;
		gc.gridy = 4;
		gc.anchor = GridBagConstraints.LINE_START;
		add(dob, gc);

		/////////////// GENDER ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 30);
		add(new JLabel("Gender: "), gc);

		gc.gridx = 1;
		gc.gridy = 5;
		gc.anchor = GridBagConstraints.LINE_START;
		add(gender, gc);

		/////////////// CRIME TYPE ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 30);
		add(new JLabel("Crime Type: "), gc);
	
		gc.gridx = 1;
		gc.gridy = 6;
		gc.anchor = GridBagConstraints.LINE_START;
		add(crimeType, gc);

		/////////////// PUNISHMENT ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 30);
		add(new JLabel("Punishment: "), gc);
	
		gc.gridx = 1;
		gc.gridy = 7;
		gc.ipady = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		add(punishCombo, gc);

//		End of Edit Form
	}
}
