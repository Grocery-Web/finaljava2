package view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultRowSorter;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;

import entity.PrisonList;

public class PrisonListPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private PrisonListTableModel tableModel;
	private JPopupMenu popup;
	private TablePrisonListListener tableListener;
	
	public PrisonListPanel(int privilege) {
		tableModel = new PrisonListTableModel();
		table = new JTable(tableModel);
		popup = new JPopupMenu();
		
		JMenuItem viewAllPrisoners = new JMenuItem("Prison in details");
		popup.add(viewAllPrisoners);

		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);

				if (e.getButton() == MouseEvent.BUTTON3) {
					// When right click on the row of table, the pop up will be showed up
					popup.show(table, e.getX(), e.getY());
				}
			}
		});
		
		viewAllPrisoners.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow(); // Start from 0
				int id = (int) table.getValueAt(row, 0);

				if (tableListener != null) {
					tableListener.displayPrisonListDetail(id);
				}
							
			}
		});
		
		setLayout(new BorderLayout());

		add(new JScrollPane(table), BorderLayout.CENTER);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < 5; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
				
		
	}
	
	public void setData(List<PrisonList> db) {
		tableModel.setData(db);
		table.setAutoCreateRowSorter(true);  // Search data
	}
	
	public void search(String txt) {
		table.setAutoCreateRowSorter(true);  // Search data
		DefaultRowSorter<?, ?> sorter = (DefaultRowSorter<?, ?>) table.getRowSorter();
		sorter.setRowFilter(RowFilter.regexFilter(txt));
		sorter.setSortKeys(null);
	}
	
	public void setTableListener(TablePrisonListListener tableListener) {
		this.tableListener = tableListener;
	}
	
	public void refresh() {
		tableModel.fireTableDataChanged();
	}
}