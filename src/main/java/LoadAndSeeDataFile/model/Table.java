package LoadAndSeeDataFile.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Table {

    private final String name;
    private final Column[] columns;
    private final List<Entry> entries;

    public Table(String name, Column[] columns) {
        // todo user data should be validated
        this.name = name;
        this.columns = columns;
        this.entries = new ArrayList<>();
    }

    public void addEntry(Entry entry) {
        this.entries.add(entry);
    }

    public String getName() {
        return name;
    }

    public Column[] getColumns() {
        return columns;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", columns=" + Arrays.toString(columns) +
                ", entries=" + entries +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Table)) return false;
        Table table = (Table) o;
        return Objects.equals(name, table.name) &&
                Arrays.equals(columns, table.columns) &&
                Objects.equals(entries, table.entries);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(name, entries);
        result = 31 * result + Arrays.hashCode(columns);
        return result;
    }
}
