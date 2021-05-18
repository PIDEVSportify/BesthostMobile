package Gui;

import Services.SideMenuToolbar;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Util;
import com.codename1.ui.*;
import Services.ActivityServices;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.MultiList;

import java.util.ArrayList;
import java.util.Map;

public class AfficherActivity extends Form {
   /* public AfficherActivity() {

        Toolbar sideMenu=this.getToolbar();
        this.setTitle("Activities");
        SideMenuToolbar.createSideMenuToolbar(sideMenu);



        SpanLabel sp = new SpanLabel();
        sp.setText(ActivityServices.getInstance().getAllActivities().toString());
        add(sp);
        show();

    }*/
   public AfficherActivity() {
     //  previous.setTitle("");
      setLayout(BoxLayout.y());

       Toolbar sideMenu=this.getToolbar();
       this.setTitle("Activities");
       SideMenuToolbar.createSideMenuToolbar(sideMenu);
       ArrayList<Map<String, Object>> data = ActivityServices.getInstance().getAllActivities();

       DefaultListModel<Map<String, Object>> model = new DefaultListModel<>(data);
       MultiList ml = new MultiList(model);



       ml.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent evt) {

               Dialog m = new Dialog();

               m.add(String.valueOf(model.getItemAt(model.getSelectedIndex())));

               m.setDisposeWhenPointerOutOfBounds(true);
               m.show();


               //new ListMaisonForm().show();
           }
       });


       Button devGuide = new Button("Show PDF");
       devGuide.addActionListener(e -> {
           FileSystemStorage fs = FileSystemStorage.getInstance();
           String fileName = fs.getAppHomePath() + "pdf-sample.pdf";
           if(!fs.exists(fileName)) {
               Util.downloadUrlToFile("http://www.polyu.edu.hk/iaee/files/pdf-sample.pdf", fileName, true);
           }
           Display.getInstance().execute(fileName);
       });

       add(devGuide);
       add(ml);
       show();
       //getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
   }

}