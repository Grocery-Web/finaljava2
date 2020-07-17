package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.toedter.calendar.JDateChooser;

import entity.Criminal;

public class AdditonalCriminalInfoFormPanel extends JPanel{
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private JLabel title;
	private JLabel hisOfViolent;
	private JDateChooser appliedDate;
	
//	updated Criminal
	private String updatedViolent;
	private Criminal criminal;
	private String crimeTypes;
	private String html = "<html><body style='width: %1spx'>%1s";
	
	public AdditonalCriminalInfoFormPanel(Criminal cri,String crimeTypes) {
		Dimension dim = getPreferredSize();
		dim.width = 300;
		setPreferredSize(dim);
		setMinimumSize(dim);
		
		appliedDate = new JDateChooser();
		appliedDate.setDateFormatString("yyyy-MM-dd");
		appliedDate.setPreferredSize(new Dimension(120, 20));
		
		//SET HISTORY OF VIOLENT ON RENDER AND 
		if(cri.getHisOfViolent() == null) {
			hisOfViolent =  new JLabel("No Records Recognition");
		}else {
			hisOfViolent =  new JLabel(String.format(html, 200, cri.getHisOfViolent()));
		}
				
		//DEFINE PARAMETERS
		criminal = new Criminal(cri.getCriminalId(), cri.getPersonalId(), cri.getComplaintId(), "administrative sanctions", null, 
				cri.getHisOfViolent(), cri.getRating());
		this.crimeTypes = crimeTypes;
		
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
//		gc.fill = GridBagConstraints.HORIZONTAL;
		
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
	
	public Criminal getCriminal() {
		Date getApplidated = null;
		getApplidated = appliedDate.getDate();
		
		if(hisOfViolent.getText().equals("No Records Recognition")) {
			updatedViolent = "Pecuniary penalty:" + appliedDate.getDate() + " | Guilt:" + crimeTypes;
		}else {
			updatedViolent = criminal.getHisOfViolent() + "<br>***************<br>" + "Pecuniary penalty:" + appliedDate.getDate() + 
					" | Guilt:" + crimeTypes;
		}
		
		criminal.setAppliedDate(getApplidated);
		criminal.setHisOfViolent(updatedViolent);
		
		return criminal;
	}
}
