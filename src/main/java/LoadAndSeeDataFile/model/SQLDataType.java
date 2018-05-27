package LoadAndSeeDataFile.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SQLDataType {
    CHAR(1, 2147483647),
    VARCHAR(12, 2147483647),
//    LONGVARCHAR(12, 2147483647), // alias to VARCHAR
//    NUMERIC(3, 65535), // alias to DECIMAL
    DECIMAL(3, 65535),
    BOOLEAN(16, 1),
    TINYINT(-6, 3),
    SMALLINT(5, 5),
    INTEGER(4, 10),
    BIGINT(-5, 19),
    REAL(7, 7),
//    FLOAT(8, 17), // alias to DOUBLE
    DOUBLE(8, 17),
//    BINARY(-3, 2147483647), // alias to VARBINARY
    VARBINARY(-3, 2147483647),
//    LONGVARBINARY(-3, 2147483647), // alias to VARBINARY
    DATE(91, 8),
    TIME(92, 6),
    TIMESTAMP(93, 23);

    public final int JDBC_CODE;
    public final int DEFAULT_SIZE;

    SQLDataType(int JDBC_CODE, int DEFAULT_SIZE) {
        this.JDBC_CODE = JDBC_CODE;
        this.DEFAULT_SIZE = DEFAULT_SIZE;
    }

    private static final Map<Integer, SQLDataType> FROM_JDBC_CODE = new HashMap<>();
    static {
        Arrays.stream(SQLDataType.values())
                .forEach(item -> FROM_JDBC_CODE.put(item.JDBC_CODE, item));
    }

    public static SQLDataType from(int jdbcCode) {
        return FROM_JDBC_CODE.get(jdbcCode);
    }

    /**
     *
     * @return a regex group, named "type", capturing any char sequence matching one of this enum members, full lower or upper cases
     */
    public static String getRegexGroup() {
        String collect = Arrays.stream(values())
                .map(Enum::toString)
                .flatMap(item -> Stream.of(item, item.toLowerCase()))
                .collect(Collectors.joining("|"));

        return "(?<type>" + collect + ")";
    }
}
