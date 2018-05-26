package LoadAndSeeDataFile;

import java.io.File;

public class TestFileHelper {
    private final File directory;

    public TestFileHelper(String directory) {
        this.directory = new File(directory);
    }

    public File getfile(String child) {
        return new File(directory, child);
    }
}
