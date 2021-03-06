package com.aitesam.slate_nuces;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
public class LoginServic extends Activity  {
	// UI Objects
	
	//Http Objects
	private HttpClient httpClient = new DefaultHttpClient();
	public Cookie cookie = null;
	public  String cookies2;
	public  String cookieString;
	private static final int TIMEOUT_MS = 3000;
	private static final String redirURL = "http://slateisb.nu.edu.pk/portal/relogin";
	// Static Variabel for Multi Classes
	public int login_pass;
	public Editor mSetting;
	public Editor preferenceEditor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_login_page);
		//Setting View of UI
		mSetting = PreferenceManager.getDefaultSharedPreferences(this).edit();
		preferenceEditor = getSharedPreferences("com.mycompany.android.myapp",MODE_PRIVATE).edit();
		Config.test_web="abc";
			
				if(isNetworkAvailable()){
				//First Setting  Keys
				do_login mLogin=new do_login();
				mLogin.execute();
				Toast.makeText(getApplicationContext(), "Logging In...", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show();
				}
		
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
	        nameValuePairs.add(new BasicNameValuePair("eid", "i120515"));//mRollNumber  
	        nameValuePairs.add(new BasicNameValuePair("pw","password123"));//mPass  
	        nameValuePairs.add(new BasicNameValuePair("trusted", "1"));
	        HttpResponse end = null;
	        try {
	            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            HttpResponse response = httpClient.execute(httpPost);
	            HttpResponse tResponse=httpClient.execute(new HttpGet("http://slateisb.nu.edu.pk/portal/pda/~"+"i120515"+"/tool/fe9a4b7a-6ed7-4324-8a6b-8e35d612dd25"));
	            StatusLine mStatusLine=tResponse.getStatusLine();
    			Log.d("Http Status code", String.valueOf(mStatusLine.getStatusCode()));
    			ByteArrayOutputStream out2 = new ByteArrayOutputStream();
    	        tResponse.getEntity().writeTo(out2);
    	        out2.close();
    	        String responseStr = out2.toString();
    	        //String responseString2=responseStr.replaceFirst("View announcement", "");
    	        int number=anucount(responseStr);
    	        
    	        Log.d("PageResult", String.valueOf(number));
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
	            ByteArrayOutputStream out = new ByteArrayOutputStream();
		        response.getEntity().writeTo(out);
		        out.close();
		        String responseString = out.toString();
	            login_pass=responseString.indexOf("alertMessage");
	            Log.d("Login Response", responseString);
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            Log.d("Http Request", e.toString());
	        } 
	        Config.mCookies = ((AbstractHttpClient) httpClient).getCookieStore().getCookies();
	        List<Cookie> cookies = Config.mCookies;
	        if (cookies != null && !cookies.isEmpty()) {

	            CookieSyncManager.createInstance(LoginServic.this);
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
		private int anucount(String responseString2) {
			// TODO Auto-generated method stub
			Date test = new Date();
			
			int start=responseString2.indexOf("View announcement");
	        int end=responseString2.indexOf("pda-footer");
	        responseString2=responseString2.substring(start, end);
	        Log.d("In Function", test.toString());
			int number=0;
			int index=0;
			while(true){
				index=responseString2.indexOf("View announcement");
				if(index==-1){
					break;
				}
				else{
					number=number+1;
					responseString2=responseString2.replaceFirst("View announcement", "");
				}
					
					//return 0;
			}
			return number;
		}
		@Override 
		protected void onPostExecute(String result){
			writeToFile(result,"config1.txt");
			preferenceEditor.putString("cookie", result);
			preferenceEditor.commit();
			
			//ab.setText(result);
			Log.d("Login Pass",String.valueOf(login_pass));
			if((login_pass==-1)){
				Intent mMainIntent= new Intent(LoginServic.this,MainActivity.class);
				startActivity(mMainIntent);
				finish();
				
			}
			else{
				Intent mMainIntent= new Intent(LoginServic.this,MainActivity.class);
				startActivity(mMainIntent);
				finish();
				
				//Toast.makeText(getApplicationContext(), "Login Failed Please Try Again", Toast.LENGTH_LONG).show();
		}
			//System.exit(1);
			//Intent mMainIntent= new Intent(LoginPage.this,MainActivity.class);
			//startActivity(mMainIntent);
		}
	}
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	public void writeToFile(String data,String mFileName) {
	    try {
	        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(mFileName, Context.MODE_PRIVATE));
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
