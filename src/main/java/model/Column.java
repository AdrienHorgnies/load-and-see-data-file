package model;

public class Column {
    private final String name;
    private final SQLDataType type;
    private final int size;

    public Column(String name, SQLDataType type, int size) {
        this.name = name;
        this.type = type;
        this.size = size;
    }
}
