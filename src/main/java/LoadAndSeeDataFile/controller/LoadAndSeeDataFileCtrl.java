package LoadAndSeeDataFile.controller;

import LoadAndSeeDataFile.model.Table;
import LoadAndSeeDataFile.service.FileParser;
import LoadAndSeeDataFile.service.Prop;
import LoadAndSeeDataFile.service.SQLAdapter;
import LoadAndSeeDataFile.view.LoadAndSeeDataFileView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class LoadAndSeeDataFileCtrl implements ActionListener {

    private final LoadAndSeeDataFileView managedView;
    private final Prop prop;

    private final JFileChooser fileChooser;
    private final FileParser fileParser;
    /**
     * can be null
     */
    private final SQLAdapter sqlAdapter;

    public LoadAndSeeDataFileCtrl(LoadAndSeeDataFileView managedView) {
        try {
            prop = Prop.getInstance();
        } catch (IOException e) {
            managedView.displayError(Prop.UNREADABLE_TITLE, Prop.UNREADABLE_MESSAGE);
            throw new RuntimeException(e);
        }

        this.managedView = managedView;
        this.fileChooser = new JFileChooser();
        this.fileParser = new FileParser();

        managedView.fileBtn.addActionListener(this);

        this.sqlAdapter = createSqlAdapter().orElse(null);
    }

    private Optional<SQLAdapter> createSqlAdapter() {
        try {
            Class.forName(prop.get("database-driver.name"));
            String connectionURL = "jdbc:mysql://" + prop.get("db.host") + ":" + prop.get("db.port") + "/" + prop.get("db.name") + "?verifyServerCertificate=false&useSSL=true";
            Connection connection = DriverManager.getConnection(connectionURL, prop.get("db.user"), prop.get("db.password"));
            return Optional.of(new SQLAdapter(connection));
        } catch (SQLException e) {
            e.printStackTrace();
            managedView.displayError(prop.get("error.connection.title"), prop.get("error.connection.message"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            managedView.displayError(prop.get("error.database-driver.title"), prop.get("error.database-driver.message"));
        }
        return Optional.empty();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == managedView.fileBtn) {
            selectFileToParse();
        } else {
            managedView.displayError(prop.get("error.unimplemented.title"), prop.get("error.unimplemented.message"));
        }
    }

    private void selectFileToParse() {
        int optionChosen = fileChooser.showOpenDialog(managedView.window);

        if (optionChosen == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                Table table = fileParser.parse(file);
                if (sqlAdapter != null) {
                    sqlAdapter.createTable(table);
                    table = sqlAdapter.retrieveTable(table.getName());
                }
                managedView.tableHolder.setModel(table);
                managedView.tableLabel.setText(prop.get("label.table.prefix") + " " + table.getName());
            } catch (IOException e) {
                e.printStackTrace();
                managedView.displayError(prop.get("error.unreadable.title"), prop.get("error.unreadable.message"));
            } catch (SQLException e) {
                e.printStackTrace();
                managedView.displayError(prop.get("error.sql.title"), prop.get("error.sql.message") + "\n" + e.getSQLState() + " : " + e.getMessage());
            }
        }
    }
}
