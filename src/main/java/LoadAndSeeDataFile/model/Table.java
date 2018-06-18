package LoadAndSeeDataFile.model;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Table implements TableModel {

    private final String name;
    private final Column[] columns;
    private final List<Record> records;

    public Table(String name, Column[] columns) {
        this.name = name;
        this.columns = columns;
        this.records = new ArrayList<>();
    }

    public void addRecord(Record record) {
        // todo check that records are valid regarding to their corresponding type
        // todo check that records number fits columns number
        this.records.add(record);
    }

    public String getName() {
        return name;
    }

    public Column[] getColumns() {
        return columns;
    }

    public List<Record> getRecords() {
        return records;
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", columns=" + Arrays.toString(columns) +
                ", records=" + records +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Table)) return false;
        Table table = (Table) o;
        return Objects.equals(name, table.name) &&
                Arrays.equals(columns, table.columns) &&
                Objects.equals(records, table.records);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(name, records);
        result = 31 * result + Arrays.hashCode(columns);
        return result;
    }

    @Override
    public int getRowCount() {
        return this.getRecords().size();
    }

    @Override
    public int getColumnCount() {
        return this.getColumns().length;
    }

    @Override
    public String getColumnName(int i) {
        return this.getColumns()[i].getName();
    }

    @Override
    public Class<?> getColumnClass(int i) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        return this.getRecords().get(i).getData(i1);
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
        // no cell is editable at the moment
    }

    @Override
    public void addTableModelListener(TableModelListener tableModelListener) {
        // it's not editable yet, so nothing to listen to
    }

    @Override
    public void removeTableModelListener(TableModelListener tableModelListener) {
        // it's not editable yet, so nothing to listen to
    }
}
