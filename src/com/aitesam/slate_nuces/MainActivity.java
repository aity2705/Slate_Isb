package com.aitesam.slate_nuces;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.navdrawer.SimpleSideDrawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends SherlockActivity {
	public static int THEME = R.style.Theme_Sherlock;
	private SimpleSideDrawer mNav;
	WebView mWebView;
	public ImageView test;
	private String mCookies;
	public WebView web_test;
	public int a=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		String flag_login_page=readFromFile("prefrence.txt");
		//Log.d("FileCheck", flag_login_page);
		//if(flag_login_page.equals("")){
		//Intent logintest=new Intent(this,LoginPage.class);
		//startActivity(logintest);
		//writeToFile("1","prefrence.txt");
		//}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Setting Views
		final ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayUseLogoEnabled(false);
		mWebView=(WebView)findViewById(R.id.web_frag);
		//Setting Up WebView
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
            @Override
            public void onPageFinished(WebView view, String url) {
            	 view.loadUrl("javascript:(function(e,a,g,h,f,c,b,d){if(!(f=e.jQuery)||g>f.fn.jquery||h(f)){c=a.createElement('script');c.type='text/javascript';c.src='http://ajax.googleapis.com/ajax/libs/jquery/'+g+'/jquery.min.js';c.onload=c.onreadystatechange=function(){if(!b&&(!(d=this.readyState)||d=='loaded'||d=='complete')){h((f=e.jQuery).noConflict(1),b=1);f(c).remove()}};a.documentElement.childNodes[0].appendChild(c)}})(window,document,'1.3.2',function($,L){$('#pda-footer').hide();});");
            	view.loadUrl("javascript:(function(e,a,g,h,f,c,b,d){if(!(f=e.jQuery)||g>f.fn.jquery||h(f)){c=a.createElement('script');c.type='text/javascript';c.src='http://ajax.googleapis.com/ajax/libs/jquery/'+g+'/jquery.min.js';c.onload=c.onreadystatechange=function(){if(!b&&(!(d=this.readyState)||d=='loaded'||d=='complete')){h((f=e.jQuery).noConflict(1),b=1);f(c).remove()}};a.documentElement.childNodes[0].appendChild(c)}})(window,document,'1.3.2',function($,L){$('#pda-portlet-menu').hide();});");
            }

        });
		//Setting Up Cookies
        mCookies=readFromFile("config.txt");
        Log.d("Cookie_Read", mCookies);//For Debugging
        CookieSyncManager.createInstance(this);
	       CookieManager cookieManager = CookieManager.getInstance();
	       cookieManager.removeAllCookie();
	        //cookieManager.setAcceptCookie(true);
	        cookieManager.setCookie("http://www.slateisb.nu.edu.pk", mCookies);
	     // Loading the WebView
	        mWebView.loadUrl("http://www.slateisb.nu.edu.pk/portal");
	        
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
        	findViewById(R.id.behind_btn).setOnClickListener(new OnClickListener()
            {
            	@Override
            	public void onClick(View v){
        //    		
            		//Toast.makeText(this, "asdasd", Toast.LENGTH_LONG);
            		web_test=(WebView)findViewById(R.id.webView1);
            		web_test.loadUrl("http://www.google.com");
            	}
            	
            });
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
		if(mWebView.canGoBack()){
		mWebView.goBack();}
		else{
		super.onBackPressed();
		//System.exit(1);
		}
	}
	private String readFromFile(String mFileName) {

	    String ret = "";

	    try {
	        InputStream inputStream = openFileInput(mFileName);

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
	        Intent logintest=new Intent(this,LoginPage.class);
			startActivity(logintest);
			writeToFile("1","prefrence.txt");
			
	    } catch (IOException e) {
	        Log.e("login activity", "Can not read file: " + e.toString());
	        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
	        Intent logintest=new Intent(this,LoginPage.class);
			startActivity(logintest);
			writeToFile("1","prefrence.txt");
			
	    }

	    return ret;
	}
	/*private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	}*/
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

}
