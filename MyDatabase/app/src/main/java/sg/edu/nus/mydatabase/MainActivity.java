package sg.edu.nus.mydatabase;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.SimpleTimeZone;
import java.util.regex.Pattern;


public class MainActivity extends ActionBarActivity {
    contactDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        db=new contactDB(this);
        setContentView(R.layout.activity_main);

        ArrayList<String[]> contacts=GetContacts();

        for(int i=0;i<contacts.size();i++){
            String[] contact=contacts.get(i);
            insertRow(Integer.parseInt(contact[0]),contact[1],contact[2]);
        }
    }


    public void addContact(String name, String number){
        db.open();
        long id=db.insertContact(name, number);
        if(id>0){
            Toast.makeText(this,"Add successful.",Toast.LENGTH_LONG).show();
            TextView nameView = (TextView)findViewById(R.id.inputName);
            nameView.setText("");
            TextView contactNumberView = (TextView)findViewById(R.id.inputContact);
            contactNumberView.setText("");
            insertRow(id,name,number);
        }else
            Toast.makeText(this,"Add failed.",Toast.LENGTH_LONG).show();

        db.close();
    }


    public ArrayList<String []> GetContacts(){
        db.open();
        Cursor c=db.getAllContacts();
        ArrayList<String[]> contacts=new ArrayList<String[]>();
        if(c.moveToFirst()){
            do{
                String[]contact = {c.getString(0),c.getString(1),c.getString(2)};
                contacts.add(contact);
            }while(c.moveToNext());
        }
        db.close();
        return contacts;
    }


    public String[]GetContact(long rowId){
        db.open();
        Cursor c=db.getContact(rowId);
        String[] contact = new String[3];

        if(c.moveToFirst()){
            String[] temp={c.getString(0),c.getString(1),c.getString(2)};
            contact = temp;
            EditText nameText=(EditText)findViewById(R.id.inputName);
            EditText contactText=(EditText)findViewById(R.id.inputContact);

            Toast.makeText(this,"Select successful",Toast.LENGTH_LONG).show();

            nameText.setText(temp[1]);
            contactText.setText(temp[2]);
        }else
            Toast.makeText(this,"No contact found",Toast.LENGTH_LONG).show();

        db.close();
        return contact;
    }

    public void UpdateContact(long rowId,String name,String number){
        db.open();
        if(db.updateContact(rowId, name, number))
            Toast.makeText(this,"Update successful.",Toast.LENGTH_LONG).show();
        else Toast.makeText(this,"Update failed.",Toast.LENGTH_LONG).show();

        db.close();
    }

    public void DeleteContact(long rowId){
        db.open();
        if(db.deleteContact(rowId))
            Toast.makeText(this,"Delete successful.",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"Delete failed",Toast.LENGTH_LONG).show();
        db.close();
    }
    //Button Function

    public void submitContact(View view){
        EditText nameText=(EditText)findViewById(R.id.inputName);
        EditText contactText=(EditText)findViewById(R.id.inputContact);
        String name=nameText.getText().toString();
        String contact=contactText.getText().toString();
        addContact(name, contact);
    }

    public void deleteContact(View view){
        EditText idText=(EditText)findViewById(R.id.inputID);
        EditText nameText=(EditText)findViewById(R.id.inputName);
        EditText contactText=(EditText)findViewById(R.id.inputContact);
        int id=0;
        if(isNumeric(idText.getText().toString())) {
            id=Integer.parseInt(idText.getText().toString());
            DeleteContact(id);

            ViewGroup table=(ViewGroup)findViewById(R.id.table);
            table.removeAllViews();

            setContentView(R.layout.activity_main);

            ArrayList<String[]> contacts=GetContacts();

            for(int i=0;i<contacts.size();i++){
                String[] contact=contacts.get(i);
                insertRow(Integer.parseInt(contact[0]),contact[1],contact[2]);
            }
        }else{
            Toast.makeText(this,"Delete failed",Toast.LENGTH_LONG).show();
        }
    }

    public void updateContact(View view){
        EditText idText=(EditText)findViewById(R.id.inputID);
        EditText nameText=(EditText)findViewById(R.id.inputName);
        EditText contactText=(EditText)findViewById(R.id.inputContact);

        String name=nameText.getText().toString();
        String contact=contactText.getText().toString();

        if(isNumeric(idText.getText().toString())) {
            int id=Integer.parseInt(idText.getText().toString());
            UpdateContact(id, name, contact);

            ViewGroup table=(ViewGroup)findViewById(R.id.table);
            table.removeAllViews();

            setContentView(R.layout.activity_main);

            ArrayList<String[]> contacts=GetContacts();

            for(int i=0;i<contacts.size();i++){
                String[] up_contact=contacts.get(i);
                insertRow(Integer.parseInt(up_contact[0]),up_contact[1],up_contact[2]);
            }
        }else{
            Toast.makeText(this,"Update failed",Toast.LENGTH_LONG).show();
        }
    }

    public void selectContact(View view){
        EditText idText=(EditText)findViewById(R.id.inputID);
        EditText nameText=(EditText)findViewById(R.id.inputName);
        EditText contactText=(EditText)findViewById(R.id.inputContact);

        String name=nameText.getText().toString();
        String contact=contactText.getText().toString();

        if(isNumeric(idText.getText().toString())) {
            int id=Integer.parseInt(idText.getText().toString());
            GetContact(id);
        }else{
            Toast.makeText(this,"No contact found",Toast.LENGTH_LONG).show();
        }
    }

    public void insertRow(long rowId,String name,String number){
        ViewGroup table=(ViewGroup)findViewById(R.id.table);
        TableRow newRow=new TableRow(table.getContext());
        TextView idText=new TextView(newRow.getContext());
        TextView nameText=new TextView(newRow.getContext());
        TextView numberText=new TextView(newRow.getContext());


        newRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));


        idText.setText(String.valueOf(rowId));
        idText.setLayoutParams(
                new TableRow.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,0.3f));
        idText.setGravity(Gravity.CENTER);


        nameText.setText(String.valueOf(name));
        nameText.setLayoutParams(
                new TableRow.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT, 0.3f));
        nameText.setGravity(Gravity.CENTER);


        numberText.setText(String.valueOf(number));
        numberText.setLayoutParams(
                new TableRow.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT, 0.4f));
        numberText.setGravity(Gravity.CENTER);


        newRow.addView(idText);
        newRow.addView(nameText);
        newRow.addView(numberText);
        table.addView(newRow);

    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
