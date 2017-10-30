package jarvis.jarvis;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.support.v4.content.ContextCompat;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.io.BufferedReader;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import android.widget.Button;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class Hologram extends AppCompatActivity implements TextToSpeech.OnInitListener{
    private TextToSpeech tts;
    private Button btnSpeaker;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    GifDrawable gif;
    GifImageView gif1View;
    GifImageView gif2View;
    GifImageView gif3View;
    GifImageView gif4View;
    DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hologram);
        gif1View = (GifImageView)findViewById(R.id.gif1);
        gif2View = (GifImageView)findViewById(R.id.gif2);
        gif3View = (GifImageView)findViewById(R.id.gif3);
        gif4View = (GifImageView)findViewById(R.id.gif4);
        btnSpeaker = (Button) findViewById(R.id.btnSpeaker);
        tts = new TextToSpeech(this, this);
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
                setMood("speaking");
                Log.e("started", "started");
            }

            @Override
            public void onDone(String s) {
                setMood("ideal");
                Log.e("started", "started");
            }

            @Override
            public void onError(String s) {
                Log.e("errSpk:", "msg: "+s);
            }
        });

        // hide the action bar
        btnSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Your device does not support speech input", Toast.LENGTH_SHORT).show();
        }
    }
    public static JSONObject getJSONObjectFromURL(String url) throws Exception {
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
                speakOut(jo.getString("ans1"));
            }
            catch(Exception ex){
                Log.e("err:", "err" + ex.getMessage());
                speakOut("Looks like I have room for improvement");
            }
            return "Done";
        }
        @Override
        protected void onPostExecute(String result) {
            setMood("ideal");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    final ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.e("Command:", result.get(0));
                    try {
                        setMood("thinking");
                        new GetATAResults().execute(new String[]  {result.get(0)});
                    } catch (Exception e) {
                        Log.e("as:","asd" + e.getMessage());
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }
    public void onInit(int status) {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String greet = "";
        if(timeOfDay >= 0 && timeOfDay < 12)
            greet = "Good Morning" + db.getUserName();
        else if(timeOfDay >= 12 && timeOfDay < 16)
            greet = "Good Afternoon" + db.getUserName();
        else if(timeOfDay >= 16 && timeOfDay < 21)
            greet = "Good Evening" + db.getUserName();
        else if(timeOfDay >= 21 && timeOfDay < 24)
            greet = "Hello!" + db.getUserName();
        speakOut(greet);
        int res = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        //If the app does have this permission, then return true//

        if (res == PackageManager.PERMISSION_GRANTED) {
            Log.e("Perm:", "We have permission");
        }
        else {
            Log.e("Perm:", "We don't have permission, so asking for permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},1);

        }
    }
    void speakOut(String spk) {

        tts.speak(spk, TextToSpeech.QUEUE_FLUSH, null);

    }
    void setMood(String mood){
        try {
            if (mood.equals("speaking")) {
                gif = new GifDrawable(getResources(), R.drawable.speaking);
                gif1View.setImageDrawable(gif);
                gif2View.setImageDrawable(gif);
                gif3View.setImageDrawable(gif);
                gif4View.setImageDrawable(gif);
            } else if (mood.equals("thinking")) {
                gif = new GifDrawable(getResources(), R.drawable.thinking);
                gif1View.setImageDrawable(gif);
                gif2View.setImageDrawable(gif);
                gif3View.setImageDrawable(gif);
                gif4View.setImageDrawable(gif);
            } else if (mood.equals("ideal")) {
                gif = new GifDrawable(getResources(), R.drawable.ideal);
                gif1View.setImageDrawable(gif);
                gif2View.setImageDrawable(gif);
                gif3View.setImageDrawable(gif);
                gif4View.setImageDrawable(gif);
            }
        }
        catch(Exception ex){
            Log.e("err: ", "error: " + ex.getMessage());
        }
    }
}