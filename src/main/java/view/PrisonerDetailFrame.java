package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.JButton;

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrisonerDetailFrame frame = new PrisonerDetailFrame();
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
	public PrisonerDetailFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 493, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblName = new JLabel("Name: ");
		
		textName = new JTextField();
		textName.setColumns(10);
		
		JLabel lblPrisonerId = new JLabel("Prisoner ID:");
		
		textPrisonId = new JTextField();
		textPrisonId.setColumns(10);
		
		JLabel lblDob = new JLabel("Date of Birth:");
		
		textDob = new JTextField();
		textDob.setColumns(10);
		
		JLabel lblGender = new JLabel("Gender:");
		
		textGender = new JTextField();
		textGender.setColumns(10);
		
		JLabel lblStartDate = new JLabel("Start Date:");
		
		textStartDate = new JTextField();
		textStartDate.setColumns(10);
		
		lblReleaseDate = new JLabel("Release Date:");
		
		textReleaseDate = new JTextField();
		textReleaseDate.setColumns(10);
		
		lblJailDuration = new JLabel("Jail Duration:");
		
		textJailDuration = new JTextField();
		textJailDuration.setColumns(10);
		
		lblType = new JLabel("Type:");
		
		textType = new JTextField();
		textType.setColumns(10);
		
		lblPrison = new JLabel("Prison:");
		
		textPrison = new JTextField();
		textPrison.setColumns(10);
		
		lblReleaseStatus = new JLabel("Release Status:");
		
		JRadioButton rdbtnImprisoned = new JRadioButton("Imprisoned");
		
		JRadioButton rdbtnReleased = new JRadioButton("Released");
		
		JLabel lblImg = new JLabel("");
		
		JButton btnUpdate = new JButton("Update");
		
		JButton btnSubmit = new JButton("Submit");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblReleaseStatus)
								.addGap(18)
								.addComponent(rdbtnImprisoned)
								.addGap(18)
								.addComponent(rdbtnReleased, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
								.addGap(187))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
										.addComponent(lblPrison)
										.addPreferredGap(ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
										.addComponent(textPrison, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblType)
										.addPreferredGap(ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
										.addComponent(textType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblJailDuration)
										.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
										.addComponent(textJailDuration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblReleaseDate)
										.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
										.addComponent(textReleaseDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblStartDate)
										.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
										.addComponent(textStartDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblGender)
										.addGap(52)
										.addComponent(textGender))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblDob)
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(textDob, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(lblPrisonerId)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblName)
											.addGap(57)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(textName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(textPrisonId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED))))
								.addGap(30)
								.addComponent(lblImg, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(43, Short.MAX_VALUE)))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(textPrison, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPrison)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(23)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblName)
									.addGap(22)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblPrisonerId)
										.addComponent(textPrisonId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(13)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblDob)
										.addComponent(textDob, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(17)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblGender)
										.addComponent(textGender, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(19)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblStartDate)
										.addComponent(textStartDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(19)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblReleaseDate)
										.addComponent(textReleaseDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(19)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblJailDuration)
										.addComponent(textJailDuration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblType)
										.addComponent(textType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(18))
								.addComponent(textName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblImg, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE))
							.addGap(17)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblReleaseStatus)
						.addComponent(rdbtnImprisoned)
						.addComponent(rdbtnReleased))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnUpdate)
						.addComponent(btnSubmit))
					.addGap(19))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
