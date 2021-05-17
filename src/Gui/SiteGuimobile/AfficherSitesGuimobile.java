package Gui.SiteGuimobile;

import Entities.camping;
import Entities.offre;
import Gui.MapGuimobile.AfficherMapGuimobile;
import Services.Camping_c;
import Services.Offre_c;
import Gui.RatingGuimobile.AjouterRatingGuimobile;
import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.wefeel.QRMaker.QRMaker;

import java.io.IOException;
import java.util.ArrayList;

public class AfficherSitesGuimobile {
    public AfficherSitesGuimobile(Form back) throws IOException {
        AjouterRatingGuimobile RatingGuimobile=new AjouterRatingGuimobile();
        int index=1;
        Slider starRank = new Slider();
        Camping_c c=new Camping_c();
        Offre_c c1=new Offre_c();
        ArrayList<camping> getId_site=new ArrayList<>();
        Form hi = new Form("Sites Details",new BoxLayout(BoxLayout.Y_AXIS));
        Button map=new Button("Map");
        map.setUIID("map");
        hi.setUIID("formsite");

        int mm = Display.getInstance().convertToPixels(3);
        for(camping l:c.result()) {
            starRank=RatingGuimobile.createStarRankSlider();
            starRank.setEditable(false);
            if(l.getOffre_id_id()!=0)
                for(offre l1:c1.result()){
                    if(l.getOffre_id_id()==l1.getId())
                        l.setFulloffre("Nombre des places: "+l1.getNombre_places()+"\n"+"Date début: "+l1.getDate_debut()+"\n"+"Date fin: "+l1.getDate_fin()+"\n"+"Prix: "+l1.getPrix()+"Dt");
                }
            else
                l.setFulloffre("Pas d'offre pour le moment");
            Image im = Image.createImage("/"+l.getImage_camping()).fill(mm*10,mm*8);
            if(l.getRating_camping()!=0) {
                starRank.setProgress((int) ((Math.floor(l.getAverage_rating() / l.getRating_camping() * 100) / 100)*2));
                getId_site.add(new camping(l.getId(), l.getFulloffre(), l.getLocalisation_camping(), l.getDescription_camping(), l.getType_camping(), im, l.getOffre_id_id(), Math.floor(l.getAverage_rating() / l.getRating_camping() * 100) / 100));
                hi.add(createListEntry("Camping: "+index++,l.getDescription_camping(), im, QRMaker.QRCode(l.getFulloffre()).fill(500,500),starRank, Math.floor(l.getAverage_rating() / l.getRating_camping() * 100) / 100,l.getRating_camping(),hi,mm,l.getFulloffre(),l.getLocalisation_camping(),l.getType_camping(),l.getId()));
            }
            else {
                starRank.setProgress(0);
                getId_site.add(new camping(l.getId(), l.getFulloffre(), l.getLocalisation_camping(), l.getDescription_camping(), l.getType_camping(), im, l.getOffre_id_id(), 0));
                hi.add(createListEntry("Camping: "+index++,l.getDescription_camping(), im,QRMaker.QRCode(l.getFulloffre()).fill(500,500), starRank,  0,l.getRating_camping(),hi,mm,l.getFulloffre(),l.getLocalisation_camping(),l.getType_camping(),l.getId()));
            }
        }
        map.addActionListener(l -> new AfficherMapGuimobile(hi));
        hi.add(map);
        hi.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e ->back.showBack());
        hi.show();
    }

    private SwipeableContainer createListEntry(String s, String description_camping, Image im, Image im_qr,Slider starRank, double v,int total,Form hi,int mm,String fulloffre,String local,String type,int id) {
        final boolean[] check = {false};
        MultiButton button = new MultiButton(s);
        button.setEmblem(im);
        button.setTextLine2("Appuyez longuement");
        button.setTextLine3("pour plus d'informations");
        button.setUIIDLine2("appuyer");
        button.setUIIDLine3("appuyer1");
        starRank.setBadgeText(v+" ("+total+")");
        button.addLongPressListener(al -> {
            NavigationCommand [] cmds = new NavigationCommand[2];
            cmds[1] = new NavigationCommand("Rate");
            cmds[0] = new NavigationCommand("Close"){
                @Override
                public void actionPerformed(ActionEvent evt) {
                    setDisposesDialog(true);
                    check[0] =true;
                }
            };
            Dialog.show("Details",description_camping+"\nLocalisation: "+local+"\nOffre:\n",cmds,2,im_qr,0);
            if(!check[0])
            cmds[1].setNextForm(new AjouterRatingGuimobile(hi,id));
            check[0] =false;
        });
        return new SwipeableContainer(FlowLayout.encloseCenterMiddle(starRank),
                button);
    }

}
