package LoadAndSeeDataFile.controller;

import LoadAndSeeDataFile.view.LoadAndSeeDataFileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadAndSeeDataFileCtrl implements ActionListener {
    private final LoadAndSeeDataFileView MANAGED_VIEW;

    public LoadAndSeeDataFileCtrl(LoadAndSeeDataFileView MANAGED_VIEW) {
        this.MANAGED_VIEW = MANAGED_VIEW;
        MANAGED_VIEW.getFileBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == MANAGED_VIEW.getFileBtn()) {
            // todo put actual action.
            System.out.println("HELLOOOOO");
        } else {
            // todo view must handle this and all other exceptions)
            throw new UnsupportedOperationException("No action has been specified for " + actionEvent.getSource().toString());
        }
    }
}
