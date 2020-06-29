package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PersonFormPanel extends JPanel {
	
	private JTextField personalID;
	private JTextField nameField;
	private JTextField occupationField;
	private JTextField nationality;
	private JTextField address;
	private JButton submitBtn;
	private JTextField dob;
	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	private ButtonGroup genderGroup;
	private boolean cd1, cd2, cd3, cd4, cd5;
	
	public PersonFormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 300;
		setPreferredSize(dim);
		setMinimumSize(dim);
		
		personalID = new JTextField(10);
		personalID.getDocument().addDocumentListener(new DocumentListener() {
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
		
		nameField = new JTextField(10);
		nameField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				cd2Check();
				checkUnlock();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				cd2Check();
				checkUnlock();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				cd2Check();
				checkUnlock();				
			}
		});
		
		occupationField = new JTextField(10);
		occupationField.getDocument().addDocumentListener(new DocumentListener() {
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
		
		nationality = new JTextField(10);
		nationality.getDocument().addDocumentListener(new DocumentListener() {
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
		
		address = new JTextField(10);
		address.getDocument().addDocumentListener(new DocumentListener() {
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
		
		dob = new JTextField(10);
		
		maleRadio = new JRadioButton("male");
		femaleRadio = new JRadioButton("female");
		genderGroup = new ButtonGroup();
		submitBtn = new JButton("Submit");
		submitBtn.setMnemonic(KeyEvent.VK_S);
		submitBtn.setEnabled(false);
		
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
//	Functions to check for all input fields
	private void cd1Check() {
		if (!personalID.getText().equals("") && personalID.getText().matches("\\d+")) {
        	personalID.setBorder(new LineBorder(Color.GREEN, 1));
            cd1 = true;
        } else {
        	personalID.setBorder(new LineBorder(Color.RED, 1));
        	cd1 = false;
        }
	}
	
	private void cd2Check() {
		if (!nameField.getText().equals("") && nameField.getText().matches("[\\D ]{1,50}")) {
        	nameField.setBorder(new LineBorder(Color.GREEN, 1));
            cd2 = true;
        } else {
        	nameField.setBorder(new LineBorder(Color.RED, 1));
        	cd2 = false;
        }
	}
	
	private void cd3Check() {
		if (!occupationField.getText().equals("") && occupationField.getText().matches("[a-zA-Z]{4,20}")) {				 		   
	       	occupationField.setBorder(new LineBorder(Color.GREEN, 1));
	        cd3 = true;
		} else {
			occupationField.setBorder(new LineBorder(Color.RED, 1));
	        cd3 = false;
	    }
	}
	
	private void cd4Check() {
		if (!nationality.getText().equals("") && nationality.getText().matches("[a-zA-Z]{4,50}")) {
			nationality.setBorder(new LineBorder(Color.GREEN, 1));
			cd4 = true;
		} else {
			nationality.setBorder(new LineBorder(Color.RED, 1));
			cd4 = false;
		}
	}
	
	private void cd5Check() {
		if (!address.getText().equals("") && address.getText().matches(".{2,4000}")) {
			address.setBorder(new LineBorder(Color.GREEN, 1));
			cd5 = true;
		} else {
			address.setBorder(new LineBorder(Color.RED, 1));
			cd5 = false;
		}
	}
	
	private void checkUnlock() {
		boolean unlock = cd1 && cd2 && cd3 && cd4 && cd5;
		submitBtn.setEnabled(unlock);
	}

	public void layoutComponents() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
//		This form just have one column, therefore we just use weightY to allocate the space between each components
		
		/////////////// PERSONAL ID ///////////////////
		gc.weighty = 0.1; // assign at least small additional space between each component on Vertical
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Personal ID: "),gc);
		
		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		add(personalID, gc);
		
		gc.gridx = 2;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		JLabel q1 = new JLabel("?");
		q1.setToolTipText("Please enter a number as Personal ID");
		add(q1, gc);
		
		/////////////// NAME ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Name: "),gc);
		
		gc.gridy = 1;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(nameField,gc);
		
		/////////////// OCCUPATION ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Occupation: "),gc);
		
		gc.gridy = 2;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(occupationField,gc);
		
		/////////////// Date of Birth ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Date of Birth: "),gc);
		
		gc.gridy = 3;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(dob,gc);
		
		/////////////// NATIONALITY ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Nationality: "),gc);
		
		gc.gridy = 4;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(nationality,gc);
		
		/////////////// ADDRESS///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Address: "),gc);
		
		gc.gridy = 5;
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