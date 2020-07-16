package view;

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

import entity.Prisoner;

public class PrisonerPanel extends JPanel{
	private JTable table;
	private PrisonerTableModel tableModel;
	private JPopupMenu popup;
	
	public PrisonerPanel() {
		tableModel = new PrisonerTableModel();
		table = new JTable(tableModel);
		popup = new JPopupMenu();
		
		JMenuItem detailItem = new JMenuItem("Prisoner Details");
		JMenuItem releaseItem = new JMenuItem("Release");
		JMenuItem transferItem = new JMenuItem("Transfer");
		popup.add(detailItem);
		popup.add(releaseItem);
		popup.add(transferItem);

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
		
//		table.setAutoCreateRowSorter(true);  // Search data

		setLayout(new BorderLayout());

		add(new JScrollPane(table), BorderLayout.CENTER);
		
//		ALIGN TEXT IN CENTER
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < 8; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}
	
	public void setData(List<Prisoner> db) {
		tableModel.setData(db);
	}
	
	public void search(String txt) {
		DefaultRowSorter<?, ?> sorter = (DefaultRowSorter<?, ?>) table.getRowSorter();
		sorter.setRowFilter(RowFilter.regexFilter(txt));
		sorter.setSortKeys(null);
	}
	
	public void refresh() {
		tableModel.fireTableDataChanged();
	}
}
