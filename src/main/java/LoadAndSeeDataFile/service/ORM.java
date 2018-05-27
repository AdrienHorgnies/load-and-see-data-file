package LoadAndSeeDataFile.service;

import LoadAndSeeDataFile.model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ORM {

    public void createTable(Connection connection, Table table) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE  "+ table.getName() +";");
        int rowCount = preparedStatement.executeUpdate();

        if (rowCount != 0) {
            throw new SQLException("DDL query modified " + rowCount + " lines when it should be 0");
        }
    }
}
