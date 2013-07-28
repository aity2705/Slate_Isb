package com.aitesam.slate_nuces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;

public class Launch extends Activity {
	public Editor mPreference;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		//ActionBar actionBar = getSupportActionBar();
		//actionBar.hide();
		super.onCreate(savedInstanceState);
		mPreference = getSharedPreferences("com.slateisb.android.aitesam",MODE_PRIVATE).edit();
		mPreference.commit();
		SharedPreferences mSharedPreferences = getSharedPreferences("com.slateisb.android.aitesam",Context.MODE_PRIVATE);
    	String Activity_flag=mSharedPreferences.getString("LoginCheck", "");
    	Log.d("Preference_flag", Activity_flag);
    	Intent srvIntent = new Intent();
        srvIntent.setClass(this, MainActivity.class);
        Intent srvIntent1 = new Intent();
        srvIntent1.setClass(this, LoginPage.class);
        Log.d("Preference_flag", Activity_flag);
    	if(Activity_flag.equals("1")){
    		this.startActivity(srvIntent);
    	}
    	else{
    		this.startActivity(srvIntent1);
    	
    	}
		//Intent inta=new Intent(this,ServiceClass.class);
		//startService(inta);
		//mPreference = getSharedPreferences("com.mycompany.android.myapp",MODE_PRIVATE).edit();
	//Intent intent=new Intent(this,LoginServic.class);	
	//startActivity(intent);
		//startService(new Intent(Launch.this, ServiceClass.class));
	finish();
	}
	}