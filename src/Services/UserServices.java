package Services;
import MaisonEntities.User;
import GUI.HomeGui;
import GUI.LoginGui;
import GUI.ProfileGui;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;

public class UserServices {

    public static User user;


    public static User checkCredentials(String email , String password) {
        if(email.trim().isEmpty() || password.trim().isEmpty())
            return null;

        User user = new User();
        ConnectionRequest cnx = new ConnectionRequest()
        {
            @Override
            protected void handleErrorResponseCode(int code, String message) {
                if (code==500)
                {

                }
            }
        };
        //cnx.setUrl("https://5c08e6a1d27a.ngrok.io/api/User/connect");

        cnx.setUrl("http://127.0.0.1:8000/api/User/connect");
        cnx.setPost(true);
        cnx.addArgument("email",email);
        cnx.addArgument("password",password);
        NetworkManager.getInstance().addToQueueAndWait(cnx);

        Map<String, Object> result = null;
        try {
            if(cnx.getResponseCode()==200 ||cnx.getResponseCode()==202)
                result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(cnx.getResponseData()), "UTF-8"));
        } catch (IOException e) {
            e.getMessage();
        }
        if (cnx.getResponseCode()==200 && result!=null) {


            user.setEmail(result.get("email").toString());
            user.setPassword(result.get("password").toString());
            user.setCin( Double.valueOf(result.get("cin").toString()).intValue());
            user.setFirst_name(result.get("first_name").toString());
            user.setLast_name(result.get("last_name").toString());
            if (result.get("avatar")!=null)
                user.setAvatar(result.get("avatar").toString());
            else
                user.setAvatar(null);

            System.out.println(user);
            return user;
        }
        return null;

    }

    public static boolean addUser(User user)
    {

        ConnectionRequest cnx = new ConnectionRequest()
        {
            @Override
            protected void handleErrorResponseCode(int code, String message) {
                if (code==500)
                {

                }
            }
        };
       // cnx.setUrl("https://5c08e6a1d27a.ngrok.io/api/User/new");
        cnx.setUrl("http://127.0.0.1:8000/api/User/new");
        cnx.setPost(true);
        cnx.addArgument("email",user.getEmail());
        cnx.addArgument("password",user.getPassword());
        cnx.addArgument("cin",String.valueOf(user.getCin()));
        cnx.addArgument("first_name",user.getFirst_name());
        cnx.addArgument("last_name",user.getLast_name());
        NetworkManager.getInstance().addToQueueAndWait(cnx);

        if(cnx.getResponseCode()==200|| cnx.getResponseCode()==202)
        {
            UserServices.uploadAvatar(user.getAvatar(), user.getEmail());
            return true;
        }

        return false;
    }



    public static void fetchUser(String email)

    {
        ConnectionRequest cnx = new ConnectionRequest()
        {
            @Override
            protected void handleErrorResponseCode(int code, String message) {
                if (code==500)
                {

                }
            }
        };
        //cnx.setUrl("https://5c08e6a1d27a.ngrok.io/api/User/fetch");
        cnx.setUrl("http://127.0.0.1:8000/api/User/fetch");
        cnx.setPost(true);
        cnx.addArgument("email",email);

        NetworkManager.getInstance().addToQueueAndWait(cnx);


        try {
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(cnx.getResponseData()),"UTF-8"));

            User user = new User();
            user.setEmail(result.get("email").toString());
            user.setFirst_name(result.get("first_name").toString());
            user.setLast_name(result.get("last_name").toString());
            user.setAvatar(result.get("avatar").toString());
            user.setCin( Double.valueOf(result.get("cin").toString()).intValue());
            UserServices.user=user;

        } catch (IOException e) {
            e.printStackTrace();
        };
    }

    public static void  uploadAvatar(String avatar,String email)

    {

        if (avatar!=null)
        {
            String filename = avatar.substring(avatar.lastIndexOf("/"),avatar.lastIndexOf("."));
            String extension = avatar.substring(avatar.lastIndexOf("."));
            String time=new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date());
            String filenameToSend=filename+time+extension;

            MultipartRequest cr =  new MultipartRequest()
            {
                @Override
                protected void handleErrorResponseCode(int code, String message) {
                    if (code==500)
                    {
                    }
                }
            };
            //cr.setUrl("https://5c08e6a1d27a.ngrok.io/api/User/uploadAvatar");
            cr.setUrl("http://127.0.0.1:8000/api/User/uploadAvatart");
            cr.setPost(true);
            cr.addArgumentNoEncoding("email",email);
            String mime="image/jpeg";
            try {
                cr.addData("file", avatar, mime);

            } catch (IOException e) {
                e.printStackTrace();
            }
            cr.setFilename("file", filenameToSend);//any unique name you want

            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            cr.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(cr);
            UserServices.user.setAvatar("uploads/"+filenameToSend);



        }
    }


    public static void updateGeneralSettings(String email,String first_name,String last_name)
    {

        ConnectionRequest cnx = new ConnectionRequest()
        {
            @Override
            protected void handleErrorResponseCode(int code, String message) {
                if (code==500)
                {

                }
            }
        };
        //cnx.setUrl("https://5c08e6a1d27a.ngrok.io/api/User/updateGeneralSettings");
        cnx.setUrl("http://127.0.0.1:8000/api/User/updateGeneralSettings");
        cnx.setPost(true);
        cnx.addArgument("email",email);
        cnx.addArgument("first_name",first_name);
        cnx.addArgument("last_name",last_name);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        cnx.setDisposeOnCompletion(dlg);

        NetworkManager.getInstance().addToQueueAndWait(cnx);

        if(cnx.getResponseCode()==200)
        {
            UserServices.user.setFirst_name(first_name);
            UserServices.user.setLast_name(last_name);
//                UserServices.fetchUser(email);
        }

    }


    public static void  updateSecuritySettings(String email,String password)
    {

        ConnectionRequest cnx= new ConnectionRequest(){
            @Override
            protected void handleErrorResponseCode(int code, String message) {
                if (code==500)
                {

                }
            }
        };
        //cnx.setUrl("https://5c08e6a1d27a.ngrok.io/api/User/updateSecuritySettings");
        cnx.setUrl("http://127.0.0.1:8000/api/User/updateSecuritySettings");

        cnx.setPost(true);
        cnx.addArgument("email",email);
        cnx.addArgument("password",password);

        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        cnx.setDisposeOnCompletion(dlg);

        NetworkManager.getInstance().addToQueueAndWait(cnx);

        if (cnx.getResponseCode()==200)
        {
//                    UserServices.fetchUser(email);
        }

    }

    public static void deleteUser(String email)
    {
        ConnectionRequest cnx = new ConnectionRequest();

       // cnx.setUrl("https://5c08e6a1d27a.ngrok.io/api/User/delete");
        cnx.setUrl("http://127.0.0.1:8000/api/User/delete");
        cnx.addArgument("email",email);
        cnx.setPost(true);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        cnx.setDisposeOnCompletion(dlg);

        NetworkManager.getInstance().addToQueueAndWait(cnx);

        new LoginGui().show();


    }






    public static User getUser()
    {
        return user;
    }

    public UserServices(User u)
    {
        if (user==null)
            user=u;

    }
}
