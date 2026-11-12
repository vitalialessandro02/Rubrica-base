package rubrica.service;



import rubrica.model.Utente;
import rubrica.persistenza.UtenteRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;

/**
 * Gestisce l'autenticazione degli utenti.
 * Le password sono confrontate tramite hash SHA-256, mai in chiaro.
 */
public class AutenticazioneService {

    private static final String USERNAME_INIZIALE = "admin";
    private static final String PASSWORD_INIZIALE = "admin";

    private final List<Utente> utenti;

    /**
     * Carica gli utenti salvati. Se non ce n'è nessuno, crea e salva
     * l'utente iniziale , per far entare   qualcuno
     */
    public AutenticazioneService(UtenteRepository repository) {
        List<Utente> caricati = repository.caricaTutti();
        if (caricati.isEmpty()) {
            caricati = List.of(new Utente(USERNAME_INIZIALE, calcolaHash(PASSWORD_INIZIALE)));
            repository.salvaTutti(caricati);
        }
        this.utenti = caricati;
    }

    /**
     * @return true se esiste un utente con questo username e questa password
     */
    public boolean autentica(String username, String password) {
        String hash = calcolaHash(password);
        return utenti.stream().anyMatch(u ->
                u.getUsername().equals(username) && u.getPassword().equals(hash));
    }

    /**
     * calcola l'hash della password per non esporre la password vera e propria
     * @param password
     * @return hash della password
     */
    private String calcolaHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {

            throw new IllegalStateException("SHA-256 non disponibile", e);
        }
    }
}
