package GUI;
import Services.MaisonService;
import com.codename1.components.SpanLabel;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;

public class ListMaisonForm extends Form {

    public ListMaisonForm(Form previous) {
        setTitle("List Maisons");


        SpanLabel sp = new SpanLabel();
        sp.setText(MaisonService.getInstance().getAllMaisons().toString());
        add(sp);
        show();
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,e->previous.showBack());


    }
}
