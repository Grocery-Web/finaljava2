package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.StringJoiner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import entity.Criminal;
import entity.PrisonList;
import entity.Prisoner;

public class CriminalDetailsFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSplitPane splitPane;
	private JButton okBtn;
	private JButton cancelBtn;
	private JPanel buttonsPanel;
	private CriminalFormPanel criFormPanel;
	private PrisonerFormPanel prisonerFormPanel;
	private AdditonalCriminalInfoFormPanel additionalPanel;
	private CardLayout cardLayout;
	private JPanel panelCont;
	
//	LISTENER
	private TableCriminalDetailsListener criDetailListener;
	
	public CriminalDetailsFrame(Criminal cri, List<String> crimeTypesLst,List<PrisonList> prisonLst,int privilege) {
		super("Criminal Details");
		
		//CRIME TYPES
		StringJoiner joiner = new StringJoiner(" | ");
		for (String crimeType : crimeTypesLst) {
			joiner.add(crimeType);
		}
		String crimeTypes = joiner.toString();
		
		//CREATE COMPONENTS
		okBtn = new JButton("Submit");

		cancelBtn = new JButton("Cancel");
		buttonsPanel =  new JPanel();
		prisonerFormPanel = new PrisonerFormPanel(cri,prisonLst, crimeTypes);
		criFormPanel = new CriminalFormPanel(cri,crimeTypes,privilege);
		additionalPanel =  new AdditonalCriminalInfoFormPanel(cri,crimeTypes);
		panelCont = new JPanel();
		
		setLayout(new BorderLayout());
		
//		CARD LAYOUT
		cardLayout = new CardLayout();
		panelCont.setLayout(cardLayout);
		panelCont.add(prisonerFormPanel, "1");
		panelCont.add(additionalPanel, "2");
		
//		SPLIT FORM
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, criFormPanel, panelCont);
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
				if(criFormPanel.getPunishment().equals("in process")) {
					if(criDetailListener != null) {
						criDetailListener.tableDumpEvent();
					}
				}
				
				if(criFormPanel.getPunishment().equals("administrative sanctions")) {
					Criminal criminal = additionalPanel.getCriminal();
					if(criDetailListener != null) {
						criDetailListener.tableUpdatedCriminal(criminal);
					}
				}
				
				if(criFormPanel.getPunishment().equals("imprisoner")) {
					Prisoner prisoner = prisonerFormPanel.getPrisoner();
					Criminal criminal = prisonerFormPanel.getCriminal();
					if(criDetailListener != null) {
						criDetailListener.tableInsertPrisoner(prisoner,criminal);
					}
				}
			}
		});
		
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				criDetailListener.tableDumpEvent();
			}
		});
		
//		FORM LISTENER
		criFormPanel.setFormListener(new CriminalFormListener() {
			@Override
			public void formEventListener(String punishment) {
				if(punishment.equals("imprisoner")) {
					// CALL PRISONER FORM
					cardLayout.show(panelCont, "1");
					splitPane.setDividerLocation(450);
					splitPane.setDividerSize((Integer) UIManager.get("SplitPane.dividerSize"));
					splitPane.getRightComponent().setVisible(true);
					okBtn.setEnabled(false);
					
					prisonerFormPanel.getCondition(new PrisonerFormPanelListener() {
						
						@Override
						public void tableEventValidation(boolean flag) {
							okBtn.setEnabled(flag);
						}
					});
				}else if(punishment.equals("administrative sanctions")) {
					// CALL ADDITONAL FORM
					cardLayout.show(panelCont, "2");
					splitPane.setDividerLocation(450);
					splitPane.setDividerSize((Integer) UIManager.get("SplitPane.dividerSize"));
					splitPane.getRightComponent().setVisible(true);
					okBtn.setEnabled(false);
					
					additionalPanel.getCondition(new AdditionalCriminalFormListener() {
						
						@Override
						public void tableEventValidation(boolean flag) {
							okBtn.setEnabled(flag);
						}
					});
				}else {
					splitPane.getRightComponent().setVisible(false);
					okBtn.setEnabled(true);
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
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void setTableListener(TableCriminalDetailsListener criDetailListener) {
		this.criDetailListener = criDetailListener;
	}
}
