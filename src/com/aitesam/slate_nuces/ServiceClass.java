package com.aitesam.slate_nuces;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
 
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
 
public class ServiceClass extends Service{
	public Editor preferenceEditor;
	public int i=0;
    private static String TAG = ServiceClass.class.getSimpleName();
    private MyThread mythread;
    public boolean isRunning = false;
     
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
 
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");     
        mythread  = new MyThread();
    }
 
    @Override
    public synchronized void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        i=3;
        if(!isRunning){
        	
            mythread.interrupt();
            mythread.stop();
        }       
    }
 
    @Override
    public synchronized void onStart(Intent intent, int startId) {
        super.onStart(intent, startId); 
        Log.d(TAG, "onStart");
        if(!isRunning){
            mythread.start();
            isRunning = true;
        }
    }
     
    public void readWebPage(){
    	preferenceEditor = getSharedPreferences("com.slateisb.android.aitesam",MODE_PRIVATE).edit();
    	SharedPreferences mSharedPreferences = getSharedPreferences("com.slateisb.android.aitesam",Context.MODE_PRIVATE);
    	String mUid=mSharedPreferences.getString("uid", "");
    	String mPassword=mSharedPreferences.getString("pwd", "");
    	Log.d("Uid", mUid);
    	Log.d("Password", mPassword);
    	List<Cookie> mCookies = null;
    	String mCookieString="sasas";
    	final int TIMEOUT_MS = 15000;
    	final String redirURL = "http://slateisb.nu.edu.pk/portal/relogin";
          HttpClient httpClient = new DefaultHttpClient();
          HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), TIMEOUT_MS);
          HttpConnectionParams.setSoTimeout(httpClient.getParams(), TIMEOUT_MS);
          HttpPost httpPost = new HttpPost(redirURL);  
          List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
          nameValuePairs.add(new BasicNameValuePair("curl", "varl"));  
          nameValuePairs.add(new BasicNameValuePair("flags", "0")); 
          nameValuePairs.add(new BasicNameValuePair("forcedownlevel", "0"));    
          nameValuePairs.add(new BasicNameValuePair("formdir", "9"));
          nameValuePairs.add(new BasicNameValuePair("eid", mUid));//mRollNumber  
          nameValuePairs.add(new BasicNameValuePair("pw", mPassword));//mPass  
          nameValuePairs.add(new BasicNameValuePair("trusted", "1"));
          HttpResponse end = null;
          try {
          	httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
  			HttpResponse response = httpClient.execute(httpPost);
  			HttpResponse tResponse=httpClient.execute(new HttpGet("http://slateisb.nu.edu.pk/portal/pda/~"+mUid+"/tool/fe9a4b7a-6ed7-4324-8a6b-8e35d612dd25"));
            StatusLine mStatusLine=tResponse.getStatusLine();
			Log.d("Http Status code", String.valueOf(mStatusLine.getStatusCode()));
			ByteArrayOutputStream out2 = new ByteArrayOutputStream();
	        tResponse.getEntity().writeTo(out2);
	        out2.close();
	        String responseStr = out2.toString();
	        //String responseString2=responseStr.replaceFirst("View announcement", "");
	        int number=anucount(responseStr);
	        preferenceEditor.putString("NotificationCount", String.valueOf(number));
			preferenceEditor.commit();
	        
  		} catch (ClientProtocolException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
          mCookies = ((AbstractHttpClient) httpClient).getCookieStore().getCookies();
          List<Cookie> cookies1 = mCookies;
          if (cookies1 != null && !cookies1.isEmpty()) {

              CookieSyncManager.createInstance(this);
              CookieManager cookieManager = CookieManager.getInstance();
              for (Cookie cookie : cookies1) {

                  Cookie sessionInfo = cookie;
                  mCookieString = sessionInfo.getName() + "="
                          + sessionInfo.getValue() + "; domain="
                          + sessionInfo.getDomain();
                  //cookieManager.setCookie("http://www.slateisb.nu.edu.pk/portal", cookieString);
                  CookieSyncManager.getInstance().sync();
                  Log.d("beforeWebView", mCookieString);
                  preferenceEditor.putString("cookie", mCookieString);
      			preferenceEditor.commit();
                  Log.d("Cookiesss", mCookieString);
                  //writeToFile(mCookieString,"config1.txt");
              }
          }  
                  
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

	class MyThread extends Thread{
        static final long DELAY = 600000;
        @Override
        public void run(){          
            while(isRunning){
                Log.d(TAG,"Running");
                try {                   
                    readWebPage();
                    Thread.sleep(DELAY);
                    //i++;
                    Log.d("Threa", String.valueOf(i));
                    if(i==3)
                    	break;
                } catch (InterruptedException e) {
                    isRunning = false;
                    e.printStackTrace();
                }
            }
        }
         
    }
 
}