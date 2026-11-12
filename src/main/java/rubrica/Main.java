package rubrica;

import rubrica.model.Persona;
import rubrica.persistenza.PersonaFileRepository;
import rubrica.persistenza.PersonaPerCartellaRepository;
import rubrica.service.RubricaService;
import rubrica.InterfacciaUtente.FinestraPrincipale;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        /* versione in un unico file
        RubricaService service = new RubricaService(new PersonaFileRepository());*/
        // verione più files
        RubricaService service = new RubricaService(new PersonaPerCartellaRepository());


        SwingUtilities.invokeLater(() -> new FinestraPrincipale(service).setVisible(true));
    }
}