package Gui;
import Entities.User;
import Services.SideMenuToolbar;
import Services.UserServices;
import com.codename1.ui.*;
import com.codename1.ui.util.Resources;


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
