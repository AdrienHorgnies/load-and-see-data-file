package LoadAndSeeDataFile.service;

import LoadAndSeeDataFile.model.Column;
import LoadAndSeeDataFile.model.Record;
import LoadAndSeeDataFile.model.SQLDataType;
import LoadAndSeeDataFile.model.Table;

import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SQLAdapter {

    private final Connection connection;

    public SQLAdapter(Connection connection) {
        this.connection = connection;
    }

    public void createTable(Table table) throws SQLException {
        PreparedStatement ddlStatement = this.connection.prepareStatement("CREATE TABLE " + table.getName() + ddlFragment(table.getColumns()) + ";");

        int rowCount = ddlStatement.executeUpdate();

        if (rowCount != 0) {
            throw new SQLException("DDL query modified " + rowCount + " lines when it should be 0");
        }

        if (table.getRecords().isEmpty()) {
            return;
        }

        int tableWidth = table.getColumns().length;
        int recordCount = table.getRecords().size();
        PreparedStatement dmlStatement = this.connection.prepareStatement(insertQueryFrom(table));

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

    public Table retrieveTable(String tableName) throws SQLException {
        ResultSet resultSet = this.connection.createStatement().executeQuery("SELECT * FROM " + tableName);

        ResultSetMetaData metaData = resultSet.getMetaData();

        Column[] columns = new Column[metaData.getColumnCount()];
        for (int idx = 1; idx <= metaData.getColumnCount(); idx++) {
            String columnName = metaData.getColumnName(idx);
            SQLDataType type = SQLDataType.from(metaData.getColumnType(idx));
            int size = metaData.getPrecision(idx);
            columns[idx - 1] = new Column(columnName, type, size);
        }

        Table table = new Table(tableName, columns);

        while (resultSet.next()) {
            String[] data = new String[columns.length];
            for (int idx = 0; idx < columns.length; idx++) {
                data[idx] = resultSet.getString(idx + 1);
            }
            table.addRecord(new Record(data));
        }

        return table;
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
