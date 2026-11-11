package rubrica.model;
/**
classe persona contiene  le informazioni di uno dei contatti della
rubrica.
La persona ha  seguenti informazioni:
‐ nome: stringa
-cognome: stringa
- indirizzo: stringa
- telefono: stringa
-eta: intero
 */
public class Persona {
    private String nome;
    private String cognome;
    private String indirizzo;
    private String telefono;

    private int eta;

    /**
     *Crea un nuovo contatto.
     *
     * @param nome nome della persona
     * @param cognome cognome della persona
     * @param indirizzo indirizzo della persona
     * @param telefono telefono della persona
     * @param eta eta della persona
     */
    public Persona(String nome, String cognome, String indirizzo, String telefono, int eta) {
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eta = eta;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getEta() {
        return eta;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", eta=" + eta +
                '}';
    }
}