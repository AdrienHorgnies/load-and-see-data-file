package LoadAndSeeDataFile.service;

import LoadAndSeeDataFile.TestFileHelper;
import org.junit.Test;

public class FileToTableParserTest {

    private final TestFileHelper testFileHelper = new TestFileHelper("../../resources/filesToParseToTables");

    private static final String NONEXISTANT = "Wubbalubbadubdub.csv";
    private static final String EMPTY = "empty.csv";
    private static final String ONLY_TABLE_NAME = "only-table-name.csv";
    private static final String NO_DATA = "no-data.csv";
    private static final String HAPPY_FLOW = "happy-flow.csv";

    @Test
    public void testParse_nonexistant() {
        FileToTableParser parser = new FileToTableParser();
    }

    @Test
    public void testParse_Empty() {
        FileToTableParser parser = new FileToTableParser();
    }

    @Test
    public void testParse_onlyTableName() {
        FileToTableParser parser = new FileToTableParser();
    }

    @Test
    public void testParse_noData() {
        FileToTableParser parser = new FileToTableParser();
    }

    @Test
    public void testParse_happyFlow() {
        FileToTableParser parser = new FileToTableParser();
    }
}
