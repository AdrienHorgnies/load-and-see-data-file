package LoadAndSeeDataFile.service.exceptions;

public class ColumnFormatException extends RuntimeException {

    public ColumnFormatException(String columnStr) {
        super("The following text is not a valid column representation : " + columnStr);
    }
}
