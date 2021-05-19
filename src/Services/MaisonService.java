package Services;

import MaisonEntities.Maison;
import Utils.Statics;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;

import java.io.CharArrayReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MaisonService {

        public static MaisonService instance;
        private ConnectionRequest req;
        public MaisonService(){
            req= new ConnectionRequest();
        }
        public static MaisonService getInstance(){
            if (instance == null){
                instance = new MaisonService();
            }
            return instance;
        }

        public boolean resultOK;
        public boolean addMaisons(Maison m){
                String url = Statics.BASE_URL+"/jsonMaisonAdd/new?nom="+m.getNom()+"&adresse="+m.getAdresse()+"/&description="+m.getDescription()+"&type="+m.getType()+"&nombre_chambres="+m.getNombre_chambres()+"&prix="+m.getPrix();
            ConnectionRequest req = new ConnectionRequest(url);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    resultOK = req.getResponseCode()==200;

                }
            });
            NetworkManager.getInstance().addToQueueAndWait(req);
            return resultOK;
        }


   public ArrayList<Maison> maisons;

        public ArrayList<Maison> parseMaison(String jsonText) throws IOException {
            maisons=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> maisonListJson= j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String, Object>>)maisonListJson.get("root");
            for (Map<String,Object> obj : list){
                Maison m = new Maison();
                //float id = Float.parseFloat(obj.get("id").toString());
                m.setNom(obj.get("nom").toString());
                m.setAdresse(obj.get("adresse").toString());
                m.setDescription(obj.get("description").toString());
                m.setType(obj.get("type").toString());


                maisons.add(m);

            }

            return maisons;
        }
        public ArrayList <Maison> getAllMaisons(){
            String url = Statics.BASE_URL+"/jsonMaisonAll";

             req.setUrl(url);
             req.setPost(false);
             req.addResponseListener(new ActionListener<NetworkEvent>() {
                 @Override
                 public void actionPerformed(NetworkEvent evt) {
                     try {
                         maisons = parseMaison(new String(req.getResponseData()));
                         req.removeResponseListener(this);
                     } catch (IOException e) {
                         e.printStackTrace();
                     }

                 }
             });
            NetworkManager.getInstance().addToQueueAndWait(req);
             return maisons;
        }

    // display all users
  /*  public ArrayList<Maison>displayMaisons() {
        ArrayList<Maison> result = new ArrayList();

        String url = Statics.BASE_URL+"/displayMaison";
        ConnectionRequest req = new ConnectionRequest(url);
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String,Object>mapUsers = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapUsers.get("root");

                    for (Map<String, Object> obj : listOfMaps){
                        Maison maison  = new Maison();

//                        float id = Float.parseFloat(obj.get("id").toString());

                        String nom = obj.get("nom").toString();
                        String addresse = obj.get("addresse").toString();
                        String description = obj.get("description").toString();
                        String type = obj.get("type").toString();
                        String prix = obj.get("prix").toString();
                        String nb_chambre = obj.get("nb_chambre").toString();
  //                     float phone = Float.parseFloat(obj.get("phone").toString());

                        //maison.setId((int)id);
                        maison.setNom(nom);
                        maison.setDescription(description);
                        maison.setAdresse(addresse);
                        maison.setType(type);
                        maison.setPrix(Integer.valueOf(prix));

                        maison.setNombre_chambres(Integer.parseInt(nb_chambre));

                        result.add(maison);

                    }
                } catch (IOException ex) {

                }
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }
*/
}





//int prix = Integer.parseInt(obj.get("prix").toString()) ;
//m.setPrix((int)prix);
//  m.setPrix(Integer.valueOf(obj.get("prix").toString()));

// int nb_chambres = Integer.parseInt(obj.get("nombre_chambres").toString()) ;
// m.setNombre_chambres((int)nb_chambres);
