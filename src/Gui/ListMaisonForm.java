package Gui;
import Entities.Maison;
import Services.MaisonService;
import Services.SideMenuToolbar;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;

public class ListMaisonForm extends Form {
    private Maison maison = MaisonService.maison;
    private Maison m = new Maison();

    public ListMaisonForm() {
        Toolbar sideMenu=this.getToolbar();

        SideMenuToolbar.createSideMenuToolbar(sideMenu);

        setTitle("Maison");

       /* SpanLabel sp = new SpanLabel();
        sp.setText(maison.getNom());
       */


        SpanLabel sp = new SpanLabel();

        add(sp);
        show();



    }
}
