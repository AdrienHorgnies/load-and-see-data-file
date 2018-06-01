package LoadAndSeeDataFile.service;

import LoadAndSeeDataFile.model.Column;
import LoadAndSeeDataFile.model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SQLAdapter {

    public void createTable(Connection connection, Table table) throws SQLException {
        PreparedStatement ddlStatement = connection.prepareStatement("CREATE TABLE " + table.getName() + ddlFragment(table.getColumns()) + ";");

        int rowCount = ddlStatement.executeUpdate();

        if (rowCount != 0) {
            throw new SQLException("DDL query modified " + rowCount + " lines when it should be 0");
        }

        if (table.getRecords().isEmpty()) {
            return;
        }

        int tableWidth = table.getColumns().length;
        int recordCount = table.getRecords().size();
        PreparedStatement dmlStatement = connection.prepareStatement(insertQueryFrom(table));

        int totalCellCount = recordCount * tableWidth;
        IntStream.range(0, totalCellCount)
                .forEach((absIdx) -> {
                    try {
                        dmlStatement.setString(absIdx + 1, table.getRecords().get(absIdx / tableWidth).getData(absIdx % tableWidth));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

        dmlStatement.executeUpdate();
    }

    public Table retrieveTable(Connection connection, String tableName) {
        return null;
    }

    private String ddlFragment(Column[] columns) {
        return "(" + Arrays.stream(columns)
                .map(this::ddlFragment)
                .collect(Collectors.joining(","))
                + ")";
    }

    private String ddlFragment(Column column) {
        return column.getName() + " " + column.getType() + (column.getSize() > 0 ? "(" + column.getSize() + ")" : "");
    }

    private String insertQueryFrom(Table table) {
        String baseQuery = "INSERT INTO " + table.getName() + " VALUES ";

        String placeHolderUnit = "(" + String.join(",", Collections.nCopies(table.getColumns().length, "?")) + ")";

        String placeHolders = String.join(",", Collections.nCopies(table.getRecords().size(), placeHolderUnit));

        return baseQuery + placeHolders;
    }
}
