package view;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class CriminalFormPanel extends JPanel{
	
	public CriminalFormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 300;
		setPreferredSize(dim);
		setMinimumSize(dim);
		
//		SET FORM LAYOUT
		Border innerBorder = BorderFactory.createTitledBorder("Criminal Details");
		Border outerBorder = BorderFactory.createEmptyBorder(4, 4, 4, 4);
		setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
	}
}
