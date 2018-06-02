package LoadAndSeeDataFile.view;

import javax.swing.*;
import java.awt.*;

public class LoadAndSeeDataFileView {
    // todo put all this in a configuration file.
    private final String WINDOW_TITLE = "Load and see data file";
    private final String PICK_FILE_BTN_TXT = "Choose file";
    private final String TABLE_NAME_LABEL_TXT = "Please select a database file";

    private final int WINDOW_WIDTH = 600;
    private final int WINDOW_HEIGHT = 800;

    public LoadAndSeeDataFileView() {
        JFrame window = window(WINDOW_TITLE);

        JButton fileBtn = new JButton(PICK_FILE_BTN_TXT);
        window.add(fileBtn, fileBtnGbc());

        JLabel tableLabel = new JLabel(TABLE_NAME_LABEL_TXT);
        window.add(tableLabel, tableLabelGbc());

        JTable dataTable = new JTable();
        window.add(dataTable, dataTableGbc());

        window.setVisible(true);
    }

    private JFrame window(String windowTitle) {
        JFrame window = new JFrame(windowTitle);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new GridBagLayout());
        window.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        window.setLocationRelativeTo(null);
        return window;
    }

    private GridBagConstraints makeBaseGbc() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets.set(15, 10, 15, 10);
        return gridBagConstraints;
    }

    private GridBagConstraints fileBtnGbc() {
        GridBagConstraints gbc = makeBaseGbc();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        return gbc;
    }

    private GridBagConstraints tableLabelGbc() {
        GridBagConstraints gbc = makeBaseGbc();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        return gbc;
    }

    private GridBagConstraints dataTableGbc() {
        GridBagConstraints gbc = makeBaseGbc();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight= GridBagConstraints.REMAINDER;
        return gbc;
    }
}
