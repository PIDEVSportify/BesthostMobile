package Gui.DashboardSpeaky;

import Gui.OfferGuimobile.AfficherOffersGuimobile;
import Gui.OfferGuimobile.AjouterOfferGuimobile;
import Gui.SiteGuimobile.AfficherSitesGuimobile;
import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

import java.io.IOException;

public class Dashboard {
    public Dashboard(Form back){
        Form hi = new Form("Camping",BoxLayout.y());
        hi.setUIID("form");
        Label label1_l=new Label("Life is best");
        Label label2_l=new Label("when you're");
        Label label3_l=new Label("camping");
        label1_l.setUIID("label1l");
        label2_l.setUIID("label2l");
        label3_l.setUIID("label3l");
        Button Ajouteroffer=new Button("Ajouter votre offre");
        Ajouteroffer.setUIID("Ajouteroffer");
        Button displayoffers=new Button("Informez-vous sur les offres disponibles");
        displayoffers.animate();
        displayoffers.setUIID("displayoffers");
        Button displaysites=new Button("Informez-vous sur les sites camping disponibles");
        displaysites.animate();
        displaysites.setUIID("displaysites");
        Ajouteroffer.addActionListener(e -> new AjouterOfferGuimobile(hi).show());
        displayoffers.addActionListener(e -> new AfficherOffersGuimobile(hi));
        displaysites.addActionListener(e -> {
            try {
                new AfficherSitesGuimobile(hi);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        hi.addAll(label1_l,label2_l,label3_l,Ajouteroffer,displayoffers,displaysites);
        hi.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e ->back.showBack());
        hi.show();
    }
}
