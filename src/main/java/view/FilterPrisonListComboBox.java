package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import entity.PrisonList;

public class FilterPrisonListComboBox extends JComboBox{
	private List<PrisonList> entries;

    public List<PrisonList> getEntries()
    {
        return entries;
    }

    public FilterPrisonListComboBox(List<PrisonList> entries)
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
        List<PrisonList> entriesFiltered = new ArrayList<PrisonList>();

        for (PrisonList entry : getEntries())
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
