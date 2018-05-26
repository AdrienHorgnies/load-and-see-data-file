package model;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final String name;
    private final Column[] columns;
    private final List<String[]> data;

    public Table(String name, Column[] columns) {
        // todo user data should be validated
        this.name = name;
        this.columns = columns;
        this.data = new ArrayList<>();
    }

    public void pushData(String[] data) {
        // todo user data should always be validated
        this.data.add(data);
    }
}
