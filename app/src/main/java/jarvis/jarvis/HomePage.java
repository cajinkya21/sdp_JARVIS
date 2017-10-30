package jarvis.jarvis;

import android.content.Context;
import android.speech.tts.TextToSpeech;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class HomePage extends AppCompatActivity implements TextToSpeech.OnInitListener{
    private TextToSpeech tts;
    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    Managers mngr;
    public static int flag = 0;
    public static String ans1, ans2, ans3, que;
    DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        txtSpeechInput = (TextView) findViewById(R.id.listenedText);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        mngr = new Managers();

        tts = new TextToSpeech(this, this);

        // hide the action bar
        // getActionBar().hide();

        btnSpeak.setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                    mngr.analyse(result.get(0), this);
                    /*
                     * Here, we get the result of the speech input.
                     * Now create a class with methods accordingly and then make the application do the required actions.
                     * 1)Chat with you
                     * 2)Manage Contacts
                     * 3)Manage Passwords
                     * 4)Manage Study
                     * 5)Introduce Someone
                     */
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
    }
    void speakOut(String spk){
        tts.speak(spk, TextToSpeech.QUEUE_FLUSH, null);
    }

}

class Managers{
    private  HomePage pag;
    private Context context;
    void analyse(String cmd, HomePage p) {
        pag = p;
        context = pag;
        if(cmd.contains("manage my contact"))
            contactManager();
        //search for this question into the database
        if(HomePage.flag == 1){
            HomePage.ans1 = cmd;
            HomePage.flag = 2;
            return;
        }
        else if(HomePage.flag == 2){
            HomePage.ans2 = cmd;
            HomePage.flag = 3;
            return;
        }
        else if(HomePage.flag == 3){
            HomePage.ans3 = cmd;
            DatabaseHandler db = new DatabaseHandler(p);
            db.learnIt(HomePage.que, HomePage.ans1,
                    HomePage.ans2, HomePage.ans3);
            p.speakOut("Wonderful Thanks!");
            HomePage.flag = 0;
            return;
        }
        DatabaseHandler db = new DatabaseHandler(p);
        Results R = new Results();
        try {
            R = db.searchQuery(cmd);
        }
        catch(Exception ex){
            Log.e("err: ", ex.getMessage() + " err");
        }
        if(R.status == true){
            Random r = new Random();
            int num = r.nextInt(3) + 1;
            if(num == 1)
                p.speakOut(R.ans1);
            else if(num == 2)
                p.speakOut(R.ans2);
            else if(num == 3)
                p.speakOut(R.ans3);
        }
        else {
            HomePage.que = cmd;
            p.speakOut("I couldn't understand this, can you suggest 3 good replies?");
            HomePage.flag = 1;
        }
    }


    private void contactManager(){
        Intent intent = new Intent(context, ContactManager.class);
        pag.startActivity(intent);
    }

    private void passwordManager(){
        Intent intent = new Intent(context, PasswordManager.class);
        pag.startActivity(intent);
    }

    private void studyManager(){
        Intent intent = new Intent(context, StudyManager.class);
        pag.startActivity(intent);
    }
    private void introductionManager(){

    }

}

