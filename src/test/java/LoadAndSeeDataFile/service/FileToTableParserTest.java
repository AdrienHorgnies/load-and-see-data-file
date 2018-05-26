package LoadAndSeeDataFile.service;

import LoadAndSeeDataFile.TestFileHelper;
import LoadAndSeeDataFile.model.Column;
import LoadAndSeeDataFile.model.SQLDataType;
import LoadAndSeeDataFile.model.Table;
import LoadAndSeeDataFile.service.exceptions.FileFormatException;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

public class FileToTableParserTest {

    private final TestFileHelper testFileHelper = new TestFileHelper("src/test/resources/files-to-parse");

    private static final String NONEXISTENT = "Wubbalubbadubdub.csv";
    private static final String EMPTY = "empty.csv";
    private static final String ONLY_TABLE_NAME = "only-table-name.csv";
    private static final String NO_DATA = "no-data.csv";
    private static final String HAPPY_FLOW = "happy-flow.csv";

    @Test(expected = FileNotFoundException.class)
    public void testParse_nonexistent() throws FileNotFoundException {
        FileToTableParser parser = new FileToTableParser();

        parser.parse(testFileHelper.getfile(NONEXISTENT));
    }

    @Test(expected = FileFormatException.class)
    public void testParse_Empty() throws FileNotFoundException {
        FileToTableParser parser = new FileToTableParser();

        parser.parse(testFileHelper.getfile(EMPTY));
    }

    @Test(expected = FileFormatException.class)
    public void testParse_onlyTableName() throws FileNotFoundException {
        FileToTableParser parser = new FileToTableParser();

        parser.parse(testFileHelper.getfile(EMPTY));
    }

    @Test
    public void testParse_noData() throws FileNotFoundException {
        FileToTableParser parser = new FileToTableParser();

        Table expected = new Table("eleves", new Column[]{
                new Column("prenom", SQLDataType.VARCHAR, 50),
                new Column("nom", SQLDataType.VARCHAR, 100),
                new Column("age", SQLDataType.VARCHAR)
        });

        Table actual = parser.parse(testFileHelper.getfile(NO_DATA));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testParse_happyFlow() throws FileNotFoundException {
        FileToTableParser parser = new FileToTableParser();

        Table expected = new Table("eleves", new Column[]{
                new Column("prenom", SQLDataType.VARCHAR, 50),
                new Column("nom", SQLDataType.VARCHAR, 100),
                new Column("age", SQLDataType.VARCHAR)
        });
        expected.pushData(new String[] {"prenom", "nom", "age"});
        expected.pushData(new String[] {"Ayoyama", "Yuga", "16"});
        expected.pushData(new String[] {"Ashido", "Mino", "17"});
        expected.pushData(new String[] {"Asui", "Tsuyu", "16"});
        expected.pushData(new String[] {"Iida", "Tenya", "17"});
        expected.pushData(new String[] {"Uraraka", "Ochaco", "15"});
        expected.pushData(new String[] {"Ojiro", "Mashirao", "14"});
        expected.pushData(new String[] {"Kaminari", "Denki", "16"});
        expected.pushData(new String[] {"Kirishima", "Eijiro", "18"});
        expected.pushData(new String[] {"Koda", "Koji", "11"});
        expected.pushData(new String[] {"Sato", "Rikido", "13"});
        expected.pushData(new String[] {"Shoji", "Mezo", "12"});
        expected.pushData(new String[] {"Jiro", "Kyoka", "14"});
        expected.pushData(new String[] {"Sero", "Hanta", "16"});
        expected.pushData(new String[] {"Tokoyami", "Fumikage", "19"});
        expected.pushData(new String[] {"Todoroki", "Shoto", "17"});
        expected.pushData(new String[] {"Hagakure", "Toru", "17"});
        expected.pushData(new String[] {"Bakugo", "Katsuki", "17"});
        expected.pushData(new String[] {"Midoryiya", "Izuku", "15"});
        expected.pushData(new String[] {"Mineta", "Minoru", "16"});
        expected.pushData(new String[] {"Yaoyorozu", "Momo", "20"});

        Table actual = parser.parse(testFileHelper.getfile(HAPPY_FLOW));

        assertThat(actual).isEqualTo(expected);
    }
}
