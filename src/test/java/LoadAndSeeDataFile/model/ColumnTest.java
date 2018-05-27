package LoadAndSeeDataFile.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ColumnTest {

    @Test
    public void testPattern() {
        assertTrue(Column.PATTERN.matcher("VARCHAR(50):PRENOM").matches());
        assertTrue(Column.PATTERN.matcher("VARCHAR:PRENOM").matches());
        assertTrue(Column.PATTERN.matcher("INTEGER:AGE").matches());

        assertFalse(Column.PATTERN.matcher("VARCHAR():PRENOM").matches());
        assertFalse(Column.PATTERN.matcher("VARCHAR(-1):PRENOM").matches());
        assertFalse(Column.PATTERN.matcher("VARCHAR(50:PRENOM").matches());
        assertFalse(Column.PATTERN.matcher("VARCHAR(50)PRENOM").matches());
    }
}