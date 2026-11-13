package rubrica.persistenza;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * Apre le connessioni a MySQL usando i parametri letti da
 * credenziali_database.properties .
 */

public class ConnessioneDB {
    private static final String NOME_FILE = "credenziali_db.properties";

    private final String url;
    private final String username;
    private final String password;

    public ConnessioneDB() {
        Properties p = new Properties();
        try (FileInputStream in = new FileInputStream(NOME_FILE)) {
            p.load(in);
        } catch (IOException e) {
            throw new PersistenzaException("Impossibile leggere " + NOME_FILE, e);
        }
        this.url = "jdbc:mysql://" + p.getProperty("host", "localhost")
                + ":" + p.getProperty("porta", "3306")
                + "/" + p.getProperty("database", "rubrica");
        this.username = p.getProperty("username");
        this.password = p.getProperty("password");
    }

    /** @return una nuova connessione*/
    public Connection apri() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new PersistenzaException("Connessione al database fallita", e);
        }
    }
}




