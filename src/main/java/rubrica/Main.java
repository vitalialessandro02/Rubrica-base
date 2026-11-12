package rubrica;

import rubrica.model.Persona;
import rubrica.persistenza.PersonaFileRepository;
import rubrica.service.RubricaService;
import rubrica.InterfacciaUtente.FinestraPrincipale;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        RubricaService service = new RubricaService(new PersonaFileRepository());

        SwingUtilities.invokeLater(() -> new FinestraPrincipale(service).setVisible(true));
    }
}