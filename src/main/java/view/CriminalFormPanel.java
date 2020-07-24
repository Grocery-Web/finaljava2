package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import entity.Criminal;

public class CriminalFormPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel criminalID;
	private JLabel nameField;
	private JLabel nationality;
	private JLabel dob;
	private JLabel gender;
	private JLabel crimeType;
	private JLabel rating;
	private JComboBox punishCombo;
	private JLabel imgLabel;
	
	//LISTENER
	private CriminalFormListener criminalFormListener;
	
	//local Variables;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private String getPunishment = "in process";
	
	public CriminalFormPanel(Criminal cri, String crimeTypes, int privilege) {
		Dimension dim = getPreferredSize();
		dim.width = 300;
		setPreferredSize(dim);
		setMinimumSize(dim);
				
		//CREATE COMPONENTS
		criminalID =  new JLabel(Integer.toString(cri.getComplaintId()));
		nameField =  new JLabel(cri.getName());
		nationality =  new JLabel(cri.getNationality());
		dob =  new JLabel(dateFormat.format(cri.getDob()));
		gender =  new JLabel(cri.getGender().toString());
		rating = new JLabel(Integer.toString(cri.getRating()));
		
		crimeType = new JLabel(crimeTypes);
		
		punishCombo = new JComboBox();
		
		// IMAGE LABEL
		imgLabel =  new JLabel();
		imgLabel.setBorder(BorderFactory.createEtchedBorder());
		imgLabel.setPreferredSize(new Dimension(100, 100));
		var url ="avatar/" + cri.getImage();
		try {
			Image imgPerson = 
					new ImageIcon(
						getClass().getClassLoader()
						.getResource(url)
						).getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH);
			
			imgLabel.setIcon(new ImageIcon(imgPerson));
			
		} catch (Exception e) {
			if (cri.getGender().toString() == "male") {
				url = "images/male.png";
			}
			else url = "images/female.png";
			
			Image imgPerson = 
					new ImageIcon(
						getClass().getClassLoader()
						.getResource(url)
						).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			
			imgLabel.setIcon(new ImageIcon(imgPerson));
		}
		
		//Combobox Model
		DefaultComboBoxModel punishModel = new DefaultComboBoxModel();
		punishModel.addElement("in process");
		if(privilege == 2) {
			punishModel.addElement("imprisoner");
			punishModel.addElement("administrative sanctions");
		}
		punishCombo.setModel(punishModel);
		
		//SET FORM LAYOUT
		Border innerBorder = BorderFactory.createTitledBorder("Criminal Details");
		Border outerBorder = BorderFactory.createEmptyBorder(4, 4, 4, 4);
		setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
		
		//COMBOBOX LISTENER
		punishCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if(criminalFormListener != null) {
						criminalFormListener.formEventListener((String) punishCombo.getSelectedItem());
						getPunishment = punishCombo.getSelectedItem().toString();
					}
                }

                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    //do nothing
                }
			}
		});
		
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
		gc.anchor = GridBagConstraints.LINE_START;
		add(punishCombo, gc);
		
		/////////////// Rating ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 30);
		add(new JLabel("Rating: "), gc);
	
		gc.gridx = 1;
		gc.gridy = 8;
		gc.ipady = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		add(rating, gc);

//		End of Edit Form
	}
	
	public void setFormListener(CriminalFormListener criminalFormListener) {
		this.criminalFormListener = criminalFormListener;
	}
	
	public String getPunishment() {
		return getPunishment;
	}
}
