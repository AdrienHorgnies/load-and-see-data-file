package LoadAndSeeDataFile.model;

import java.util.Arrays;

// todo rename this class "Record" as it is a more widely used word
public class Entry {

    private String[] data;

    public Entry(String[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;
        Entry entry = (Entry) o;
        return Arrays.equals(data, entry.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
}
