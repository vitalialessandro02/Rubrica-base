package rubrica.service;

/**
 *
 * classe che segnala che l'operazione richiesta
 *  non è consentita perchè la persona inserita può essere inesistente o già presente.
 *
 *
 */
public class ValidazioneException extends RuntimeException {
    public ValidazioneException(String messaggio) {
        super(messaggio);
    }
}
