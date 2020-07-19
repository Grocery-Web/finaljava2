package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entity.Prisoner;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class PrisonerDetailFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textName;
	private JTextField textPrisonId;
	private JTextField textDob;
	private JTextField textGender;
	private JTextField textStartDate;
	private JLabel lblReleaseDate;
	private JTextField textReleaseDate;
	private JLabel lblJailDuration;
	private JTextField textJailDuration;
	private JLabel lblType;
	private JTextField textType;
	private JLabel lblPrison;
	private JTextField textPrison;
	private JLabel lblReleaseStatus;
	private JLabel lblHistory;
	private JTextArea textHisOfViolence;
	private JScrollPane jpHistory;
	private ButtonGroup groupBtn;
	private SimpleDateFormat sdf;
	private JRadioButton rdbtnImprisoned, rdbtnReleased;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PrisonerDetailFrame frame = new PrisonerDetailFrame();
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
	public PrisonerDetailFrame(Prisoner pri) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 564, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		// Prisoner name
		JLabel lblName = new JLabel("Name: ");	
		textName = new JTextField();
		textName.setColumns(10);
		textName.setText(pri.getName());
		textName.setEditable(false);	
		
		// Prisoner ID
		JLabel lblPrisonerId = new JLabel("Prisoner ID:");
		textPrisonId = new JTextField();
		textPrisonId.setColumns(10);
		textPrisonId.setText(String.valueOf(pri.getPrisonerId()));
		textPrisonId.setEditable(false);

		
		// Prisoner DOB
		JLabel lblDob = new JLabel("Date of Birth:");	
		textDob = new JTextField();
		textDob.setColumns(10);
		textDob.setText(new SimpleDateFormat("yyyy-MM-dd").format(pri.getDob()));
		textDob.setEditable(false);
		
		// Prisoner Gender
		JLabel lblGender = new JLabel("Gender:");	
		textGender = new JTextField();
		textGender.setColumns(10);
		textGender.setText(pri.getGender().toString());
		textGender.setEditable(false);
		
		// Prisoner Start Date
		JLabel lblStartDate = new JLabel("Start Date:");	
		textStartDate = new JTextField();
		textStartDate.setColumns(10);
		textStartDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(pri.getStartDate()));
		textStartDate.setEditable(false);

		// Prisoner Release Date
		lblReleaseDate = new JLabel("Release Date:");	
		textReleaseDate = new JTextField();
		textReleaseDate.setColumns(10);
		if (pri.getEndDate() != null) {
			textReleaseDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(pri.getEndDate()));
		}
		textReleaseDate.setEditable(false);
	
		// Prisoner Jail Duration
		lblJailDuration = new JLabel("Jail Duration:");	
		textJailDuration = new JTextField();
		textJailDuration.setColumns(10);
		textJailDuration.setText(String.valueOf(pri.getDuration()) + " days");
		textJailDuration.setEditable(false);
		
		// Prisoner Type
		lblType = new JLabel("Type:");	
		textType = new JTextField();
		textType.setColumns(10);
		textType.setText(pri.getType());
		textType.setEditable(false);
		
		// Prison name
		lblPrison = new JLabel("Prison:");
		textPrison = new JTextField();
		textPrison.setColumns(10);
		textPrison.setText(pri.getPrisonName());
		textPrison.setEditable(false);
		
		// Release Status
		lblReleaseStatus = new JLabel("Release Status:");		
		rdbtnImprisoned = new JRadioButton("Imprisoned");		
		rdbtnReleased = new JRadioButton("Released");
		groupBtn = new ButtonGroup();
		groupBtn.add(rdbtnImprisoned);
		groupBtn.add(rdbtnReleased);
		if (pri.isReleasedStatus() == false) {
			rdbtnImprisoned.setSelected(true);
		} else {
			rdbtnReleased.setSelected(true);
		}
		Enumeration<AbstractButton> enumeration = groupBtn.getElements();
		while (enumeration.hasMoreElements()) {
		    enumeration.nextElement().setEnabled(false);
		}
	
		
		// Prisoner Image
		JLabel lblImg = new JLabel("");
		var url ="avatar/" + pri.getImage();
				
				//try to get avatar. If not, get the default avatar base on their gender
				try {
					Image imgPerson = 
							new ImageIcon(
								getClass().getClassLoader()
								.getResource(url)
								).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
					
					lblImg.setIcon(new ImageIcon(imgPerson));
					
				} catch (Exception e) {
					if (pri.getGender().toString() == "male") {
						url = "images/male.png";
					}
					else url = "images/female.png";
					
					Image imgPerson = 
							new ImageIcon(
								getClass().getClassLoader()
								.getResource(url)
								).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
					
					lblImg.setIcon(new ImageIcon(imgPerson));
				}
				
		// History of violence
		lblHistory = new JLabel("Violence History:");
		textHisOfViolence = new JTextArea();
		textHisOfViolence.setLineWrap(true);
		textHisOfViolence.setWrapStyleWord(true);
		textHisOfViolence.setEditable(false);
		jpHistory = new JScrollPane(textHisOfViolence);
		jpHistory.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblReleaseStatus)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblName)
										.addComponent(lblPrisonerId))
									.addGap(42)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(textPrisonId, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
										.addComponent(textName, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblDob)
										.addComponent(lblGender)
										.addComponent(lblStartDate)
										.addComponent(lblType)
										.addComponent(lblPrison)
										.addComponent(lblReleaseDate)
										.addComponent(lblJailDuration))
									.addGap(31)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textPrison, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
										.addComponent(textType)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(textJailDuration, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
											.addComponent(textReleaseDate, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
											.addComponent(textStartDate, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
											.addComponent(textGender, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
											.addComponent(textDob, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblImg, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblHistory)
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(rdbtnImprisoned)
									.addGap(18)
									.addComponent(rdbtnReleased, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
								.addComponent(jpHistory, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))))
					.addGap(335))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(88)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDob)
								.addComponent(textDob, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(26)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblImg, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblName)
										.addComponent(textName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(textPrisonId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblPrisonerId))
									.addGap(42)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(textGender, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblGender))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(textStartDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblStartDate))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblReleaseDate)
										.addComponent(textReleaseDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(textJailDuration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblJailDuration))
									.addGap(14)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblType)
										.addComponent(textType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPrison)
								.addComponent(textPrison, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(28)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHistory)
						.addComponent(jpHistory, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblReleaseStatus)
						.addComponent(rdbtnImprisoned)
						.addComponent(rdbtnReleased))
					.addGap(65))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
