package jarvis.jarvis;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by tushar on 29/10/17.
 */
public class HologramTest extends AppCompatActivity {
    final Context context = this;
    public JSONObject getJSONObjectFromURL(String url) throws Exception {
        HttpURLConnection con = null;
        try {
            URL u = new URL(url);
            con = (HttpURLConnection) u.openConnection();

            con.connect();


            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            String jsonString = sb.toString();
            System.out.println("JSON: " + jsonString);

            return new JSONObject(jsonString);
        } catch (MalformedURLException ex) {
            Log.e("as:","asd" + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            Log.e("as:","asd" + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    Log.e("as:","asd" + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
        return null;

    }
    private class GetATAResults extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                JSONObject jo = getJSONObjectFromURL("http://www.veronica.heliohost.org/?username=tushar&upass=08db7f9afe43aea7f049310ba4013158&qtype=1&query=" + urls[0]);
                Log.e("JSON Retrieved: ", jo.toString() );
                return jo.getString("ans1");
            }
            catch(Exception ex){
                Log.e("err:", "err" + ex.getMessage());
                return "Error";
            }
        }
        @Override
        protected void onPostExecute(String result) {

        }
    }
    @Test
    public void isSpeakerWorking() throws Exception{
        assertEquals(8, 4+4);
    }
    @Test
    public void checkPermissions() throws Exception{
        int res = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET);
        assertEquals(PackageManager.PERMISSION_GRANTED, res);
    }
    @Test
    public void checkATA() throws Exception{
        assertEquals("Hi", new GetATAResults().execute(Arrays.toString(new String[]{"hello"})));
    }

}