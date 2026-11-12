package rubrica.persistenza;

import rubrica.model.Utente;

import java.util.List;

public interface UtenteRepository {

    /**
     * Legge tutti gli utenti salvati.
     *
     * @return la lista degli utenti; vuota se non c'è nulla di salvato
     * @throws PersistenzaException se la lettura fallisce
     */
    List<Utente> caricaTutti();

    /**
     * Salva l'intera lista, sostituendo quanto salvato prima.
     *
     * @param utenti gli utenti da salvare
     * @throws PersistenzaException se la scrittura fallisce
     */
    void salvaTutti(List<Utente> utenti);
}
