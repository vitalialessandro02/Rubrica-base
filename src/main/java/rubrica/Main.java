package rubrica;

import rubrica.InterfacciaUtente.PaginaLogin;
import rubrica.model.Persona;
import rubrica.persistenza.*;
import rubrica.service.AutenticazioneService;
import rubrica.service.RubricaService;
import rubrica.InterfacciaUtente.FinestraPrincipale;
import javax.swing.SwingUtilities;
import java.sql.Connection;
import java.util.List;

public class Main {

    public static void main(String[] args) {

            CostruzionePersistenza factory = new CostruzionePersistenza();
            RubricaService rubrica = new RubricaService(factory.creaPersonaRepository());
            AutenticazioneService autenticazione =
                    new AutenticazioneService(factory.creaUtenteRepository());

            SwingUtilities.invokeLater(() ->
                    new PaginaLogin(autenticazione,
                            () -> new FinestraPrincipale(rubrica).setVisible(true)
                    ).setVisible(true));

    }
}