package LoadAndSeeDataFile.model;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SQLDataType {
    CHARACTER,
    VARCHAR,
    LONGVARCHAR,
    NUMERIC,
    DECIMAL,
    BIT,
    TINYINT,
    SMALLINT,
    INTEGER,
    BIGINT,
    REAL,
    FLOAT,
    DOUBLE,
    BINARY,
    VARBINARY,
    LONGVARBINARY,
    DATE,
    TIME,
    TIMESTAMP;

    /**
     *
     * @return a regex group, named "type", capturing any char sequence matching one of this enum members
     */
    public static String getRegexGroup() {
        String collect = Arrays.stream(values())
                .map(Enum::toString)
                .flatMap(item -> Stream.of(item, item.toLowerCase()))
                .collect(Collectors.joining("|"));

        return "(?<type>" + collect + ")";
    }
}
