package LoadAndSeeDataFile.model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Column {
    /**
     * a regex pattern to match a string representation of a column.
     */
    public static final Pattern PATTERN = Pattern.compile(SQLDataType.getRegexGroup() + "(?:\\((?<size>\\d+)\\))?:(?<name>\\w+)");

    private final String name;
    private final SQLDataType type;
    private final int size;

    public Column(String name, SQLDataType type, int size) {
        this.name = name;
        this.type = type;
        this.size = size;
    }

    public Column(String name, SQLDataType type) {
        this.name = name;
        this.type = type;
        this.size = 0;
    }

    public String getName() {
        return name;
    }

    public SQLDataType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", size=" + size +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Column)) return false;
        Column column = (Column) o;
        return size == column.size &&
                Objects.equals(name, column.name) &&
                type == column.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, type, size);
    }
}
