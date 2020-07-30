package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import dao.ComplaintDAO;
import dao.ComplaintDetailDAO;
import dao.CriminalDAO;
import dao.PersonDAO;
import dao.PrisonListDAO;
import dao.PrisonerDAO;
import dao.VictimDAO;
import entity.Account;
import entity.Complaint;
import entity.ComplaintDetail;
import entity.Criminal;
import entity.Person;
import entity.PrisonList;
import entity.Prisoner;
import entity.Victim;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelCont;
	private Toolbar toolbar;
	private PersonFormPanel personForm;
	private PersonPanel personPanel;
	private JSplitPane splitPane;
	private JTabbedPane tabPane;
	private ComplaintsPanel complaintPanel;
	private ComplaintFormPanel complaintForm;
	private CardLayout cardLayout;
	private IncidentsPanel incidentPanel;
	private CriminalPanel criminalPanel;
	private PrisonListPanel prisonListPanel;
	private PrisonerPanel prisonerPanel;
	private VictimPanel victimPanel;
	
//	DAO
	private PersonDAO personDAO;
	private ComplaintDAO complaintDAO;
	private ComplaintDetailDAO comDetailDAO;
	private CriminalDAO criminalDAO;
	private PrisonListDAO prisonListDAO;
	private VictimDAO victimDAO;
	private PrisonerDAO prisonerDAO;

//	EXTERNAL FRAME OR DIALOG
	private ComplaintDetailFrame cplDetailFrame;
	private RelevantComplaintForm relComplain;
	private PersonDetailFrame detailPersonFrame;
	private PrisonListDetailFrame prisonListDetailFrame;
	private VictimFormPanel victimForm;
	private CriminalDetailsFrame criDetailFrame;
	private RelevantCriminalForm relCriminal;
	private IncidentDetailFrame incDetailFrame;
	private PrisonerDetailFrame prisonerDetailFrame;
	private RelevantPrisonerForm relPrisoner;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
//					MainFrame frame = new MainFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	
	public MainFrame(Account acc) {
		setTitle("Crime Management Dashboard");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setJMenuBar(createMenuBar());
		
//		CREATE INTERNAL COMPONENTS 
		toolbar = new Toolbar();
		panelCont = new JPanel();
		personForm = new PersonFormPanel();
		personPanel = new PersonPanel(acc.getPrivilege());
		complaintPanel = new ComplaintsPanel(acc.getPrivilege());
		complaintForm = new ComplaintFormPanel();
		tabPane = new JTabbedPane();
		incidentPanel =  new IncidentsPanel(acc.getPrivilege());
		criminalPanel = new CriminalPanel();
		prisonListPanel = new PrisonListPanel(acc.getPrivilege());
		prisonerPanel = new PrisonerPanel(acc.getPrivilege());
		victimPanel = new VictimPanel(acc.getPrivilege());

//		CREATE DAO
		personDAO = new PersonDAO();
		complaintDAO = new ComplaintDAO();
		comDetailDAO = new ComplaintDetailDAO();
		criminalDAO = new CriminalDAO();
		prisonListDAO = new PrisonListDAO();
		victimDAO = new VictimDAO();
		prisonerDAO = new PrisonerDAO();

//		CARD LAYOUT
		cardLayout = new CardLayout();
		panelCont.setLayout(cardLayout);
		panelCont.add(personForm, "1");
		panelCont.add(complaintForm, "2");

//		SPLIT PANE
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelCont, tabPane);
		splitPane.setOneTouchExpandable(true);

//		TAB PANE
		tabPane.addTab("Person Info", personPanel);
		tabPane.addTab("Complaints", complaintPanel);
		tabPane.addTab("Incidents", incidentPanel);
		tabPane.addTab("Criminals", criminalPanel);
		tabPane.addTab("Prisoners", prisonerPanel);
		tabPane.addTab("Victims", victimPanel);
		tabPane.addTab("Prison List", prisonListPanel);

//		CALL BACK TABLES
		personPanel.setData(personDAO.getAlivePeople());
		complaintPanel.setData(complaintDAO.getAllUnverifiedComplaints());
		incidentPanel.setData(complaintDAO.getAllApprovedComplaints());
		criminalPanel.setData(criminalDAO.getAllCriminals());
		prisonListPanel.setData(prisonListDAO.getAllPrisonList());
		prisonerPanel.setData(prisonerDAO.getUnreleasedPrisoners());
		victimPanel.setData(victimDAO.getAllVictims());

//		TOOLBAR LISTENER
		toolbar.setToolbarListener(new ToolbarListener() {

			@Override
			public void addPersonEventOccured() {
				cardLayout.show(panelCont, "1");
			}

			@Override
			public void addComplaintEventOccured() {
				cardLayout.show(panelCont, "2");
			}

			@Override
			public void searchTextEventOccured(String txt) {
				int selectedIndex = tabPane.getSelectedIndex();
				if (selectedIndex == 0) { //PERSON PANEL
					personPanel.search(txt);
				}

				if (selectedIndex == 1) { //COMPLAINTS PANEL
					complaintPanel.search(txt);
				}
				
				if (selectedIndex == 2) { //INCIDENTS PANEL
					incidentPanel.search(txt);
				}
				
				if (selectedIndex == 3) { //CRIMINALS PANEL
					criminalPanel.search(txt);
				}
				
				if (selectedIndex == 4) { //PRISONERS PANEL
					prisonerPanel.search(txt);
				}
				
				if (selectedIndex == 5) { //VICTIMS PANEL
					victimPanel.search(txt);
				}
				
				if (selectedIndex == 6) { //PRISON PANEL
					prisonListPanel.search(txt);
				}
			}

			@Override
			public void refreshEventOccured() {
				refresh();
				complaintForm.clearInput();
				personForm.clearForm();
			}
		});

//		FORM LISTENER
		complaintForm.setFormListener(new FormComplaintListener() {
			@Override
			public void insertEventListener(Complaint cpt) {
				complaintDAO.addComplaint(cpt);
				refresh();
			}
		});

		personForm.setFormListener(new FormPersonListener() {
			@Override
			public void insertEventListener(Person per, File file) {
//				Verify Personal ID
				int personalID = per.getPersonalId();
				Person findPerson = personDAO.findPersonById(personalID);

				if (findPerson.getPersonalId() == 0) {
					if(file != null) {
//						Save Image to path
						saveImage(file);
						
//						Find image in path and rename it
						String path = System.getProperty("user.dir") + "/src/main/resources/avatar/" + file.getName();
						File fileInPath = new File(path);

						try {
							renameFile(fileInPath, Integer.toString(per.getPersonalId()));
						} catch (IOException e) {
							e.printStackTrace();
						}
						
//						Set name of image in Person
						per.setImage(personalID + ".png");

						personDAO.addPerson(per);
						refresh();
					}else {
						personDAO.addPerson(per);
						refresh();
					}

				} else {
					JOptionPane.showMessageDialog(MainFrame.this, "PersonalID has already existed. Try again!!",
							"ERROR", JOptionPane.ERROR_MESSAGE);
				}		
			}
		});

// 		COMPLAINTS TABLE LISTENER
		complaintPanel.setTableListener(new TableComplaintsListener() {
			@Override
			public void tableEventDeleted(int id) {
				complaintDAO.deleteComplaint(id);
				refresh();
			}
			
			//COMPLAINT DETALS TABLE LISTENER
			@Override
			public void tableEventDetail(int cplId) {
				Complaint complaint = complaintDAO.findComplaintById(cplId);
				cplDetailFrame = new ComplaintDetailFrame(complaint,acc.getPrivilege());
				cplDetailFrame.setLocationRelativeTo(null);
				cplDetailFrame.setVisible(true);
				cplDetailFrame.setData(comDetailDAO.getPeopleListByComplaintId(cplId));
				
				cplDetailFrame.setTableListener(new TableComplaintDetailListener() {

					@Override
					public void tableEventUpdated(Complaint cpl,List<Integer> lstID) {
						complaintDAO.updateComplaintById(cplId, cpl);
						if(lstID.size()>0) {
							comDetailDAO.removePerson(lstID,cplId);
						}
						JOptionPane.showMessageDialog(null, "Update complaint successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
						refresh();
						cplDetailFrame.dispose();
					}
					
					@Override
					public void tableEventSubmited(Complaint cpl, List<Criminal> lstCri,List<Integer> lstID) {
						complaintDAO.updateComplaintById(cplId, cpl);
						if(lstID.size()>0) {
							comDetailDAO.removePerson(lstID,cplId);
						}
						for (Criminal criminal : lstCri) {
							Criminal lastCriminal = criminalDAO.findLastUpdatedByPersonalId(criminal.getPersonalId());
							if(lastCriminal.getHisOfViolent() != null && lastCriminal.getAppliedDate() != null) {
								criminal.setHisOfViolent(lastCriminal.getHisOfViolent());
							}
							criminalDAO.addCriminal(criminal);
						}
						cplDetailFrame.dispose();
						refresh();
					}
				});
				cplDetailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});

// 		PERSON TABLE LISTENER
		personPanel.setTableListener(new TablePersonListener() {

			@Override
			public void tableEventDeleted(int id) {
				int action = JOptionPane.showConfirmDialog(null, 
						"Do you really want to delete this account", "Confirm Exit", 
						JOptionPane.OK_CANCEL_OPTION);
				if(action == JOptionPane.OK_OPTION) {
					personDAO.deletePerson(id);	
					refresh();
				}
			}
			
			@Override
			public void tableEventAddToCriminalList(int personalId) {
				int countInPrison = personDAO.checkPersonInJail(personalId);
				if(countInPrison < 1) {
					// check if person is criminal
					int countCriminal = personDAO.checkPersonIsCriminal(personalId);
					if(countCriminal < 1) {
						Person per = personDAO.findPersonById(personalId);
						List<Complaint> incidentList = complaintDAO.getAllApprovedComplaints();
						
						relCriminal = new RelevantCriminalForm(per, incidentList);
						relCriminal.setVisible(true);
						relCriminal.setFormListener(new RelevantCriminalFormListener() {
							@Override
							public void criminalFormEventListener(ComplaintDetail comDetail, Criminal newCriminal) {
								List<String> crimeTypeList = comDetailDAO.getCrimeTypeOfPerson(comDetail.getPersonId(), comDetail.getCompId());
								int countCrimeType = 0;
								for (String crimeType : crimeTypeList) {
									if(crimeType.equals(comDetail.getCrimeType())) {
										countCrimeType++;
									}
								}
								if(countCrimeType < 1) {
									comDetailDAO.setComplaintDetail(comDetail);
									criminalDAO.addCriminal(newCriminal);
									relCriminal.dispose();
								}else {
									JOptionPane.showMessageDialog(null, "This type of crime has already attached to this person, choose other ones!", "Error", 
											JOptionPane.OK_OPTION|JOptionPane.ERROR_MESSAGE);
								}
							}
						}); 
					} else {
						JOptionPane.showMessageDialog(null, "This person is already in another incident", "Error", 
								JOptionPane.OK_OPTION|JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Person is serving in Jail. Could not choose!", "Error", 
							JOptionPane.OK_OPTION|JOptionPane.ERROR_MESSAGE);
				}
			}

			@Override
			public void tableEventAttached(int personalId) {
				// IF PERSON IN JAIL
				int inJail = personDAO.checkPersonInJail(personalId);
				// IF PERSON IS A CRIMINAL
				int isCriminal = personDAO.checkPersonIsCriminal(personalId);
				
				if(inJail == 1) {
					JOptionPane.showMessageDialog(MainFrame.this, "Person is serving in Jail. Could not choose!", "Oops! something went wrong", 
							JOptionPane.WARNING_MESSAGE);
				}else if(isCriminal == 1) {
					JOptionPane.showMessageDialog(MainFrame.this, "Person is in Criminal list. Could not choose!", "Oops! something went wrong", 
							JOptionPane.WARNING_MESSAGE);
				}else {
					Person per = personDAO.findPersonById(personalId);
					List<Complaint> listComplaints = complaintDAO.getAllUnverifiedComplaints();
					
					relComplain = new RelevantComplaintForm(per, listComplaints);
					relComplain.setVisible(true);
					relComplain.setFormListener(new RelevantFormListener() {
						@Override
						public void formEventListener(ComplaintDetail comDetail) {
							int ExistedInComplaint = personDAO.checkPersonExistedInComplaint(personalId, comDetail.getCompId());
							if(ExistedInComplaint != 0) {
								JOptionPane.showMessageDialog(MainFrame.this, "Person is in the other complaint. Could not choose!", "Oops! something went wrong", 
										JOptionPane.WARNING_MESSAGE);
							}else {
								List<String> crimeTypeList = comDetailDAO.getCrimeTypeOfPerson(comDetail.getPersonId(), comDetail.getCompId());
								int count = 0;
								for (String crimeType : crimeTypeList) {
									if(crimeType.equals(comDetail.getCrimeType())) {
										count++;
									}
								}
								if(count < 1) {
									comDetailDAO.setComplaintDetail(comDetail);
									relComplain.dispose();
								}else {
									JOptionPane.showMessageDialog(null, "This type of crime has already attached to this person, choose other ones!", "Error", 
											JOptionPane.OK_OPTION|JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					});
				}
			}
			
			@Override
			public void tableEventAddVictim(int id) {
				Person ps = personDAO.findPersonById(id);
				int inJail = personDAO.checkPersonInJail(id);
				int isCriminal = personDAO.checkPersonIsCriminal(id);
				if (inJail > 0 || isCriminal > 0) {
					ImageIcon img = new ImageIcon(getClass().getResource("/images/handcuffs.png"));
					JOptionPane.showMessageDialog(MainFrame.this, "This person is being supervised in custody. Cannot add to victim", "Failed", 
							JOptionPane.WARNING_MESSAGE, img);
					return;
				}
				List<Complaint> filteredList = new ArrayList<Complaint>();
				List<Complaint> list = complaintDAO.getAllApprovedComplaints();
				Set<Integer> committedIncidents = complaintDAO.findIncidentsCommitedByPerson(id)
													.stream().collect(Collectors.toSet());
				filteredList = list.stream().filter(i -> !committedIncidents.contains(i.getId())).collect(Collectors.toList());
				victimForm = new VictimFormPanel(ps, filteredList);
				victimForm.setLocationRelativeTo(null);
				victimForm.setVisible(true);
				victimForm.setFormListener(new VictimFormListener() {
					@Override
					public void linkNewVictim(Victim victim) {
						if (victimDAO.checkIfPersonExistAsVictim(victim)) {
							JOptionPane.showMessageDialog(null, "This person is already a victim of this incident.", "Failed", 
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						victimDAO.linkNewVictim(victim, acc.getUserID());
						victimForm.dispose();
						refresh();
					}
				});
			}

			@Override
			public void tableEventPersonDetail(int id) {
				Person per = personDAO.findPersonById(id);
				
				int jailStatus = personDAO.checkPersonInJail(id);

				Criminal cri = criminalDAO.findLastUpdatedByPersonalId(id);
//				String history = cri.getHisOfViolent().replace("<br>***************<br>", "\n\n");
				String history = cri.getHisOfViolent();
				if (history != null) {
					history = history.replace("<br>***************<br>", "\n\n");
				}
				detailPersonFrame = new PersonDetailFrame(per, jailStatus, history, acc.getPrivilege());
				detailPersonFrame.setLocationRelativeTo(null);
				detailPersonFrame.setVisible(true);
				
				detailPersonFrame.setFormListener(new PersonDetailListener() {
					
					@Override
					public void formEventListener(int id) {
						int action = JOptionPane.showConfirmDialog(null, 
								"Do you really want to delete this account", "Confirm Exit", 
								JOptionPane.OK_CANCEL_OPTION);
						if(action == JOptionPane.OK_OPTION) {
							personDAO.deletePerson(id);
							detailPersonFrame.setVisible(false);
							refresh();
							MainFrame.this.setVisible(true);
						}
					}

					@Override
					public void updateEventListener(Person acc) {
						personDAO.updatePersonByID(acc);
						detailPersonFrame.dispose();
						refresh();
					}
				});
			}
		});
		
//		PRISONLIST TABLE LISTENER
		
		prisonListPanel.setTableListener(new TablePrisonListListener() {
			
			@Override
			public void displayPrisonListDetail(int id) {
//				Code in writing
				
				PrisonList prison = prisonListDAO.getPrisonListByID(id);
				List<Prisoner> prisonInList = prisonListDAO.getAllPrisonerByPrisonListID(id);
				List<PrisonList> prisonlist = prisonListDAO.getAllPrisonList();
				
				prisonListDetailFrame = new PrisonListDetailFrame(prison, prisonlist, prisonInList, acc.getPrivilege());
				prisonListDetailFrame.setLocationRelativeTo(null);
				prisonListDetailFrame.setVisible(true);	
				
				prisonListDetailFrame.setFormListener(new TablePrisonerInListListener() {
					@Override
					public void savePrisonInfo(PrisonList prl, List<Prisoner> listReleasedPrisoners, List<Prisoner> listTransferedPrisoners) {	
						
						prisonListDAO.updatePrisonInfo(prl);
						
						if(listReleasedPrisoners.size() > 0) {
							prisonerDAO.releaseListPrisoners(listReleasedPrisoners,acc.getUserID());
						}
						
						if(listTransferedPrisoners.size() > 0) {
							prisonerDAO.transferListPrisoner(listTransferedPrisoners,acc.getUserID());
						}
						
						JOptionPane.showMessageDialog(null, "All processes have been done", "Success", JOptionPane.INFORMATION_MESSAGE);
						prisonListDetailFrame.setVisible(false);	
						refresh();
					}					
				});
			}
		});
		
//		INCIDENT TABLE LISTENER		
		incidentPanel.setTableListener(new TableIncidentListener() {		
			//INCIDENT DETALS TABLE LISTENER
			@Override
			public void tableEventDetail(int id) {
				Complaint incident = complaintDAO.findComplaintById(id);
				incDetailFrame = new IncidentDetailFrame(incident);
				incDetailFrame.setLocationRelativeTo(null);
				incDetailFrame.setVisible(true);
				incDetailFrame.setData(comDetailDAO.getCriminalListByIncidentId(id));
				incDetailFrame.setDataVictim(comDetailDAO.getVictimListByIncidentId(id));
				
				incDetailFrame.setTableListener(new TableIncidentDetailListener() {

					@Override
					public void tableEventUpdated(Complaint inc) {
						complaintDAO.updateComplaintById(id, inc);
						JOptionPane.showMessageDialog(null, "Update incident successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
						refresh();
						incDetailFrame.dispose();
					}
					
				});
				incDetailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		
//		CRIMINAL TABLE LISTENER
		criminalPanel.setTableListener(new TableCriminalListener() {
			
			@Override
			public void tableEventDetail(int id) {
				Criminal cri = criminalDAO.findCriminalbyId(id);
		
				Person per = personDAO.findPersonById(cri.getPersonalId());
				cri.setImage(per.getImage());
				cri.setName(per.getName());
				cri.setNationality(per.getNationality());
				cri.setDob(per.getDob());
				cri.setGender(per.getGender());
				
				Criminal criminal = criminalDAO.findLastUpdatedByPersonalId(per.getPersonalId());
				cri.setHisOfViolent(criminal.getHisOfViolent());
				
			
				List<String> crimeTypes = comDetailDAO.getCrimeTypeOfPerson(cri.getPersonalId(), cri.getComplaintId());
				
				List<PrisonList> prisonlst = prisonListDAO.getAllPrisonList();

				criDetailFrame = new CriminalDetailsFrame(cri,crimeTypes,prisonlst,acc.getPrivilege());
				MainFrame.this.setVisible(false);
				criDetailFrame.setVisible(true);
				criDetailFrame.setTableListener(new TableCriminalDetailsListener() {
					
					@Override
					public void tableInsertPrisoner(Prisoner prisoner,Criminal cri) {
						PrisonList prison = prisonListDAO.getPrisonListByID(prisoner.getPrisonId());
						if(prison.getCapacity() == prison.getQuantity()) {
							JOptionPane.showMessageDialog(null, "Prison reach limitations. Please choose another prison", "Oopss!", JOptionPane.INFORMATION_MESSAGE);
						}else{
							prisonerDAO.addPrisoner(prisoner,acc.getUserID());
							tableUpdatedCriminal(cri);
						};
					}
					
					
					@Override
					public void tableUpdatedCriminal(Criminal cri) {
						criminalDAO.updateCriminal(cri,acc.getUserID());
						refresh();
						criDetailFrame.dispose();
						MainFrame.this.setVisible(true);
					}

					@Override
					public void tableDumpEvent() {
						criDetailFrame.setVisible(false);
						MainFrame.this.setVisible(true);
					}
				});
			}
		});
		
//		PRISONER TABLE LISTENER
		prisonerPanel.setTableListener(new TablePrisonerListener() {

			@Override
			public void tableEventDetail(int id) {
				Prisoner prisoner = prisonerDAO.findPrisonerByID(id);
				prisonerDetailFrame = new PrisonerDetailFrame(prisoner);
				prisonerDetailFrame.setLocationRelativeTo(null);
				prisonerDetailFrame.setVisible(true);
				prisonerDetailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
			
			@Override
			public void tableEventRelease(int id) {
				prisonerDAO.releasePrisoner(id,acc.getUserID());
				Prisoner prisoner = prisonerDAO.findPrisonerByID(id);
				Date startDate = prisoner.getStartDate();
				Date endDate = prisoner.getEndDate();
				if (endDate != null) {
					int getDuration = (int) getDateDiff(startDate, endDate, TimeUnit.DAYS);
				    prisonerDAO.updateDurationByPrisonerID(id, getDuration + 1);
					refresh();
				}

			}
			
			@Override
			public void tableEventTransfer(int id) {
				Prisoner prisoner = prisonerDAO.findPrisonerByID(id);
				List<PrisonList> prisonList = prisonListDAO.getAllAvailablePrisons();

				for (int i = 0; i < prisonList.size(); i++) {
					if (prisonList.get(i).getId() == prisoner.getPrisonId()) {
						prisonList.remove(i);
					}
				}
				relPrisoner = new RelevantPrisonerForm(prisoner, prisonList);
				relPrisoner.setVisible(true);
				relPrisoner.setFormListener(new RelevantPrisonerFormListener() {
					@Override
					public void prisonerFormEventListener(Prisoner prisoner, PrisonList prison) {
						prisonerDAO.transferPrisoner(id, prison.getId(),acc.getUserID());
						relPrisoner.dispose();
						refresh();
					}
				});
			}
		});
		
//		VICTIM TABLE LISTENER
		victimPanel.setTableListener(new TableVictimListener() {

			@Override
			public void tableEventDeleted(int personalId) {
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure to remove this person from Victim list ?", "Remove", JOptionPane.YES_NO_OPTION);
				if (choice == 0) {
					victimDAO.removeVictimbyPersonalId(personalId);
					refresh();
					JOptionPane.showMessageDialog(null, "Victim has been removed", "Success", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

//		ADD COMPONENTS INTO LAYOUT
		add(splitPane, BorderLayout.CENTER);
		add(toolbar, BorderLayout.PAGE_START);

		setMinimumSize(new Dimension(900, 800));
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu windowMenu = new JMenu("Window");

		JMenu showMenu = new JMenu("Show");
		JMenuItem exitBtn = new JMenuItem("Exit");

		JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Form Account");
		showFormItem.setSelected(true);

		showMenu.add(showFormItem);
		windowMenu.add(showMenu);
		windowMenu.add(exitBtn);

		menuBar.add(windowMenu);

		showFormItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();

				panelCont.setVisible(menuItem.isSelected());
			}
		});

		exitBtn.setMnemonic(KeyEvent.VK_X);
		exitBtn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// using MainFrame.this to show Dialog contained in the MainFrame if no it will
				// appear outside
				int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit the application",
						"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);

				if (action == JOptionPane.OK_OPTION) {
					Login.main(null);
					MainFrame.this.setVisible(false);
				}
			}
		});

		return menuBar;
	}
	
	private void saveImage(File file) {
		try {
			// save image
			BufferedImage img = ImageIO.read(file);
			try {
				String location = System.getProperty("user.dir") + "/src/main/resources/avatar/" + file.getName();
				String format = "PNG";
				ImageIO.write(img, format, new File(location));

			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} catch (IOException err) {
			err.printStackTrace(); // todo: implement proper error handeling
		}
	}

	private File renameFile(File toBeRenamed, String new_name) throws IOException {
		// need to be in the same path
		File fileWithNewName = new File(toBeRenamed.getParent(), new_name + ".png");
		if (fileWithNewName.exists()) {
			throw new IOException("file exists");
		}
		// Rename file (or directory)
		boolean success = toBeRenamed.renameTo(fileWithNewName);
		if (!success) {
			// File was not successfully renamed
		}
		return fileWithNewName;
	}
	
	private void refresh() {
		personPanel.setData(personDAO.getAlivePeople());
		personPanel.refresh();
		incidentPanel.setData(complaintDAO.getAllApprovedComplaints());
		incidentPanel.refresh();
		complaintPanel.setData(complaintDAO.getAllUnverifiedComplaints());
		complaintPanel.refresh();
		criminalPanel.setData(criminalDAO.getAllCriminals());
		criminalPanel.refresh();

		prisonerPanel.setData(prisonerDAO.getUnreleasedPrisoners());
		prisonerPanel.refresh();
		victimPanel.setData(victimDAO.getAllVictims());
		victimPanel.refresh();
		
		prisonListPanel.setData(prisonListDAO.getAllPrisonList());
		prisonListPanel.refresh();
	}
	
	public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
}
