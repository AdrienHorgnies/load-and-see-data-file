package LoadAndSeeDataFile.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Prop {
    private java.util.Properties database;

    private Prop() throws IOException {
        this.database = new Properties();
        this.database.load(new FileInputStream("database.properties"));
    }

    public static String host() {
        return Singleton.INSTANCE.database.getProperty("host");
    }

    public static String port() {
        return Singleton.INSTANCE.database.getProperty("port");
    }

    public static String name() {
        return Singleton.INSTANCE.database.getProperty("name");
    }

    public static String user() {
        return Singleton.INSTANCE.database.getProperty("user");
    }

    public static String password() {
        return Singleton.INSTANCE.database.getProperty("password");
    }

    private static class Singleton {
        static final Prop INSTANCE;
        static {
            Prop prop;
            try {
                prop = new Prop();
            } catch (IOException e) {
                e.printStackTrace();
                prop = null;
            }
            INSTANCE = prop;
        }
    }
}
