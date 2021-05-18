package Gui;

import Entities.Maison;
import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;

public class AddMaisonForm extends Form {


    public AddMaisonForm(Form previous ) {
        setTitle("Add new home");
        setLayout(BoxLayout.y());
        Integer id=null;
        TextField tfNom = new TextField("","Nom Maison");
        TextField tfAddrese = new TextField("","Addresse Maison");
        TextField tfDescription = new TextField("","Description Maison");
        TextField tfType = new TextField("","Type Maison");
        TextField tfPrix = new TextField("","Prix Maison");
        TextField tfNb_chambres = new TextField("","Nb chambres Maison");


        Button btnValider = new Button("Add Maison");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                 Maison m = new Maison(tfNom.getText(),tfAddrese.getText(),tfDescription.getText()
                         ,tfType.getText(),Integer.parseInt(tfPrix.getText()),Integer.parseInt(tfNb_chambres.getText())
                 );

                System.out.println(tfNom.getText());
                System.out.println(m);
                // new MaisonService().addMaisons(m);
            }

        });
        addAll(tfNom,tfAddrese,tfDescription,tfType,tfPrix,tfNb_chambres,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,e->previous.showBack());
    }
}
