package LoadAndSeeDataFile.view;

import javax.swing.*;

import static LoadAndSeeDataFile.view.LoadAndSeeDataFileViewStyle.*;

public class LoadAndSeeDataFileView {
    // todo put all this in a configuration file.
    private final String APPLICATION_TITLE = "Load and see data file";
    private final String PICK_FILE_BTN_TXT = "Choose file";
    private final String TABLE_NAME_LABEL_TXT = "Please select a database file";

    public final JFrame window;
    public final JButton fileBtn;
    public final JTable tableHolder;

    public LoadAndSeeDataFileView() {
        window = window(APPLICATION_TITLE);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fileBtn = new JButton(PICK_FILE_BTN_TXT);
        window.add(fileBtn, fileBtnGbc());

        JLabel tableLabel = new JLabel(TABLE_NAME_LABEL_TXT);
        window.add(tableLabel, tableLabelGbc());

        tableHolder = new JTable();
        window.add(tableHolder, dataTableGbc());

        window.setVisible(true);
    }
}
