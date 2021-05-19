package Gui.OfferGuimobile;

import Entities.offre;
import Services.Offre_c;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.DateSpinner;

import java.io.IOException;
import java.util.Date;

public class AjouterOfferGuimobile extends Form {
    public AjouterOfferGuimobile(Form previous) {
        setTitle("Save your offer");
        setUIID("formtwo");
        Offre_c c=new Offre_c();
        TextField Nb_places=new TextField("","Nombre des places",20, TextArea.NUMERIC);
        TextField Prix_offre=new TextField("","Prix Dt",20,TextArea.NUMERIC);

        Label datedebutoffre=new Label("Date début:");
        datedebutoffre.getAllStyles().setFgColor(0xffffff);
        DateSpinner datedebutspinner=new DateSpinner();

        Label datefinoffre=new Label("Date fin:");
        datefinoffre.getAllStyles().setFgColor(0xffffff);
        DateSpinner datefinspinner=new DateSpinner();

        Container c1=new Container(new FlowLayout(LEFT,TOP));
        Container c2=new Container(BoxLayout.y());

        Button submit=new Button("Save");
        submit.setUIID("Save");
        c1.add(Nb_places);
        c1.getAllStyles().setMargin(Component.BOTTOM,30);
        c2.add(datedebutoffre).add(datedebutspinner).add(datefinoffre).add(datefinspinner).add(Prix_offre).add(submit);
        c2.getAllStyles().setMargin(Component.BOTTOM,30);

        submit.addActionListener(evt -> {
            String datedebut_crypto = datedebutspinner.getCurrentDay()+"-"+datedebutspinner.getCurrentMonth()+"-"+datedebutspinner.getCurrentYear();
            String datefin_crypto = datefinspinner.getCurrentDay()+"-"+datefinspinner.getCurrentMonth()+"-"+datefinspinner.getCurrentYear();
            String checknb_places=Nb_places.getText();
            String checkprix=Prix_offre.getText();
            if(!checknb_places.isEmpty() && !checkprix.isEmpty()) {
                if (compareDates(datedebut_crypto, datefin_crypto))
                    if (checknb_places.matches("\\d+") && checkprix.matches("\\d+")) {
                        int innb_places = Integer.parseInt(checknb_places);
                        int inprix = Integer.parseInt(checkprix);
                        offre offre = new offre(innb_places, datedebut_crypto, datefin_crypto, inprix);
                        try {
                            c.Add(offre);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Dialog.show("Success", "You have successfully added a new offer.", "Ok", null);
                    } else
                        Dialog.show("Warning", "Check if the input values(places,price) is an integer..", "Ok", null);
            }
                    else
                        Dialog.show("Information", "Please fill the required fields (Nombre des places, Prix).", "Ok", null);
        });

        add(c1).add(c2);
        show();
      getToolbar().addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK,e ->previous.showBack());
    }

    public static boolean compareDates(String d1,String d2)
    {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            if(!d1.isEmpty() && !d2.isEmpty()) {
                Date date1 = sdf.parse(d1);
                Date date2 = sdf.parse(d2);
                if (date1.before(date2))
                    return true;
                else
                    Dialog.show("Warning", "Date début is greater than Date fin", "Ok", null);
            }else
                Dialog.show("Information", "Both dates are required", "Ok", null);
        }
        catch(ParseException ex){
            ex.printStackTrace();
        }
        return false;
    }
}
