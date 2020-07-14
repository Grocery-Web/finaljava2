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
import javax.swing.border.Border;

import entity.Criminal;
import entity.PrisonList;
import entity.Prisoner;

public class PrisonerFormPanel extends JPanel {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private JLabel criminalID;
	private JLabel title;
	private JTextField startDate;
	private JTextField duration;
	private JComboBox adjudged;
	private FilterPrisonListComboBox fpl;
	
//	Variables for info of Prisoner
	private int prisonId = 1;
	private String type = "";
	private Date endDate;
	
	public PrisonerFormPanel(Criminal cri, List<PrisonList> prisonLst) {
		Dimension dim = getPreferredSize();
		dim.width = 300;
		setPreferredSize(dim);
		setMinimumSize(dim);
		
		criminalID = new JLabel(Integer.toString(cri.getCriminalId()));
		startDate = new JTextField(10);
		duration = new JTextField(10);
		duration.setEditable(false);
		title = new JLabel("PRISONER INFOMATION");
		title.setFont(new Font("Tahoma", Font.PLAIN, 30));
		title.setForeground(Color.RED);
		
		fpl = new FilterPrisonListComboBox(prisonLst);
		
		fpl.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					prisonId = ((PrisonList) fpl.getSelectedItem()).getId();
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
		
		adjudged.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selecterItem = adjudged.getSelectedItem().toString();
				if(selecterItem.equals("Termed imprisonment")) {
					duration.setEditable(true);
				}else {
					duration.setText(null);
					duration.setEditable(false);
				}
			}
		});
		
		adjudged.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					type = adjudged.getSelectedItem().toString();
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
		gc.weighty = 2;
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(20, 0, 0, 5);
		add(new JLabel("Duration: "),gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(duration,gc);
		
//		End of Edit Form
	}
	
	public Prisoner getPrisoner() {
		Date getstartDate = null;
		
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(dateFormat.parse(startDate.getText()));
			c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(duration.getText()));
			
			endDate = c.getTime();
			getstartDate = dateFormat.parse(startDate.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Time input is wrong", "info", JOptionPane.ERROR_MESSAGE);
		}
	
		Prisoner prisoner = new Prisoner(Integer.parseInt(criminalID.getText()), prisonId, type, getstartDate, Integer.parseInt(duration.getText()), 
				endDate, false);
		
		return prisoner;
	}
}
