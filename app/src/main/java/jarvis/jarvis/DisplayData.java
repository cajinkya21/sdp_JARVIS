package jarvis.jarvis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DisplayData extends AppCompatActivity {
    ListView lv;
    TextView tv;
    public static String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        lv = (ListView) findViewById(R.id.myDataListView);
        tv = (TextView) findViewById(R.id.myTextView);
        if(data.equals("password")){
            tv.setText("pm");
            final DatabaseHandler db = new DatabaseHandler(this);
            List<String> your_array_list = new ArrayList<>();

            String show;
            List<Account_Pass> lis = db.getAllAccounts();
            for (Account_Pass cn : lis) {
                show = "ID: " + new Integer(cn.getID()).toString() + "\nAccount Name: " + cn.getname() + "\nUser Name: " + cn.getUName() + "\nPassword: " + cn.getpass();
                your_array_list.add(show);
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    your_array_list );
            lv.setAdapter(arrayAdapter);
        }
        else if(data.equals("contact")){
            tv.setText("pm");
            final DatabaseHandler db = new DatabaseHandler(this);
            List<String> your_array_list = new ArrayList<>();

            String show;
            List<Contact> lis = db.getAllContacts();
            for (Contact cn : lis) {
                show = "ID: " + new Integer(cn.getID()).toString() + "\nName: " + cn.getName() + "\nPhone No: " + cn.getPhoneNumber() + "\nEmail ID: " + cn.getEmailId();
                your_array_list.add(show);
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    your_array_list );
            lv.setAdapter(arrayAdapter);
        }
    }
}
