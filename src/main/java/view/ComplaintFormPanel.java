package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import entity.Complaint;

public class ComplaintFormPanel extends JPanel{
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	private JTextField datetime;
	private JTextField place;
	private JTextField declarantName;
	private JTextArea detail;
	private JButton submitBtn;
	private JScrollPane scroll;
	
//	INTERFACE LISTERNER
	private ComplaintListener cplListener;
	
	public ComplaintFormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 300;
		setPreferredSize(dim);
		setMinimumSize(dim);
		
		datetime = new JTextField(10);
		place = new JTextField(10);
		declarantName = new JTextField(10);
		detail = new JTextArea(8,10);
		submitBtn = new JButton("Submit");
		submitBtn.setMnemonic(KeyEvent.VK_S);
		
//		ADD SCROLL PANE INTO DETAIL AREA
		detail.setBorder(BorderFactory.createEtchedBorder());
		scroll = new JScrollPane(detail,
			                    JScrollPane.VERTICAL_SCROLLBAR_NEVER,
			                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
//		ACTION PERFORM ON SUBMIT BUTTON
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date getTime = null;
				try {
					getTime = formatter.parse(datetime.getText());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				String getPlace = place.getText();
				String getDeclarantName = declarantName.getText();
				String getDetails = detail.getText();
				
				Complaint cpt = new Complaint(getTime, getPlace, getDeclarantName, getDetails, false);
				
				if(cplListener != null) {
					cplListener.complaintListener(cpt);
				}
			}
		});
		
//		SET FORM LAYOUT
		Border innerBorder = BorderFactory.createTitledBorder("Add Complaint");
		Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
		
		layoutComponents();
	}
	
	public void layoutComponents() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
//		This form just have one column, therefore we just use weightY to allocate the space between each components
		
		/////////////// DATETIME ///////////////////
		gc.weighty = 0.1; // assign at least small additional space between each component on Vertical
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("DateTime: "),gc);
		
		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		add(datetime, gc);
		
		/////////////// PLACE ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Place: "),gc);
		
		gc.gridy = 1;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(place,gc);
		
		/////////////// DECLARANT NAME ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Declarant Name: "),gc);
		
		gc.gridy = 2;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(declarantName,gc);
		
		/////////////// DETAILS ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Details: "),gc);
		
		gc.gridy = 3;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(scroll,gc);
		
		/////////////// SUBMIT BUTTON ///////////////////
		gc.weighty = 2; //all additional space below will be distributed to this component on Vertical
		
		gc.gridy++;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(submitBtn,gc);
		
//		End of Edit Form
	}

	public void setFormListener(ComplaintListener cplListener) {
		this.cplListener = cplListener;
	}
}
