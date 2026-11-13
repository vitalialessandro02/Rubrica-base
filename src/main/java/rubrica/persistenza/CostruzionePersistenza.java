package rubrica.persistenza;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Sceglie le implementazioni di persistenza in base al parametro "modalita"
 * di credenziali_database.properties: file | cartella | database.
 * Se il file manca o il parametro è assente, usa "file" (comportamento base).
 */
public class CostruzionePersistenza {

    private static final String NOME_FILE = "credenziali_db.properties";
    private static final String MODALITA_DEFAULT = "file";

    private final String modalita;
    private ConnessioneDB connessione;

    public CostruzionePersistenza() {
        this.modalita = leggiModalita();

    }

    public PersonaRepository creaPersonaRepository() {
        return switch (modalita) {
            case "cartella" -> new PersonaPerCartellaRepository();
            case "database" -> new PersonaDBRepository(connessione());
            default -> new PersonaFileRepository();
        };
    }

    public UtenteRepository creaUtenteRepository() {
        return modalita.equals("database")
                ? new UtenteDBRepository(connessione())
                : new UtenteFileRepository();
    }

    /** Crea la connessione una sola volta e la condivide tra i due repository. */
    private ConnessioneDB connessione() {
        if (connessione == null) {
            connessione = new ConnessioneDB();
        }
        return connessione;
    }

    private String leggiModalita() {
        Properties p = new Properties();
        try (FileInputStream in = new FileInputStream(NOME_FILE)) {
            p.load(in);
        } catch (IOException e) {
            return MODALITA_DEFAULT; // nessun file: modalità base
        }
        return p.getProperty("modalita", MODALITA_DEFAULT).trim().toLowerCase();
    }
}