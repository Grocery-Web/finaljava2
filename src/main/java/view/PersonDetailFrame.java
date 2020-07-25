package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.Image;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.time.LocalDate;
import java.util.Calendar;

import entity.Gender;
import entity.Person;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JTextArea;

public class PersonDetailFrame extends JFrame {

	private JPanel contentPane;
	private JLabel lblImgUser;
	private JButton btnUpload;
	private JLabel lblNewLabel;
	private JLabel lblGender;
	private JLabel lblDob;
	private JLabel lblAddress;
	private JLabel lblNationality;
	private JLabel lblJob;
	private JTextField txtName;
	private JTextField txtAddress;
	private JTextField txtNation;
	private JTextField txtJob;
	private JButton btnSave;
	private JDateChooser datePerson;
	private JButton btnClose;
	private JButton btnDelete;
	private JLabel lblId;
	private PersonDetailListener psListen;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextArea textViolenceHistory;
	private JScrollPane jpHistory;
	
	Person userInFrame;
	File imgChooser = null;
	int ID; //Person's ID
	private JTextField textStatus;
	private JLabel lblPersonalID;
	private JTextFieldDateEditor editor;
	private JLabel q1, q2, q3, q4, q5;
	private boolean cd1, cd2, cd3, cd4, cd5;
	public String s = Character.toString("\u2713".toCharArray()[0]);

	/**
	 * Create the frame.
	 */
	public PersonDetailFrame( Person person, int jailStatus, String history, int privilege) {
		//get person and save all data to userInFrame.
		initPersonDetailFrame();
		ID = person.getPersonalId();
		
		lblPersonalID.setText(Integer.toString(person.getPersonalId()));
		
		txtName.setText(person.getName());
		txtAddress.setText(person.getAddress());
		txtNation.setText(person.getNationality());
		txtJob.setText(person.getJob());		
		datePerson.setDate(person.getDob());
		if (person.getGender().toString() == "male") {
			rdbtnMale.setSelected(true);
		}
		else
			rdbtnFemale.setSelected(true);
		
		if (jailStatus == 0) {
			textStatus.setText("Being free");
		} else {
			textStatus.setText("Being imprisoned");
		}
		
		if (history != null) {
			textViolenceHistory.setText(history);
		} else {
			textViolenceHistory.setText("No Criminal Record");
		}
			
		
		var url ="avatar/" + person.getImage();
		
		//try to get avatar. If not, get the default avatar base on their gender
		try {
			Image imgPerson = 
					new ImageIcon(
						getClass().getClassLoader()
						.getResource(url)
						).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			
			lblImgUser.setIcon(new ImageIcon(imgPerson));
			
		} catch (Exception e) {
			if (person.getGender().toString() == "male") {
				url = "images/male.png";
			}
			else url = "images/female.png";
			
			Image imgPerson = 
					new ImageIcon(
						getClass().getClassLoader()
						.getResource(url)
						).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			
			lblImgUser.setIcon(new ImageIcon(imgPerson));
		}
		
		
		userInFrame = new Person();
		
		userInFrame.setPersonalId(person.getPersonalId());
		userInFrame.setName(person.getName());
		userInFrame.setAddress(person.getAddress());
		userInFrame.setNationality(person.getNationality());
		userInFrame.setJob(person.getJob());
		userInFrame.setDob(person.getDob());
		userInFrame.setGender(person.getGender());

//		IF USER PRIVILEGE
		if(privilege == 3) {
			btnDelete.setEnabled(false);
		}
	}
	
	public PersonDetailFrame() {
		initPersonDetailFrame();
	}

	private void initPersonDetailFrame() {
		setTitle("Person Detail");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 809, 555);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		setResizable(false);
		
		lblImgUser = new JLabel("");
		
		btnUpload = new JButton("Browse ...");
		btnUpload.setForeground(Color.BLACK);
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnUploadactionPerformed(e);
			}
		});
		btnUpload.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblNewLabel = new JLabel("Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblDob = new JLabel("DOB");
		lblDob.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblNationality = new JLabel("Nationality");
		lblNationality.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblJob = new JLabel("Job");
		lblJob.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtName.setColumns(10);
		txtName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				cd1Check();
				checkUnlock();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				cd1Check();
				checkUnlock();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				cd1Check();
				checkUnlock();
			}
		});
		
		txtAddress = new JTextField();
		txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtAddress.setColumns(10);
		txtAddress.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				cd3Check();
				checkUnlock();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				cd3Check();
				checkUnlock();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				cd3Check();
				checkUnlock();
			}
		});
		
		txtNation = new JTextField();
		txtNation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNation.setColumns(10);
		txtNation.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				cd4Check();
				checkUnlock();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				cd4Check();
				checkUnlock();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				cd4Check();
				checkUnlock();
			}
		});
		
		txtJob = new JTextField();
		txtJob.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtJob.setColumns(10);
		txtJob.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				cd5Check();
				checkUnlock();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				cd5Check();
				checkUnlock();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				cd5Check();
				checkUnlock();
			}
		});
		
		btnSave = new JButton("Save");
		btnSave.setForeground(Color.BLUE);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSaveactionPerformed(e);
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		datePerson = new JDateChooser();
		editor = (JTextFieldDateEditor) datePerson.getDateEditor();
		editor.setEditable(false);
		datePerson.setDateFormatString("yyyy-MM-dd");
		datePerson.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				cd2Check();
				checkUnlock();
			}
		});
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCloseactionPerformed(e);
			}
		});
		btnClose.setForeground(Color.BLACK);
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDeleteactionPerformed(e);
			}
		});
		btnDelete.setForeground(Color.RED);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblId = new JLabel("ID");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonGroup.add(rdbtnMale);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonGroup.add(rdbtnFemale);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textStatus = new JTextField();
		textStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textStatus.setColumns(10);
		textStatus.setEditable(false);
		
		JLabel lblViolenceHistory = new JLabel("Violence History");
		lblViolenceHistory.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textViolenceHistory = new JTextArea();
		textViolenceHistory.setLineWrap(true);
		textViolenceHistory.setWrapStyleWord(true);
		textViolenceHistory.setEditable(false);
		jpHistory = new JScrollPane(textViolenceHistory);
		jpHistory.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		lblPersonalID = new JLabel("");
		
		q1 = new JLabel("?");
		q2 = new JLabel("?");
		q3 = new JLabel("?");
		q4 = new JLabel("?");
		q5 = new JLabel("?");
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblImgUser, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnSave, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
								.addComponent(btnUpload, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
								.addComponent(btnClose, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblGender, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(rdbtnMale, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(rdbtnFemale, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblDob, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(datePerson, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(q2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addGap(252))
							.addComponent(lblNationality, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
											.addComponent(lblViolenceHistory, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblStatus, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(jpHistory, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
													.addComponent(textStatus, Alignment.LEADING)
													.addComponent(txtJob, Alignment.LEADING)
													.addComponent(txtNation, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
													.addComponent(q5, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
													.addComponent(q4, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)))))
									.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
										.addComponent(lblAddress, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(q3, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap())
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
										.addComponent(txtName, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(q1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
										.addGap(259))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblPersonalID)
										.addContainerGap()))))
						.addComponent(lblJob, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblPersonalID))
									.addGap(18)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel)
										.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(q1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
									.addGap(18)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblGender, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
											.addComponent(rdbtnMale))
										.addComponent(rdbtnFemale))
									.addGap(16)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblDob, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
										.addComponent(datePerson, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(q2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
									.addGap(18)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblAddress, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(18)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblNationality, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
												.addComponent(txtNation, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblJob, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
												.addComponent(txtJob, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(18)
											.addComponent(q4, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
											.addGap(20)
											.addComponent(q5, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
											.addGap(3))))
								.addComponent(lblImgUser, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE)))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(q3, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addGap(88)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(90)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnUpload, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(textStatus, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblStatus, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblViolenceHistory, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(jpHistory, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))))
					.addGap(38))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
// FUNCTIONS TO CHECK INPUT VALIDATION
	private void cd1Check() {
		if (!txtName.getText().equals("") && txtName.getText().matches("[\\D ]{1,50}")) {
			txtName.setBorder(new LineBorder(Color.GREEN, 1));
			q1.setText(s);
			q1.setForeground(new Color(0, 153, 51));
			q1.setToolTipText(null);
			cd1 = true;
		} else {
			txtName.setBorder(new LineBorder(Color.RED, 1));
			q1.setText("?");
			q1.setForeground(Color.RED);
			q1.setToolTipText("1 - 50 alphabet and special characters");
			cd1 = false;
		}
	}
	
	private void cd2Check() {
		Calendar cal = Calendar.getInstance();
		if (datePerson.getDate() != null) {
			cal.setTime(datePerson.getDate());
			LocalDate date1 = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
			LocalDate date2 = LocalDate.now();
			if (date1.compareTo(date2) <= 0) {
				q2.setText(s); q2.setForeground(new Color(0, 153, 51));
				q2.setToolTipText(null);
				cd2 = true;
			} else {
				q2.setText("?"); q2.setForeground(Color.RED);
				q2.setToolTipText("Select a date that is no later than today.");
				cd2 = false;
			}
		}
	}
	
	private void cd3Check() {
		if (!txtAddress.getText().equals("") && txtAddress.getText().matches("(.|\\s){2,4000}")) {
			txtAddress.setBorder(new LineBorder(Color.GREEN, 1));
			q3.setText(s);
			q3.setForeground(new Color(0, 153, 51));
			q3.setToolTipText(null);
			cd3 = true;
		} else {
			txtAddress.setBorder(new LineBorder(Color.RED, 1));
			q3.setText("?");
			q3.setForeground(Color.RED);
			q3.setToolTipText("Minimum length required is 2 characters");
			cd3 = false;
		}
	}

	private void cd4Check() {
		if (!txtNation.getText().equals("") && txtNation.getText().matches("[a-zA-Z]{4,50}")) {
			txtNation.setBorder(new LineBorder(Color.GREEN, 1));
			q4.setText(s);
			q4.setForeground(new Color(0, 153, 51));
			q4.setToolTipText(null);
			cd4 = true;
		} else {
			txtNation.setBorder(new LineBorder(Color.RED, 1));
			q4.setText("?");
			q4.setForeground(Color.RED);
			q4.setToolTipText("4 - 50 alphabet characters");
			cd4 = false;
		}
	}

	private void cd5Check() {
		if (!txtJob.getText().equals("") && txtJob.getText().matches("[a-zA-Z\\s]{4,20}")) {
			txtJob.setBorder(new LineBorder(Color.GREEN, 1));
			q5.setText(s);
			q5.setForeground(new Color(0, 153, 51));
			q5.setToolTipText(null);
			cd5 = true;
		} else {
			txtJob.setBorder(new LineBorder(Color.RED, 1));
			q5.setText("?");
			q5.setForeground(Color.RED);
			q5.setToolTipText("4 - 20 alphabet characters or spaces");
			cd5 = false;
		}
	}
	
	private void checkUnlock() {
		boolean unlock = cd1 && cd2 && cd3 && cd4 && cd5;
		btnSave.setEnabled(unlock);
	}
	
	protected void btnUploadactionPerformed(ActionEvent e) {
		
		JFileChooser filechooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images (jpg, gif, png)", "jpg","gif","png");
		filechooser.setFileFilter(filter);
	    filechooser.setDialogTitle("Choose Your Avatar");
	    int returnval = filechooser.showOpenDialog(this);
	    if (returnval == JFileChooser.APPROVE_OPTION)
	    {
	    	imgChooser = filechooser.getSelectedFile();
	    	BufferedImage bi;
	    	try {
	    		 	bi = ImageIO.read(imgChooser);
	    		 	lblImgUser.setIcon(new ImageIcon(bi.getScaledInstance(200, 200,  Image.SCALE_SMOOTH)));
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Something went wrong. Please try again");
			}
	    	
	    }
	}
	protected void btnSaveactionPerformed(ActionEvent e) {
		
		userInFrame.setName(txtName.getText());
		userInFrame.setAddress(txtAddress.getText());
		userInFrame.setNationality(txtNation.getText());
		userInFrame.setJob(txtJob.getText());
		userInFrame.setDob( datePerson.getDate());
		userInFrame.setImage(Integer.toString(userInFrame.getPersonalId())+".png");
		
		if (rdbtnMale.isSelected()) {
			userInFrame.setGender(Gender.male);
		}
		else {
			userInFrame.setGender(Gender.female);
		}
		if (imgChooser != null) {
			try {
				BufferedImage img = ImageIO.read(imgChooser);
				String location = System.getProperty("user.dir") + "/src/main/resources/avatar/" + ID + ".png";
				String format = "PNG";
				ImageIO.write(img, format, new File(location));
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
			}
		}
		userInFrame.setAlive(true);
		psListen.updateEventListener(userInFrame);
	}
	
	protected void btnCloseactionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	public void setFormListener(PersonDetailListener psListener) {
		this.psListen = psListener;
		
	}
	
	protected void btnDeleteactionPerformed(ActionEvent e) {
		if (psListen !=null) {
			psListen.formEventListener(userInFrame.getPersonalId());
		}
	}
}
