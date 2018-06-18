package LoadAndSeeDataFile.service;

import LoadAndSeeDataFile.model.Column;
import LoadAndSeeDataFile.model.Record;
import LoadAndSeeDataFile.model.SQLDataType;
import LoadAndSeeDataFile.model.Table;
import LoadAndSeeDataFile.service.exceptions.ColumnFormatException;
import LoadAndSeeDataFile.service.exceptions.FileFormatException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;

public class FileParser {

    private static final String DEFAULT_DELIMITER = ";";

    private final String delimiter;

    public FileParser() {
        this.delimiter = DEFAULT_DELIMITER;
    }

    public Table parse(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String tableName = reader.readLine();

        if (tableName == null) {
            throw new FileFormatException();
        }

        String columnsLine = reader.readLine();

        if (columnsLine == null) {
            throw new FileFormatException();
        }

        Column[] columns = parseColumns(columnsLine);

        Table table = new Table(tableName, columns);

        reader.lines()
                .map(this::parseRecord)
                .forEach(table::addRecord);

        return table;
    }

    private Column[] parseColumns(String columnLine) {
        String[] columnsStr = columnLine.split(delimiter);

        return Arrays.stream(columnsStr)
                .map((columnStr) -> {
                    Matcher matcher = Column.PATTERN.matcher(columnStr);

                    if (!matcher.matches()) {
                        throw new ColumnFormatException(columnStr);
                    }

                    String name = matcher.group("name");
                    SQLDataType type = SQLDataType.valueOf(matcher.group("type").toUpperCase());
                    String sizeStr = matcher.group("size");

                    if (sizeStr == null) {
                        return new Column(name, type);
                    }
                    int size = Integer.parseInt(sizeStr);

                    return new Column(name, type, size);
                })
                .toArray(Column[]::new);
    }

    private Record parseRecord(String contentLine) {
        return new Record(contentLine.split(delimiter));
    }
}
