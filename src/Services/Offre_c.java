package Services;

import Config.MyConnectionspeaky;
import Entities.offre;
import Interfaces.CRUD;
import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

public class Offre_c implements CRUD<offre> {
    private HttpURLConnection connexion;

    public HttpURLConnection getConnexion(String Url) throws ProtocolException {
        return this.connexion= MyConnectionspeaky.getInstance(Url).getConn();
    }
    public Offre_c(){}

    @Override
    public void Diplay(){}

    @Override
    public void Add(offre offre) throws IOException {
        HttpURLConnection con = getConnexion("Addoffer_formatjson");
        StringBuilder response = new StringBuilder();
        String jsonInputString = "{ \"nombre_places_offre\": "+offre.getNombre_places()+", \"date_debut_offre\": \""+offre.getDate_debut()+"\", \"date_fin_offre\": \""+offre.getDate_fin()+"\", \"prix_offre\": "+offre.getPrix()+" }";
        Camping_c.Extractcon(con, response, jsonInputString);
    }

    @Override
    public void Update(offre offre) throws IOException {
        HttpURLConnection con = getConnexion("Modifyoffer_formatjson");
        StringBuilder response = new StringBuilder();
        String jsonInputString = "{ \"id\": "+offre.getId()+", \"nombre_places_offre\": "+offre.getNombre_places()+", \"date_debut_offre\": \""+offre.getDate_debut()+"\", \"date_fin_offre\": \""+offre.getDate_fin()+"\", \"prix_offre\": "+offre.getPrix()+" }";
        Camping_c.Extractcon(con, response, jsonInputString);
    }

    @Override
    public void Delete(offre offre) throws IOException {
        HttpURLConnection con = getConnexion("Deleteoffer_formatjson");
        StringBuilder response = new StringBuilder();
        String jsonInputString = "{ \"id\": "+offre.getId()+" }";
        Camping_c.Extractcon(con, response, jsonInputString);
    }

    public ArrayList<offre> result() throws IOException {
        HttpURLConnection con = getConnexion("lister_offres");
      ArrayList<offre> list=new ArrayList<offre>();
        StringBuilder response = new StringBuilder();
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        JSONParser json = new JSONParser();
        Map<String, Object> data = null;
        try {
            data = json.parseJSON(new CharArrayReader(new String(response).toCharArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        java.util.List<Map<String, Object>> content = (java.util.List<Map<String, Object>>)data.get("root");
            for(Map<String, Object> obj : content) {
                double id= (double) obj.get("id");
                double nb_places_offre = (double) obj.get("nombre_places_offre");
                String dateDebutOffre= (String) obj.get("date_debut_offre");
                String dateFinOffre= (String) obj.get("date_fin_offre");
                double prixOffre= (double) obj.get("prix_offre");
                offre o=new offre((int)id,(int)nb_places_offre,dateDebutOffre,dateFinOffre,(int)prixOffre);
                list.add(o);
            }
        con.disconnect();
      return list;
    }
}
