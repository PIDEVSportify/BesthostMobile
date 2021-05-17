package GUI;
import MaisonEntities.Activity;

import Services.SideMenuToolbar;
import com.codename1.components.SpanLabel;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import Services.ActivityServices;
import com.codename1.ui.Toolbar;

public class AfficherActivity extends Form {
    public AfficherActivity() {

        Toolbar sideMenu=this.getToolbar();
        this.setTitle("Activities");
        SideMenuToolbar.createSideMenuToolbar(sideMenu);



        SpanLabel sp = new SpanLabel();
        sp.setText(ActivityServices.getInstance().getAllActivities().toString());
        add(sp);
        show();

    }
}