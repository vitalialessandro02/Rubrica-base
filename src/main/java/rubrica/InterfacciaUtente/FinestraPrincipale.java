package rubrica.InterfacciaUtente;

import rubrica.model.Persona;
import rubrica.service.RubricaService;
import javax.swing.*;
import java.awt.*;

/** Finestra principale: tabella dei contatti e bottoni nuovo, modifica, elimina. */
public class FinestraPrincipale extends JFrame {

    private final RubricaService service;
    private final TabellaPersoneModel modelloTabella;
    private final JTable tabella;

    public FinestraPrincipale(RubricaService service) {
        super("Rubrica");
        this.service = service;
        this.modelloTabella = new TabellaPersoneModel(service);
        this.tabella = new JTable(modelloTabella);
        tabella.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        costruisciInterfaccia();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null); // centra sullo schermo
    }

    private void costruisciInterfaccia() {
        setLayout(new BorderLayout());
        add(new JScrollPane(tabella), BorderLayout.CENTER);

        JButton nuovo = new JButton("Nuovo");
        JButton modifica = new JButton("Modifica");
        JButton elimina = new JButton("Elimina");

        nuovo.addActionListener(e -> nuovaPersona());
        modifica.addActionListener(e -> modificaPersona());
        elimina.addActionListener(e -> eliminaPersona());

        JPanel pannelloBottoni = new JPanel(new FlowLayout());
        pannelloBottoni.add(nuovo);
        pannelloBottoni.add(modifica);
        pannelloBottoni.add(elimina);
        add(pannelloBottoni, BorderLayout.SOUTH);
    }

    private void nuovaPersona() {
        EditorPersona editor = new EditorPersona(this, null, service::aggiungiPersona);
        editor.setVisible(true);
        modelloTabella.aggiorna();
    }

    private void modificaPersona() {
        Persona selezionata = getPersonaSelezionata();
        if (selezionata == null) {
            JOptionPane.showMessageDialog(this,
                    "Per modificare è necessario prima selezionare una persona.");
            return;
        }
        EditorPersona editor = new EditorPersona(this, selezionata,
                nuoviDati -> service.modifica(selezionata, nuoviDati));
        editor.setVisible(true);
        modelloTabella.aggiorna();
    }

    private void eliminaPersona() {
        Persona selezionata = getPersonaSelezionata();
        if (selezionata == null) {
            JOptionPane.showMessageDialog(this,
                    "Per eliminare è necessario prima selezionare una persona.");
            return;
        }
        int risposta = JOptionPane.showConfirmDialog(this,
                "Eliminare la persona " + selezionata.getNome() + " " + selezionata.getCognome() + "?",
                "Conferma eliminazione", JOptionPane.YES_NO_OPTION);
        if (risposta == JOptionPane.YES_OPTION) {
            service.elimina(selezionata);
            modelloTabella.aggiorna();
        }
    }

    /** @return la persona della riga selezionata, o null se nessuna riga è selezionata */
    private Persona getPersonaSelezionata() {
        int riga = tabella.getSelectedRow();
        return riga == -1 ? null : modelloTabella.getPersonaAt(riga);
    }


}