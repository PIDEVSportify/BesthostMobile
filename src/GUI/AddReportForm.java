package GUI;

import MaisonEntities.Report;
import Services.MaisonService;
import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;

public class AddReportForm extends Form {


    public AddReportForm(Form previous ) {
        setTitle("Add new Report");
        setLayout(BoxLayout.y());
        Integer id=null;
        TextField Reason = new TextField("","enter your reason to report");

        TextField UserId = new TextField("","enter your reason to UserId");

        Button btnValider = new Button("Send Report");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                 Report m = new Maison(Reason.getText(),UserId.getText(),tfDescription.getText()
                         ,tfType.getText(),Integer.parseInt(tfPrix.getText()),Integer.parseInt(tfNb_chambres.getText())
                 );

                System.out.println(tfNom.getText());
                System.out.println(m);
                 new MaisonService().addMaisons(m);
            }

        });
        addAll(tfNom,tfAddrese,tfDescription,tfType,tfPrix,tfNb_chambres,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,e->previous.showBack());
    }
}
