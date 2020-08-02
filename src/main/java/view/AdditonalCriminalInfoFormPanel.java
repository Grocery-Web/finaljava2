package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import entity.Criminal;

public class AdditonalCriminalInfoFormPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
//	listener
	private AdditionalCriminalFormListener formListener;
	
//	components
	private JLabel title;
	private JLabel hisOfViolent;
	private JDateChooser appliedDate;
	private JTextFieldDateEditor editor;
	
//	validation
	private JLabel q1;
	private boolean cd1 = false;
	private String s = Character.toString("\u2713".toCharArray()[0]);
	
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
		appliedDate.setPreferredSize(new Dimension(200,20));
		editor = (JTextFieldDateEditor) appliedDate.getDateEditor();
		editor.setEditable(false);
		appliedDate.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				cd1Check();
				getValidation();
			}
		});
		q1 = new JLabel(); q1.setPreferredSize(new Dimension(10, 20));;
		
		//SET HISTORY OF VIOLENT ON RENDER AND 
		if(cri.getHisOfViolent() == null) {
			hisOfViolent =  new JLabel("No Records Recognition");
		}else {
			hisOfViolent =  new JLabel(String.format(html, 200, cri.getHisOfViolent()));
		}
				
		//DEFINE PARAMETERS
		criminal = new Criminal(cri.getCriminalId(), cri.getPersonalId(), cri.getComplaintId(), "administrative sanctions", null, 
				cri.getHisOfViolent(), cri.getRating(),cri.getComplaintName(),true);
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
		
		gc.gridx = 2;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(q1, gc);
		
//		End of Edit Form
	}
	
	public Criminal getCriminal() {
		String getApplidated = null;
		getApplidated = dateFormat.format(appliedDate.getDate());
		
		if(hisOfViolent.getText().equals("No Records Recognition")) {
			updatedViolent = "Pecuniary penalty:" + getApplidated + " | Guilt:" + crimeTypes;
		}else {
			updatedViolent = criminal.getHisOfViolent() + "<br>***************<br>" + "Pecuniary penalty:" + getApplidated + 
					" | Guilt:" + crimeTypes;
		}
		
		try {
			criminal.setAppliedDate(dateFormat.parse(getApplidated));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		criminal.setHisOfViolent(updatedViolent);
		
		return criminal;
	}
	
//	FUNCTIONS TO CHECK INPUT CONDITIONS
	private void cd1Check() {
		Date date1 = appliedDate.getDate();
		Date date2 = new Date();
		if (date1 != null) {
			if (date1.compareTo(date2) <= 0) {				
				q1.setText(s); q1.setForeground(new Color(0, 153, 51));
				q1.setToolTipText(null);
				cd1 = true;
			} else {
				q1.setText("?"); q1.setForeground(Color.RED);
				q1.setToolTipText("Select a date that is no later than today.");
				cd1 = false;
			}
		}
	}
	
	public void getValidation() {
		if(formListener != null) {
			formListener.tableEventValidation(cd1);
		}
	}
	
	public void getCondition(AdditionalCriminalFormListener formListener) {
		this.formListener = formListener;
	}
}
