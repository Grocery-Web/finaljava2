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
import javax.swing.ButtonGroup;

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
	
	Person userInFrame;
	File imgChooser = null;
	int ID; //Person's ID

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
	
	
	
	public PersonDetailFrame( Person person ) {
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
			// TODO: handle exception
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

		
	}
	
	public PersonDetailFrame() {
		initPersonDetailFrame();
	}

	private void initPersonDetailFrame() {
		setTitle("Person Detail");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 560, 546);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		
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
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(lblImgUser, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnUpload, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSave, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnClose, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))))
					.addGap(19)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtID, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtName, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblGender, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtnMale, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(rdbtnFemale))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDob, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAddress, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNationality, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblJob, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(datePerson, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
								.addComponent(txtAddress, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
								.addComponent(txtNation, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
								.addComponent(txtJob, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))))
					.addGap(44))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(65)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblImgUser, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(txtID, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel)
								.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(20)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblGender, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(rdbtnFemale)
									.addComponent(rdbtnMale)))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblDob, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(datePerson, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblAddress, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNationality, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtNation, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblJob, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtJob, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))))
					.addGap(31)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnUpload, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
					.addGap(31)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(20, Short.MAX_VALUE))
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
