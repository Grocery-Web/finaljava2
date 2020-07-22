package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private TablePrisonerListener tableListener;
	
	public PrisonerPanel(int privilege) {
		tableModel = new PrisonerTableModel();
		table = new JTable(tableModel);
		popup = new JPopupMenu();
		
		JMenuItem detailItem = new JMenuItem("Prisoner Details");
		JMenuItem releaseItem = new JMenuItem("Release");
		JMenuItem transferItem = new JMenuItem("Transfer");
		if(privilege == 2) {
			popup.add(detailItem);
			popup.add(releaseItem);
			popup.add(transferItem);
		}
		

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
		
		detailItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow(); // Start from 0
				int id = (int) table.getValueAt(row, 0);

				if (tableListener != null) {
					tableListener.tableEventDetail(id);
				}
			}
		});
		
		releaseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow(); // Start from 0
				int id = (int) table.getModel().getValueAt(row, 0);

				if (tableListener != null) {
					tableListener.tableEventRelease(id);
				}
			}
		});
		
		transferItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow(); // Start from 0
				int id = (int) table.getModel().getValueAt(row, 0);

				if (tableListener != null) {
					tableListener.tableEventTransfer(id);
				}
			}
		});
		
		setLayout(new BorderLayout());

		add(new JScrollPane(table), BorderLayout.CENTER);
		
//		ALIGN TEXT IN CENTER
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < 9; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}
	
	public void setData(List<Prisoner> db) {
		tableModel.setData(db);
	}
	
	public void search(String txt) {
		table.setAutoCreateRowSorter(true);  // Search data
		DefaultRowSorter<?, ?> sorter = (DefaultRowSorter<?, ?>) table.getRowSorter();
		sorter.setRowFilter(RowFilter.regexFilter(txt));
		sorter.setSortKeys(null);
	}
	
	public void refresh() {
		tableModel.fireTableDataChanged();
	}
	
	public void setTableListener(TablePrisonerListener tableListener) {
		this.tableListener = tableListener;
	}
}
