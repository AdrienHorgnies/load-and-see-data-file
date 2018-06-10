package LoadAndSeeDataFile.view;

import javax.swing.*;

import static LoadAndSeeDataFile.view.LoadAndSeeDataFileViewStyle.*;

public class LoadAndSeeDataFileView {
    // todo put all this in a configuration file.
    public static final String TABLE_INDICATOR = "table : ";
    public static final String APPLICATION_TITLE = "Load and see data file";
    public static final String PICK_FILE_BTN_TXT = "Choose file";
    public static final String TABLE_NAME_LABEL_TXT = "Please select a database file";

    public final JFrame window;
    public final JLabel tableLabel;
    public final JButton fileBtn;
    public final JTable tableHolder;

    public LoadAndSeeDataFileView() {
        window = window(APPLICATION_TITLE);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fileBtn = new JButton(PICK_FILE_BTN_TXT);
        window.add(fileBtn, fileBtnGbc());

        tableLabel = new JLabel(TABLE_NAME_LABEL_TXT);
        window.add(tableLabel, tableLabelGbc());

        tableHolder = new JTable();
        window.add(new JScrollPane(tableHolder), dataTableGbc());

        window.setVisible(true);
    }

    public void displayError(String title, String message) {
        JOptionPane.showMessageDialog(this.window, message, title, JOptionPane.ERROR_MESSAGE);

    }
}
