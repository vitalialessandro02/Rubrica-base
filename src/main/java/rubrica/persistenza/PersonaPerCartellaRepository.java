package rubrica.persistenza;

import rubrica.model.Persona;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Salva ogni persona in un file separato dentro una cartella:
 * Persona1.txt, Persona2.txt, ... Ogni file contiene una sola riga
 * {@code nome;cognome;indirizzo;telefono;eta}.
 */
public class PersonaPerCartellaRepository implements PersonaRepository{
    private static final String NOME_CARTELLA = "informazioni";
    private static final String PREFISSO = "Persona";
    private static final String ESTENSIONE = ".txt";
    private static final String SEPARATORE = ";";
    private static final int NUMERO_CAMPI = 5;

    private final File cartella;

    public PersonaPerCartellaRepository() {
        this(new File(NOME_CARTELLA));
    }

    /** Costruttore utile per  scegliere un'altra cartella. */
    public PersonaPerCartellaRepository(File cartella) {
        this.cartella = cartella;

    }

    @Override
    public List<Persona> caricaTutte() {
        List<Persona> persone = new ArrayList<>();

        File[] files = cartella.listFiles((dir, nome) -> eNomeFilePersona(nome));
        if (files == null) {
            return persone; // cartella assente: rubrica vuota
        }
        // Ordina per numero,  l'ordine in tabella resta quello di inserimento
        Arrays.sort(files, Comparator.comparingInt(this::numeroDi));
        for (File f : files) {
            Persona p = leggi(f);
            if (p != null) {
                persone.add(p);
            }
        }
        return persone;
    }

    @Override
    public void salvaTutte(List<Persona> persone) {
        cartella.mkdirs();
        eliminaFileEsistenti();
        int numero = 1;
        for (Persona p : persone) {
            scrivi(new File(cartella, PREFISSO + numero + ESTENSIONE), p);
            numero++;
        }
    }

    /**
     * Cancella dalla cartella tutti i vecchi file PersonaN.txt, così
     * salvaTutte può riscrivere  quelli aggiornati
     */
    private void eliminaFileEsistenti() {
        File[] files = cartella.listFiles((dir, nome) -> eNomeFilePersona(nome));
        if (files != null) {
            for (File f : files) {
                if (!f.delete()) {
                    throw new PersistenzaException("Impossibile eliminare " + f.getName(), null);
                }
            }
        }
    }

    /** Accetta solo i nomi del tipo Persona<numero>.txt */
    private boolean eNomeFilePersona(String nome) {
        return nome.matches(PREFISSO + "\\d+\\" + ESTENSIONE);
    }

    private int numeroDi(File f) {
        String nome = f.getName();
        return Integer.parseInt(nome.substring(PREFISSO.length(), nome.length() - ESTENSIONE.length()));
    }

    private void scrivi(File file, Persona p) {
        try (PrintStream out = new PrintStream(file)) {
            out.println(String.join(SEPARATORE,
                    p.getNome(), p.getCognome(), p.getIndirizzo(),
                    p.getTelefono(), String.valueOf(p.getEta())));
        } catch (FileNotFoundException e) {
            throw new PersistenzaException("Impossibile scrivere " + file.getName(), e);
        }
    }

    /** @return la persona letta, o null se il file è vuoto o malformato */
    private Persona leggi(File file) {
        try (Scanner scanner = new Scanner(file)) {
            if (!scanner.hasNextLine()) {
                return null;
            }
            String[] campi = scanner.nextLine().split(SEPARATORE, -1);
            if (campi.length != NUMERO_CAMPI) {
                return null;
            }
            return new Persona(campi[0], campi[1], campi[2], campi[3],
                    Integer.parseInt(campi[4].trim()));
        } catch (FileNotFoundException e) {
            throw new PersistenzaException("Impossibile leggere " + file.getName(), e);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}





