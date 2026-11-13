package rubrica.persistenza;


import rubrica.model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** Salva e legge gli utenti dalla tabella "utenti" di MySQL. */
public class UtenteDBRepository implements UtenteRepository {

    private static final String SELECT = "SELECT username, password_hash FROM utenti";
    private static final String DELETE = "DELETE FROM utenti";
    private static final String INSERT = "INSERT INTO utenti (username, password_hash) VALUES (?, ?)";

    private final ConnessioneDB connessione;

    public UtenteDBRepository(ConnessioneDB connessione) {
        this.connessione = connessione;
    }

    @Override
    public List<Utente> caricaTutti() {
        List<Utente> utenti = new ArrayList<>();
        try (Connection c = connessione.apri();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SELECT)) {
            while (rs.next()) {
                utenti.add(new Utente(rs.getString("username"), rs.getString("password_hash")));
            }
        } catch (SQLException e) {
            throw new PersistenzaException("Impossibile leggere gli utenti dal database", e);
        }
        return utenti;
    }

    @Override
    public void salvaTutti(List<Utente> utenti) {
        try (Connection c = connessione.apri()) {
            c.setAutoCommit(false);
            try (Statement del = c.createStatement();
                 PreparedStatement ins = c.prepareStatement(INSERT)) {
                del.executeUpdate(DELETE);
                for (Utente u : utenti) {
                    ins.setString(1, u.getUsername());
                    ins.setString(2, u.getPassword());
                    ins.addBatch();
                }
                ins.executeBatch();
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new PersistenzaException("Impossibile salvare gli utenti nel database", e);
        }
    }
}