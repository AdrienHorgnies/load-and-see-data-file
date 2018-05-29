package LoadAndSeeDataFile.model;

import java.util.Arrays;

public class Record {

    private String[] data;

    public Record(String[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Record{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Record)) return false;
        Record record = (Record) o;
        return Arrays.equals(data, record.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
}
