package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import dao.PersonDAO;
import entity.Gender;
import entity.Person;

public class PersonFormPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	private JTextField personalID;
	private JTextField nameField;
	private JTextField occupationField;
	private JTextField nationality;
	private JTextArea address;
	private JLabel imgLabel;
	private JButton submitBtn, imgChooser;
	private JDateChooser dob;
	private JTextFieldDateEditor editor;
	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	private ButtonGroup genderGroup;
	private boolean cd1, cd2, cd3, cd4, cd5, cd6, cd7;
	private JLabel q1, q2, q3, q4, q5, q6;
	private JScrollPane scroll;
	private FormPersonListener formListener;
	private File file = null;

	public String s = Character.toString("\u2713".toCharArray()[0]);

	public PersonFormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 300;
		setPreferredSize(dim);
		setMinimumSize(dim);

		personalID = new JTextField(10);

//		Create Component and Validation input
		personalID.getDocument().addDocumentListener(new DocumentListener() {
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
		q1 = new JLabel();
		q1.setPreferredSize(new Dimension(10, 20));

		nameField = new JTextField(10);
		nameField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				cd2Check();
				checkUnlock();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				cd2Check();
				checkUnlock();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				cd2Check();
				checkUnlock();
			}
		});
		q2 = new JLabel();
		q2.setPreferredSize(new Dimension(10, 20));

		occupationField = new JTextField(10);
		occupationField.getDocument().addDocumentListener(new DocumentListener() {
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
		q3 = new JLabel();
		q3.setPreferredSize(new Dimension(10, 20));

		nationality = new JTextField(10);
		nationality.getDocument().addDocumentListener(new DocumentListener() {
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
		q4 = new JLabel();
		q4.setPreferredSize(new Dimension(10, 20));

		address = new JTextArea(5, 5);
		address.getDocument().addDocumentListener(new DocumentListener() {
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
		q5 = new JLabel();
		q5.setPreferredSize(new Dimension(10, 100));

		dob = new JDateChooser();
		editor = (JTextFieldDateEditor) dob.getDateEditor();
		editor.setEditable(false);
		dob.setDateFormatString("yyyy-MM-dd");
		dob.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				cd6Check();
				checkUnlock();
			}
		});

		q6 = new JLabel();
		q6.setPreferredSize(new Dimension(10, 20));

		maleRadio = new JRadioButton("male");
		femaleRadio = new JRadioButton("female");
		genderGroup = new ButtonGroup();

		submitBtn = new JButton("Add Person");
		submitBtn.setMnemonic(KeyEvent.VK_A);

//		ADD SCROLL PANE INTO ADRRESS AREA
		address.setBorder(BorderFactory.createEtchedBorder());
		scroll = new JScrollPane(address, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

//		Radio Button
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);
//		using ButtonGroup to ensure that only one values chosen at one time
		maleRadio.setActionCommand("male");
		femaleRadio.setActionCommand("female");
		
		ItemListener gender = new ItemListener() {		
			@Override
			public void itemStateChanged(ItemEvent e) {
				AbstractButton btn = (AbstractButton) e.getSource();
				BufferedImage img = null;
				if (!btn.isSelected()) {
					cd7 = false; checkUnlock();
				} else {
					if (btn.getText() == "male") {
						try {
							img = ImageIO.read(getClass().getResource("/images/male.png"));
						} catch (IOException e2) {
							e2.printStackTrace();
						}
					} else {
						try {
							img = ImageIO.read(getClass().getResource("/images/female.png"));
						} catch (IOException e2) {
							e2.printStackTrace();
						}
					}
					Image dimg = img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH);
					imgLabel.setIcon(new ImageIcon(dimg));
					cd7 = true; checkUnlock();
				}		
			}
		};
		maleRadio.addItemListener(gender);
		femaleRadio.addItemListener(gender);

//		Image Chooser:
		imgChooser = new JButton("Open File...");
		imgChooser.setFocusPainted(false);
		imgLabel = new JLabel();
		imgLabel.setBorder(BorderFactory.createEtchedBorder());
		imgLabel.setPreferredSize(new Dimension(120, 200));

		imgChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images (jpg, gif, png)", "jpg","gif","png");
				fc.setFileFilter(filter);
				int result = fc.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					file = fc.getSelectedFile();
					BufferedImage img = null;
					try {
						img = ImageIO.read(file);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					Image dimg = img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH);
					imgLabel.setIcon(new ImageIcon(dimg));
				}
			}
		});
//		Button Action Perform
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Person per = new Person();

				per.setPersonalId(Integer.parseInt(personalID.getText()));
				per.setName(nameField.getText());

				Gender genderCat;
				String gender = genderGroup.getSelection().getActionCommand();

				if (gender == "male") {
					genderCat = Gender.male;
					per.setImage("male.png");
				} else {
					genderCat = Gender.female;
					per.setImage("female.png");
				}
				per.setGender(genderCat);
				per.setDob(new java.sql.Date(dob.getDate().getTime()));
				per.setAddress(address.getText());
				per.setNationality(nationality.getText());
				per.setJob(occupationField.getText());
				per.setAlive(true);

				if (formListener != null) {
					formListener.insertEventListener(per, file);
				}
				clearForm();
			}
		});

		Border innerBorder = BorderFactory.createTitledBorder("Add Person");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		layoutComponents();
	}

//	Functions to check for all input fields
	private void cd1Check() {
		PersonDAO personDAO = new PersonDAO();
		var listID = personDAO.getAllID();
		
		if (!personalID.getText().equals("") && personalID.getText().matches("\\d+")) {
			if (listID.contains(Integer.parseInt(personalID.getText()))) {
				personalID.setBorder(new LineBorder(Color.RED, 1));
				q1.setText("?");
				q1.setForeground(Color.RED);
				q1.setToolTipText("This ID already exists in database.");
				cd1 = false;
			} else {
				personalID.setBorder(new LineBorder(Color.GREEN, 1));
				q1.setText(s);
				q1.setForeground(new Color(0, 153, 51));
				q1.setToolTipText(null);
				cd1 = true;
			}
		} else {
			personalID.setBorder(new LineBorder(Color.RED, 1));
			q1.setText("?");
			q1.setForeground(Color.RED);
			q1.setToolTipText("Please enter a number as Personal ID");
			cd1 = false;
		}
	}

	private void cd2Check() {
		if (!nameField.getText().equals("") && nameField.getText().matches("[\\D ]{1,50}")) {
			nameField.setBorder(new LineBorder(Color.GREEN, 1));
			q2.setText(s);
			q2.setForeground(new Color(0, 153, 51));
			q2.setToolTipText(null);
			cd2 = true;
		} else {
			nameField.setBorder(new LineBorder(Color.RED, 1));
			q2.setText("?");
			q2.setForeground(Color.RED);
			q2.setToolTipText("1 - 50 alphabet and special characters");
			cd2 = false;
		}
	}

	private void cd3Check() {
		if (!occupationField.getText().equals("") && occupationField.getText().matches("[a-zA-Z]{4,20}")) {
			occupationField.setBorder(new LineBorder(Color.GREEN, 1));
			q3.setText(s);
			q3.setForeground(new Color(0, 153, 51));
			q3.setToolTipText(null);
			cd3 = true;
		} else {
			occupationField.setBorder(new LineBorder(Color.RED, 1));
			q3.setText("?");
			q3.setForeground(Color.RED);
			q3.setToolTipText("4 - 20 alphabet characters");
			cd3 = false;
		}
	}

	private void cd4Check() {
		if (!nationality.getText().equals("") && nationality.getText().matches("[a-zA-Z]{4,50}")) {
			nationality.setBorder(new LineBorder(Color.GREEN, 1));
			q4.setText(s);
			q4.setForeground(new Color(0, 153, 51));
			q4.setToolTipText(null);
			cd4 = true;
		} else {
			nationality.setBorder(new LineBorder(Color.RED, 1));
			q4.setText("?");
			q4.setForeground(Color.RED);
			q4.setToolTipText("4 - 50 alphabet characters");
			cd4 = false;
		}
	}

	private void cd5Check() {
		if (!address.getText().equals("") && address.getText().matches("(.|\\s){2,4000}")) {
			address.setBorder(new LineBorder(Color.GREEN, 1));
			q5.setText(s);
			q5.setForeground(new Color(0, 153, 51));
			q5.setToolTipText(null);
			cd5 = true;
		} else {
			address.setBorder(new LineBorder(Color.RED, 1));
			q5.setText("?");
			q5.setForeground(Color.RED);
			q5.setToolTipText("Minimum length required is 2 characters");
			cd5 = false;
		}
	}

	private void cd6Check() {
		Date date1 = dob.getDate();
		Date date2 = new Date();
		if (date1 != null) {
			if (date1.compareTo(date2) <= 0) {
				q6.setText(s);
				q6.setForeground(new Color(0, 153, 51));
				q6.setToolTipText(null);
				cd6 = true;
			} else {
				q6.setText("?");
				q6.setForeground(Color.RED);
				q6.setToolTipText("Select a date that is no later than today.");
				cd6 = false;
			}
		}
	}

	private void checkUnlock() {
		boolean unlock = cd1 && cd2 && cd3 && cd4 && cd5 && cd6 && cd7;
		submitBtn.setEnabled(unlock);
	}

	public void layoutComponents() {
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

//		This form just have one column, therefore we just use weightY to allocate the space between each components

		/////////////// PERSONAL ID ///////////////////
		gc.weighty = 0.1; // assign at least small additional space between each component on Vertical

		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Personal ID: "), gc);

		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		add(personalID, gc);

		gc.gridx = 4;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.CENTER;
		add(q1, gc);

		/////////////// NAME ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Name: "), gc);

		gc.gridy = 1;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(nameField, gc);

		gc.gridx = 4;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.CENTER;
		add(q2, gc);

		/////////////// OCCUPATION ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Occupation: "), gc);

		gc.gridy = 2;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(occupationField, gc);

		gc.gridx = 4;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.CENTER;
		add(q3, gc);

		/////////////// Date of Birth ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Date of Birth: "), gc);

		gc.gridy = 3;
		gc.gridx = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.LINE_START;
		add(dob, gc);

		gc.gridy = 3;
		gc.gridx = 4;
		gc.anchor = GridBagConstraints.LINE_START;
		add(q6, gc);

		/////////////// NATIONALITY ///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Nationality: "), gc);

		gc.gridy = 4;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(nationality, gc);

		gc.gridy = 4;
		gc.gridx = 4;
		gc.anchor = GridBagConstraints.LINE_START;
		add(q4, gc);

		/////////////// ADDRESS///////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Address: "), gc);

		gc.gridy = 5;
		gc.gridx = 1;
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.HORIZONTAL;

		gc.anchor = GridBagConstraints.LINE_START;
		add(scroll, gc);

		gc.gridy = 5;
		gc.gridx = 4;
		gc.anchor = GridBagConstraints.LINE_END;
		add(q5, gc);

		/////////////// RADIO BUTTON ///////////////////
		gc.gridy++;
		gc.weighty = 0.01;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LAST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Gender: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(maleRadio, gc);

		gc.gridy++;

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(femaleRadio, gc);

		/////////////// FILE CHOOSER ////////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Photo: "), gc);

		gc.gridy = 8;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(imgChooser, gc);

		////////////// IMAGE PANEL //////////////////////
		gc.weighty = 0.5;
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		add(imgLabel, gc);

		/////////////// SUBMIT BUTTON ///////////////////
		gc.weighty = 2; // all additional space below will be distributed to this component on Vertical

		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.PAGE_START;
		gc.gridwidth = 2;
		add(submitBtn, gc);

//		End of Edit Form
	}

	public void setFormListener(FormPersonListener formListener) {
		this.formListener = formListener;
	}
	
	public void clearForm() {
		personalID.setText(null);
		nameField.setText(null);
		occupationField.setText(null);
		nationality.setText(null);
		address.setText(null);
		imgLabel.setIcon(null);
		dob.setCalendar(null);
		genderGroup.clearSelection();
	}
}