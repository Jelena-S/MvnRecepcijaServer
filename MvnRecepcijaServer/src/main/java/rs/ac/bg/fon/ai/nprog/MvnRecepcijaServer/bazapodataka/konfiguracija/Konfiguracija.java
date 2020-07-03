package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konfiguracija;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Konfiguracija {
	private static Konfiguracija instance;
    private String username;
    private String password;
    private String dbUrl;
    private int port;

    private Konfiguracija() {
        Properties propertiesDatabase = new Properties();
        FileInputStream fis = null;
        
        try {
            fis = new FileInputStream("src\\main\\resources\\configuration.properties"); //napravi fajl sa parametrima za konekciju ///////src//properties//database.properties
            propertiesDatabase.load(fis);
            dbUrl = propertiesDatabase.getProperty("url"); //izmeni
            username = propertiesDatabase.getProperty("username");
            password = propertiesDatabase.getProperty("password");
            String portCitaj = propertiesDatabase.getProperty("port");
            port = Integer.parseInt(portCitaj);
            
        } catch (Exception ex) {
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    public static Konfiguracija getInstance() {
        if (instance == null) {
            instance = new Konfiguracija();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public int getPort() {
        return port;
    }
}
