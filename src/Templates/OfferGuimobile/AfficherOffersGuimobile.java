package Templates.OfferGuimobile;

import Entities.offre;
import Services.Offre_c;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.MultiList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AfficherOffersGuimobile{
    public AfficherOffersGuimobile(Form back) {
        int index=1;
        ArrayList<offre> getId_offer=new ArrayList<>();
        Offre_c c=new Offre_c();
        Form hi = new Form("Offers Details", new BorderLayout());
        hi.getStyle().setBgColor(0xffffff);
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        try {
            for(offre l:c.result()) {
                getId_offer.add(new offre(l.getNombre_places(),l.getDate_debut(),l.getDate_fin(),l.getPrix()));
                data.add(createListEntry("Offre: "+index++));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        DefaultListModel<Map<String, Object>> model = new DefaultListModel<>(data);
        MultiList ml = new MultiList(model);

        ml.addActionListener(l-> Dialog.show("Details", "Nombre des places: "+getId_offer.get(ml.getSelectedIndex()).getNombre_places()+
                "\nDate debut: "+getId_offer.get(ml.getSelectedIndex()).getDate_debut()+
                "\nDate fin: "+getId_offer.get(ml.getSelectedIndex()).getDate_fin()+"\nPrix: "+getId_offer.get(ml.getSelectedIndex()).getPrix()+"Dt", "Done", null));
        hi.add(BorderLayout.CENTER, ml);
        hi.getToolbar().addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK,e ->back.showBack());
        hi.show();
    }
    private Map<String, Object> createListEntry(String offre) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("Line1", offre);
        entry.put("Line2", "Appuyer pour plus d'informations");
        return entry;
    }
}
