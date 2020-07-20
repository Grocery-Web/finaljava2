package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import entity.Criminal;
import entity.PrisonList;
import entity.Prisoner;

public class PrisonerFormPanel extends JPanel {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private String html = "<html><body style='width: %1spx'>%1s";
	
//	FOR VALIDATION
	private JLabel q1,q2;
	private boolean cd1 = false;
	private boolean cd2 = true;
	private String s = Character.toString("\u2713".toCharArray()[0]);
	
//	FOR COMPONENTS
	private JLabel criminalID;
	private JLabel title;
	private JDateChooser startDate;
	private JTextFieldDateEditor editor;
	private JTextField duration;
	private JComboBox adjudged;
	private JLabel hisOfViolent;
	private FilterPrisonListComboBox fpl;
	
//	LISTENER
	private PrisonerFormPanelListener prisonformListener;
	
//	Variables for info of Prisoner
	private int prisonId = 1;
	private String type = "Death penalty";
	private Date endDate;
	private String prisonName = "";
	
//	updated Criminal
	private String updatedViolent;
	private Criminal criminal;
	private String crimeTypes;

	public PrisonerFormPanel(Criminal cri, List<PrisonList> prisonLst,String crimeTypes) {
		//DEFINE PARAMETERS
		this.criminal = cri;
		this.crimeTypes = crimeTypes;
		
		Dimension dim = getPreferredSize();
		dim.width = 300;
		setPreferredSize(dim);
		setMinimumSize(dim);
		
		//VALIDATION DATE INPUT
		criminalID = new JLabel(Integer.toString(cri.getCriminalId()));
		startDate = new JDateChooser();
		startDate.setDateFormatString("yyyy-MM-dd");
		editor = (JTextFieldDateEditor) startDate.getDateEditor();
		editor.setEditable(false);
		startDate.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				cd1Check();
				getValidation();
			}
		});
		q1 = new JLabel(); q1.setPreferredSize(new Dimension(10, 20));
		
		//VALIDATION DURATION INPUT
		duration = new JTextField(10);
		duration.setEditable(false);
		q2 = new JLabel(); q2.setPreferredSize(new Dimension(10, 20));
		
		title = new JLabel("PRISONER INFOMATION");
		title.setFont(new Font("Tahoma", Font.PLAIN, 30));
		title.setForeground(Color.RED);
		
		//SET HISTORY OF VIOLENT ON RENDER AND 
		if(cri.getHisOfViolent() == null) {
			hisOfViolent =  new JLabel("No Records Recognition");
		}else {
			hisOfViolent =  new JLabel(String.format(html, 200, cri.getHisOfViolent()));
		}
		
		fpl = new FilterPrisonListComboBox(prisonLst);
		//By Default
		prisonId = ((PrisonList) fpl.getItemAt(0)).getId();
		prisonName = ((PrisonList) fpl.getItemAt(0)).getName();
		
		fpl.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					prisonId = ((PrisonList) fpl.getSelectedItem()).getId();
					prisonName = ((PrisonList) fpl.getSelectedItem()).getName();
                }
			}
		});
		
		String[] items = { "Death penalty", "Life-sentence", "Termed imprisonment"};
		adjudged = new JComboBox(items);
		
//		Combobox Model
		DefaultComboBoxModel adjudtedModel = new DefaultComboBoxModel();
		adjudtedModel.addElement("Death penalty");
		adjudtedModel.addElement("Life-sentence");
		adjudtedModel.addElement("Termed imprisonment");
		adjudged.setModel(adjudtedModel);
			
		adjudged.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					type = adjudged.getSelectedItem().toString();
					if(type.equals("Termed imprisonment")) {
						// not yet edited Text field
						cd2Check();
						getValidation();
						duration.setEditable(true);
						
						//textfield listener
						duration.getDocument().addDocumentListener(new DocumentListener() {

							@Override
							public void insertUpdate(DocumentEvent e) {
								cd2Check();
								getValidation();
							}

							@Override
							public void removeUpdate(DocumentEvent e) {
								cd2Check();
								getValidation();
							}

							@Override
							public void changedUpdate(DocumentEvent e) {
								cd2Check();
								getValidation();
							}
							
						});
					}
					
					if(type.equals("Life-sentence") || type.equals("Death penalty")) {
						duration.setText(null);
						duration.setEditable(false);
						q2.setText(null);
						duration.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
						
						cd2 = true;
						getValidation();
					}
                }
			}
		});
		
		layoutControls();
		
//		SET FORM LAYOUT
		Border innerBorder = BorderFactory.createTitledBorder("Prisoner Form");
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
		
		/////////////// CRIMINAL ID ///////////////////
		gc.gridwidth = 1;
		
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Criminal ID: "),gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(criminalID,gc);
		
		/////////////// START DATE ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Date in Jail: "),gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(startDate,gc);
		
		gc.gridx = 2;
		gc.anchor = GridBagConstraints.LINE_START;
		add(q1, gc);
		
		/////////////// PRISON ///////////////////
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Name of Prison: "),gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(fpl,gc);
		
		/////////////// TYPE OF ADJUDTED ///////////////////
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Type of Prisoner: "),gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(adjudged,gc);
		
		/////////////// DURATION ///////////////////
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Duration: "),gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(duration,gc);
		
		gc.gridx = 2;
		gc.anchor = GridBagConstraints.LINE_START;
		add(q2, gc);
		
		/////////////// HISTORY OF VIOLENT ///////////////////
		gc.weighty = 2;
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(20, 0, 0, 5);
		add(new JLabel("History of Violent: "),gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(hisOfViolent,gc);
		
//		End of Edit Form
	}
	
//	FUNCTIONS TO CHECK INPUT CONDITIONS
	private void cd1Check() {
		Date date1 = startDate.getDate();
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
	
	private void cd2Check() {
		if (!duration.getText().equals("") && duration.getText().matches("\\d+")) {
			duration.setBorder(new LineBorder(Color.GREEN, 1));
			q2.setText(s);
			q2.setForeground(new Color(0, 153, 51));
			q2.setToolTipText(null);
			cd2 = true;
		}else {
			duration.setBorder(new LineBorder(Color.RED, 1));
			q2.setText("?");
			q2.setForeground(Color.RED);
			q2.setToolTipText("Please enter a number as Duration timespan.");
			cd2 = false;
		}
	}
	
	
//	FUNCTIONS TO COLLECT INFOMATION OF OBJECTS
	public Prisoner getPrisoner(){
		Date getApplidated = null;
		boolean releaseStatus = false;
		Prisoner prisoner = null;
		
		getApplidated = startDate.getDate();
		criminal.setAppliedDate(getApplidated);
		if(type.equals("Termed imprisonment")) {
			if(duration.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Duration must be inputed", "Oops!!", JOptionPane.ERROR_MESSAGE);
			}else {
				// GET END DATE
				Calendar c = Calendar.getInstance();
				c.setTime(startDate.getDate());
				c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(duration.getText()));
				endDate = c.getTime();
				
				// VALIDATION RELEASE STATUS
				Date today = new Date();
				if(endDate.compareTo(today) <= 0) {
					releaseStatus = true;
				}
				prisoner = new Prisoner(Integer.parseInt(criminalID.getText()), prisonId, type, getApplidated, Integer.parseInt(duration.getText()), 
						endDate,releaseStatus,prisonName);
			}
		}else {
			prisoner = new Prisoner(Integer.parseInt(criminalID.getText()), prisonId, type, getApplidated, 0, 
					null, false,prisonName);
		}

		return prisoner;
	}
	
	public Criminal getCriminal() {
		Date getApplidated = null;
		getApplidated = startDate.getDate();
		criminal.setAppliedDate(getApplidated);
		
		// RECORD CRIME TYPE INTO HISTORY OF VIOLENT
		if(type.equals("Death penalty")) {
			if(hisOfViolent.getText().equals("No Records Recognition")) {
				updatedViolent = "Death penalty from:" + startDate.getDate() + " | Guilt:" + crimeTypes;
			}else {
				updatedViolent = criminal.getHisOfViolent() + "<br>***************<br>" + "Death penalty:" + startDate.getDate() + 
						" | Guilt:" + crimeTypes;
			}
			criminal.setHisOfViolent(updatedViolent);
			criminal.setPunishment("imprisoner");
		}else if(type.equals("Life-sentence")) {
			if(hisOfViolent.getText().equals("No Records Recognition")) {
				updatedViolent = "Life-sentence from:" + startDate.getDate() + " | Guilt:" + crimeTypes;
			}else {
				updatedViolent = criminal.getHisOfViolent() + "<br>***************<br>" + "Life-sentence:" + startDate.getDate() + 
						" | Guilt:" + crimeTypes;
			}
			criminal.setHisOfViolent(updatedViolent);
			criminal.setPunishment("imprisoner");
		}else {
			if(hisOfViolent.getText().equals("No Records Recognition")) {
				updatedViolent = "Termed imprisonment from:" + startDate.getDate() + ",Period:" + duration.getText() + " days | Guilt:" + crimeTypes;
			}else {
				updatedViolent = criminal.getHisOfViolent() + "<br>***************<br>" + "Termed imprisonment:" + startDate.getDate() + 
						" | Guilt:" + crimeTypes;
			}
			criminal.setHisOfViolent(updatedViolent);
			criminal.setPunishment("imprisoner");
		}

		return criminal;
	}
	
	public void getValidation() {
		boolean flag = (cd1 == true && cd2 == true)  ? true : false;
		if(prisonformListener != null) {
			prisonformListener.tableEventValidation(flag);
		}
	}
	
	public void getCondition(PrisonerFormPanelListener prisonformListener) {
		this.prisonformListener = prisonformListener;
	}
}
