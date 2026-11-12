package rubrica;

import rubrica.InterfacciaUtente.PaginaLogin;
import rubrica.model.Persona;
import rubrica.persistenza.PersonaFileRepository;
import rubrica.persistenza.PersonaPerCartellaRepository;
import rubrica.persistenza.UtenteFileRepository;
import rubrica.service.AutenticazioneService;
import rubrica.service.RubricaService;
import rubrica.InterfacciaUtente.FinestraPrincipale;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        /* versione in un unico file
        RubricaService service = new RubricaService(new PersonaFileRepository());*/
        // verione più files
        RubricaService rubrica = new RubricaService(new PersonaFileRepository());
        AutenticazioneService autenticazione = new AutenticazioneService(new UtenteFileRepository());

        // Tutta la GUI Swing va avviata nel thread grafico.
        // All'avvio si vede solo il login; la finestra principale si apre dopo l'accesso.
        SwingUtilities.invokeLater(() ->
                new PaginaLogin(autenticazione,
                        () -> new FinestraPrincipale(rubrica).setVisible(true)
                ).setVisible(true));
    }
}