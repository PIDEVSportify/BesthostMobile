package Config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class MyConnectionspeaky {
    private HttpURLConnection con;
    public  static MyConnectionspeaky current_connection;

    public MyConnectionspeaky(String Url) throws ProtocolException {
        try {
            URL url = new URL("https://127.0.0.1:8000/"+Url);
            con = (HttpURLConnection) url.openConnection();
            /***System.out.println("Connected Successfully to Db ");***/
        } catch (IOException e) {
            e.printStackTrace();
        }
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        con.setAllowUserInteraction(true);
    }
    public static MyConnectionspeaky getInstance (String Url) throws ProtocolException {
        current_connection=new MyConnectionspeaky(Url);
        return current_connection;
    }
    public HttpURLConnection getConn() {
        return con;
    }
}
