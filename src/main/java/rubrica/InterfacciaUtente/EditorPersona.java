package rubrica.InterfacciaUtente;



import rubrica.service.ValidazioneException;
import rubrica.model.Persona;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * Modulo di inserimento/modifica di una persona.
 *  al salvataggio esegue l'azione ricevuta dall'esterno.
 */
public class EditorPersona extends JDialog {

    private final JTextField campoNome = new JTextField(20);
    private final JTextField campoCognome = new JTextField(20);
    private final JTextField campoIndirizzo = new JTextField(20);
    private final JTextField campoTelefono = new JTextField(20);
    private final JTextField campoEta = new JTextField(20);

    private final Consumer<Persona> azioneSalva;

    /**
     * @param padre       finestra sopra cui apparire
     * @param persona     persona da modificare, oppure null per una nuova
     * @param azioneSalva cosa fare con i dati inseriti (può lanciare ValidazioneException)
     */
    public EditorPersona(JFrame padre, Persona persona, Consumer<Persona> azioneSalva) {
        super(padre, persona == null ? "Nuova persona" : "Modifica persona", true);
        this.azioneSalva = azioneSalva;

        if (persona != null) {
            compilaCampi(persona);
        }
        costruzioneInterfaccia();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(padre);
    }

    private void compilaCampi(Persona p) {
        campoNome.setText(p.getNome());
        campoCognome.setText(p.getCognome());
        campoIndirizzo.setText(p.getIndirizzo());
        campoTelefono.setText(p.getTelefono());
        campoEta.setText(String.valueOf(p.getEta()));
    }

    private void costruzioneInterfaccia() {
        // Una riga per campo
        JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        aggiungiRiga(form, "Nome", campoNome);
        aggiungiRiga(form, "Cognome", campoCognome);
        aggiungiRiga(form, "Indirizzo", campoIndirizzo);
        aggiungiRiga(form, "Telefono", campoTelefono);
        aggiungiRiga(form, "Età", campoEta);

        JButton salva = new JButton("Salva");
        JButton annulla = new JButton("Annulla");
        salva.addActionListener(e -> salva());
        annulla.addActionListener(e -> dispose());

        /*JPanel bottoni = new JPanel(new FlowLayout());
        bottoni.add(salva);
        bottoni.add(annulla);

        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(bottoni, BorderLayout.SOUTH);*/
        //extra 3
        JToolBar barra = new JToolBar();
        barra.setFloatable(false);
        barra.add(salva);
        barra.add(annulla);

        setLayout(new BorderLayout());
        add(barra, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
    }

    private void aggiungiRiga(JPanel pannello, String etichetta, JTextField campo) {
        pannello.add(new JLabel(etichetta));
        pannello.add(campo);
    }

    private void salva() {
        try {
            int eta = Integer.parseInt(campoEta.getText().trim());
            Persona p = new Persona(
                    campoNome.getText().trim(),
                    campoCognome.getText().trim(),
                    campoIndirizzo.getText().trim(),
                    campoTelefono.getText().trim(),
                    eta);
            azioneSalva.accept(p);
            dispose(); // chiude solo se tutto è andato bene
        } catch (NumberFormatException ex) {
            mostraErrore("L'età deve essere un numero intero.");
        } catch (ValidazioneException ex) {
            mostraErrore(ex.getMessage());
        }
    }

    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Dati non validi", JOptionPane.ERROR_MESSAGE);
    }
}