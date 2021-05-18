package Services;

import Entities.Maison;
import Utils.Statics;
import com.codename1.io.*;
import com.codename1.l10n.ParseException;
import com.codename1.ui.events.ActionListener;

import java.io.CharArrayReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaisonService {

        public static Maison maison;
    ArrayList<Map<String, Object>> data = new ArrayList<>();
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

        /*public boolean resultOK;
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
        }*/


   public ArrayList<Maison> maisons;

    public ArrayList<Map<String, Object>> parseMaison(String jsonText) throws IOException, ParseException {
        JSONParser j = new JSONParser();
        Map<String,Object> MaisonsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)MaisonsListJson.get("root");
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        for(Map<String,Object> obj : list){
            Maison m = new Maison();
            //float id = Float.parseFloat(obj.get("id").toString());
            m.setNom(obj.get("nom").toString());
            m.setAdresse(obj.get("adresse").toString());

            m.setType(obj.get("type").toString());

            m.setDescription(obj.get("description").toString());
            data.add(createListEntry(m));

        }
        return  data;
    }
    public ArrayList<Map<String, Object>> getAllMaisons(){
        String url = Statics.BASE_URL+"/jsonMaisonAll";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    data = parseMaison(new String(req.getResponseData()));
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });


        NetworkManager.getInstance().addToQueueAndWait(req);
        return data;
    }

    private Map<String, Object> createListEntry(Maison m) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("Line1",m.getNom());
        entry.put("Line2",m.getAdresse());
        entry.put("Line3",m.getType());
        entry.put("Line4",m.getDescription());

        return entry;
    }

    public static Maison getMaison(){
        return maison;
    }


}


