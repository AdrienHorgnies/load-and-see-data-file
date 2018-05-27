package LoadAndSeeDataFile.service;

import LoadAndSeeDataFile.model.Column;
import LoadAndSeeDataFile.model.SQLDataType;
import LoadAndSeeDataFile.model.Table;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class ORMIntTest {

    private Connection connection;

    @Before
    public void before() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1");
    }

    @After
    public void after() throws SQLException {
        connection.close();
    }

    @Test
    public void testCreateTable() throws SQLException {
        ORM orm = new ORM();

        Table table = new Table("eleves", new Column[]{
                new Column("prenom", SQLDataType.VARCHAR, 50),
                new Column("nom", SQLDataType.VARCHAR, 100),
                new Column("age", SQLDataType.INTEGER)
        });

        orm.createTable(connection, table);

        // checking result
        DatabaseMetaData metaData = connection.getMetaData();
        // h2 makes everything from the structure uppercase
        String expectedTableName = table.getName().toUpperCase();

        ResultSet tableSet = metaData.getTables(null, null, expectedTableName, null);

        assertTrue(tableSet.next());

        Column[] expectedColumns = Arrays.stream(table.getColumns())
                // h2 makes everything from the structure uppercase
                .map(col -> new Column(col.getName().toUpperCase(), col.getType(), col.getSize()))
                .toArray(Column[]::new);

        ResultSet columnSet = metaData.getColumns(null, null, expectedTableName, null);

        Map<Integer, Column> columnMap = new HashMap<>();

        while (columnSet.next()) {
            String name = columnSet.getString("COLUMN_NAME");
            String type = columnSet.getString("SQL_DATA_TYPE");
            int size = columnSet.getInt("COLUMN_SIZE");
            int ordinalPosition = columnSet.getInt("ORDINAL_POSITION");

            columnMap.put(ordinalPosition, new Column(name, SQLDataType.valueOf(type), size));
        }

        Column[] actualColumns = new Column[columnMap.size()];
        columnMap.forEach((key, value) -> {
            // SQL index starts at one...
            int idx = key - 1;

            actualColumns[idx] = value;
        });

        assertThat(actualColumns).isEqualTo(expectedColumns);
    }
}