package LoadAndSeeDataFile.view;

import LoadAndSeeDataFile.service.Prop;
import LoadAndSeeDataFile.service.exceptions.UnsupportedLanguageException;

import javax.swing.*;
import java.io.IOException;

import static LoadAndSeeDataFile.view.LoadAndSeeDataFileViewStyle.*;

public class LoadAndSeeDataFileView {

    public final JFrame window;
    public final JLabel tableLabel;
    public final JButton fileBtn;
    public final JTable tableHolder;

    public LoadAndSeeDataFileView() {
        Prop prop;
        try {
            prop = Prop.getInstance();
        } catch (IOException e) {
            displayError(Prop.UNREADABLE_TITLE, Prop.UNREADABLE_MESSAGE);
            throw new RuntimeException(e);
        }  catch (UnsupportedLanguageException e) {
            displayError(Prop.UNSUPPORTED_LANGUAGE_TITLE, Prop.UNSUPPORTED_LANGUAGE_MESSAGE + "\n" + e.getMessage());
            // todo don't put null, load properties with english or bubble up error without crashing
            prop = null;
        }

        window = window(prop.get("application.title"));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fileBtn = new JButton(prop.get("button.choose-file"));
        window.add(fileBtn, fileBtnGbc());

        tableLabel = new JLabel(prop.get("label.table.instruction"));
        window.add(tableLabel, tableLabelGbc());

        tableHolder = new JTable();
        window.add(new JScrollPane(tableHolder), dataTableGbc());

        window.setVisible(true);
    }

    public void displayError(String title, String message) {
        JOptionPane.showMessageDialog(this.window, message, title, JOptionPane.ERROR_MESSAGE);

    }
}
