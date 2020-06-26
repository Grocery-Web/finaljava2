package View;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar implements ActionListener{
	private JButton addPersonButton;
	private JButton addComplaintButton;
	private ToolbarListener toolbarListener;
	
	public Toolbar() {
		
//		Get rid of the border if you want the toolbar draggable
		setBorder(BorderFactory.createEtchedBorder());
		
		addPersonButton 	= new JButton();
		addComplaintButton 	= new JButton();
		
		addPersonButton.addActionListener(this);
		Image imgPerson = new ImageIcon(this.getClass().getResource("/img/person.png")).getImage();
		addPersonButton.setIcon(new ImageIcon(imgPerson));
		addPersonButton.setToolTipText("Add Person");
		
		addComplaintButton.addActionListener(this);
		Image imgComplaint = new ImageIcon(this.getClass().getResource("/img/complaint.png")).getImage();
		addComplaintButton.setIcon(new ImageIcon(imgComplaint));
		addComplaintButton.setToolTipText("Add Complaint");
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(addPersonButton);
		addSeparator();
		add(addComplaintButton);
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
