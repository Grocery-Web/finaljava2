package view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import entity.Complaint;

public class ComplaintsPanel  extends JPanel {
	private JTable table;
	private ComplaintTableModel tableModel;
	private JPopupMenu popup;
	
	public ComplaintsPanel() {
		tableModel = new ComplaintTableModel();
		table = new JTable(tableModel);
		popup = new JPopupMenu();
		
		JMenuItem removeItem = new JMenuItem("Delete Row");
		popup.add(removeItem);
		
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
		
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(table),BorderLayout.CENTER);
		
//		ALIGN TEXT IN CENTER
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < 6; i++) {
        	table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
	}
	
	public void setData(List<Complaint> db) {
		tableModel.setData(db);
	}
	
	public void refresh() {
		tableModel.fireTableDataChanged();
	}
}
