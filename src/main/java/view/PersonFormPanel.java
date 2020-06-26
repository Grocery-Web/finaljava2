package view;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class PersonFormPanel extends JPanel{
	
	private JTextField nameField;
	private JTextField dob;
	private JTextField occupationField;
	private JTextField nationality;
	private JTextField address;
	private JButton submitBtn;
	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	private ButtonGroup genderGroup;
	
	public PersonFormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 300;
		setPreferredSize(dim);
		setMinimumSize(dim);
		
		nameField = new JTextField(10);
		occupationField = new JTextField(10);
		nationality = new JTextField(10);
		address = new JTextField(10);
		dob = new JTextField(10);
		maleRadio = new JRadioButton("male");
		femaleRadio = new JRadioButton("female");
		genderGroup = new ButtonGroup();
		submitBtn = new JButton("Submit");
		submitBtn.setMnemonic(KeyEvent.VK_S);
		
//		Radio Button
		maleRadio.setSelected(true);
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);
//		using ButtonGroup to ensure that only one values chosen at one time
		maleRadio.setActionCommand("male");
		femaleRadio.setActionCommand("female");
			
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		
		Border innerBorder = BorderFactory.createTitledBorder("Add Person");
		Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
		
		layoutComponents();
	}
	
	public void layoutComponents() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
//		This form just have one column, therefore we just use weightY to allocate the space between each components
		
		/////////////// NAME ///////////////////
		gc.weighty = 0.1; // assign at least small additional space between each component on Vertical
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Name: "),gc);
		
		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		add(nameField, gc);
		
		/////////////// OCCUPATION ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Occupation: "),gc);
		
		gc.gridy = 1;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(occupationField,gc);
		
		/////////////// Date of Birth ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Date of Birth: "),gc);
		
		gc.gridy = 2;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(dob,gc);
		
		/////////////// NATIONALITY ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Nationality: "),gc);
		
		gc.gridy = 3;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(nationality,gc);
		
		/////////////// ADDRESS///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Address: "),gc);
		
		gc.gridy = 4;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(address,gc);
		
		/////////////// RADIO BUTTON ///////////////////
		gc.gridy++;
		gc.weighty = 0.01;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LAST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Gender: "),gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(maleRadio,gc);
		
		gc.gridy++;
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(femaleRadio,gc);
		
		/////////////// SUBMIT BUTTON ///////////////////
		gc.weighty = 2; //all additional space below will be distributed to this component on Vertical
		
		gc.gridy++;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(submitBtn,gc);
		
//		End of Edit Form
	}
	
}