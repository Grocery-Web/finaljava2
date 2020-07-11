package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class CriminalDetailsFrame extends JFrame {
	private JSplitPane splitPane;
	private JButton okBtn;
	private JButton cancelBtn;
	private JPanel buttonsPanel;
	private CriminalFormPanel criFormPanel;
	private PrisonerFormPanel prisonerFormPanel;
	
	public CriminalDetailsFrame() {
		super("Criminal Details");
		
		//CREATE COMPONENTS
		okBtn = new JButton("Submit");
		cancelBtn = new JButton("Cancel");
		buttonsPanel =  new JPanel();
		prisonerFormPanel = new PrisonerFormPanel();
		criFormPanel = new CriminalFormPanel();
		
		setLayout(new BorderLayout());
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, criFormPanel, prisonerFormPanel);
		splitPane.setOneTouchExpandable(true);
		
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(okBtn);
		buttonsPanel.add(cancelBtn);
		
		Dimension btnSize = cancelBtn.getPreferredSize();
		okBtn.setPreferredSize(btnSize);
		
		add(splitPane, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
		
		setMinimumSize(new Dimension(700,600));
		setLocationRelativeTo(null);
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
}
