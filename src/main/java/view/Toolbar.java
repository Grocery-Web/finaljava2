package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Toolbar extends JToolBar implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton addPersonButton;
	private JButton addComplaintButton;
	private CustomTextField txtSearch;
	private ToolbarListener toolbarListener;
	private JLabel searchLabel;

	public Toolbar() {

//		Get rid of the border if you want the toolbar draggable
		setBorder(BorderFactory.createEtchedBorder());

		addPersonButton = new JButton();
		addComplaintButton = new JButton();
		txtSearch = new CustomTextField(10);
		searchLabel = new JLabel("Search:");

//		SET PLACE HOLDER IN SEARCH BOX
		txtSearch.setPlaceholder("Search....");

//		SET IMAGE FOR BUTTONS
		addPersonButton.addActionListener(this);
		Image imgPerson = new ImageIcon(this.getClass().getResource("/images/person.png")).getImage();
		addPersonButton.setIcon(new ImageIcon(imgPerson));
		addPersonButton.setToolTipText("Add Person");

		addComplaintButton.addActionListener(this);
		Image imgComplaint = new ImageIcon(this.getClass().getResource("/images/complaint.png")).getImage();
		addComplaintButton.setIcon(new ImageIcon(imgComplaint));
		addComplaintButton.setToolTipText("Add Complaint");

//		SET FOCUS LISTENER IN SEARCH BOX
		txtSearch.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!txtSearch.getText().equals("Search....")) {
					toolbarListener.searchText(txtSearch.getText());
				}else {
					txtSearch.setText("");
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
//				SEARCH BOX LISTENER
				txtSearch.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						toolbarListener.searchText(txtSearch.getText());
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						toolbarListener.searchText(txtSearch.getText());
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						toolbarListener.searchText(txtSearch.getText());
					}
				});
			}
		});

//		ALLIGN COMPONENT ON TOOLBAR
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		add(addPersonButton);
		addSeparator();
		add(addComplaintButton);
		add(Box.createHorizontalGlue());
		add(Box.createRigidArea(new Dimension(1400, 0)));
		add(searchLabel);
		addSeparator();
		add(txtSearch);
	}

//	BUTTON ACTION PERFORM
	public void actionPerformed(ActionEvent e) {
		var clicked = e.getSource();

		if (clicked == addPersonButton) {
			if (toolbarListener != null) {
				toolbarListener.addPersonEventOccured();
			}
		} else if (clicked == addComplaintButton) {
			if (toolbarListener != null) {
				toolbarListener.addComplaintEventOccured();
			}
		}
	}

//	TOOLBAR LISTENER
	public void setToolbarListener(ToolbarListener toolbarListener) {
		this.toolbarListener = toolbarListener;
	}
}
