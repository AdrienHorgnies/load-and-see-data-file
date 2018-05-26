package LoadAndSeeDataFile.service;

import LoadAndSeeDataFile.model.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileToTableParser {
    public Table parse(File file) throws FileNotFoundException {
        FileReader fileReader = new FileReader(file);
        return null;
    }
}
