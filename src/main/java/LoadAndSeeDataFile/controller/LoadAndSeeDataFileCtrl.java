package LoadAndSeeDataFile.controller;

import LoadAndSeeDataFile.model.Table;
import LoadAndSeeDataFile.service.FileParser;
import LoadAndSeeDataFile.view.LoadAndSeeDataFileView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static LoadAndSeeDataFile.view.LoadAndSeeDataFileView.TABLE_INDICATOR;

public class LoadAndSeeDataFileCtrl implements ActionListener {

    public static final String ERROR_REPORT_INBOX = "error-report@load-and-see-data-file.com";
    public static final String ERROR_REPORT_REQUEST = "Would you please mind to write an error report to " + ERROR_REPORT_INBOX + " ?";
    public static final String UNSUPPORTED_ACTION_MESSAGE = "It seems you found an unsupported action. " + ERROR_REPORT_REQUEST;
    public static final String UNSUPPORTED_ACTION_TITLE = "Unsupported action";

    private final LoadAndSeeDataFileView managedView;

    private final JFileChooser fileChooser;
    private final FileParser fileParser;

    public LoadAndSeeDataFileCtrl(LoadAndSeeDataFileView managedView) {
        this.managedView = managedView;
        this.fileChooser = new JFileChooser();
        this.fileParser = new FileParser();

        managedView.fileBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == managedView.fileBtn) {
            selectFileToParse();
        } else {
            managedView.displayError(UNSUPPORTED_ACTION_TITLE, UNSUPPORTED_ACTION_MESSAGE);
        }
    }

    private void selectFileToParse() {
        int optionChosen = fileChooser.showOpenDialog(managedView.window);

        if (optionChosen == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                Table table = fileParser.parse(file);
                managedView.tableHolder.setModel(table);
                managedView.tableLabel.setText(TABLE_INDICATOR + table.getName());
            } catch (IOException e) {
                // todo inform view something went wrong
                e.printStackTrace();
            }
        }
    }
}
