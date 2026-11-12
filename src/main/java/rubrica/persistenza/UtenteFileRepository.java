package rubrica.persistenza;

import rubrica.model.Utente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Salva e legge gli utenti da un file di testo, uno per riga:
 * {@code username;password}
 */
public class UtenteFileRepository implements UtenteRepository {

    private static final String NOME_FILE = "utenti.txt";
    private static final String SEPARATORE = ";";
    private static final int NUMERO_CAMPI = 2;

    private final File file;

    public UtenteFileRepository() {
        this(new File(NOME_FILE));
    }

    /** Costruttore che permette di scegliere un altro file. */
    public UtenteFileRepository(File file) {
        this.file = file;
    }

    @Override
    public List<Utente> caricaTutti() {
        List<Utente> utenti = new ArrayList<>();
        if (!file.exists()) {
            return utenti; // prima esecuzione: nessun utente
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] campi = scanner.nextLine().split(SEPARATORE, -1);
                if (campi.length == NUMERO_CAMPI) {
                    utenti.add(new Utente(campi[0], campi[1]));
                }
            }
        } catch (FileNotFoundException e) {
            throw new PersistenzaException("Impossibile leggere " + file.getName(), e);
        }
        return utenti;
    }

    @Override
    public void salvaTutti(List<Utente> utenti) {
        try (PrintStream out = new PrintStream(file)) {
            for (Utente u : utenti) {
                out.println(u.getUsername() + SEPARATORE + u.getPassword());
            }
        } catch (FileNotFoundException e) {
            throw new PersistenzaException("Impossibile scrivere " + file.getName(), e);
        }
    }
}