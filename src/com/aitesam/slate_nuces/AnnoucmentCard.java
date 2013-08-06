package com.aitesam.slate_nuces;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.fima.cardsui.objects.Card;

public class AnnoucmentCard extends Card {
public String title,link;
public WebView mWebView;
SharedPreferences mSharedPreferences;
public AnnoucmentCard(String title,String link){
		this.title=title;
		this.link=link;
	}

	@Override
	public View getCardContent(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.web_card, null);
		mSharedPreferences = context.getSharedPreferences("com.slateisb.android.aitesam",Context.MODE_PRIVATE);
		((TextView) view.findViewById(R.id.title)).setText(title);
		String asds="<html><body><p>Hello Mobile</p></body></html>";
		mWebView=(WebView) view.findViewById(R.id.webtest);
        //mWebView=(WebView)findViewById(R.id.web_frag);
		//mWebView.setWebChromeClient(new WebChromeClient());
		//Setting Up WebView
		WebSettings webSettings = mWebView.getSettings();
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setJavaScriptEnabled(true);
        //webSettings.setAppCachePath(this.getFilesDir().getPath());
        webSettings.setSupportZoom(false);
        
        mWebView.setWebViewClient(new WebViewClient() {
        	@Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
            	//getSherlock().setProgressBarIndeterminateVisibility(true);
               if(url.equals("http://slateisb.nu.edu.pk/portal/pda/?force.logout=yes")){
        
            		return true;
            	}
                view.loadUrl(url);
                return false; 
           }
            @Override
            public void onPageFinished(WebView view, String url) {
            	 view.loadUrl("javascript:(function(e,a,g,h,f,c,b,d){if(!(f=e.jQuery)||g>f.fn.jquery||h(f)){c=a.createElement('script');c.type='text/javascript';c.src='http://ajax.googleapis.com/ajax/libs/jquery/'+g+'/jquery.min.js';c.onload=c.onreadystatechange=function(){if(!b&&(!(d=this.readyState)||d=='loaded'||d=='complete')){h((f=e.jQuery).noConflict(1),b=1);f(c).remove()}};a.documentElement.childNodes[0].appendChild(c)}})(window,document,'1.3.2',function($,L){$('#pda-footer').hide();});");
            	 view.loadUrl("javascript:(function(e,a,g,h,f,c,b,d){if(!(f=e.jQuery)||g>f.fn.jquery||h(f)){c=a.createElement('script');c.type='text/javascript';c.src='http://ajax.googleapis.com/ajax/libs/jquery/'+g+'/jquery.min.js';c.onload=c.onreadystatechange=function(){if(!b&&(!(d=this.readyState)||d=='loaded'||d=='complete')){h((f=e.jQuery).noConflict(1),b=1);f(c).remove()}};a.documentElement.childNodes[0].appendChild(c)}})(window,document,'1.3.2',function($,L){$('#pda-portlet-menu').hide();});");
        
            }

        });
        
        String mCookie=mSharedPreferences.getString("cookie", "");
		//Setting Up Cookies
       // mCookies=readFromFile("config1.txt");
        Log.d("Cookie_Read_Preference", mCookie);//For Debugging
        CookieSyncManager.createInstance(context);
	       CookieManager cookieManager = CookieManager.getInstance();
	       cookieManager.removeAllCookie();
	        cookieManager.setAcceptCookie(true);
	        cookieManager.setCookie("http://www.slateisb.nu.edu.pk", mCookie);
	     
		mWebView.loadUrl(link);
		//((WebView) view.findViewById(R.id.webtest)).loadData(asds, "text/html; charset=UTF-8", null);
		//((WebView) view.findViewById(R.id.webtest)).loadUrl(link);
		return view;
	}

	
	
	
}