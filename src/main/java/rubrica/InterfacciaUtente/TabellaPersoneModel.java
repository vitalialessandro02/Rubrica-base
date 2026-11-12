package rubrica.InterfacciaUtente;

import rubrica.service.RubricaService;
import rubrica.model.Persona;

import javax.swing.table.AbstractTableModel;

/**
 * Adatta la lista di persone del service al formato richiesto da JTable.
 * Mostra  nome, cognome e telefono , indirizzo , età.
 */
public class TabellaPersoneModel extends AbstractTableModel {

    private static final String[] COLONNE = {"Nome", "Cognome", "Telefono"};

    private final RubricaService service;

    public TabellaPersoneModel(RubricaService service) {
        this.service = service;
    }

    @Override
    public int getRowCount() {
        return service.getPersone().size();
    }

    @Override
    public int getColumnCount() {
        return COLONNE.length;
    }

    @Override
    public String getColumnName(int colonna) {
        return COLONNE[colonna];
    }

    @Override
    public Object getValueAt(int riga, int colonna) {
        Persona p = service.getPersone().get(riga);
        return switch (colonna) {
            case 0 -> p.getNome();
            case 1 -> p.getCognome();
            case 2 -> p.getTelefono();
            case 3->p.getIndirizzo();
            default -> null;
        };
    }

    /** Restituisce la persona mostrata in una certa riga della tabella. */
    public Persona getPersonaAt(int riga) {
        return service.getPersone().get(riga);
    }

    /** Da chiamare dopo ogni modifica alla lista, per ridisegnare la tabella. */
    public void aggiorna() {
        fireTableDataChanged();
    }
}