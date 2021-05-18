package Services;

import Entities.Activity;
import Utils.Statics;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.ui.events.ActionListener;

import java.io.CharArrayReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServices {
    ArrayList<Map<String, Object>> data = new ArrayList<>();

    public static ActivityServices instance;
    private ConnectionRequest req;
    public ActivityServices(){
        req = new ConnectionRequest();
    }

    public static ActivityServices getInstance(){
        if (instance == null){
            instance = new ActivityServices();
        }
        return instance;
    }

    public boolean resultOK;



    public ArrayList<Activity> activities;

    /* public ArrayList<Activity> parseActivity(String jsonText) throws IOException {
         activities=new ArrayList<>();
         JSONParser j = new JSONParser();
         Map<String,Object> activityListJson= j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

         List<Map<String,Object>> list = (List<Map<String, Object>>)activityListJson.get("root");
         for (Map<String,Object> obj : list){

             Activity a = new Activity();
             //float id = Float.parseFloat(obj.get("id").toString());
            a.setCategorie(obj.get("Categorie").toString());
            a.setDescription(obj.get("Description").toString());
            //a.setDate(obj.get("Date_val").toString());
            a.setType(obj.get("Type").toString());
            a.setId_act(obj.get("Id_act").toString());
            //a.setId_gerant(obj.get("Id_gerant").toString());



             activities.add(a);

         }

         return activities;
     }
     public ArrayList <Activity> getAllActivities(){
         String url = Statics.BASE_URL+"/activitymob/activity/liste";

         req.setUrl(url);
         req.setPost(false);
         req.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
                 try {
                     activities = parseActivity(new String(req.getResponseData()));
                     req.removeResponseListener(this);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }

             }
         });
         NetworkManager.getInstance().addToQueueAndWait(req);
         return activities;
     }*/
   public ArrayList<Map<String, Object>> parseActivity(String jsonText) throws IOException, ParseException {
       JSONParser j = new JSONParser();
       Map<String,Object> ActivitiesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
       List<Map<String,Object>> list = (List<Map<String,Object>>)ActivitiesListJson.get("root");
       ArrayList<Map<String, Object>> data = new ArrayList<>();
       for(Map<String,Object> obj : list){
           Activity t = new Activity();
           t.setId_act(obj.get("Id_act").toString());
           t.setType(obj.get("Type").toString());
           t.setCategorie(obj.get("Categorie").toString());
           t.setDescription(obj.get("Description").toString());



           data.add(createListEntry(t));

       }
       return  data;
   }
    public ArrayList<Map<String, Object>> getAllActivities(){
        String url = Statics.BASE_URL+"/activitymob/activity/liste";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    data = parseActivity(new String(req.getResponseData()));
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });


        NetworkManager.getInstance().addToQueueAndWait(req);
        return data;
    }

    private Map<String, Object> createListEntry(Activity t) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("Line1",t.getId_act());
        entry.put("Line2",t.getDescription());
        entry.put("Line3",t.getType());
        entry.put("Line4",t.getCategorie());

        return entry;

    }


}
