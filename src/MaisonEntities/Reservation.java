package MaisonEntities;

public class Reservation {

    private Integer id ;
    private   String nom_client,email_client,numero_client,date_debut,date_fin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getEmail_client() {
        return email_client;
    }

    public void setEmail_client(String email_client) {
        this.email_client = email_client;
    }

    public String getNumero_client() {
        return numero_client;
    }

    public void setNumero_client(String numero_client) {
        this.numero_client = numero_client;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public Reservation(Integer id, String nom_client, String email_client, String numero_client, String date_debut, String date_fin) {
        this.id = id;
        this.nom_client = nom_client;
        this.email_client = email_client;
        this.numero_client = numero_client;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", nom_client='" + nom_client + '\'' +
                ", email_client='" + email_client + '\'' +
                ", numero_client='" + numero_client + '\'' +
                ", date_debut='" + date_debut + '\'' +
                ", date_fin='" + date_fin + '\'' +
                '}';
    }
}
