package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
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
import javax.swing.border.EmptyBorder;

import dao.ComplaintDAO;
import dao.ComplaintDetailDAO;
import dao.CriminalDAO;
import dao.PersonDAO;
import dao.PrisonListDAO;
import entity.Complaint;
import entity.ComplaintDetail;
import entity.Criminal;
import entity.Person;

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

//	DAO
	private PersonDAO personDAO;
	private ComplaintDAO complaintDAO;
	private ComplaintDetailDAO comDetailDAO;
	private CriminalDAO criminalDAO;
	private PrisonListDAO prisonListDAO;

//	EXTERNAL FRAME OR DIALOG
	private ComplaintDetailFrame cplDetailFrame;
	private RelevantComplaintForm relComplain;
	private PersonDetailFrame detailPersonFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		personPanel = new PersonPanel();
		complaintPanel = new ComplaintsPanel();
		complaintForm = new ComplaintFormPanel();
		tabPane = new JTabbedPane();
		incidentPanel =  new IncidentsPanel();
		criminalPanel = new CriminalPanel();
		prisonListPanel = new PrisonListPanel();

//		CREAT DAO
		personDAO = new PersonDAO();
		complaintDAO = new ComplaintDAO();
		comDetailDAO = new ComplaintDetailDAO();
		criminalDAO = new CriminalDAO();
		prisonListDAO = new PrisonListDAO();

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
		tabPane.addTab("Prison List", prisonListPanel);

//		CALL BACK TABLES
		personPanel.setData(personDAO.getAlivePeople());
		complaintPanel.setData(complaintDAO.getAllUnverifiedComplaints());
		incidentPanel.setData(complaintDAO.getAllApprovedComplaints());
		criminalPanel.setData(criminalDAO.getAllCriminals());
		prisonListPanel.setData(prisonListDAO.getAllPrisonList());

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
			public void searchText(String txt) {
				int selectedIndex = tabPane.getSelectedIndex();
				if (selectedIndex == 0) {
					personPanel.search(txt);
				}

				if (selectedIndex == 1) {
					complaintPanel.search(txt);
				}

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
			public void tableEventDetail(int id) {
				Complaint complaint = complaintDAO.findComplaintById(id);
				cplDetailFrame = new ComplaintDetailFrame(complaint);
				cplDetailFrame.setLocationRelativeTo(null);
				cplDetailFrame.setVisible(true);
				cplDetailFrame.setData(comDetailDAO.getPeopleListByComplaintId(id));
				
				cplDetailFrame.setTableListener(new TableComplaintDetailListener() {
					@Override
					public void tableEventDeleted(int personId) {
						comDetailDAO.removePerson(personId,id);
						cplDetailFrame.setData(comDetailDAO.getPeopleListByComplaintId(id));
						cplDetailFrame.refresh();
					}

					@Override
					public void tableEventUpdated(Complaint cpl) {
						complaintDAO.updateComplaintById(id, cpl);
						JOptionPane.showMessageDialog(null, "Update complaint successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
						refresh();
						cplDetailFrame.dispose();
					}
					
					@Override
					public void tableEventSubmited(Complaint cpl, List<Criminal> lstCri) {
						complaintDAO.updateComplaintById(id, cpl);
						for (Criminal criminal : lstCri) {
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
			public void tableEventAttached(int personalId) {
				Person per = personDAO.findPersonById(personalId);
				List<Complaint> listComplaints = complaintDAO.getAllUnverifiedComplaints();
				
				relComplain = new RelevantComplaintForm(per, listComplaints);
				relComplain.setVisible(true);
				relComplain.setFormListener(new RelevantFormListener() {
					@Override
					public void formEventListener(ComplaintDetail comDetail) {
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
				});
			}

			@Override
			public void tableEventPersonDetail(int id) {
				Person per = personDAO.findPersonById(id);
				
				detailPersonFrame = new PersonDetailFrame(per);
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
						refresh();
						detailPersonFrame.setVisible(false);
						MainFrame.this.setVisible(true);
					}
				});
				
			}
		});

//		ADD COMPONENTS INTO LAYOUT
		add(splitPane, BorderLayout.CENTER);
		add(toolbar, BorderLayout.PAGE_START);

		setMinimumSize(new Dimension(700, 600));
		setSize(1000, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

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
					System.exit(0);
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
	}
}
