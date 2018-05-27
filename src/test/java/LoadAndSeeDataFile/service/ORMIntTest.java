package LoadAndSeeDataFile.service;

import LoadAndSeeDataFile.model.Column;
import LoadAndSeeDataFile.model.Entry;
import LoadAndSeeDataFile.model.SQLDataType;
import LoadAndSeeDataFile.model.Table;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class ORMIntTest {

    private Connection connection;

    @Before
    public void before() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testDb;DB_CLOSE_DELAY=-1");
    }

    @After
    public void after() throws SQLException {
        connection.createStatement().execute("SHUTDOWN");
    }

    @Test
    public void testCreateTable_onlyStructure() throws SQLException {
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

            int jdbcCode = columnSet.getInt("SQL_DATA_TYPE");
            SQLDataType type = SQLDataType.from(jdbcCode);

            int size = columnSet.getInt("COLUMN_SIZE");

            int ordinalPosition = columnSet.getInt("ORDINAL_POSITION");

            columnMap.put(ordinalPosition, new Column(name, type, size));
        }

        Column[] actualColumns = new Column[columnMap.size()];
        columnMap.forEach((key, value) -> {
            // SQL index starts at one...
            int idx = key - 1;

            actualColumns[idx] = value;
        });

        assertThat(actualColumns).isEqualTo(expectedColumns);
    }

    @Test
    public void testCreateTable_withData() throws SQLException {
        ORM orm = new ORM();

        Table table = new Table("eleves", new Column[]{
                new Column("prenom", SQLDataType.VARCHAR, 50),
                new Column("nom", SQLDataType.VARCHAR, 100),
                new Column("age", SQLDataType.INTEGER)
        });
        table.addEntry(new Entry(new String[] {"Ayoyama", "Yuga", "16"}));
        table.addEntry(new Entry(new String[] {"Ashido", "Mino", "17"}));
        table.addEntry(new Entry(new String[] {"Asui", "Tsuyu", "16"}));
        table.addEntry(new Entry(new String[] {"Iida", "Tenya", "17"}));
        table.addEntry(new Entry(new String[] {"Uraraka", "Ochaco", "15"}));
        table.addEntry(new Entry(new String[] {"Ojiro", "Mashirao", "14"}));
        table.addEntry(new Entry(new String[] {"Kaminari", "Denki", "16"}));
        table.addEntry(new Entry(new String[] {"Kirishima", "Eijiro", "18"}));
        table.addEntry(new Entry(new String[] {"Koda", "Koji", "11"}));
        table.addEntry(new Entry(new String[] {"Sato", "Rikido", "13"}));
        table.addEntry(new Entry(new String[] {"Shoji", "Mezo", "12"}));
        table.addEntry(new Entry(new String[] {"Jiro", "Kyoka", "14"}));
        table.addEntry(new Entry(new String[] {"Sero", "Hanta", "16"}));
        table.addEntry(new Entry(new String[] {"Tokoyami", "Fumikage", "19"}));
        table.addEntry(new Entry(new String[] {"Todoroki", "Shoto", "17"}));
        table.addEntry(new Entry(new String[] {"Hagakure", "Toru", "17"}));
        table.addEntry(new Entry(new String[] {"Bakugo", "Katsuki", "17"}));
        table.addEntry(new Entry(new String[] {"Midoryiya", "Izuku", "15"}));
        table.addEntry(new Entry(new String[] {"Mineta", "Minoru", "16"}));
        table.addEntry(new Entry(new String[] {"Yaoyorozu", "Momo", "20"}));

        orm.createTable(connection, table);

        String tableName = table.getName().toUpperCase();

        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM " + tableName);

        List<Entry> actual = new ArrayList<>();
        while (resultSet.next()) {
            actual.add(new Entry(new String[]{
                    resultSet.getString(0),
                    resultSet.getString(1),
                    resultSet.getString(2)
            }));
        }

        assertThat(actual).isEqualTo(table.getEntries());
    }
}