package sg.edu.nus.mygooglemap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Mac on 15/7/21.
 */
public class SecondReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String info=intent.getStringExtra("num");
        Log.i("Cat",info);
    }
}
