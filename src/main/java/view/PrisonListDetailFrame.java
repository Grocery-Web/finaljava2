package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import entity.PrisonList;
import entity.Prisoner;

public class PrisonListDetailFrame extends JFrame {

	protected static final int Prisoner = 0;
	protected static final PrisonList PrisonList = null;
	private JPanel contentPane;
	private JLabel lblName;
	private JLabel Address;
	private JLabel lblQuantity;
	private JLabel lblCapacity;
	private JTextField txtName;
	private JTextField txtAddress;
	private JTextField txtQuantity;
	private JTextField txtCapacity;
	private JLabel lblImg;
	private JButton btnUploadIMG;
	private JScrollPane scrollPane;
	private JTable table;
	File imgChooser;
	private JButton btnSave;
	private String imgName;
	private JButton btnRelease;
	private JButton btnTransfer;
	private TablePrisonerInListListener psListen;

	private int prisonID;
	private String prisonName;
	private JLabel q1;
	private JLabel q2;
	private boolean cd1, cd2;
	private String s = Character.toString("\u2713".toCharArray()[0]);
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private List<Prisoner> listReleasedPrisoners = new ArrayList<Prisoner>();
	private List<Prisoner> listTransferedPrisoners = new ArrayList<Prisoner>();

//	External form
	private RelevantPrisonerForm relPrisoner;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PrisonListDetailFrame frame = new PrisonListDetailFrame(PrisonList pr, List<Prisoner> prs);
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
//	public PrisonListDetailFrame() {
//		initFrame();
//	}

	private void cd1Check() {
		if (!txtName.getText().equals("") && txtName.getText().matches("(\\d|[a-z,A-Z]|\\s){3,50}")) {
			txtName.setBorder(new LineBorder(Color.GREEN, 1));
			q1.setText(s);
			q1.setForeground(new Color(0, 153, 51));
			q1.setToolTipText(null);
			cd1 = true;
		} else {
			txtName.setBorder(new LineBorder(Color.RED, 1));
			q1.setText("?");
			q1.setForeground(Color.RED);
			q1.setToolTipText("3 - 50 characters required (alphabetical characters, numbers and spaces).");
			cd1 = false;
		}
	}

	private void cd2Check() {
		if (!txtAddress.getText().equals("") && txtAddress.getText().matches("(.|\\s){3,100}")) {
			txtAddress.setBorder(new LineBorder(Color.GREEN, 1));
			q2.setText(s);
			q2.setForeground(new Color(0, 153, 51));
			q2.setToolTipText(null);
			cd2 = true;
		} else {
			txtAddress.setBorder(new LineBorder(Color.RED, 1));
			q2.setText("?");
			q2.setForeground(Color.RED);
			q2.setToolTipText("3 - 100 characters required.");
			cd2 = false;
		}
	}

	private void checkUnlock() {
		boolean unlock = cd1 && cd2;
		btnSave.setEnabled(unlock);
	}

	public PrisonListDetailFrame(PrisonList prison, List<PrisonList> prisonlist, List<Prisoner> prisonInList) {
		initFrame(prison, prisonlist, prisonInList);

		txtName.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				cd1Check();
				checkUnlock();
			};

			@Override
			public void removeUpdate(DocumentEvent e) {
				cd1Check();
				checkUnlock();

			};

			@Override
			public void changedUpdate(DocumentEvent e) {
				cd1Check();
				checkUnlock();

			};

		});

		txtAddress.getDocument().addDocumentListener(new DocumentListener() {

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

		txtName.setText(prison.getName());
		txtAddress.setText(prison.getAddress());
		txtCapacity.setText(Integer.toString(prison.getCapacity()));
		txtQuantity.setText(Integer.toString(prison.getQuantity()));
		imgName = prison.getImg();

		prisonID = prison.getId();
		prisonName = prison.getName();

		// Display Prison's img

		try {
			String url = "images/" + prison.getImg();
			Image prisonIMG = new ImageIcon(getClass().getClassLoader().getResource(url)).getImage()
					.getScaledInstance(400, 130, Image.SCALE_SMOOTH);
			lblImg.setIcon(new ImageIcon(prisonIMG));
		} catch (Exception e) {
		}

		// TABLE: GET ALL PRISONERS IN THIS PRISON
		loadData(prisonInList);

	}

	public void loadData(List<Prisoner> prs) {
		var model = new DefaultTableModel();
		model.addColumn("PrisonerID");
		model.addColumn("name");
		model.addColumn("DOB");
		model.addColumn("Gender");
		model.addColumn("Start Date");
		model.addColumn("Duration (days)");
		model.addColumn("End");
		model.addColumn("Nationality");
		model.addColumn("Type");

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, centerRenderer);

		for (var acc : prs) {

			if (acc.getEndDate() != null) {
				model.addRow(new Object[] {

						acc.getPrisonerId(), acc.getName(), acc.getDob(), acc.getGender(), acc.getStartDate(),
						acc.getDuration(), acc.getEndDate(), acc.getNationality(), acc.getType(), });

			} else {
				model.addRow(new Object[] {

						acc.getPrisonerId(), acc.getName(), acc.getDob(), acc.getGender(), acc.getStartDate(),
						acc.getDuration(), "NULL", acc.getNationality(), acc.getType(), });
			}

		}

		table.setModel(model);
	}

	private void initFrame(PrisonList pr, List<PrisonList> prisonlist, List<Prisoner> prs) {
		setTitle("List of Prisoners");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 852, 636);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));

		Address = new JLabel("Address");
		Address.setFont(new Font("Tahoma", Font.PLAIN, 14));

		lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));

		lblCapacity = new JLabel("Capacity");
		lblCapacity.setFont(new Font("Tahoma", Font.PLAIN, 14));

		txtName = new JTextField();
		txtName.setColumns(10);

		txtAddress = new JTextField();
		txtAddress.setColumns(10);

		txtQuantity = new JTextField();
		txtQuantity.setFont(new Font("Tahoma", Font.BOLD, 10));
		txtQuantity.setEditable(false);
		txtQuantity.setColumns(10);

		txtCapacity = new JTextField();
		txtCapacity.setFont(new Font("Tahoma", Font.BOLD, 10));
		txtCapacity.setEditable(false);
		txtCapacity.setColumns(10);

		lblImg = new JLabel("");

		btnUploadIMG = new JButton("Browse ...");
		btnUploadIMG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnUploadIMGactionPerformed(e);
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPanemouseClicked(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				scrollPanemouseReleased(e);
			}
		});

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSaveactionPerformed(e, pr);
			}
		});

		btnRelease = new JButton("Release");
		btnRelease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnReleaseactionPerformed(e, prs);
			}
		});

		btnTransfer = new JButton("Transfer");
		btnTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnTransferactionPerformed(e, prs, prisonlist);
			}
		});

		q1 = new JLabel();

		q2 = new JLabel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(25)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
						.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblCapacity, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblQuantity, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(Address, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 69,
										Short.MAX_VALUE))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtQuantity, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtCapacity, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(q1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
								.addComponent(q2, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
						.addGap(11).addComponent(lblImg, GroupLayout.PREFERRED_SIZE, 358, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(btnUploadIMG))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false).addGroup(gl_contentPane
								.createSequentialGroup()
								.addComponent(btnRelease, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnTransfer, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 777, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(26, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(25)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblImg, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE).addComponent(btnUploadIMG)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblName)
										.addComponent(q1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
								.addGap(17)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(Address, GroupLayout.PREFERRED_SIZE, 17,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(q2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblQuantity, GroupLayout.PREFERRED_SIZE, 17,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(txtQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblCapacity, GroupLayout.PREFERRED_SIZE, 17,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(txtCapacity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))))
				.addGap(36).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE)
				.addGap(28).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnSave)
						.addComponent(btnRelease).addComponent(btnTransfer))
				.addContainerGap(76, Short.MAX_VALUE)));

		table = new JTable();
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}

	protected void btnUploadIMGactionPerformed(ActionEvent e) {

		// Update images
		JFileChooser filechooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images (jpg, gif, png)", "jpg", "gif", "png");
		filechooser.setFileFilter(filter);
		filechooser.setDialogTitle("Choose Another Prison's Image");
		int returnval = filechooser.showOpenDialog(this);
		if (returnval == JFileChooser.APPROVE_OPTION) {
			imgChooser = filechooser.getSelectedFile();
			BufferedImage bi;
			try {
				bi = ImageIO.read(imgChooser);
				lblImg.setIcon(new ImageIcon(bi.getScaledInstance(400, 130, Image.SCALE_SMOOTH)));
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Something went wrong. Please try again");
			}
		}
	}

	protected void btnSaveactionPerformed(ActionEvent e, PrisonList pr) {

		// GET UPDATED IMAGE
		if (imgChooser != null) {
			try {
				BufferedImage img = ImageIO.read(imgChooser);
				String location = System.getProperty("user.dir") + "/src/main/resources/images/" + imgName;
				String format = "PNG";
				ImageIO.write(img, format, new File(location));
				JOptionPane.showMessageDialog(null, "Successfully save image");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
			}
		}

		// GET UPDATED PRISON INFO
		PrisonList prl = new PrisonList(pr.getId(), txtName.getText(), txtAddress.getText(), pr.getQuantity(),
				pr.getCapacity(), pr.getImg());

		if (psListen != null) {
			psListen.savePrisonInfo(prl, listReleasedPrisoners, listTransferedPrisoners);
		}

	}

	protected void btnReleaseactionPerformed(ActionEvent e, List<Prisoner> prs) {

		int selectRow = table.getSelectedRow();
		if (selectRow >= 0) {
			int prisonerID = (int) table.getValueAt(selectRow, 0);
			String type = (String) table.getValueAt(selectRow, 8);

			int action = JOptionPane.showConfirmDialog(null, "Do you really want to release this prisoner",
					"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);

			if (action == JOptionPane.OK_OPTION) {
				// CURRENT DATE
				long millis = System.currentTimeMillis();
				java.sql.Date date = new java.sql.Date(millis);

				if (type.equals("Termed imprisonment")) {
					for (Prisoner prisoner : prs) {
						if (prisoner.getPrisonerId() == prisonerID) {
							prs.remove(prisoner);
							prisoner.setReleasedStatus(true);
							prisoner.setEndDate(date);
							prisoner.setType("Released ahead of term");

							// Recalculate duration
							Date startDate = prisoner.getStartDate();
							Date endDate = prisoner.getEndDate();
							int getDuration = (int) getDateDiff(startDate, endDate, TimeUnit.DAYS);
							prisoner.setDuration(getDuration);

							listReleasedPrisoners.add(prisoner);
							break;
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "Prisoner is not acquitted of one'crime", "Oops!!",
							JOptionPane.WARNING_MESSAGE);
				}
				loadData(prs);
			} 
		} else {
			JOptionPane.showMessageDialog(null, "Please choose prisoner!");
		}
	}

	public void setFormListener(TablePrisonerInListListener psListener) {
		this.psListen = psListener;
	}

	protected void btnTransferactionPerformed(ActionEvent e, List<Prisoner> prs, List<PrisonList> prisonlist) {
		List<PrisonList> newList = prisonlist;
		int selectRow = table.getSelectedRow();

		if (selectRow >= 0) {
			int prisonerID = (int) table.getValueAt(selectRow, 0);

			for (Prisoner prisoner : prs) {
				if (prisoner.getPrisonerId() == prisonerID) {

					for (int i = 0; i < newList.size(); i++) {
						if (newList.get(i).getId() == prisoner.getPrisonId()) {
							newList.remove(i);
						}
					}

					relPrisoner = new RelevantPrisonerForm(prisoner, newList);
					relPrisoner.setVisible(true);
					relPrisoner.setFormListener(new RelevantPrisonerFormListener() {
						@Override
						public void prisonerFormEventListener(Prisoner prisoner, PrisonList prison) {
							prisoner.setPrisonId(prison.getId());
							listTransferedPrisoners.add(prisoner);
							prs.remove(prisoner);
							relPrisoner.dispose();
							loadData(prs);
						}
					});
					break;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please choose prisoner!");
		}
	}

	protected void scrollPanemouseClicked(MouseEvent e) {
		table.clearSelection();
		table.setFocusable(false);
	}

	protected void scrollPanemouseReleased(MouseEvent e) {
		table.clearSelection();
		table.setFocusable(false);
	}
	
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
}
