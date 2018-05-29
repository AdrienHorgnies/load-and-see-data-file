package LoadAndSeeDataFile.service;

import LoadAndSeeDataFile.model.Column;
import LoadAndSeeDataFile.model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SQLAdapter {

    public void createTable(Connection connection, Table table) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE " + table.getName() + sqlFrom(table.getColumns()) + ";");

        int rowCount = preparedStatement.executeUpdate();

        if (rowCount != 0) {
            throw new SQLException("DDL query modified " + rowCount + " lines when it should be 0");
        }

        // todo insert Record's using the preparedStatement : "INSERT INTO tableName VALUES (?,?,?)
        // todo values should be formatted depending their type which is available from Table
    }

    private String sqlFrom(Column[] columns) {
        return "(" + Arrays.stream(columns)
                .map(this::sqlFrom)
                .collect(Collectors.joining(","))
                + ")";
    }


    private String sqlFrom(Column column) {
        return column.getName() + " " + column.getType() + (column.getSize() > 0 ? "(" + column.getSize() + ")" : "");
    }
}
