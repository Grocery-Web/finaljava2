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
import dao.PersonDetailDAO;
import entity.Gender;
import entity.Person;

import javax.swing.border.TitledBorder;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;

public class PersonDetailTest extends JFrame {

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
	private JTextField txtGender;
	private JTextField txtAddress;
	private JTextField txtNation;
	private JTextField txtJob;
	private JButton btnSave;
	File imgChooser = null;
	private JDateChooser datePerson;
	Person person = null;
	int ID;
	private JButton btnClose;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersonDetailTest frame = new PersonDetailTest();
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
	
	
	public PersonDetailTest( int id ) {
		initPersonDetailFrame();
		ID = id;
		person = new PersonDAO().findPersonById(id);
		
		txtName.setText(person.getName());
		txtAddress.setText(person.getAddress());
		txtNation.setText(person.getNationality());
		txtJob.setText(person.getJob());		
		datePerson.setDate(person.getDob());
		txtGender.setText(person.getGender().toString());
		
		var url ="images/" + person.getImage();
		Image imgPerson = 
				new ImageIcon(
					getClass().getClassLoader()
					.getResource(url)
					).getImage().getScaledInstance(200, 300, Image.SCALE_FAST);
		
		lblImgUser.setIcon(new ImageIcon(imgPerson));
			
		
		
	}
	
	public PersonDetailTest() {
		initPersonDetailFrame();
	}

	private void initPersonDetailFrame() {
		setTitle("Person Detail");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 558, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		
		lblImgUser = new JLabel("");
		
		btnUpload = new JButton("Browse...");
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
		
		txtGender = new JTextField();
		txtGender.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtGender.setColumns(10);
		
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
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCloseactionPerformed(e);
			}
		});
		btnClose.setForeground(Color.RED);
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblImgUser, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(53)
									.addComponent(btnUpload)))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblAddress, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblDob, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(datePerson, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblGender, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtGender, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtName))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblJob, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtJob, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblNationality, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtNation, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)))
								.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(42, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(14)
					.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblImgUser, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(4)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel)
								.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblGender, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtGender, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addGap(22)
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
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblJob, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtJob, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))))
					.addGap(31)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnUpload)
						.addComponent(btnSave))
					.addContainerGap(14, Short.MAX_VALUE))
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
	    		 	lblImgUser.setIcon(new ImageIcon(bi.getScaledInstance(200, 300,  Image.SCALE_FAST)));
			} catch (Exception e2) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Something went wrong. Please try again");
			}
	    	
	    }
	}
	protected void btnSaveactionPerformed(ActionEvent e) {
		
		PersonDAO psDAO = new PersonDAO();
		
		person.setName(txtName.getText());
		person.setAddress(txtAddress.getText());
		person.setNationality(txtNation.getText());
		person.setJob(txtJob.getText());
		person.setDob( datePerson.getDate());
		if (txtGender.getText() == "male") {
			person.setGender(Gender.male);
		}
		else person.setGender(Gender.female);
		if (imgChooser != null) {
			try {
				BufferedImage img = ImageIO.read(imgChooser);
				String location = System.getProperty("user.dir") + "/src/main/resources/images/" + ID + ".png";
				String format = "PNG";
				ImageIO.write(img, format, new File(location));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
			}
		}
		psDAO.updatePersonByID(ID, person);
	}
	protected void btnCloseactionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
