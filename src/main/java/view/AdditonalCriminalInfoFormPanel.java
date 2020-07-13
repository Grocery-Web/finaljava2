package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import entity.Criminal;

public class AdditonalCriminalInfoFormPanel extends JPanel{
	private JLabel title;
	private JLabel hisOfViolent;
	private JTextField appliedDate;
	
	public AdditonalCriminalInfoFormPanel(Criminal cri) {
		Dimension dim = getPreferredSize();
		dim.width = 300;
		setPreferredSize(dim);
		setMinimumSize(dim);
		
		appliedDate = new JTextField(10);
		
		if(cri.getHisOfViolent() == null) {
			hisOfViolent =  new JLabel("No Records Recognition");
		}else {
			hisOfViolent =  new JLabel(cri.getHisOfViolent());
		}
		
		
		//TITLE
		title = new JLabel("ADDITIONAL INFOMATION");
		title.setFont(new Font("Tahoma", Font.PLAIN, 30));
		title.setForeground(Color.RED);
		
		layoutControls();
		
//		SET FORM LAYOUT
		Border innerBorder = BorderFactory.createTitledBorder("Additional Form");
		Border outerBorder = BorderFactory.createEmptyBorder(4, 4, 4, 4);
		setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
	}
	
	private void layoutControls() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		/////////////// TITLE ///////////////////
		gc.weighty = 0.5; // assign at least small additional space between each component on Vertical
		gc.fill = GridBagConstraints.HORIZONTAL;
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 2;
		add(title,gc);
		
		/////////////// HISTORY OF VIOLENT ///////////////////
		gc.weighty = 0.1;
		gc.gridwidth = 1;
		
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("History of Violent: "),gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(hisOfViolent,gc);
		
		/////////////// APPLIED DATE ///////////////////
		gc.weighty = 2;
		
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(30, 0, 0, 5);
		add(new JLabel("Date in Punishment: "),gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(appliedDate,gc);
		
//		End of Edit Form
	}
}
