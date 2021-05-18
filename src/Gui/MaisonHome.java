package Gui;

import Entities.Maison;
import Services.MaisonService;
import Services.SideMenuToolbar;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Util;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.MultiList;

import java.util.ArrayList;
import java.util.Map;

public class MaisonHome extends Form {
    Form current;


    public MaisonHome() {
        current=this;
        Toolbar sideMenu=this.getToolbar();

        SideMenuToolbar.createSideMenuToolbar(sideMenu);

        setLayout(BoxLayout.y());
        /*add(new Label("choose an option"));
        Button btnAddMaison = new Button("Add Maison");
        Button btnListMaison = new Button("List maison");




        btnAddMaison.addActionListener(e-> new AddMaisonForm(current).show() );
        btnListMaison.addActionListener(e-> new ListMaisonForm(current).show() );
        addAll(btnAddMaison,btnListMaison);
*/

        setTitle("List Maisons");




       /* SpanLabel sp = new SpanLabel();
        sp.setText(MaisonService.getInstance().getAllMaisons().toString());

        add(sp);
        show();*/
        //getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e->previous.showBack());

        ArrayList<Map<String, Object>> data = MaisonService.getInstance().getAllMaisons();


        DefaultListModel<Map<String, Object>> model = new DefaultListModel<>(data);

        MultiList ml = new MultiList(model);

        Button devGuide = new Button("Show PDF");
        devGuide.addActionListener(e -> {
            FileSystemStorage fs = FileSystemStorage.getInstance();
            String fileName = fs.getAppHomePath() + "pdf-sample.pdf";
            if(!fs.exists(fileName)) {
                Util.downloadUrlToFile("http://www.polyu.edu.hk/iaee/files/pdf-sample.pdf", fileName, true);
            }
            Display.getInstance().execute(fileName);
        });


        ml.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                Dialog m = new Dialog("maison");


                m.add(String.valueOf(model.getItemAt(model.getSelectedIndex())));

                m.setDisposeWhenPointerOutOfBounds(true);

                m.show();


                //new ListMaisonForm().show();
            }
        });



        add(devGuide);
        add(ml);
        show();

    }
}
