package LoadAndSeeDataFile.controller;

import LoadAndSeeDataFile.model.Table;
import LoadAndSeeDataFile.service.FileParser;
import LoadAndSeeDataFile.view.LoadAndSeeDataFileView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class LoadAndSeeDataFileCtrl implements ActionListener {
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
            // todo view must handle this and all other exceptions)
            throw new UnsupportedOperationException("No action has been specified for " + actionEvent.getSource().toString());
        }
    }

    private void selectFileToParse() {
        int optionChosen = fileChooser.showOpenDialog(managedView.window);

        if (optionChosen == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                Table table = fileParser.parse(file);
                // todo it doesn't display a header, did I mess up or is it not a feature ?
                managedView.tableHolder.setModel(table);
            } catch (IOException e) {
                // todo inform view something went wrong
                e.printStackTrace();
            }
        }
    }
}
