package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dao.ComplaintDAO;
import dao.PersonDAO;
import entity.Complaint;

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

//	INTERFACE LISTERNER
	private ComplaintListener cplListener;

//	DAO
	private PersonDAO personDAO;
	private ComplaintDAO complaintDAO;
	
//	EXTERNAL FRAME OR DIALOG
	private ComplaintDetailFrame cplDetailFrame;
	
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

//		CREAT DAO
		personDAO = new PersonDAO();
		complaintDAO = new ComplaintDAO();

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

//		CALL BACK TABLES
		personPanel.setData(personDAO.getAllAccount());
		complaintPanel.setData(complaintDAO.getAllComplaints());

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
				if(selectedIndex == 0) {
					personPanel.search(txt);
				}
				
				if(selectedIndex == 1) {
					complaintPanel.search(txt);
				}

			}
		});

//		FORM LISTENER
		complaintForm.setFormListener(new ComplaintListener() {
			@Override
			public void complaintListener(Complaint cpt) {
				complaintDAO.addComplaint(cpt);
				complaintPanel.setData(complaintDAO.getAllComplaints());
				complaintPanel.refresh();
			}
		});

// 		COMPLAINTS TABLE LISTENER
		complaintPanel.setTableListener(new TableComplaintsListener() {
			@Override
			public void tableEventDeleted(int id) {
				complaintDAO.deleteComplaint(id);
				complaintPanel.setData(complaintDAO.getAllComplaints());
				complaintPanel.refresh();
			}

			@Override
			public void tableEventDetail(int id) {
				Complaint complaint = complaintDAO.findComplaintById(id);
				cplDetailFrame = new ComplaintDetailFrame(complaint);
				cplDetailFrame.setVisible(true);
				cplDetailFrame.setLocationRelativeTo(null);
				cplDetailFrame.setFrameListener(new ComplaintDetailListener() {
					@Override
					public void updateEventListener(Complaint cpl) {
						// TODO Auto-generated method stub
						System.out.println(cpl);
					}
				});
				cplDetailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});

//		ADD COMPONENTS INTO LAYOUT
		add(splitPane, BorderLayout.CENTER);
		add(toolbar, BorderLayout.PAGE_START);

		setMinimumSize(new Dimension(700, 600));
		setSize(600, 500);
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
}
