package LoadAndSeeDataFile.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        return "Table{" +
                "\n    name='" + name + '\'' +
                ",\n    columns=Column[" +
                "\n        " + Arrays.stream(columns).map(Column::toString).collect(Collectors.joining(",\n        ")) + "]" +
                ",\n    data=List[" +
                "\n        " + data.stream().map(Arrays::toString).collect(Collectors.joining(",\n        ")) + "]" +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Table)) return false;
        Table table = (Table) o;
        return Objects.equals(name, table.name) &&
                Arrays.equals(columns, table.columns) &&
                Objects.equals(data, table.data);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(name, data);
        result = 31 * result + Arrays.hashCode(columns);
        return result;
    }
}
