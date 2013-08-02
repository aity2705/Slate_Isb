package com.aitesam.slate_nuces;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class Test extends BroadcastReceiver {
	final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
	public Editor preferenceEditor;

    @Override
    public void onReceive(Context context, Intent intent) {
    	Intent srvIntent = new Intent();
        srvIntent.setClass(context, ServiceClass.class);
        preferenceEditor = context.getSharedPreferences("com.slateisb.android.aitesam",context.MODE_PRIVATE).edit();
    	 ConnectivityManager connectivityManager 
         = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	 NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //Toast.makeText(context, "Network", Toast.LENGTH_LONG).show();
        SharedPreferences mSharedPreferences = context.getSharedPreferences("com.slateisb.android.aitesam",Context.MODE_PRIVATE);
    	String Activity_flag=mSharedPreferences.getString("LoginCheck", "");
        boolean test=activeNetworkInfo != null && activeNetworkInfo.isConnected();
        Log.d("status", String.valueOf(test));
        if(Activity_flag.equals("1")){
        if(test){
        	context.startService(srvIntent);
        	//Toast.makeText(context, "Nuces Service Started", Toast.LENGTH_LONG).show();
        }
        else{
        	//Toast.makeText(context, "Nuces Service Stopped", Toast.LENGTH_LONG).show();
        	preferenceEditor.putString("FirstTime", "1");
        	context.stopService(srvIntent);
        }
        }
        
        	
     } 
}