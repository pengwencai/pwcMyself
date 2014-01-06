package com.example.forliuyanghong;

import android.os.Bundle;
import android.app.Activity;
import android.telephony.TelephonyManager;
import android.view.Menu;

public class MainActivity extends Activity {
private final static String UID="D3WSBCEP5MB7AH6WG76J";
private final static String APPID="com.tutk.IPCloud.2(Android)";
private static String imei="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 TelephonyManager localTelephonyManager = (TelephonyManager)MainActivity.this.getSystemService("phone");
		 imei=localTelephonyManager.getDeviceId();
	      HttpGetTool localHttpGetTool = new HttpGetTool(MainActivity.this, MainActivity.class.getName());
	      String[] arrayOfString = new String[1];
	      arrayOfString[0] = ("http://push.iotcplatform.com/apns/apns.php?cmd=reg_mapping&token=" + DatabaseManager.s_GCM_token + "&appid=" + APPID + "&uid=" + UID + "&imei=" + imei);
	      localHttpGetTool.execute(arrayOfString);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
