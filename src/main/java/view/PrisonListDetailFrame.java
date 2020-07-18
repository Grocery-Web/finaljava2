package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import dao.PrisonListDAO;
import entity.PrisonList;
import entity.PrisonerInList;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;

public class PrisonListDetailFrame extends JFrame {

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
	private TablePrisonerInListListener psListen ;

	private int prisonID;
	private String prisonName;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrisonListDetailFrame frame = new PrisonListDetailFrame();
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
	public PrisonListDetailFrame() {
		initFrame();
	}

	public PrisonListDetailFrame(PrisonList pr, List<PrisonerInList> prs) {
		initFrame();
		txtName.setText(pr.getName());
		txtAddress.setText(pr.getAddress());
		txtCapacity.setText(Integer.toString(pr.getCapacity()));
		txtQuantity.setText(Integer.toString(pr.getQuantity()));
		imgName = pr.getImg();
		
		prisonID = pr.getId();
		prisonName = pr.getName();
	    
		
		//Display Prison's img
		
		try {
			String url = "images/" + pr.getImg();
			Image prisonIMG = 
					new ImageIcon(
						getClass().getClassLoader()
						.getResource(url)
						).getImage().getScaledInstance(400, 130, Image.SCALE_SMOOTH);
			lblImg.setIcon(new ImageIcon(prisonIMG));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//TABLE: GET ALL PRISONERS IN THIS PRISON
		loadData(prs);
		
	}
	
	public void refreshQuantity(PrisonList pr) {
		txtCapacity.setText(Integer.toString(pr.getCapacity()));
		txtQuantity.setText(Integer.toString(pr.getQuantity()));		
	}

	public void loadData(List<PrisonerInList> prs) {
		var model = new DefaultTableModel();
		model.addColumn("PersonID");
		model.addColumn("PrisonerID");
		model.addColumn("name");
		model.addColumn("DOB");
		model.addColumn("Gender");
		model.addColumn("Start Date");
		model.addColumn("Duration (days)");
		model.addColumn("End");
		model.addColumn("Nationality");
		model.addColumn("Type");
		
		
		for (var acc : prs) {
			
			if (acc.getEndDate() != null) {
				model.addRow(new Object[] {		
						
						acc.getPersonID(), acc.getPrisonID(), 
						acc.getName(), acc.getDob(),
						acc.getGender(),  acc.getStartDate(),
						acc.getDuration(), acc.getEndDate(), 
						acc.getNationality(), acc.getType(),
				});
				
			}
			else {
				model.addRow(new Object[] {		
						
						acc.getPersonID(), acc.getPrisonID(), 
						acc.getName(), acc.getDob(),
						acc.getGender(),  acc.getStartDate(),
						acc.getDuration(), "NULL", 
						acc.getNationality(), acc.getType(),
				});
			}
				
			
		}
		
		
		table.setModel(model);
	}
	
	private void initFrame() {
		setTitle("List of Prisoners");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 852, 636);
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
		
		btnUploadIMG = new JButton("Update Image");
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
				btnSaveactionPerformed(e);
			}
		});
		
		btnRelease = new JButton("Release");
		btnRelease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnReleaseactionPerformed(e);
			}
		});
		
		btnTransfer = new JButton("Transfer");
		btnTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnTransferactionPerformed(e);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblCapacity, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblQuantity, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(Address, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtQuantity, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtCapacity, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(lblImg, GroupLayout.PREFERRED_SIZE, 380, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnUploadIMG))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(btnRelease, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnTransfer, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 777, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnUploadIMG)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblName))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(Address, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblQuantity, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblCapacity, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtCapacity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblImg, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
					.addGap(36)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave)
						.addComponent(btnRelease)
						.addComponent(btnTransfer))
					.addContainerGap(76, Short.MAX_VALUE))
		);
		
		table = new JTable();
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}
	protected void btnUploadIMGactionPerformed(ActionEvent e) {
		
		//Update images
		JFileChooser filechooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images (jpg, gif, png)", "jpg","gif","png");
		filechooser.setFileFilter(filter);
		filechooser.setDialogTitle("Choose Another Prison's Image");
		int returnval = filechooser.showOpenDialog(this);
		if (returnval == JFileChooser.APPROVE_OPTION) {
			imgChooser = filechooser.getSelectedFile();
			BufferedImage bi;
			try {
				bi = ImageIO.read(imgChooser);
			    lblImg.setIcon(new ImageIcon(bi.getScaledInstance(400, 130,  Image.SCALE_SMOOTH)));
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Something went wrong. Please try again");
			}
		}
	}
	protected void btnSaveactionPerformed(ActionEvent e) {
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
	}
	
	protected void btnReleaseactionPerformed(ActionEvent e) {
		int selectRow = table.getSelectedRow();
		if (selectRow >=0 ) {
			int prisonerID = (int) table.getValueAt(selectRow, 1);
			if (psListen !=null) {
				psListen.releasePrisoner(prisonerID);
			}	
		}
		else {
			JOptionPane.showMessageDialog(null, "Please choose prisoner!");
		}
		
	}
	
	public void setFormListener(TablePrisonerInListListener psListener) {
		this.psListen = psListener;
	}
	protected void btnTransferactionPerformed(ActionEvent e) {
		
		PrisonListDAO plDAO = new PrisonListDAO();
		List<PrisonList> prisonList = plDAO.getAllPrisonListExceptPrisonID(prisonID);
		int toPrison = -1;
		
//		System.out.println(prisonList);
		int selectRow = table.getSelectedRow();
		if (selectRow >=0 ) {
			int prisonerID = (int) table.getValueAt(selectRow, 1);
			
			Object[] obj = new Object[]  {};
			for (PrisonList prison : prisonList) {
				obj = appendValue(obj, prison.getName());
			}
			
			String s = (String)JOptionPane.showInputDialog(
                    null,
                    "Please choose Prison Name:\n",
                    "Transfer Prisoner",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    obj,
                    obj[0]);
			
			int count = 0;
			for (PrisonList prison : prisonList) {
				if (s == prison.getName()) {
					toPrison = prison.getId();
					break;
				}
				count ++;
			}
			if (toPrison >= 0) {
				psListen.transferPrisoner(prisonID, toPrison, prisonerID);
			}
			
		}
		else {
			JOptionPane.showMessageDialog(null, "Please choose prisoner!");
		}
	}


	private Object[] appendValue(Object[] obj, Object newObj) {
		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
		temp.add(newObj);
		return temp.toArray();
	}
	protected void scrollPanemouseClicked(MouseEvent e) {
		table.clearSelection();
		table.setFocusable(false);
	}
	protected void scrollPanemouseReleased(MouseEvent e) {
		table.clearSelection();
		table.setFocusable(false);
	}
}
