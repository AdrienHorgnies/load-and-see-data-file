package LoadAndSeeDataFile;

import LoadAndSeeDataFile.controller.LoadAndSeeDataFileCtrl;
import LoadAndSeeDataFile.view.LoadAndSeeDataFileView;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        new LoadAndSeeDataFileCtrl(new LoadAndSeeDataFileView());
    }
}
