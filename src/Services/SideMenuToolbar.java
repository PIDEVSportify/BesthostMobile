package Services;

import Entities.User;
import Gui.HomeGui;
import Gui.LoginGui;
import Gui.ProfileGui;
import com.codename1.charts.util.ColorUtil;
import com.codename1.components.ImageViewer;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.*;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;


import java.io.IOException;

public class SideMenuToolbar {

    public static void createSideMenuToolbar(Toolbar toolbar) {
        Label lbl_username = new Label("  "+UserServices.user.getFirst_name() + " " + UserServices.user.getLast_name());
        lbl_username.getAllStyles().setFgColor(ColorUtil.rgb(255,153,153));
        Container cont_top= new Container(BoxLayout.y());


        if(UserServices.user.getAvatar()!=null) {
            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(400, 380, 0xffff0000), false);
            URLImage img = URLImage.createToStorage(placeholder, UserServices.user.getAvatar(), "https://127.0.0.1:8000/" + UserServices.user.getAvatar());

            ImageViewer img_profile = new ImageViewer(img);

        cont_top.addAll(img_profile,lbl_username);
        }
        else
            cont_top.add(lbl_username);
        Container cont_center = new Container(BorderLayout.center());
        cont_center.addComponent(BorderLayout.CENTER,cont_top);
        Container cont = BorderLayout.south(cont_center);
        toolbar.addComponentToSideMenu(cont);
        toolbar.addMaterialCommandToSideMenu("Home", FontImage.MATERIAL_HOME, e -> {
            new HomeGui().show();
        });
        toolbar.addMaterialCommandToSideMenu("Profil", FontImage.MATERIAL_PERSON, e -> {
            new ProfileGui().show();

        });
        toolbar.addMaterialCommandToSideMenu("Se déconnecter", FontImage.MATERIAL_LOCK, e -> {
            if (Dialog.show("Déconnexion","Vous voulez vraiment vous déconnecter ?","Oui","Non"))
            {
                UserServices.user=null;
                new LoginGui().show();
            }

        });

    }

}
