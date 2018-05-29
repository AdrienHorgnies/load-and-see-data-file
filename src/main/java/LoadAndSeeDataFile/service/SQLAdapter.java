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
        PreparedStatement ddlStatement = connection.prepareStatement("CREATE TABLE " + table.getName() + DDLFragment(table.getColumns()) + ";");

        int rowCount = ddlStatement.executeUpdate();

        if (rowCount != 0) {
            throw new SQLException("DDL query modified " + rowCount + " lines when it should be 0");
        }

        if (table.getRecords().isEmpty()) {
            return;
        }

        int tableWidth = table.getColumns().length;
        int recordCount = table.getRecords().size();
        PreparedStatement dmlStatement = connection.prepareStatement(insertQueryFrom(table.getName(), tableWidth, recordCount));

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

    private String DDLFragment(Column[] columns) {
        return "(" + Arrays.stream(columns)
                .map(this::DDLFragment)
                .collect(Collectors.joining(","))
                + ")";
    }

    private String DDLFragment(Column column) {
        return column.getName() + " " + column.getType() + (column.getSize() > 0 ? "(" + column.getSize() + ")" : "");
    }

    private String insertQueryFrom(String tableName, int columnCount, int recordCount) {
        String baseQuery = "INSERT INTO " + tableName + " VALUES ";

        String placeHolderUnit = "(" + String.join(",", Collections.nCopies(columnCount, "?")) + ")";

        String placeHolders = String.join(",", Collections.nCopies(recordCount, placeHolderUnit));

        return baseQuery + placeHolders;
    }
}
