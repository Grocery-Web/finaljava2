package view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultRowSorter;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;

import entity.PrisonList;

public class PrisonListPanel extends JPanel {
	private JTable table;
	private PrisonListTableModel tableModel;
	private JPopupMenu popup;
	
	public PrisonListPanel() {
		tableModel = new PrisonListTableModel();
		table = new JTable(tableModel);
		popup = new JPopupMenu();
		
		JMenuItem viewAllPrisoners = new JMenuItem("View all prisoners");
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
	}
	
	public void search(String txt) {
		DefaultRowSorter<?, ?> sorter = (DefaultRowSorter<?, ?>) table.getRowSorter();
		sorter.setRowFilter(RowFilter.regexFilter(txt));
		sorter.setSortKeys(null);
	}
}