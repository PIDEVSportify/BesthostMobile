package MaisonEntities;



public class Activity {
    private int id;
    private String id_act,description,categorie,type;


    public Activity() {
    }

    public Activity(String description, String categorie, String type) {
        this.description = description;
        this.categorie = categorie;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_act() {
        return id_act;
    }

    public void setId_act(String id_act) {
        this.id_act = id_act;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", id_act='" + id_act + '\'' +

                ", description='" + description + '\'' +
                ", categorie='" + categorie + '\'' +
                ", type='" + type + '\'' +

                '}';
    }
}