package jarvis.jarvis;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

    public class OneTimeHelp extends AppCompatActivity implements TextToSpeech.OnInitListener{
    private TextToSpeech tts;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_help);
        tts = new TextToSpeech(this, this);
        Button buttonOne = (Button) findViewById(R.id.nextButton);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, SignUp.class);
                startActivity(intent);
            }
        });
    }
    public void onInit(int status) {
        tts.speak("", TextToSpeech.QUEUE_FLUSH, null);
    }
}
