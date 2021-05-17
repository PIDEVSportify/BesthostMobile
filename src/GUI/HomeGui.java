package GUI;
import MaisonEntities.User;
import Services.SideMenuToolbar;
import Services.UserServices;
import com.codename1.components.Accordion;
import com.codename1.ui.*;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

import java.io.IOException;


public class HomeGui extends Form {
    public static User user;
    private Resources theme;
    Form current;

    public HomeGui ()
    {
        current = this;
        this.user= UserServices.getUser();

        this.setTitle("Accueil");

        Toolbar sideMenu=this.getToolbar();
        SideMenuToolbar.createSideMenuToolbar(sideMenu);





    }



}
