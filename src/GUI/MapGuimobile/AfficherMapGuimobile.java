package GUI.MapGuimobile;

import Entities.camping;
import Entities.offre;
import Services.Camping_c;
import Services.Offre_c;
import com.codename1.googlemaps.MapContainer;
import com.codename1.maps.Coord;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Style;

import java.io.IOException;
import java.util.ArrayList;

public class AfficherMapGuimobile {
    public AfficherMapGuimobile(Form back){
        Form hi = new Form("Map");
        hi.setLayout(new BorderLayout());
        if(BrowserComponent.isNativeBrowserSupported()) {
            final MapContainer cnt = new MapContainer("AIzaSyAObzCbk0hwnk8LbUcTeFguDk5ZDr36VS4");
            hi.addComponent(BorderLayout.CENTER, cnt);
            cnt.setCameraPosition(new Coord(36.62659598298575, 10.007110389971563));
            Style s = new Style();
            s.setFgColor(0xff0000);
            s.setBgTransparency(0);
            Camping_c c = new Camping_c();
            Offre_c c1 = new Offre_c();
            int mm = Display.getInstance().convertToPixels(3);
            try {
                for (camping l : c.result()) {
                    if (l.getOffre_id_id() != 0)
                        for (offre l1 : c1.result()) {
                            if (l.getOffre_id_id() == l1.getId())
                                l.setFulloffre("Nombre des places: " + l1.getNombre_places() + "\n" + "Date début: " + l1.getDate_debut() + "\n" + "Date fin: " + l1.getDate_fin() + "\n" + "Prix: " + l1.getPrix() + "Dt");
                        }
                    else
                        l.setFulloffre("Pas d'offre pour le moment");
                    Image im = Image.createImage("/" + l.getImage_camping()).fill(120, 100);
                    cnt.addMarker(EncodedImage.createFromImage(im, false), new Coord(Double.parseDouble(l.getLatitude_camping()), Double.parseDouble(l.getLongitude_camping())), "", "", new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            String msg=l.getDescription_camping()+"\nLocalisation: "+l.getLocalisation_camping()+"\nOffre: "+l.getFulloffre();
                            Dialog.show("Marker", msg+"\nPour plus d'information n'hésitez pas à me contacter sur Facebook https://www.facebook.com/selim.benaich/\nTel:+21693165012 ", "Ok", null);
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        hi.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e ->back.showBack());
        hi.show();
    }
}
