package jarvis.jarvis;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity implements TextToSpeech.OnInitListener{
    private TextToSpeech tts;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        tts = new TextToSpeech(this, this);
        Button buttonOne = (Button) findViewById(R.id.loginButton);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int x = 1 + 5;
                String s = Integer.valueOf(x).toString();
                TextView p = (TextView)findViewById(R.id.passwordTextBox);
                if(p.getText().toString().equals(s)){
                    Log.e("TTS", "logged in");
                }
            }
        });
    }
    public void onInit(int status) {
        /*
         * Retrieve isFirstTime flag from the DB
         * If yes, then do following.
         * else directly go to the Login page
         */
        tts.speak("Please fill in the password.", TextToSpeech.QUEUE_FLUSH, null);
    }
}
