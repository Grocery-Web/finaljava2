package view;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultRowSorter;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;

import entity.Person;

public class PersonPanel extends JPanel {
	private JTable table;
	private PersonTableModel tableModel;
	private JPopupMenu popup;
	private TablePersonListener tableListener;
	
	public PersonPanel() {
		tableModel = new PersonTableModel();
		table = new JTable(tableModel);
		popup = new JPopupMenu();
		
		JMenuItem removeItem = new JMenuItem("Delete Person");
		JMenuItem criminalItem = new JMenuItem("Add into Criminal List");
		JMenuItem victimItem = new JMenuItem("Add into Victim List");
		JMenuItem complaintItem = new JMenuItem("Attach Complaint");
		JMenuItem detailItem = new JMenuItem("Person Details");
		popup.add(removeItem);
		popup.add(criminalItem);
		popup.add(victimItem);
		popup.add(complaintItem);
		popup.add(detailItem);
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);
				
				if(e.getButton() == MouseEvent.BUTTON3) {
					//When right click on the row of table, the pop up will be showed up
					popup.show(table, e.getX(), e.getY());
				}
			}
			
		});
		
		complaintItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow(); // Start from 0
				int id = (int) table.getModel().getValueAt(row, 0);

				if (tableListener != null) {
					tableListener.tableEventAttached(id);
				}
			}
		});
		
		criminalItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow(); // Start from 0
				int id = (int) table.getModel().getValueAt(row, 0);

				if (tableListener != null) {
					tableListener.tableEventAddToCriminalList(id);
				}
			}
		});
		
		removeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow(); // Start from 0
				int id = (int) table.getModel().getValueAt(row, 0);

				if (tableListener != null) {
					tableListener.tableEventDeleted(id);
				}
			}
		});

		detailItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow(); // Start from 0
				int id = (int) table.getModel().getValueAt(row, 0);

				if (tableListener != null) {
					tableListener.tableEventPersonDetail(id);
				}
			}
		});
		
		victimItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow(); // Start from 0
				int id = (int) table.getModel().getValueAt(row, 0);
				
				if (tableListener != null) {
					tableListener.tableEventAddVictim(id);
				}
			}
		});
		
		table.setAutoCreateRowSorter(true);  // Search data
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(table),BorderLayout.CENTER);
		
//		ALIGN TEXT IN CENTER
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < 7; i++) {
        	table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
	}
	
	public void setData(List<Person> db) {
		tableModel.setData(db);
	}
	
	public void refresh() {
		tableModel.fireTableDataChanged();
	}
	
	public void search(String txt) {
		DefaultRowSorter<?, ?> sorter = (DefaultRowSorter<?, ?>) table.getRowSorter();
		sorter.setRowFilter(RowFilter.regexFilter(txt));
		sorter.setSortKeys(null);
	}
	
	public void setTableListener(TablePersonListener tableListener) {
		this.tableListener = tableListener;
	}
}
