package Gui.OfferGuimobile;

import Entities.offre;
import Gui.RatingGuimobile.AjouterRatingGuimobile;
import Services.Offre_c;
import com.codename1.components.MultiButton;
import com.codename1.components.ShareButton;
import com.codename1.io.Util;
import com.codename1.share.ShareService;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
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
        Form hi = new Form("Offers Details", new BoxLayout(BoxLayout.Y_AXIS));
        hi.setUIID("formoffer");
        ShareButton shareButton=new ShareButton();
        try {
            for(offre l:c.result()) {
                shareButton=new ShareButton();
                shareButton.setText("Share Offer");
                getId_offer.add(new offre(l.getNombre_places(),l.getDate_debut(),l.getDate_fin(),l.getPrix()));
                hi.add(createListEntry("Offre: "+index++,l.getNombre_places(),l.getDate_debut(),l.getDate_fin(),l.getPrix(),shareButton));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        hi.getToolbar().addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK,e ->back.showBack());
        hi.show();
    }

    private SwipeableContainer createListEntry(String s, int nb,  String d1, String d2,int pr, ShareButton shareButton) {
        MultiButton button = new MultiButton(s);
        button.setTextLine2("Appuyez pour plus d'informations");
        button.setUIIDLine2("appuyer");
        Image fb_logo = null;
        try {
            fb_logo = Image.createImage("/fb.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String Title="Offre:\nNombre des places: "+nb+"\nDate debut: "+d1+"\nDate fin: "+d2+"\nPrix: "+pr+"Dt";
        assert fb_logo != null;
        fb_logo = fb_logo.scaledHeight(85);
        ShareService fb = new ShareService("custom Facebook", fb_logo) {
            @Override
            public void share(String text) {
                sharePost(Title);
            }
            @Override
            public boolean canShareImage() {
                return true;
            }
        };
        shareButton.setTextToShare(Title);
        shareButton.addShareService(fb);
        button.addActionListener(al -> Dialog.show("Details", "Nombre des places: "+nb+"\nDate debut: "+d1+"\nDate fin: "+d2+"\nPrix: "+pr+"Dt", "Ok", null));
        return new SwipeableContainer(FlowLayout.encloseCenterMiddle(shareButton),
                button);
    }
    public void sharePost (String str){
        Display.getInstance().execute("https://www.facebook.com/sharer/sharer.php"
                + "?u="+ Util.encodeUrl("https://127.0.0.1:8000")
                +"&p[images][0]="+Util.encodeUrl("https://upload.wikimedia.org/wikipedia/commons/b/b6/Logo_ESPRIT_-_Tunisie.png")
                + "&quote="+Util.encodeUrl(str));
    }
}
