package LoadAndSeeDataFile.service;

import LoadAndSeeDataFile.model.Column;
import LoadAndSeeDataFile.model.Record;
import LoadAndSeeDataFile.model.SQLDataType;
import LoadAndSeeDataFile.model.Table;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class SQLAdapterIntTest {

    private Connection connection;

    @Before
    public void before() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testDb;DB_CLOSE_DELAY=-1");
    }

    @After
    public void after() throws SQLException {
        connection.createStatement().execute("SHUTDOWN;");
    }

    @Test
    public void testCreateTable_onlyStructure() throws SQLException {
        SQLAdapter sqlAdapter = new SQLAdapter();

        Column[] expectedColumns = {
                new Column("FIRSTNAME", SQLDataType.VARCHAR, 50),
                new Column("LASTNAME", SQLDataType.VARCHAR, 100),
                new Column("AGE", SQLDataType.INTEGER)
        };
        Table table = new Table("STUDENT", expectedColumns);

        sqlAdapter.createTable(connection, table);

        // checking result
        DatabaseMetaData actualMetaData = connection.getMetaData();

        ResultSet actualTableMetaData = actualMetaData.getTables(null, null, table.getName(), null);

        assertTrue(actualTableMetaData.next());

        ResultSet actualColumnsSet = actualMetaData.getColumns(null, null, table.getName(), null);

        List<Column> columnList = new ArrayList<>();
        while (actualColumnsSet.next()) {
            String name = actualColumnsSet.getString("COLUMN_NAME");

            int jdbcCode = actualColumnsSet.getInt("SQL_DATA_TYPE");
            SQLDataType type = SQLDataType.from(jdbcCode);

            int size = actualColumnsSet.getInt("COLUMN_SIZE");

            columnList.add(new Column(name, type, size));
        }
        Column[] actualColumns = columnList.toArray(new Column[columnList.size()]);

        assertThat(actualColumns).isEqualTo(expectedColumns);
    }

    @Test
    public void testCreateTable_withData() throws SQLException {
        SQLAdapter sqlAdapter = new SQLAdapter();

        Table table = new Table("eleves", new Column[]{
                new Column("prenom", SQLDataType.VARCHAR, 50),
                new Column("nom", SQLDataType.VARCHAR, 100),
                new Column("age", SQLDataType.INTEGER)
        });
        table.addRecord(new Record(new String[] {"Ayoyama", "Yuga", "16"}));
        table.addRecord(new Record(new String[] {"Ashido", "Mino", "17"}));
        table.addRecord(new Record(new String[] {"Asui", "Tsuyu", "16"}));
        table.addRecord(new Record(new String[] {"Iida", "Tenya", "17"}));
        table.addRecord(new Record(new String[] {"Uraraka", "Ochaco", "15"}));
        table.addRecord(new Record(new String[] {"Ojiro", "Mashirao", "14"}));
        table.addRecord(new Record(new String[] {"Kaminari", "Denki", "16"}));
        table.addRecord(new Record(new String[] {"Kirishima", "Eijiro", "18"}));
        table.addRecord(new Record(new String[] {"Koda", "Koji", "11"}));
        table.addRecord(new Record(new String[] {"Sato", "Rikido", "13"}));
        table.addRecord(new Record(new String[] {"Shoji", "Mezo", "12"}));
        table.addRecord(new Record(new String[] {"Jiro", "Kyoka", "14"}));
        table.addRecord(new Record(new String[] {"Sero", "Hanta", "16"}));
        table.addRecord(new Record(new String[] {"Tokoyami", "Fumikage", "19"}));
        table.addRecord(new Record(new String[] {"Todoroki", "Shoto", "17"}));
        table.addRecord(new Record(new String[] {"Hagakure", "Toru", "17"}));
        table.addRecord(new Record(new String[] {"Bakugo", "Katsuki", "17"}));
        table.addRecord(new Record(new String[] {"Midoryiya", "Izuku", "15"}));
        table.addRecord(new Record(new String[] {"Mineta", "Minoru", "16"}));
        table.addRecord(new Record(new String[] {"Yaoyorozu", "Momo", "20"}));

        sqlAdapter.createTable(connection, table);

        String tableName = table.getName().toUpperCase();

        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM " + tableName);

        List<Record> actual = new ArrayList<>();
        while (resultSet.next()) {
            actual.add(new Record(new String[]{
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            }));
        }

        assertThat(actual).isEqualTo(table.getRecords());
    }

    @Test
    public void testRetrieveTable() throws SQLException {
        SQLAdapter sqlAdapter = new SQLAdapter();

        PreparedStatement ddlStatement = connection.prepareStatement("CREATE  TABLE STUDENT (" +
                "FIRSTNAME VARCHAR(50)," +
                "LASTNAME VARCHAR(100)," +
                "AGE INTEGER" +
                ")");

        ddlStatement.executeUpdate();

        PreparedStatement dmlStatement = connection.prepareStatement("INSERT INTO STUDENT (FIRSTNAME, LASTNAME, AGE) VALUES (?,?,?),(?,?,?),(?,?,?)");
        dmlStatement.setString(1, "Ayoyama");
        dmlStatement.setString(2, "Yuga");
        dmlStatement.setString(3, "16");
        dmlStatement.setString(4, "Ashido");
        dmlStatement.setString(5, "Mino");
        dmlStatement.setString(6, "17");
        dmlStatement.setString(7, "Asui");
        dmlStatement.setString(8, "Tsuyu");
        dmlStatement.setString(9, "16");
        dmlStatement.executeUpdate();

        Table expected = new Table("STUDENT", new Column[]{
                new Column("FIRSTNAME", SQLDataType.VARCHAR, 50),
                new Column("LASTNAME", SQLDataType.VARCHAR, 100),
                new Column("AGE", SQLDataType.INTEGER)
        });
        expected.addRecord(new Record(new String[]{"Ayoyama", "Yuga", "16"}));
        expected.addRecord(new Record(new String[]{"Ashido", "Mino", "17"}));
        expected.addRecord(new Record(new String[]{"Asui", "Tsuyu", "16"}));

        Table actual = sqlAdapter.retrieveTable(connection, "STUDENT");

        assertThat(actual).isEqualTo(expected);
    }
}
