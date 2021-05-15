package Services;

import Config.MyConnectionspeaky;
import Entities.camping;
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

public class Camping_c implements CRUD<camping> {
    private HttpURLConnection connexion;

    public HttpURLConnection getConnexion(String Url) throws ProtocolException {
        return this.connexion= MyConnectionspeaky.getInstance(Url).getConn();
    }

    public Camping_c() {}

    @Override
    public void Diplay() {}

    @Override
    public void Add(camping camping) {}

    private void CheckOffreId(camping camping) {}

    @Override
    public void Update(camping camping) {}

    public void Updaterate(camping camping) throws IOException {
        HttpURLConnection con = getConnexion("Ratesite_formatjson");
        StringBuilder response = new StringBuilder();
        String jsonInputString = "{ \"rating\": "+camping.getRating_camping()+", \"id_rate\": "+camping.getId()+" }";
        Extractcon(con, response, jsonInputString);

    }

    static void Extractcon(HttpURLConnection con, StringBuilder response, String jsonInputString) throws IOException {
        assert con != null;
        OutputStream os = con.getOutputStream();
        try(os) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response);
        }
        os.flush();
        os.close();
        con.disconnect();
    }

    @Override
    public void Delete(camping camping) {}

    public ArrayList<camping> result() throws IOException {
        HttpURLConnection con = getConnexion("lister_sites");
        ArrayList<camping> list=new ArrayList<camping>();
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
            String localisationCamping = (String) obj.get("localisation_camping");
            String descriptionCamping= (String) obj.get("description_camping");
            String type_camping= (String) obj.get("type_camping");
            String imageCamping= (String) obj.get("image_camping");
            double ratingCamping=0;
            if(obj.get("rating_camping")!=null)
                ratingCamping= (double) obj.get("rating_camping");
            double averageRating=0;
            if(obj.get("average_rating")!=null)
                averageRating= (double) obj.get("average_rating");
            String longitudeCamping= (String) obj.get("longitude_camping");
            String latitudeCamping= (String) obj.get("latitude_camping");
            double offreId=0;
            if(obj.get("formatjsonOfferId")!=null)
                offreId=(double)obj.get("formatjsonOfferId");
            camping c=new camping((int)id,(int)offreId,localisationCamping,descriptionCamping,type_camping,imageCamping,(int)ratingCamping,averageRating,longitudeCamping,latitudeCamping);
            list.add(c);
        }
        con.disconnect();
        return list;
    }
}