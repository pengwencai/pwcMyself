package com.example.forliuyanghong;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpGetTool extends AsyncTask<String, Void, String>
{
  private Context context;
  private String from;
  private boolean isConnect;

  public HttpGetTool(Context paramContext, String paramString)
  {
    this.context = paramContext;
    this.from = paramString;
    this.isConnect = false;
  }

  protected String doInBackground(String[] paramArrayOfString)
  {
    boolean bool = this.isConnect;
    String str = null;
    DefaultHttpClient localDefaultHttpClient = null;
    HttpGet localHttpGet = null;
    BasicResponseHandler localBasicResponseHandler = null;
    if (bool)
    {
      str = null;
      if (paramArrayOfString != null)
      {
        int i = paramArrayOfString.length;
        str = null;
        if (i != 0)
        {
          localDefaultHttpClient = new DefaultHttpClient();
          localHttpGet = new HttpGet(paramArrayOfString[0]);
          localHttpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
          localBasicResponseHandler = new BasicResponseHandler();
        }
      }
    }
    try
    {
      str = (String)localDefaultHttpClient.execute(localHttpGet, localBasicResponseHandler);
      localDefaultHttpClient.getConnectionManager().shutdown();
      if ((!isCancelled()) && (str != null))
      {
        Intent localIntent = new Intent(this.from);
        localIntent.putExtra("result", str);
        this.context.sendBroadcast(localIntent);
      }
      if (str != null)
        System.out.println(str);
      return str;
    }
    catch (Exception localException)
    {
      while (true)
      {
        System.out.println("error");
        str = null;
      }
    }
  }

  protected void onPostExecute(String paramString)
  {
  }

  protected void onPreExecute()
  {
    super.onPreExecute();
    NetworkInfo localNetworkInfo = ((ConnectivityManager)this.context.getSystemService("connectivity")).getActiveNetworkInfo();
    if ((localNetworkInfo != null) && (localNetworkInfo.isConnected()) && (localNetworkInfo.isAvailable()))
      this.isConnect = true;
  }
}
