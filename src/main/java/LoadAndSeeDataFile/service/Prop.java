package LoadAndSeeDataFile.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class Prop {
    public static final String UNREADABLE_TITLE = "Properties unreadable";
    public static final String UNREADABLE_MESSAGE = "Application failed to load properties files. Application won't function properly.";

    private static final List<Locale> SUPPORTED_LANGUAGES = Arrays.asList(Locale.FRENCH, Locale.ENGLISH);
    public static final String UNSUPPORTED_LANGUAGE_TITLE = "Unsupported Languages";
    public static final String UNSUPPORTED_LANGUAGE_MESSAGE = "The language you specified isn't supported. English is loaded in place. Please choose from : " + SUPPORTED_LANGUAGES.toString();
    public static boolean IS_SUPPORTED_LANGUAGE;
    /**
     * to ensure properties aren't read continuously, they are wrapped inside a singleton
     */
    private static Prop instance;
    private java.util.Properties prop;

    private Prop() throws IOException {
        try (final InputStream database = this.getClass().getClassLoader().getResourceAsStream("user.properties");
             final InputStream app = this.getClass().getClassLoader().getResourceAsStream("app.properties")) {
            prop = new Properties();
            prop.load(database);
            prop.load(app);
        }

        Locale userLocale = Locale.forLanguageTag(prop.getProperty("interface.language"));
        if (SUPPORTED_LANGUAGES.contains(userLocale)) {
            IS_SUPPORTED_LANGUAGE = true;
            String languageProperties;

            if (userLocale.equals(Locale.FRENCH)) {
                languageProperties = "i18n/fr.properties";
            } else if (userLocale.equals(Locale.ENGLISH)) {
                languageProperties = "i18n/en.properties";
            } else {
                throw new RuntimeException("Language is marked supported but no file is provided");
            }

            try (final InputStreamReader i18n = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(languageProperties), Charset.forName("UTF-8"))) {
                prop.load(i18n);
            }
        } else {
            try (final InputStreamReader i18n = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("i18n/en.properties"), Charset.forName("UTF-8"))) {
                prop.load(i18n);
            }
            IS_SUPPORTED_LANGUAGE = false;
        }
    }

    public static Prop getInstance() throws IOException {
        if (instance == null) {
            instance = new Prop();
        }
        return instance;
    }

    public String get(String key) {
        return instance.prop.getProperty(key);
    }
}
