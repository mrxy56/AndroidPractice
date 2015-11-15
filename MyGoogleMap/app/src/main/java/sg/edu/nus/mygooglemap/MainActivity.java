package sg.edu.nus.mygooglemap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity implements Fragment1.OnFragmentInteractionListener, Fragment2.OnFragmentInteractionListener{
    MyBroadcastReceiver myReceiver;
    IntentFilter myIntentFilter;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = (Toolbar) this.findViewById(R.id.mytoolbar);
        myToolbar.setLogo(R.mipmap.ic_launcher);

        myReceiver=new MyBroadcastReceiver();
        myIntentFilter=new IntentFilter("Cat_BroadCast");
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver,myIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    public void Spot1(View view) {
        Intent intent=new Intent();
        intent.setClass(MainActivity.this, Spot1.class);
        startActivity(intent);
    }

    public void Spot2(View view) {
        Intent intent=new Intent();
        intent.setClass(MainActivity.this, Spot2.class);
        startActivity(intent);
    }

    public void Spot3(View view) {
        Intent intent=new Intent();
        intent.setClass(MainActivity.this, Spot3.class);
        startActivity(intent);
    }

    public void Spot4(View view) {
        Intent intent=new Intent();
        intent.setClass(MainActivity.this, Spot4.class);
        startActivity(intent);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
