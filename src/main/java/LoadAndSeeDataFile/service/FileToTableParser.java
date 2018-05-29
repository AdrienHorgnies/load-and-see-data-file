package LoadAndSeeDataFile.service;

import LoadAndSeeDataFile.model.Column;
import LoadAndSeeDataFile.model.Entry;
import LoadAndSeeDataFile.model.SQLDataType;
import LoadAndSeeDataFile.model.Table;
import LoadAndSeeDataFile.service.exceptions.ColumnFormatException;
import LoadAndSeeDataFile.service.exceptions.FileFormatException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;

public class FileToTableParser {

    private final static String DEFAULT_DELIMITER = ";";

    private final String delimiter;

    public FileToTableParser(String delimiter) {
        this.delimiter = delimiter;
    }

    public FileToTableParser() {
        this(DEFAULT_DELIMITER);
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
                .map(this::parseEntry)
                .forEach(table::addEntry);

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
                    int size = Integer.parseInt(Optional.ofNullable(matcher.group("size")).orElse("0"));

                    return new Column(name, type, size);
                })
                .toArray(Column[]::new);
    }

    private Entry parseEntry(String contentLine) {
        // todo it shouldn't be possible to create an Entry independently like that as its formatting depends of the Table containing it.
        return new Entry(contentLine.split(delimiter));
    }
}
