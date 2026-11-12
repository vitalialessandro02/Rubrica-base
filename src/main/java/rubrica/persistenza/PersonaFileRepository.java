package rubrica.persistenza;
import rubrica.model.Persona;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Salva e legge le persone da un file di testo, una persona per riga,
 * con i campi separati da punto e virgola:
 * {@code nome;cognome;indirizzo;telefono;eta}
 */
public class PersonaFileRepository implements PersonaRepository {

    private static final String NOME_FILE = "informazioni.txt";
    private static final String SEPARATORE = ";";
    private static final int NUMERO_CAMPI = 5;

    private final File file;

    public PersonaFileRepository() {
        this(new File(NOME_FILE));
    }

    /** Costruttore utile per i test: permette di scegliere un altro file. */
    public PersonaFileRepository(File file) {
        this.file = file;
    }

    @Override
    public List<Persona> caricaTutte() {
        List<Persona> persone = new ArrayList<>();
        if (!file.exists()) {
            return persone; // nella prima esecuzione la  rubrica è vuota
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                Persona p = daRiga(scanner.nextLine());
                if (p != null) {
                    persone.add(p);
                }
            }
        } catch (FileNotFoundException e) {
            throw new PersistenzaException("Impossibile leggere " + file.getName(), e);
        }
        return persone;
    }

    @Override
    public void salvaTutte(List<Persona> persone) {
        try (PrintStream out = new PrintStream(file)) {
            for (Persona p : persone) {
                out.println(aRiga(p));
            }
        } catch (FileNotFoundException e) {
            throw new PersistenzaException("Impossibile scrivere " + file.getName(), e);
        }
    }

    private String aRiga(Persona p) {
        return String.join(SEPARATORE,
                p.getNome(), p.getCognome(), p.getIndirizzo(),
                p.getTelefono(), String.valueOf(p.getEta()));
    }

    /** @return la persona letta, o null se la riga è vuota o malformata */
    private Persona daRiga(String riga) {
        // limite -1: mantiene i campi vuoti finali
        String[] campi = riga.split(SEPARATORE, -1);
        if (campi.length != NUMERO_CAMPI) {
            return null;
        }
        try {
            return new Persona(campi[0], campi[1], campi[2], campi[3],
                    Integer.parseInt(campi[4].trim()));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
