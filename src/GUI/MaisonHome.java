package GUI;

import Services.SideMenuToolbar;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;

public class MaisonHome extends Form {
    Form current;
    public MaisonHome() {
        current=this;
        Toolbar sideMenu=this.getToolbar();
        setTitle("Maison Hote");
        SideMenuToolbar.createSideMenuToolbar(sideMenu);

        setLayout(BoxLayout.y());
        add(new Label("choose an option"));
        Button btnAddMaison = new Button("Add Maison");
        Button btnListMaison = new Button("List maison");




        btnAddMaison.addActionListener(e-> new AddMaisonForm(current).show() );
        btnListMaison.addActionListener(e-> new ListMaisonForm(current).show() );
        addAll(btnAddMaison,btnListMaison);


    }
}
