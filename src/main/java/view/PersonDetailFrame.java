package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
import java.awt.event.ActionEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.PersonDAO;
import entity.Gender;
import entity.Person;

import javax.swing.border.TitledBorder;
import com.toedter.calendar.JDateChooser;
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
	private JTextField txtID;
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersonDetailFrame frame = new PersonDetailFrame();
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
	
	
	
	public PersonDetailFrame( Person person, int jailStatus, String history, int privilege) {
		//get person and save all data to userInFrame.
		initPersonDetailFrame();
		ID = person.getPersonalId();
		
		txtID.setText(Integer.toString(person.getPersonalId()));
		txtID.setEditable(false);
		txtID.setEnabled(false);
		
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
		
		if (history != "") {
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
		
		txtAddress = new JTextField();
		txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtAddress.setColumns(10);
		
		txtNation = new JTextField();
		txtNation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNation.setColumns(10);
		
		txtJob = new JTextField();
		txtJob.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtJob.setColumns(10);
		
		btnSave = new JButton("Save");
		btnSave.setForeground(Color.BLUE);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSaveactionPerformed(e);
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		datePerson = new JDateChooser();
		datePerson.setDateFormatString("yyyy-MM-dd");
		
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
		
		txtID = new JTextField();
		txtID.setBackground(Color.WHITE);
		txtID.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtID.setColumns(10);
		
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
								.addComponent(rdbtnMale, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
								.addComponent(rdbtnFemale)
								.addGap(297))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblDob, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(datePerson, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
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
											.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(textStatus, Alignment.LEADING)
												.addComponent(txtJob, Alignment.LEADING)
												.addComponent(txtNation, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblAddress, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtAddress, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)))
								.addGap(27))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(txtID, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addGap(399))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(txtName, 0, 0, Short.MAX_VALUE)
										.addGap(293)))))
						.addComponent(lblJob, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtID, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel)
								.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnFemale)
								.addComponent(lblGender, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(rdbtnMale))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblDob, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(datePerson, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAddress, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNationality, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtNation, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblJob, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtJob, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblImgUser, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE))
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
