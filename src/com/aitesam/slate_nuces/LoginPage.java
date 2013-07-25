package com.aitesam.slate_nuces;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
//@SuppressLint("NewApi")
public class LoginPage extends Activity  {
	// UI Objects
	EditText mUid;
	EditText mPassword;
	ImageButton mLoginButton;
	//Http Objects
	private HttpClient httpClient = new DefaultHttpClient();
	public Cookie cookie = null;
	public  String cookies2;
	public  String cookieString;
	public String testa;
	private static final int TIMEOUT_MS = 3000;
	private static final String redirURL = "http://slateisb.nu.edu.pk/portal/relogin";
	// Static Variabel for Multi Classes
	public  String mRollNumber;
	public  String mPass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_page);
		//Setting View of UI
		mUid=(EditText)findViewById(R.id.eid);
		mPassword=(EditText)findViewById(R.id.pw);
		mLoginButton=(ImageButton)findViewById(R.id.btn_login);
		//
		Config.test_web="abc";
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isNetworkAvailable()){
				//First Setting  Keys
				mRollNumber=mUid.getText().toString();
				mPass=mPassword.getText().toString();
				do_login mLogin=new do_login();
				mLogin.execute();
				Toast.makeText(getApplicationContext(), "Logging In...", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	private class do_login extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), TIMEOUT_MS);
	        HttpConnectionParams.setSoTimeout(httpClient.getParams(), TIMEOUT_MS);
	        HttpPost httpPost = new HttpPost(redirURL);  
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
	        nameValuePairs.add(new BasicNameValuePair("curl", "varl"));  
	        nameValuePairs.add(new BasicNameValuePair("flags", "0")); 
	        nameValuePairs.add(new BasicNameValuePair("forcedownlevel", "0"));    
	        nameValuePairs.add(new BasicNameValuePair("formdir", "9"));
	        //nameValuePairs.add(new BasicNameValuePair("eid", "i1215"));//mRollNumber  
	        //nameValuePairs.add(new BasicNameValuePair("pw", "word123"));//mPass  
	        nameValuePairs.add(new BasicNameValuePair("eid", mRollNumber));//mRollNumber  
	        nameValuePairs.add(new BasicNameValuePair("pw", mPass));//mPass  
	        nameValuePairs.add(new BasicNameValuePair("trusted", "1"));
	        HttpResponse end = null;
	        try {
	            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            HttpResponse response = httpClient.execute(httpPost);
	          //After Login
	            List<Cookie> cookies = ((AbstractHttpClient) httpClient).getCookieStore().getCookies();
	            for (int i = 0; i < cookies.size(); i++) {
	                cookie = cookies.get(i);
	            }
	            cookies2 = ((AbstractHttpClient) httpClient).getCookieStore().getCookies().toString();
	            Log.d("cookie", cookies2);
	            end = response;
	            String deviceVersion= Build.VERSION.RELEASE;
	            int SDK_INT = android.os.Build.VERSION.SDK_INT;
	            String rs=Integer.toString(SDK_INT,10);
	            Log.d("sdk",rs);
	            
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            Log.d("Http Request", e.toString());
	        } 
	        Config.mCookies = ((AbstractHttpClient) httpClient).getCookieStore().getCookies();
	        List<Cookie> cookies = Config.mCookies;
	        if (cookies != null && !cookies.isEmpty()) {

	            CookieSyncManager.createInstance(LoginPage.this);
	            CookieManager cookieManager = CookieManager.getInstance();
	            for (Cookie cookie : cookies) {

	                Cookie sessionInfo = cookie;
	                Config.mCookieString = sessionInfo.getName() + "="
	                        + sessionInfo.getValue() + "; domain="
	                        + sessionInfo.getDomain();
	                //cookieManager.setCookie("http://www.slateisb.nu.edu.pk/portal", cookieString);
	                CookieSyncManager.getInstance().sync();
	                Log.d("beforeWebView", Config.mCookieString);
	            }
	        }
	        String ab=Config.mCookieString;
			return ab;
		}
		@Override 
		protected void onPostExecute(String result){
			writeToFile(result);
			//ab.setText(result);
			Intent mMainIntent= new Intent(LoginPage.this,MainActivity.class);
			startActivity(mMainIntent);
		}
	}
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	public void writeToFile(String data) {
	    try {
	        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("config.txt", Context.MODE_PRIVATE));
	        outputStreamWriter.write(data);
	        outputStreamWriter.close();
	    }
	    catch (IOException e) {
	        Log.e("Exception", "File write failed: " + e.toString());
	        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
	    } 
	}
	
	/*@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}*/

}
