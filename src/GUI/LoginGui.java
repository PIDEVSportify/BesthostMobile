package GUI;


import MaisonEntities.User;
import Services.UserServices;
import com.codename1.charts.util.ColorUtil;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.facebook.FaceBookAccess;
import com.codename1.io.ConnectionRequest;
import com.codename1.social.FacebookConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;


import java.io.IOException;

public class LoginGui extends  Form{

    Form current ;
    private Resources theme;

    public LoginGui () {
        theme = UIManager.initFirstTheme("/theme");
        current=this;

        this.getToolbar().setLayout(BorderLayout.center());
//        ((Label)this.getToolbar().getTitleComponent()).setIcon(theme.getImage("besthost.png").scaled(100,80));
//        this.getToolbar().addComponent(BorderLayout.CENTER,new Label("Best Host"));
        Container cont = new Container(BoxLayout.y());
        Label lbl_image=new Label(theme.getImage("besthost.png").scaled(260,200));
        Label lbl_title=new Label("Best Host");
        lbl_title.getAllStyles().setFgColor(ColorUtil.rgb(255,153,153));
        cont.addAll(lbl_image,lbl_title);
        this.getToolbar().addComponent(BorderLayout.CENTER,cont);

////
////            this.getTitleComponent().setIcon(theme.getImage("besthost.png"));

        this.setLayout(BoxLayout.y());
        TextField email = new TextField();
        TextField password  = new TextField(TextField.PASSWORD);


        email.setHint("Email");
        email.setHintIcon(FontImage.createMaterial(FontImage.MATERIAL_PERM_IDENTITY, email.getUnselectedStyle()));
        password.setHint("Mot de passe");
        password.setHintIcon(FontImage.createMaterial(FontImage.MATERIAL_LOCK,password.getUnselectedStyle()));


        Label lbl_error=new Label();
        Button btn_signin = new Button("Se Connecter");

        btn_signin.addActionListener(e->
        {
            User user=UserServices.checkCredentials(email.getText(),password.getText());
            if(user!=null)
            {
                UserServices.user=user;
                new HomeGui().show();

            }
            else
            {
                lbl_error.setText("Veuillez vérifier vos informations");
                lbl_error.getComponentForm().revalidate();

            }




        });

        Button btn_signup= new Button("S'inscrire");
        btn_signup.addActionListener(a->
        {
            new SignUpGui(current).show();
        });



//        Button btn_login_fb = new Button("facebook login");
//        btn_login_fb.addActionListener(e->{
//            //use your own facebook app identifiers here
//            //These are used for the Oauth2 web login process on the Simulator.
//            String clientId = "250238063430959";
//            String redirectURI = "https://5c08e6a1d27a.ngrok.io/connect/facebook/check";
//            String clientSecret = "cdc283b9ca1369c50486ea8c5d57bf7b";
//            Login fb = FacebookConnect.getInstance();
//            fb.setClientId(clientId);
//            fb.setRedirectURI(redirectURI);
//            fb.setClientSecret(clientSecret);
//            fb.setCallback(new LoginCallback() {
//                @Override
//                public void loginSuccessful() {
//                    String token = fb.getAccessToken().getToken();
//                    FaceBookAccess.setToken(token);
//
//                    try {
//                        FaceBookAccess.getInstance().getUser("me",UserServices.user,e->{
//
//                        });
//                    } catch (IOException ioException) {
//                        ioException.printStackTrace();
//                    }
//                    EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(80,80),true);
//                    URLImage avatar = URLImage.createToStorage(placeholder, "avatar.jpg",
//                            "https://graph.facebook.com/v2.11/me/picture?access_token=" + token);
//                    avatar.fetch();
//                    UserServices.user.setAvatar("avatar.jpg");
//
//                    //new HomeGui().show();
//
//                }
//
//                @Override
//                public void loginFailed(String errorMessage) {
//
//                }
//            });
//            //trigger the login if not already logged in
//            if(!fb.isUserLoggedIn()){
//                fb.doLogin();
//            }else{
//                //get the token and now you can query the facebook API
//                String token = fb.getAccessToken().getToken();
//
//            }
//
//        });
//










        this.addAll(lbl_error,email,password,btn_signin,btn_signup);


    }


}