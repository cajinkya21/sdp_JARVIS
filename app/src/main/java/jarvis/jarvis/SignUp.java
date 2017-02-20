package jarvis.jarvis;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUp extends AppCompatActivity implements TextToSpeech.OnInitListener{
    private TextToSpeech tts;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        tts = new TextToSpeech(this, this);
        Button buttonOne = (Button) findViewById(R.id.signupButton);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                /*
                 * Insert username into database
                 * unset the isFirstTime flag
                 * Navigate to Login
                 */
                Intent intent = new Intent(context, LoginPage.class);
                startActivity(intent);
            }
        });
    }
    public void onInit(int status) {
        /*
         * Retrieve isFirstTime flag from the DB
         * If yes, then do following.
         * else directly go to the Login page
         */
        tts.speak("I'm JARVIS, what should I call you?", TextToSpeech.QUEUE_FLUSH, null);
    }
}
