package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import entity.Criminal;
import entity.PrisonList;

public class CriminalDetailsFrame extends JFrame {
	private JSplitPane splitPane;
	private JButton okBtn;
	private JButton cancelBtn;
	private JPanel buttonsPanel;
	private CriminalFormPanel criFormPanel;
	private PrisonerFormPanel prisonerFormPanel;
	
	public CriminalDetailsFrame(Criminal cri, List<String> crimeTypes,List<PrisonList> prisonLst) {
		super("Criminal Details");
		
		//CREATE COMPONENTS
		okBtn = new JButton("Submit");
		cancelBtn = new JButton("Cancel");
		buttonsPanel =  new JPanel();
		prisonerFormPanel = new PrisonerFormPanel(cri,prisonLst);
		criFormPanel = new CriminalFormPanel(cri,crimeTypes);
		
		setLayout(new BorderLayout());
		
//		SPLIT FORM
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, criFormPanel, prisonerFormPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.5);
		
//		DESIGN BUTTON
		okBtn.setPreferredSize(new Dimension(80, 30));
		
		Dimension btnSize = okBtn.getPreferredSize();
		cancelBtn.setPreferredSize(btnSize);
		
		okBtn.setFocusPainted(false);
		cancelBtn.setFocusPainted(false);
		
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(okBtn);
		buttonsPanel.add(cancelBtn);

//		BUTTON ACTION
		okBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			}
		});
		
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		criFormPanel.setFormListener(new CriminalFormListener() {
			@Override
			public void formEventListener(String punishment) {
				if(punishment.equals("imprisoner")) {
//					ADD SPLIT PANE BETWEEN TWO FORMS
					splitPane.setDividerLocation(450);
					splitPane.setDividerSize((Integer) UIManager.get("SplitPane.dividerSize"));
					splitPane.getRightComponent().setVisible(true);
				}else {
					splitPane.getRightComponent().setVisible(false);
				}
				
			}
		});
		
//		LAYOUT
		add(splitPane, BorderLayout.CENTER);
		splitPane.getRightComponent().setVisible(false);
		add(buttonsPanel, BorderLayout.SOUTH);

		setMinimumSize(new Dimension(700,600));
		setMaximumSize(new Dimension(1000,600));
		setLocationRelativeTo(null);
		setSize(900, 500);
//		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}