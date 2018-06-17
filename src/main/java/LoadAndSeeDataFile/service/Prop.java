package LoadAndSeeDataFile.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Prop {
    public static final String UNREADABLE_TITLE = "Properties unreadable";
    public static final String UNREADABLE_MESSAGE = "Application failed to load properties files. Application won't function properly.";

    /**
     * to ensure properties aren't read continuously, they are wrapped inside a singleton
     */
    private static Prop instance;
    private java.util.Properties prop;

    private Prop() throws IOException {
        try (final InputStream database = this.getClass().getClassLoader().getResourceAsStream("database.properties");
             final InputStream app = this.getClass().getClassLoader().getResourceAsStream("app.properties");
             final InputStream i18n = this.getClass().getClassLoader().getResourceAsStream("i18n/en.properties")) {
            prop = new Properties();
            prop.load(database);
            prop.load(app);
            prop.load(i18n);
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
