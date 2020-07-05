package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import entity.Complaint;
import entity.Person;

public class IncidentsPanel extends JPanel{
	private JTable table;
	private PersonInDetailModel tableModel;
	private JPopupMenu popup;
	
	public IncidentsPanel() {
		tableModel = new PersonInDetailModel();
		table = new JTable(tableModel);
		popup = new JPopupMenu();
		
		JMenuItem removeItem = new JMenuItem("Delete Complaint");
		JMenuItem detailItem = new JMenuItem("Complaint Details");
		popup.add(removeItem);
		popup.add(detailItem);


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
	
	public void setData(HashMap<Person, String> db) {
		tableModel.setData(db);
	}
}
