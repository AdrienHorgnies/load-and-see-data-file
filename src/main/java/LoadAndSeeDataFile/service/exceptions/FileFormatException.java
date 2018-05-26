package LoadAndSeeDataFile.service.exceptions;

public class FileFormatException extends RuntimeException {
    public FileFormatException() {
        super("The file doesn't respect required format");
    }
}
