package com.aitesam.slate_nuces;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.navdrawer.SimpleSideDrawer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
	// Declare Variable
		DrawerLayout mDrawerLayout;
		ListView mDrawerList;
		ActionBarDrawerToggle mDrawerToggle;
		MenuListAdapter mMenuAdapter;
		String[] title;
		String[] subtitle;
		WebView as;
		int[] icon;
		Intent srvIntent = new Intent();
		RelativeLayout badgeLayout;
		TextView tv;
       // srvIntent.setClass(this, ServiceClass.class);
		SharedPreferences mSharedPreferences;
        
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		/*String flag_login_page=readFromFile("prefrence.txt");
		//Log.d("FileCheck", flag_login_page);
		if(flag_login_page.equals("1")){
		Intent logintest=new Intent(this,LoginPage.class);
		startActivity(logintest);
		writeToFile("1","prefrence.txt");
		}*/
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Setting Views
		final ActionBar ab = getSupportActionBar();
		mSharedPreferences = getSharedPreferences("com.slateisb.android.aitesam",Context.MODE_PRIVATE);
		String flag_time="0";
		flag_time=mSharedPreferences.getString("FirstTime", "");
		Log.d("Time",flag_time);
		if(flag_time.equals("1")){
			srvIntent.setClass(this, ServiceClass.class);
			Log.d("Time",flag_time);
	        this.startService(srvIntent);
	        try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Generate title
		title = new String[] { "Home", "Annoucments",
				"Logout","Recent" };
		// Generate subtitle
		subtitle = new String[] { "i120515", "No New Annoucments",
						"Stop Service" ,"Recent"};

		// Generate icon
		icon = new int[] { R.drawable.action_about, R.drawable.action_settings,
				R.drawable.collections_cloud,R.drawable.collections_cloud };
		// Locate DrawerLayout in drawer_main.xml
				mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

				// Locate ListView in drawer_main.xml
				mDrawerList = (ListView) findViewById(R.id.left_drawer);

				// Set a custom shadow that overlays the main content when the drawer
				// opens
				mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
						GravityCompat.START);

				// Pass results to MenuListAdapter Class
				mMenuAdapter = new MenuListAdapter(this, title, subtitle, icon);

				// Set the MenuListAdapter to the ListView
				mDrawerList.setAdapter(mMenuAdapter);
				mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

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
            	/*HttpClient mHttpClient=new DefaultHttpClient();
	    		try {
	    			HttpResponse mResponse=mHttpClient.execute(new HttpGet("http://www.slateisb.nu.edu.pk/portal/pda"));
	    			StatusLine mStatusLine=mResponse.getStatusLine();
	    			Log.d("Http Status code", String.valueOf(mStatusLine.getStatusCode()));
	    			ByteArrayOutputStream out = new ByteArrayOutputStream();
	    	        mResponse.getEntity().writeTo(out);
	    	        out.close();
	    	        String responseString = out.toString();
	    	        int i=responseString.indexOf("Message");
	    	        int j=responseString.indexOf("Aitesam");
	    	        //boolean k=responseString.matches(".*\\bMessage\\b.*");
	    	        //String pref=PreferenceManager.getDefaultSharedPreferences(this).getString("firstPlayerNameSave", "");
	    	        //boolean k=Arrays.asList(responseString.split("\n")).contains("Message");
	    	       // String sc=responseString.substring(i,j+1);
	    	        Log.d("Result", responseString);
	    	        
	    	        //writeToFile(responseString,"Result.txt");
	    		} catch (ClientProtocolException e1) {
	    			// TODO Auto-generated catch block
	    			Log.d("Http Exeption",e1.toString());
	    			e1.printStackTrace();
	    		} catch (IOException e1) {
	    			// TODO Auto-generated catch block
	    			Log.d("Http Exeption",e1.toString());
	    			e1.printStackTrace();
	    		}*/
	    	
            }

        });
        
        String mCookie=mSharedPreferences.getString("cookie", "");
		//Setting Up Cookies
        mCookies=readFromFile("config1.txt");
        Log.d("Cookie_Read_Preference", mCookie);//For Debugging
        CookieSyncManager.createInstance(this);
	       CookieManager cookieManager = CookieManager.getInstance();
	       cookieManager.removeAllCookie();
	        cookieManager.setAcceptCookie(true);
	        cookieManager.setCookie("http://www.slateisb.nu.edu.pk", mCookie);
	     // Loading the WebView
	        mWebView.loadUrl("http://www.slateisb.nu.edu.pk/portal");
	    	mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
					R.drawable.ic_drawer, R.string.drawer_open,
					R.string.drawer_close) {

				public void onDrawerClosed(View view) {
					// TODO Auto-generated method stub
					super.onDrawerClosed(view);
				}

				public void onDrawerOpened(View drawerView) {
					// TODO Auto-generated method stub
					super.onDrawerOpened(drawerView);
				}
			};

			mDrawerLayout.setDrawerListener(mDrawerToggle);

			if (savedInstanceState == null) {
				Log.d("Testing", "Clicked Ok");
				selectItem(2);
			}
	        
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
        String mNotificationCount=mSharedPreferences.getString("NotificationCount", "");
        badgeLayout = (RelativeLayout) menu.findItem(R.id.badge).getActionView();
        tv = (TextView) badgeLayout.findViewById(R.id.actionbar_notifcation_textview); tv.setText("12");
       tv.setText(mNotificationCount);
        return super.onCreateOptionsMenu(menu);
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {
			String mNotificationCount=mSharedPreferences.getString("NotificationCount", "");
			tv.setText(mNotificationCount);
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}

		return super.onOptionsItemSelected(item);
		
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
		//Intent srvIntent = new Intent();
        srvIntent.setClass(this, ServiceClass.class);
        this.startService(srvIntent);
        super.finish();
		System.exit(1);
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
	// The click listener for ListView in the navigation drawer
		private class DrawerItemClickListener implements
				ListView.OnItemClickListener {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				selectItem(position);
			}
		}

		private void selectItem(int position) {

			//FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			//as=(WebView)findViewById(R.id.content_frame);
			//as.setWebViewClient(new WebViewClient());
			// Locate Position
			String as="";
			switch (position) {
			case 0:
				as="http://www.google.com";
				break;
			case 1:
				srvIntent.setClass(this, ServiceClass.class);
		        this.stopService(srvIntent);
				
				//as="http://www.facebook.com";
				
				break;
			case 2:
				srvIntent.setClass(this, ServiceClass.class);
		        this.stopService(srvIntent);
				
				break;
			}
			//mWebView.loadUrl(as);
			Log.d("Testing", "Clicked Ok");
			mDrawerList.setItemChecked(position, true);
			// Close drawer
			mDrawerLayout.closeDrawer(mDrawerList);
		}

		@Override
		protected void onPostCreate(Bundle savedInstanceState) {
			super.onPostCreate(savedInstanceState);
			// Sync the toggle state after onRestoreInstanceState has occurred.
			mDrawerToggle.syncState();
		}

		@Override
		public void onConfigurationChanged(Configuration newConfig) {
			super.onConfigurationChanged(newConfig);
			// Pass any configuration change to the drawer toggles
			mDrawerToggle.onConfigurationChanged(newConfig);
		}

}
