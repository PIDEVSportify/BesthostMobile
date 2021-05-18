package Gui;

import Entities.User;
import Services.UserServices;
import com.codename1.capture.Capture;
import com.codename1.charts.util.ColorUtil;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.*;
import com.codename1.ui.Button;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
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
        TextField cin= new TextField(TextField.NUMERIC);
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK,s);
        this.getToolbar().addCommandToRightBar("Retour",icon,(e)->{
            previous.showBack();
        });

        Label lbl_error = new Label("");
        ScaleImageLabel img_viewer = new ScaleImageLabel();

        Button btn_take_photo= new Button("Prendre une photo");
        btn_take_photo.addActionListener(e->{
            this.avatar = Capture.capturePhoto();

            if(this.avatar!=null)
            {
                Image img= null;
                try {
                    img = Image.createImage(FileSystemStorage.getInstance().openInputStream(this.avatar)).scaled(400,440);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                img_viewer.setIcon(img);
                img_viewer.getComponentForm().revalidate();

            }
        });


        this.setLayout(BoxLayout.y());
        first_name.setHint("Nom");
        last_name.setHint("Prénom");
        password.setHint("Mot de passe");
        email.setHint("Email");
        cin.setHint("Cin");
        Button btn_signup= new Button("S'inscrire");
        btn_signup.addActionListener(e->
        {
            boolean valid = true;
            if (first_name.getText().isEmpty()) {
                first_name.getAllStyles().setFgColor(ColorUtil.rgb(255, 0, 0));
                valid=false;
            }
            else
                first_name.getAllStyles().setFgColor(ColorUtil.GREEN);


            if(last_name.getText().isEmpty())
            {
                last_name.getAllStyles().setFgColor(ColorUtil.rgb(255, 0, 0));
                valid=false;
            }
            else
                last_name.getAllStyles().setFgColor(ColorUtil.GREEN);


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

            if(cin.getText().isEmpty() || cin.getText().length()!=8)
            {
                cin.getAllStyles().setFgColor(ColorUtil.rgb(255, 0, 0));
                valid = false;

            }
            else
                cin.getAllStyles().setFgColor(ColorUtil.GREEN);

            if (password.getText().isEmpty()|| password.getText().length()<8)
            {
                password.getAllStyles().setFgColor(ColorUtil.rgb(255, 0, 0));
                valid = false;

                lbl_error.setText("Mot de passe doit contenir 8 caractères au minimum");
                lbl_error.getAllStyles().setFgColor(ColorUtil.red(255));
                lbl_error.getComponentForm().revalidate();

            }
            else
                password.getAllStyles().setFgColor(ColorUtil.GREEN);




            if (valid)
            {

                lbl_error.setText("");
                User user = new User(Integer.parseInt(cin.getText()),first_name.getText(),last_name.getText(),email.getText(),password.getText(),this.avatar);
                if(UserServices.addUser(user))
                    previous.showBack();
                else
                {
                    lbl_error.setText("Adresse mail déja enregistrée");
                    lbl_error.getAllStyles().setFgColor(ColorUtil.red(255));
                    lbl_error.getComponentForm().revalidate();

                }
            }

            first_name.getComponentForm().revalidate();
            last_name.getComponentForm().revalidate();
            cin.getComponentForm().revalidate();
            email.getComponentForm().revalidate();
            password.getComponentForm().revalidate();

        });


        this.addAll(lbl_error,first_name,last_name,cin,email,password,img_viewer,btn_take_photo,btn_signup);

    }

}
