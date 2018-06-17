package LoadAndSeeDataFile.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Prop {
    /**
     * to ensure properties aren't read continuously, they are wrapped inside a singleton
     */
    private static Prop instance;
    private java.util.Properties prop;

    private Prop() throws IOException {
        try (final InputStream stream = this.getClass().getClassLoader().getResourceAsStream("database.properties")) {
            prop = new Properties();
            prop.load(stream);
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
