package view;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar implements ActionListener{
	private JButton addPersonButton;
	private JButton addComplaintButton;
	private CustomTextField txtSearch;
	private ToolbarListener toolbarListener;
	private JLabel searchLabel;
	public Toolbar() {
		
//		Get rid of the border if you want the toolbar draggable
		setBorder(BorderFactory.createEtchedBorder());
		
		addPersonButton 	= new JButton();
		addComplaintButton 	= new JButton();
		txtSearch			= new CustomTextField(10);
		searchLabel 		= new JLabel("Search:");
		
//		SET PLACE HOLDER IN SEARCH BOX
		txtSearch.setPlaceholder("Search....");
		
//		SET IMAGE FOR BUTTONS
		addPersonButton.addActionListener(this);
		Image imgPerson = new ImageIcon(this.getClass().getResource("/img/person.png")).getImage();
		addPersonButton.setIcon(new ImageIcon(imgPerson));
		addPersonButton.setToolTipText("Add Person");
		
		addComplaintButton.addActionListener(this);
		Image imgComplaint = new ImageIcon(this.getClass().getResource("/img/complaint.png")).getImage();
		addComplaintButton.setIcon(new ImageIcon(imgComplaint));
		addComplaintButton.setToolTipText("Add Complaint");
		
//		ALLIGN COMPONENT ON TOOLBAR
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		add(addPersonButton);
		addSeparator();
		add(addComplaintButton);
		add(Box.createHorizontalGlue());
		add(Box.createRigidArea(new Dimension(1400,0)));
		add(searchLabel);
		addSeparator();
		add(txtSearch);
	}
	
	public void actionPerformed(ActionEvent e) {
		var clicked = e.getSource();
		
		if(clicked == addPersonButton) {
			if(toolbarListener != null) {
				toolbarListener.addPersonEventOccured();
			}
		}else if (clicked == addComplaintButton){
			if(toolbarListener != null) {
				toolbarListener.addComplaintEventOccured();
			}
		}
	}
	
	public void setToolbarListener (ToolbarListener toolbarListener){
		this.toolbarListener = toolbarListener;
	}
}
