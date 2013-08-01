package com.aitesam.slate_nuces;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.navdrawer.SimpleSideDrawer;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.PushService;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
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
	public WebView web_test;
	public int a=0;
	public List<ParseObject> ob;
	// Declare Variable
		DrawerLayout mDrawerLayout;
		ListView mDrawerList;
		ListView mAppList;
		ActionBarDrawerToggle mDrawerToggle;
		MenuListAdapter mMenuAdapter;
		AppListAdapter mAppAdapter;
		String[] title;
		String[] subtitle;
		WebView as;
		int[] icon;
		Intent srvIntent = new Intent();
		RelativeLayout badgeLayout;
		TextView tv;
		public String mUid;
       // srvIntent.setClass(this, ServiceClass.class);
		SharedPreferences mSharedPreferences;
		Editor mPrefernceEditor1;
		TextView mQuote;
		ActionBar ab;
        
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Setting Views
		 ab = getSupportActionBar();
		//ab.setBackgroundDrawable(new ColorDrawable(0xff123456));
		mWebView=(WebView)findViewById(R.id.web_frag);
		mNav= new SimpleSideDrawer(this);
		mNav.setLeftBehindContentView(R.layout.left_drawer_test);
		mQuote=(TextView)findViewById(R.id.quote);
		TextView RollNumber=(TextView) findViewById(R.id.Rollnumber);
		//boolean mNetworkAvailable=isNetworkAvailable();
		setTitle("");
		ParseAnalytics.trackAppOpened(getIntent());
		 
        // inform the Parse Cloud that it is ready for notifications
        PushService.setDefaultPushCallback(this, MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
		//Log.d("Network Check", String.valueOf(mNetworkAvailable));
		mSharedPreferences = getSharedPreferences("com.slateisb.android.aitesam",Context.MODE_PRIVATE);
		mPrefernceEditor1 = getSharedPreferences("com.slateisb.android.aitesam",MODE_PRIVATE).edit();
		String flag_time="0";
		flag_time=mSharedPreferences.getString("FirstTime", "");
		String Quote=mSharedPreferences.getString("Quote", "");
		RollNumber.setText(mSharedPreferences.getString("uid", ""));
		Log.d("Time",flag_time);
		if(flag_time.equals("1")){
			srvIntent.setClass(this, ServiceClass.class);
			Log.d("Time",flag_time);
	        //this.startService(srvIntent);
	        try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mQuote.setText(Quote);
		//Navigation List Adapter
		// Generate Titles
		title = new String[] { "Home" ,"Messages","Profile","Resources","Preference","Account","Setup"};
		// Generate subtitle
		subtitle = new String[] { "i120515","Annoucnments","","",""};

		// Generate icon
		icon = new int[] { R.drawable.ic_home1,R.drawable.ic_messages,R.drawable.ic_profile,R.drawable.ic_box,R.drawable.ic_settings,R.drawable.ic_account,R.drawable.ic_setup };
		mDrawerList=(ListView)findViewById(R.id.drawer_list);
		mMenuAdapter=new MenuListAdapter(this,title,subtitle,icon);
		mDrawerList.setAdapter(mMenuAdapter);
		//Applications List Adapter
		String[] titleApp=new String[] {"FaceBook","Report"};
		String[] subtitleApp=new String[] {"1","0"};
		int[] iconApp= new int[] {R.drawable.ic_facebook,R.drawable.ic_report};
		mAppList=(ListView)findViewById(R.id.drawer_list2);
		mAppAdapter=new AppListAdapter(this,titleApp,subtitleApp,iconApp);
		//mDrawerList.setAdapter(mMenuAdapter);
		mAppList.setAdapter(mAppAdapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mAppList.setOnItemClickListener(new AppItemClickListener());
		ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayUseLogoEnabled(false);
        Update mStart=new Update();
        mStart.execute();
        //mLoad();
			        	        
	}
	private void mLoad() {
		// TODO Auto-generated method stub
		mWebView=(WebView)findViewById(R.id.web_frag);
		//Setting Up WebView
		WebSettings webSettings = mWebView.getSettings();
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);

        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
               if(url.equals("http://slateisb.nu.edu.pk/portal/pda/?force.logout=yes")){
            		Toast.makeText(MainActivity.this, "Ok Test", Toast.LENGTH_LONG).show();
            		return true;
            	}
                view.loadUrl(url);
                return false; 
           }
            @Override
            public void onPageFinished(WebView view, String url) {
            	view.loadUrl("javascript:(function(e,a,g,h,f,c,b,d){if(!(f=e.jQuery)||g>f.fn.jquery||h(f)){c=a.createElement('script');c.type='text/javascript';c.src='http://ajax.googleapis.com/ajax/libs/jquery/'+g+'/jquery.min.js';c.onload=c.onreadystatechange=function(){if(!b&&(!(d=this.readyState)||d=='loaded'||d=='complete')){h((f=e.jQuery).noConflict(1),b=1);f(c).remove()}};a.documentElement.childNodes[0].appendChild(c)}})(window,document,'1.3.2',function($,L){$('#include').remove();});");
            	 view.loadUrl("javascript:(function(e,a,g,h,f,c,b,d){if(!(f=e.jQuery)||g>f.fn.jquery||h(f)){c=a.createElement('script');c.type='text/javascript';c.src='http://ajax.googleapis.com/ajax/libs/jquery/'+g+'/jquery.min.js';c.onload=c.onreadystatechange=function(){if(!b&&(!(d=this.readyState)||d=='loaded'||d=='complete')){h((f=e.jQuery).noConflict(1),b=1);f(c).remove()}};a.documentElement.childNodes[0].appendChild(c)}})(window,document,'1.3.2',function($,L){$('#pda-footer').hide();});");
            	 view.loadUrl("javascript:(function(e,a,g,h,f,c,b,d){if(!(f=e.jQuery)||g>f.fn.jquery||h(f)){c=a.createElement('script');c.type='text/javascript';c.src='http://ajax.googleapis.com/ajax/libs/jquery/'+g+'/jquery.min.js';c.onload=c.onreadystatechange=function(){if(!b&&(!(d=this.readyState)||d=='loaded'||d=='complete')){h((f=e.jQuery).noConflict(1),b=1);f(c).remove()}};a.documentElement.childNodes[0].appendChild(c)}})(window,document,'1.3.2',function($,L){$('#pda-portlet-menu').hide();});");
	    	
            }

        });
        
        String mCookie=mSharedPreferences.getString("cookie", "");
		//Setting Up Cookies
       // mCookies=readFromFile("config1.txt");
        Log.d("Cookie_Read_Preference", mCookie);//For Debugging
        CookieSyncManager.createInstance(this);
	       CookieManager cookieManager = CookieManager.getInstance();
	       cookieManager.removeAllCookie();
	        cookieManager.setAcceptCookie(true);
	        cookieManager.setCookie("http://www.slateisb.nu.edu.pk", mCookie);
	     // Loading the WebView
	        //
	        if(isNetworkAvailable()){
	        	mWebView.loadUrl("http://www.slateisb.nu.edu.pk/portal");
	        }
	        else{
	        	 ab.hide();
	        	mWebView.loadUrl("file:///android_asset/index2.html");
	        }
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getSupportMenuInflater().inflate(R.menu.main_menu, menu);
        // set up a listener for the refresh item
        final MenuItem refresh = (MenuItem) menu.findItem(R.id.menu_refresh);
        refresh.setOnMenuItemClickListener(new OnMenuItemClickListener() {
             // on selecting show progress spinner for 1s
             public boolean onMenuItemClick(MenuItem item) {
 				new Update().execute();
             	return false;
                
                
             }
         });
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
			mNav.toggleDrawer();
			
		
			/*if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}*/
		}
		if (item.getItemId() == R.id.menu_about) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
	 
				// set title
				alertDialogBuilder.setTitle("About");
	 
				// set dialog message
				alertDialogBuilder
					.setMessage("This Application is in Alpha state.\nIf Error Occurs please Report Using App." +
							"\nApplication is created By Aitesam Abdul Raheem")
					.setCancelable(false)
					.setPositiveButton("Cancle",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					  });
					
	 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
					alertDialog.show();
			/*if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}*/
		}
		if(item.getItemId()==R.id.menu_up){
			mWebView.loadUrl("http://slateisb.nu.edu.pk/portal/pda/?force.logout=yes");
			Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show();
			mPrefernceEditor1.clear();
			//mPrefernceEditor1.putString("LoginCheck", "1");
			mPrefernceEditor1.commit();
			super.finish();
			
		}
		return super.onOptionsItemSelected(item);
		
    }
    @Override
	public void onBackPressed(){
		if(mWebView.canGoBack()){
		mWebView.goBack();}
		else{
		super.onBackPressed();
		//Intent srvIntent = new Intent();
        srvIntent.setClass(this, ServiceClass.class);
        //this.startService(srvIntent);
        super.finish();
		System.exit(1);
		}
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
			String mUid=mSharedPreferences.getString("uid", "");
			Log.d("UserId", mUid);
			// Locate Position
			switch (position) {
			case 0:
				mWebView.loadUrl("http://www.slateisb.nu.edu.pk/portal");
				Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				mWebView.loadUrl("http://slateisb.nu.edu.pk/portal/pda/~"+mUid+"/tool-reset/191995cd-44c3-4d08-be1e-acef4b17be49");
				Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				mWebView.loadUrl("http://slateisb.nu.edu.pk/portal/pda/~"+mUid+"/tool-reset/6d2f8659-b136-47e0-b944-fe00f1555a6e");
				Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				mWebView.loadUrl("http://slateisb.nu.edu.pk/portal/pda/~"+mUid+"/tool-reset/c21ac760-20dc-4020-a2c1-1b11be4fc506");
				Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				mWebView.loadUrl("http://slateisb.nu.edu.pk/portal/pda/~"+mUid+"/tool-reset/533fa498-2d17-458d-9429-34fdeb6146bf");
				Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
				break;
			case 5:
				mWebView.loadUrl("http://slateisb.nu.edu.pk/portal/pda/~"+mUid+"/tool-reset/5fc24ee3-00ca-4327-a328-feaca59cfaa8");
				Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
				break;
			case 6:
				mWebView.loadUrl("http://slateisb.nu.edu.pk/portal/pda/~"+mUid+"/tool-reset/22a46ea9-ab9d-4eca-b6e4-f4b2e162fc96");
				Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
				break;
			}
			Log.d("Testing", "Clicked Ok");
			mDrawerList.setItemChecked(position, true);
			//mNav.toggleDrawer();
			mNav.close();
			// Close drawer
		}

		private class AppItemClickListener implements
			ListView.OnItemClickListener {
			@Override	
		public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				selectApp(position);
			}
		}

		private void selectApp(int position) {

	switch (position) {
	case 0:
		
		mWebView.loadUrl(mSharedPreferences.getString("Link", ""));
		Toast.makeText(this, "App Clicked", Toast.LENGTH_SHORT).show();
		break;
	case 1:
		// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.report_dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		final EditText mName = (EditText) promptsView
				.findViewById(R.id.report_name);
		final EditText mError=(EditText) promptsView.findViewById(R.id.report_error);

		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("Report",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				// get user input and set it to result
				// edit text
			    	new UploadReport().execute(mName.getText().toString(),mError.getText().toString());
				//result.setText(userInput.getText());
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

		break;
	}
	//mWebView.loadUrl(as);
	Log.d("Testing", "Clicked Ok");
	mAppList.setItemChecked(position, true);
	mNav.toggleDrawer();
	// Close drawer
}

		@Override
		protected void onPostCreate(Bundle savedInstanceState) {
			super.onPostCreate(savedInstanceState);
			// Sync the toggle state after onRestoreInstanceState has occurred.
		}

		@Override
		public void onConfigurationChanged(Configuration newConfig) {
			super.onConfigurationChanged(newConfig);
			// Pass any configuration change to the drawer toggles
		}
		public boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager 
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
		private class UploadReport extends AsyncTask<String, Void, Void>{
			@Override
			protected void onPreExecute() {
	            super.onPreExecute();
	            
	            Log.d("Test ON pre", "Running Ok");
			}
			@Override
			protected Void doInBackground(String... args) {
				// TODO Auto-generated method stub
				String mName = args[0];
				String mError=args[1];
				ParseObject gameScore = new ParseObject("Report");
		        gameScore.put("Name", mName);
		        gameScore.put("Error", mError);
		        gameScore.put("Network", "Wifi");
		        gameScore.saveInBackground();
		        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
	                    "Quotes");
	            query.orderByDescending("_created_at");
	            try {
	                ob = query.find();
	            } catch (ParseException e) {
	                Log.e("Error", e.getMessage());
	                e.printStackTrace();
	            }
		        //Intent i = getIntent();
				return null;
			}
			@Override
			 protected void onPostExecute(Void result) {
				Log.d("Report", "Report Ok");
			}

			
		}
		private class Update extends AsyncTask<String, Void, Void>{
			@Override
			protected void onPreExecute() {
	            super.onPreExecute();
	            //Toast.makeText(getApplicationContext(), "Refreshing", Toast.LENGTH_SHORT).show();
	            Log.d("Test ON pre", "Running Ok");
			}
			@Override
			protected Void doInBackground(String... args) {
				// TODO Auto-generated method stub
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
	                    "Quotes");
	            query.orderByDescending("_created_at");
	            try {
	                ob = query.find();
	            } catch (ParseException e) {
	                Log.e("Error", e.getMessage());
	                e.printStackTrace();
	            }
		        //Intent i = getIntent();

	            mLoad();
				return null;
			}
			@Override
			 protected void onPostExecute(Void result) {
				Log.d("Report", "Report Ok");
				int i=0;
				for (ParseObject country : ob) {
					mQuote.setText((String)country.get("name"));
					if(i==0){
					mPrefernceEditor1.putString("Link", (String)country.get("name"));
					//mPrefernceEditor1.putString("LoginCheck", "1");
					mPrefernceEditor1.commit();
					i++;}
					else{
						mPrefernceEditor1.putString("Quote", (String)country.get("name"));
					mPrefernceEditor1.commit();}
					// Toast.makeText(getApplicationContext(), "Refreshed", Toast.LENGTH_SHORT).show();;
	            }
			}

			
		}

}
