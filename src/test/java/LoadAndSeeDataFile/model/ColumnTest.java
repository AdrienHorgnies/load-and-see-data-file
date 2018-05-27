package LoadAndSeeDataFile.model;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ColumnTest {

    @Test
    public void testPattern() {
        Pattern pattern = Column.PATTERN;

        assertTrue(pattern.matcher("VARCHAR(50):PRENOM").matches());
        assertTrue(pattern.matcher("VARCHAR:PRENOM").matches());
        assertTrue(pattern.matcher("INTEGER:AGE").matches());

        assertFalse(pattern.matcher("VARCHAR():PRENOM").matches());
        assertFalse(pattern.matcher("VARCHAR(-1):PRENOM").matches());
        assertFalse(pattern.matcher("VARCHAR(50:PRENOM").matches());
        assertFalse(pattern.matcher("VARCHAR(50)PRENOM").matches());
    }
}