package rubrica.persistenza;

import rubrica.model.Persona;

import java.util.List;

/**
 * memorizzazione persistente delle persone
 */
        public interface PersonaRepository {
        /**
         * legge le persone salvate
         * @return la lista delle persone; vuota se non c'è nulla di salvato
         * @throws PersistenzaException se la lettura fallisce
         */
          List<Persona> caricaTutte();

        /**
         * Salva tutta la  lista,
         *
         * @param persone le persone da salvare
         * @throws PersistenzaException se la scrittura fallisce
         */
          void salvaTutte(List<Persona> persone);
}