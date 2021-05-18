package Gui;


import Entities.User;
import Services.SideMenuToolbar;
import Services.UserServices;
import com.codename1.capture.Capture;
import com.codename1.charts.util.ColorUtil;
import com.codename1.components.Accordion;
import com.codename1.components.ImageViewer;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;


import java.io.IOException;

public class ProfileGui extends Form {
    private Resources  theme = UIManager.initFirstTheme("/theme");
    private User user = UserServices.user;

    public ProfileGui ()
    {


        Toolbar sideMenu=this.getToolbar();
        this.setTitle("Profil");
        SideMenuToolbar.createSideMenuToolbar(sideMenu);

        Accordion acc_profile = new Accordion();

        Label lbl_image = null;


        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(20,20, 0xffff0000),false);
        URLImage img = URLImage.createToStorage(placeholder,user.getAvatar(),"http://127.0.0.1:8000/"+UserServices.user.getAvatar());
        ImageViewer profile_img = new ImageViewer(img);



        Container cont_generalSettings = new Container(BoxLayout.y());
        TextField txt_first_name= new TextField(user.getFirst_name());
        TextField txt_last_name= new TextField(user.getLast_name());
        txt_first_name.setHint("Nom");
        txt_last_name.setHint("prénom");
        Container cont_generalSettings_center= new Container(new BorderLayout());
        Label lbl_error_generalSettings = new Label("");

        Button btn_generalSettings = new Button("Mettre à jour");
        btn_generalSettings.addActionListener(e->
        {
            boolean valid=true;
            if (txt_first_name.getText().isEmpty()||txt_last_name.getText().isEmpty())
            {
                valid= false;

            }
            UserServices.updateGeneralSettings(user.getEmail(),txt_first_name.getText(),txt_last_name.getText());
            new ProfileGui().show();

        });
        cont_generalSettings_center.add(BorderLayout.CENTER,lbl_error_generalSettings);
        cont_generalSettings.addAll(cont_generalSettings_center,txt_first_name,txt_last_name,btn_generalSettings);

        Label lbl_generalSettings= new Label("Paramètres généraux");
        lbl_generalSettings.getAllStyles().setFgColor(ColorUtil.rgb(255,153,153));

        acc_profile.addContent(lbl_generalSettings,cont_generalSettings);

        Container cont_securitySettings = new Container(BoxLayout.y());
        Container cont_error_password_center= new Container(new BorderLayout());
        TextField txt_password = new TextField(TextArea.PASSWORD);
        TextArea txt_confirm_password = new TextField(TextArea.PASSWORD);
        txt_password.setHint("Nouveau mot de passe");
        txt_confirm_password.setHint("Confirmer mot de passe");

        Label lbl_error_password= new Label("");
        Button btn_securitySettings = new Button("Mettre à jour");
        btn_securitySettings.addActionListener(e->
        {
            boolean valid = true;
            if (txt_password.getText().isEmpty()  ||  txt_password.getText().length()<8)
            {
                lbl_error_password.setText("Mot de passe doit contenir au minimum 8 caractères.");
                valid=false;
            }
            else if (!txt_password.getText().equals(txt_confirm_password.getText()))
            {
                lbl_error_password.setText("Veuillez taper le même mot de passe");
                valid=false;
            }

            else
                lbl_error_password.setText("");
            if (valid)
            {
                UserServices.updateSecuritySettings(user.getEmail(),txt_password.getText());
                lbl_error_password.setText("Mot de passe mis à jour");
            }

            lbl_error_password.getComponentForm().revalidate();

        });

        cont_error_password_center.add(BorderLayout.CENTER,lbl_error_password);
        cont_securitySettings.addAll(cont_error_password_center,txt_password,txt_confirm_password,btn_securitySettings);

//        acc_profile.getAllStyles().setFgColor(ColorUtil.rgb(200,15,60),true);

        Label lbl_securitySettings =new Label("Paramètres de sécurité");
        lbl_securitySettings.getAllStyles().setFgColor(ColorUtil.rgb(255,153,153));
        acc_profile.addContent(lbl_securitySettings,cont_securitySettings);

        Button btn_upload_avatar= new Button("Choisir une photo");
        btn_upload_avatar.addActionListener(e->
        {
            Display.getInstance().openGallery( evt  -> {
                if(evt!=null && evt.getSource()!=null)
                {
                    String filepath=(String)evt.getSource();
                    System.out.println(filepath);
                    Image img1 = null;
                    try {
                        img1 = Image.createImage(FileSystemStorage.getInstance().openInputStream(filepath)).scaled(400,280);
                        profile_img.setImage(img1);
                        if(Dialog.show("Confirmer","","Confirmer","Annuler"))
                        {
                            UserServices.uploadAvatar(filepath,user.getEmail());
                            new ProfileGui().show();

                        }
                        else
                        {
                            img1 = Image.createImage(FileSystemStorage.getInstance().openInputStream(user.getAvatar())).scaled(400,280);
                            profile_img.setImage(img1);

                        }


                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            },Display.GALLERY_IMAGE);
        });

        Button btn_take_photo = new Button ("Prendre une photo");
        btn_take_photo.addActionListener(e->
        {
            String  photo  = Capture.capturePhoto();
            System.out.println(photo);
            if(photo!=null)
            {
                Image img2= null;
                try {
                    img2 = Image.createImage(FileSystemStorage.getInstance().openInputStream(photo)).scaled(400,280);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                profile_img.setImage(img2);

                if(Dialog.show("Confirmer","","Confirmer","Annuler"))
                {
                    UserServices.uploadAvatar(photo,user.getEmail());
                    new ProfileGui().show();

                }
                else
                {
                    try {
                        img2 = Image.createImage(FileSystemStorage.getInstance().openInputStream(user.getAvatar())).scaled(400,280);
                        profile_img.setImage(img2);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }
            }

        });

        Label lbl_avatar=new Label("Avatar");
        lbl_avatar.getAllStyles().setFgColor(ColorUtil.rgb(255,153,153));
        Container cont_avatar = new Container(BoxLayout.y());
        cont_avatar.addAll(btn_upload_avatar,btn_take_photo);
        acc_profile.addContent(lbl_avatar,cont_avatar);



        Button btn_delete_account= new Button("Supprimer compte");
        btn_delete_account.getAllStyles().setFgColor(ColorUtil.rgb(255,0,0));
        btn_delete_account.addActionListener(e->
        {
            if (Dialog.show("Confirmer","Vous êtes sur le point de supprimer votre compte. Cette action est irréversible. Continuer? ","Confirmer","Annuler"))
            {
                UserServices.deleteUser(user.getEmail());
                new LoginGui().show();
            }
        });


        Label lbl_deleteAccount =new Label("Supprimer Compte");
        lbl_deleteAccount.getAllStyles().setFgColor(ColorUtil.rgb(255,153,153));
        acc_profile.addContent(lbl_deleteAccount,btn_delete_account);

        this.setLayout(new BorderLayout());
        this.add(BorderLayout.NORTH,profile_img);
        this.add(BorderLayout.CENTER,acc_profile);

    }



}