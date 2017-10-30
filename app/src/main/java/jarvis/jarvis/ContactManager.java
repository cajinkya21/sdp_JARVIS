package jarvis.jarvis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_manager);
        Button clr = (Button) findViewById(R.id.clearButton);
        Button sv = (Button) findViewById(R.id.saveButton);
        final DatabaseHandler db = new DatabaseHandler(this);
        final Button show = (Button)findViewById(R.id.buttonShowContacts);
        final Context context = this;

        final EditText nm = (EditText) findViewById(R.id.contactName);
        final EditText mobno = (EditText) findViewById(R.id.contactNumber);
        final EditText email = (EditText) findViewById(R.id.contactEmail);

        clr.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                nm.setText("");
                mobno.setText("");
                email.setText("");
            }
        });

        sv.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Contact ct = new Contact(nm.getText().toString(), mobno.getText().toString(), email.getText().toString());
                db.addContact(ct);
            }
        });
        show.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                DisplayData.data = "contact";
                Intent intent = new Intent(context, DisplayData.class);
                startActivity(intent);
            }
        });
    }
}
