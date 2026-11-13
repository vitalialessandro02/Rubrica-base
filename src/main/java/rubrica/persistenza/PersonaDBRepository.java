package rubrica.persistenza;

import rubrica.model.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** Salva e legge le persone dalla tabella "persone" di MySQL. */
public class PersonaDBRepository implements PersonaRepository {

    private static final String SELECT =
            "SELECT nome, cognome, indirizzo, telefono, eta FROM persone ORDER BY id";
    private static final String DELETE = "DELETE FROM persone";
    private static final String INSERT =
            "INSERT INTO persone (nome, cognome, indirizzo, telefono, eta) VALUES (?, ?, ?, ?, ?)";

    private final ConnessioneDB connessione;

    public PersonaDBRepository(ConnessioneDB connessione) {
        this.connessione = connessione;
    }

    @Override
    public List<Persona> caricaTutte() {
        List<Persona> persone = new ArrayList<>();
        try (Connection c = connessione.apri();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SELECT)) {
            while (rs.next()) {
                persone.add(new Persona(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("indirizzo"),
                        rs.getString("telefono"),
                        rs.getInt("eta")));
            }
        } catch (SQLException e) {
            throw new PersistenzaException("Impossibile leggere le persone dal database", e);
        }
        return persone;
    }

    @Override
    public void salvaTutte(List<Persona> persone) {
        try (Connection c = connessione.apri()) {
            c.setAutoCommit(false);
            try (Statement del = c.createStatement();
                 PreparedStatement ins = c.prepareStatement(INSERT)) {
                del.executeUpdate(DELETE);
                for (Persona p : persone) {
                    ins.setString(1, p.getNome());
                    ins.setString(2, p.getCognome());
                    ins.setString(3, p.getIndirizzo());
                    ins.setString(4, p.getTelefono());
                    ins.setInt(5, p.getEta());
                    ins.addBatch();
                }
                ins.executeBatch();
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new PersistenzaException("Impossibile salvare le persone nel database", e);
        }
    }
}