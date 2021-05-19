package GUI;


import Entities.User;
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


        //UserServices.fetchUser("sumpark@hotmail.fr");
        // this.getToolbar().setLayout(BorderLayout.center());
//        ((Label)this.getToolbar().getTitleComponent()).setIcon(theme.getImage("besthost.png").scaled(100,80));
//        this.getToolbar().addComponent(BorderLayout.CENTER,new Label("Best Host"));
        Image besthost = null;
        try {
            besthost=Image.createImage("/besthost.png").scaled(740,580);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ScaleImageLabel lbl_image = new ScaleImageLabel(besthost);//        Label lbl_image=new Label(theme.getImage("besthost.png"));

        //this.getToolbar().addComponent(BorderLayout.CENTER,cont);

////
////            this.getTitleComponent().setIcon(theme.getImage("besthost.png"));

        this.setLayout(BorderLayout.center());
        TextField email = new TextField();

        TextField password  = new TextField(TextField.PASSWORD);



        email.setHint("Email");
        email.setHintIcon(FontImage.createMaterial(FontImage.MATERIAL_PERM_IDENTITY, email.getUnselectedStyle()));
        password.setHint("Mot de passe");
        password.setHintIcon(FontImage.createMaterial(FontImage.MATERIAL_LOCK,password.getUnselectedStyle()));



        Button btn_signin = new Button("Se Connecter");

        btn_signin.addActionListener(e->
        {
            User user=UserServices.checkCredentials(email.getText(),password.getText());
            if(user!=null)
            {
                UserServices.user=user;
                new Gui.HomeGui().show();

            }
            else
            {

                Dialog.show("Veuillez vérifier vos informations","","ok",null);
                this.revalidate();
            }




        });

        Button btn_signup= new Button("S'inscrire");
        btn_signup.addActionListener(a->
        {
            new Gui.SignUpGui(current).show();
        });


        Button btn_login_fb = new Button("facebook login");
        btn_login_fb.addActionListener(e->{
            //use your own facebook app identifiers here
            //These are used for the Oauth2 web login process on the Simulator.
            String clientId = "250238063430959";
            String redirectURI = "https://127.0.0.1:8000/connect/facebook/check";
            String clientSecret = "cdc283b9ca1369c50486ea8c5d57bf7b";
            Login fb = FacebookConnect.getInstance();

            fb.setClientId(clientId);
            fb.setRedirectURI(redirectURI);
            fb.setClientSecret(clientSecret);
            fb.setCallback(new LoginCallback() {
                @Override
                public void loginSuccessful() {
                    String token = fb.getAccessToken().getToken();
                    FaceBookAccess.setToken(token);

                    try {
                        User user = new User();

                        user.setFirst_name(FaceBookAccess.getInstance().getUser("me").getFirst_name());
                        user.setLast_name(FaceBookAccess.getInstance().getUser("me").getLast_name());
                        UserServices.user=user;

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(80,80),true);
                    URLImage avatar = URLImage.createToStorage(placeholder, "avatar.jpg",
                            "https://graph.facebook.com/v2.11/me/picture?access_token=" + token);
                    avatar.fetch();


                    new Gui.HomeGui().show();

                }

                @Override
                public void loginFailed(String errorMessage) {

                }
            });
            //trigger the login if not already logged in
            if(!fb.isUserLoggedIn()){
                fb.doLogin();
            }else{
                //get the token and now you can query the facebook API
                String token = fb.getAccessToken().getToken();

            }

        });


        Container cont_login = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container cont_top  =new Container(BorderLayout.center());
        cont_top.add(BorderLayout.CENTER,BoxLayout.encloseY(lbl_image));
        cont_login.add(cont_top);
        cont_login.addAll(email,password,btn_signin,btn_signup,btn_login_fb);

        this.add(BorderLayout.CENTER,cont_login);


    }


}