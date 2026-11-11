package rubrica.persistenza;
/**
 * se si verifica un errore  durante il caricamento o il salvataggio dei dati.
 *
 *
 */
public class PersistenzaException extends RuntimeException {

    public PersistenzaException(String messaggio, Throwable causa) {
        super(messaggio, causa);
    }
}

