package LoadAndSeeDataFile.service.exceptions;

public class UnsupportedLanguageException extends RuntimeException {

    public UnsupportedLanguageException(String language) {
        super("Specified language isn't supported :" + language);
    }
}
