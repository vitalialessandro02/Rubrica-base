package rubrica.service;

import rubrica.model.Persona;
import rubrica.persistenza.PersonaRepository;

import java.util.List;
import java.util.Vector;

public class RubricaService {
    private final List<Persona> persone= new Vector<>();

    private final PersonaRepository repository;

    private static final int ETA_MINIMA = 0;
    private static final int ETA_MASSIMA = 150;

    public RubricaService(PersonaRepository repository) {
        this.repository = repository;
        this.persone.addAll(repository.caricaTutte());
    }



    /**
     * aggiunge una persona se non è gia presente
     * @param p persona da aggiungere
     */
    public void aggiungiPersona(Persona p) {
        valida(p);
        if (esisteDuplicato(p)) {
            throw new ValidazioneException("Questa persona è già presente in rubrica");

        }
        persone.add(p);
        repository.salvaTutte(persone);
    }


    /**
     * inserisce i dati della nuova persona in quella vecchia
     * @param vecchia perona già presente nell'elenco che dovra essere aggiornata
     * @param nuova nuovi dati da aggiornare
     */
    public void modifica(Persona vecchia, Persona nuova) {
        if (!persone.contains(vecchia)) {
            throw new ValidazioneException("La persona non esiste nella rubrica");
        }
        valida(nuova);
        vecchia.setNome(nuova.getNome());
        vecchia.setCognome(nuova.getCognome());
        vecchia.setIndirizzo(nuova.getIndirizzo());
        vecchia.setTelefono(nuova.getTelefono());
        vecchia.setEta(nuova.getEta());
        repository.salvaTutte(persone);
    }

    /**
     * funzione che elimina(se esiste ) una persona dalla rubrica
     * @param p perona da eliminare
     */
    public void elimina(Persona p){
            if (!persone.contains(p)) {
                throw new ValidazioneException("La persona non esiste nella rubrica");
            }
            persone.remove(p);
        repository.salvaTutte(persone);
    }

    /**
     * Indica se in rubrica c'è già una persona con lo stesso nome,
     * cognome e telefono.
     */
    private boolean esisteDuplicato(Persona nuova) {
        for (Persona p : persone) {
            if (p.getNome().equalsIgnoreCase(nuova.getNome())
                    && p.getCognome().equalsIgnoreCase(nuova.getCognome())
                    && p.getTelefono().equals(nuova.getTelefono())) {
                return true;
            }
        }
        return false;
    }


    public List<Persona> getPersone() {
        return persone;
    }

    /**
     * Controlla che i dati di una persona siano accettabili.
     *
     * @throws ValidazioneException se un campo non è valido
     */
    private void valida(Persona p) {
        if (p == null) {
            throw new ValidazioneException("Persona mancante");
        }
        if (p.getNome() == null || p.getNome().isBlank()) {
            throw new ValidazioneException("Il nome non può essere vuoto");
        }
        if (p.getCognome() == null || p.getCognome().isBlank()) {
            throw new ValidazioneException("Il cognome non può essere vuoto");
        }
        if (p.getEta() < ETA_MINIMA || p.getEta() > ETA_MASSIMA) {
            throw new ValidazioneException(
                    "L'età deve essere tra " + ETA_MINIMA + " e " + ETA_MASSIMA);
        }
        if (p.getIndirizzo().contains(";")||p.getTelefono().contains(";")||p.getNome().contains(";")||p.getCognome().contains(";")) {
            throw new ValidazioneException("Il carattere ';' non è ammesso.");
        }
    }


}
