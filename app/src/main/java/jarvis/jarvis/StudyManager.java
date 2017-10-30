package jarvis.jarvis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class StudyManager extends AppCompatActivity {
    final Context context = this;
    DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_manager);
        final Button clr = (Button)findViewById(R.id.clearButton);
        final Button sav = (Button)findViewById(R.id.saveButton);
        final Button show = (Button)findViewById(R.id.showDataButton);
        final TextView subName = (TextView) findViewById(R.id.subjectEditText);
        final TextView topicName = (TextView)findViewById(R.id.topicEditText);
        final TextView description  = (TextView)findViewById(R.id.noteEditText);
        clr.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                subName.setText("");
                topicName.setText("");
                description.setText("");
            }
        });
        sav.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Study stdy = new Study(subName.getText().toString(), topicName.getText().toString(), description.getText().toString());
                db.addStudy(stdy);
            }
        });

        show.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }
}