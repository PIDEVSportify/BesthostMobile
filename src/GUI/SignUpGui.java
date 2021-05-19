package Gui;

import Entities.User;
import Services.UserServices;
import com.codename1.capture.Capture;
import com.codename1.charts.util.ColorUtil;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.FileSystemStorage;

import com.codename1.ui.*;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;

//import java.awt.*;
import java.io.IOException;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;


public class SignUpGui extends Form {
    private String avatar=null;
    public SignUpGui(Form previous)
    {

        this.getToolbar().setTitle("Inscription");
        TextField first_name = new TextField();
        TextField last_name = new TextField();
        TextField email = new TextField();
        TextField password = new TextField(TextField.PASSWORD);
        TextField confirm_password = new TextField(TextField.PASSWORD);
        TextField cin= new TextField(TextField.NUMERIC);
        TextField phone_number = new TextField(TextArea.PHONENUMBER);


        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK,s);
        this.getToolbar().addCommandToRightBar("Retour",icon,(e)->{
            previous.showBack();
        });


        ScaleImageLabel img_viewer = new ScaleImageLabel();

        Button btn_take_photo= new Button("Prendre une photo");
        btn_take_photo.addActionListener(e->{
            this.avatar = Capture.capturePhoto();

            if(this.avatar!=null)
            {
                Image img= null;
                try {
                    img = Image.createImage(FileSystemStorage.getInstance().openInputStream(this.avatar)).scaled(500,440);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                img_viewer.setIcon(img);
                img_viewer.getComponentForm().revalidate();

            }
        });


        this.setLayout(BorderLayout.center());
        Container cont = new Container(BoxLayout.y());
        first_name.setHint("Nom");
        last_name.setHint("Prénom");
        password.setHint("Mot de passe");
        email.setHint("Email");
        cin.setHint("Cin");
        confirm_password.setHint("Confirmer mot de passe");
        phone_number.setHint("Numéro de téléphone");
        Button btn_signup= new Button("S'inscrire");
        btn_signup.addActionListener(e->
        {
            boolean valid = true;
            if (first_name.getText().isEmpty()) {
               Dialog.show("Erreur","Veuiller remplir les champs requis","ok",null);
                valid=false;
            }
           else  if(last_name.getText().isEmpty())
            {
                Dialog.show("Erreur","Veuiller remplir les champs requis","ok",null);
                valid=false;
            }



//            Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
//                Matcher mat = pattern.matcher(email.getText());
//                if (mat.matches()) {
//                    email.getAllStyles().setFgColor(ColorUtil.GREEN);
//                }
//                else {
//
//                    email.getAllStyles().setFgColor(ColorUtil.rgb(255, 0, 0));
//                    valid = false;
//                }

               else  if(cin.getText().isEmpty() || cin.getText().length()!=8)
                {
                    Dialog.show("Erreur","Cin doit contenir 8 chiffres","ok",null);

                    valid = false;

                }
               else if (phone_number.getText().length()<8)
            {
                Dialog.show("Erreur","Entrer un numéro de téléphone valide","ok",null);

                valid = false;

            }


                else if (password.getText().isEmpty()|| password.getText().length()<8)
                {
                    Dialog.show("Erreur","Mot de passe doit contenir 8 caractères au minimum","ok",null);

                    valid = false;


                }
                else if ( password.getText().compareTo(confirm_password.getText())!=0)
            {
                Dialog.show("Erreur","Veuillez taper le même mot de passe","ok",null);
                valid=false;
            }

                if (valid)
                {
                    User user = new User(Integer.parseInt(cin.getText()),first_name.getText(),last_name.getText(),email.getText(),password.getText(),this.avatar);
                    user.setPhone_number(phone_number.getText());
                    if(UserServices.addUser(user))
                        previous.showBack();
                    else
                        {
                            this.revalidate();
                            Dialog.show("Adresse mail déja enregistrée","","ok",null);
                        }
                }



        });


        cont.addAll(first_name,last_name,cin,email,phone_number,password,confirm_password,img_viewer,btn_take_photo,btn_signup);
        this.add(BorderLayout.CENTER,cont);


    }

}
