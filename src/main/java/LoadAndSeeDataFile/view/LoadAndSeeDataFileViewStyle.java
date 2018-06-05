package LoadAndSeeDataFile.view;

import javax.swing.*;
import java.awt.*;

class LoadAndSeeDataFileViewStyle {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 800;

    private static GridBagConstraints makeBaseGbc() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets.set(15, 10, 15, 10);
        return gridBagConstraints;
    }

    static GridBagConstraints fileBtnGbc() {
        GridBagConstraints gbc = makeBaseGbc();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        return gbc;
    }

    static GridBagConstraints tableLabelGbc() {
        GridBagConstraints gbc = makeBaseGbc();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        return gbc;
    }

    static GridBagConstraints dataTableGbc() {
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

    static JFrame window(String windowTitle) {
        JFrame window = new JFrame(windowTitle);
        window.setLayout(new GridBagLayout());
        window.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        window.setLocationRelativeTo(null);
        return window;
    }
}
