package com.aitesam.slate_nuces;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.navdrawer.SimpleSideDrawer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends SherlockActivity {
	public static int THEME = R.style.Theme_Sherlock;
	private SimpleSideDrawer mNav;
	WebView mWebView;
	public ImageView test;
	private String mCookies;
	String image_URL= "http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Setting Views
		final ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayUseLogoEnabled(false);
		mWebView=(WebView)findViewById(R.id.web_frag);
		test=(ImageView)findViewById(R.id.web_image);
		BitmapFactory.Options bmOptions;
	    bmOptions = new BitmapFactory.Options();
	    bmOptions.inSampleSize = 1;
	    Bitmap bm = LoadImage(image_URL, bmOptions);
		//.execute("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");
		//Setting Up WebView
		// finish();
	    test.setImageBitmap(bm);
		 mNav = new SimpleSideDrawer(this);
	      mNav.setLeftBehindContentView(R.layout.drawer_layout);
		
		WebSettings webSettings = mWebView.getSettings();
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);

        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
            	if(url.equals("http://slateisb.nu.edu.pk/portal/pda/?force.logout=yes")){
            		Toast.makeText(MainActivity.this, "Ok Test", Toast.LENGTH_LONG).show();
            		return true;
            	}
                view.loadUrl(url);
                return false; // then it is not handled by default action
           }

        });
		//Setting Up Cookies
        mCookies=readFromFile();
        Log.d("Cookie_Read", mCookies);//For Debugging
        CookieSyncManager.createInstance(this);
	       CookieManager cookieManager = CookieManager.getInstance();
	       cookieManager.removeAllCookie();
	        //cookieManager.setAcceptCookie(true);
	        cookieManager.setCookie("http://www.slateisb.nu.edu.pk", mCookies);
	     // Loading the WebView
	        mWebView.loadUrl("http://www.slateisb.nu.edu.pk/portal");
	        
	}
	private Bitmap LoadImage(String URL, BitmapFactory.Options options)
	   {       
	    Bitmap bitmap = null;
	    InputStream in = null;       
	       try {
	           in = OpenHttpConnection(URL);
	           bitmap = BitmapFactory.decodeStream(in, null, options);
	           in.close();
	       } catch (IOException e1) {
	       }
	       return bitmap;               
	   }
	private InputStream OpenHttpConnection(String strURL) throws IOException{
		 InputStream inputStream = null;
		 URL url = new URL(strURL);
		 URLConnection conn = url.openConnection();

		 try{
		  HttpURLConnection httpConn = (HttpURLConnection)conn;
		  httpConn.setRequestMethod("GET");
		  httpConn.connect();

		  if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
		   inputStream = httpConn.getInputStream();
		  }
		 }
		 catch (Exception ex)
		 {
		 }
		 return inputStream;
		}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	/*SubMenu sub = menu.addSubMenu("Theme");
        sub.add(0, R.style.Theme_Sherlock, 0, "Default");
        sub.add(0, R.style.Theme_Sherlock_Light, 0, "Light");
        sub.add(0, R.style.Theme_Sherlock_Light_DarkActionBar, 0, "Light (Dark Action Bar)");
        sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;*/
        getSupportMenuInflater().inflate(R.menu.main_menu, menu);
        // set up a listener for the refresh item
       /* final MenuItem refresh = (MenuItem) menu.findItem(R.id.menu_search);
       refresh.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            // on selecting show progress spinner for 1s
            public boolean onMenuItemClick(MenuItem item) {
            	mNav.toggleLeftDrawer();
				return false;
                // item.setActionView(R.layout.progress_action);
               
            }
        });*/
        return super.onCreateOptionsMenu(menu);
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        case android.R.id.home:
            // TODO handle clicking the app icon/logo
        	mNav.toggleLeftDrawer();
            return true;
        case R.id.menu_refresh:
        	//showDropDownNav();
            // switch to a progress animation
           
            return true;
        default:
            return super.onOptionsItemSelected(item);
    	}
		
    }
    private void showDropDownNav() {
    	ActionBar ab = getSupportActionBar();
        if (ab.getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }
    }
	@Override
	public void onBackPressed(){
		super.onBackPressed();
	}
	private String readFromFile() {

	    String ret = "";

	    try {
	        InputStream inputStream = openFileInput("config.txt");

	        if ( inputStream != null ) {
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String receiveString = "";
	            StringBuilder stringBuilder = new StringBuilder();

	            while ( (receiveString = bufferedReader.readLine()) != null ) {
	                stringBuilder.append(receiveString);
	            }

	            inputStream.close();
	            ret = stringBuilder.toString();
	        }
	    }
	    catch (FileNotFoundException e) {
	        Log.e("login activity", "File not found: " + e.toString());
	        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
	    } catch (IOException e) {
	        Log.e("login activity", "Can not read file: " + e.toString());
	        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
	    }

	    return ret;
	}

}
