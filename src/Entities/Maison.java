package Entities;

public class Maison {

    private Integer id ,prix,nombre_chambres;
    private String nom,adresse,description,type,avatar;


    public Maison(String nom, String adresse, String description, String type, Integer prix, Integer nombre_chambres) {
        this.nom = nom;
        this.prix = prix;
        this.nombre_chambres = nombre_chambres;

        this.adresse = adresse;
        this.description = description;
        this.type = type;

    }

    public Maison() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public int getNombre_chambres() {
        return nombre_chambres;
    }

    public void setNombre_chambres(int nombre_chambres) {
        this.nombre_chambres = nombre_chambres;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }



    @Override
    public String toString() {
        return "Maison{" +
                "id=" + id +
                ", prix=" + prix +
                ", nombre_chambres=" + nombre_chambres +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
