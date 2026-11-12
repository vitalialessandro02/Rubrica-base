package rubrica.InterfacciaUtente;
import rubrica.service.AutenticazioneService;

import javax.swing.*;
import java.awt.*;
/**
 * Pagina login: finestra mostrata all'avvio.
 * Se le credenziali sono corrette si chiude ed esegue
 * l'apertura della finestra principale.
 */

public class PaginaLogin extends JFrame {
    private final JTextField campoUtente = new JTextField(15);
    private final JPasswordField campoPassword = new JPasswordField(15);

    private final AutenticazioneService autenticazione;
    private final Runnable seLoginRiuscito;
    /**
     * @param autenticazione    servizio che verifica le credenziali
     * @param seLoginRiuscito azione da eseguire quando il login è corretto
     */
    public PaginaLogin(AutenticazioneService autenticazione, Runnable seLoginRiuscito) {
        super("Login");
        this.autenticazione = autenticazione;
        this.seLoginRiuscito = seLoginRiuscito;
        costruzioneInterfaccia();
        setDefaultCloseOperation(EXIT_ON_CLOSE); // chiudere il login chiude l'applicazione
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void costruzioneInterfaccia() {
        JPanel form = new JPanel(new GridLayout(2, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        form.add(new JLabel("Utente"));
        form.add(campoUtente);
        form.add(new JLabel("Password"));
        form.add(campoPassword);

        JButton login = new JButton("LOGIN");
        login.addActionListener(e -> tentaLogin());
        getRootPane().setDefaultButton(login);

        JPanel bottoni = new JPanel(new FlowLayout());
        bottoni.add(login);

        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(bottoni, BorderLayout.SOUTH);
    }

    private void tentaLogin() {
        String utente = campoUtente.getText().trim();
        String password = new String(campoPassword.getPassword());

        if (autenticazione.autentica(utente, password)) {
            dispose();
            seLoginRiuscito.run();
        } else {
            JOptionPane.showMessageDialog(this, "Login errato",
                    "Accesso negato", JOptionPane.ERROR_MESSAGE);
            campoPassword.setText("");
            campoPassword.requestFocus();
        }
    }
}