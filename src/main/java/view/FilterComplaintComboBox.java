package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import dao.ComplaintDAO;
import entity.Complaint;

public class FilterComplaintComboBox extends JComboBox{
//	private List<Complaint> array;
//
//    public FilterComplaintComboBox(List<Complaint> array) {
//        super(array.toArray());
//        this.array = array;
//        this.setEditable(true);
//        final JTextField textfield = (JTextField) this.getEditor().getEditorComponent();
//        textfield.addKeyListener(new KeyAdapter() {
//            public void keyReleased(KeyEvent ke) {
//                SwingUtilities.invokeLater(new Runnable() {
//                    public void run() {
//                        comboFilter(textfield.getText());
//                    }
//                });
//            }
//        });
//
//    }
//
//    public void comboFilter(String enteredText) {
//    	if (!this.isPopupVisible()) {
//            this.showPopup();
//        }
//
//        List<Complaint> filterArray= new ArrayList<Complaint>();
//        for (int i = 0; i < array.size(); i++) {
//            if (array.get(i).getName().toLowerCase().contains(enteredText.toLowerCase())) {
//                filterArray.add(array.get(i));
//            }
//        }
//        if (filterArray.size() > 0) {
//            DefaultComboBoxModel model = (DefaultComboBoxModel) this.getModel();
//            model.removeAllElements();
//            for (Complaint com: filterArray)
//                model.addElement(com);
//
//            JTextField textfield = (JTextField) this.getEditor().getEditorComponent();
//            textfield.setText(enteredText);
//        }
//    }
    
	private List<Complaint> entries;

    public List<Complaint> getEntries()
    {
        return entries;
    }

    public FilterComplaintComboBox(List<Complaint> entries)
    {
        super(entries.toArray());
        this.entries = entries;
        this.setEditable(true);

        final JTextField textfield =
            (JTextField) this.getEditor().getEditorComponent();

        /**
         * Listen for key presses.
         */
        textfield.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent ke)
            {
                SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        /**
                         * On key press filter the list.
                         * If there is "text" entered in text field of this combo use that "text" for filtering.
                         */
                        comboFilter(textfield.getText());
                    }
                });
            }
        });

    }

    /**
     * Build a list of entries that match the given filter.
     */
    public void comboFilter(String enteredText)
    {
        List<Complaint> entriesFiltered = new ArrayList<Complaint>();

        for (Complaint entry : getEntries())
        {
            if (entry.getName().toLowerCase().contains(enteredText.toLowerCase()))
            {
                entriesFiltered.add(entry);
            }
        }

        if (entriesFiltered.size() > 0)
        {
            this.setModel(
                    new DefaultComboBoxModel(
                        entriesFiltered.toArray()));
            this.setSelectedItem(enteredText);
            this.showPopup();
        }
        else
        {
            this.hidePopup();
        }
    }
}
