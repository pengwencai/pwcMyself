package com.example.forliuyanghong;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

public class DatabaseManager
{
  public static final String TABLE_DEVICE = "device";
  public static final String TABLE_SEARCH_HISTORY = "search_history";
  public static final String TABLE_SNAPSHOT = "snapshot";
  public static int n_mainActivity_Status = 0;
  public static final String s_GCM_PHP_URL = "http://push.iotcplatform.com/apns/apns.php";
  public static final String s_GCM_sender = "935793047540";
  public static String s_GCM_token = "";
  public static final String s_Package_name = "com.tutk.IPCloud.2(Android)";
  private DatabaseHelper mDbHelper;

  static
  {
    n_mainActivity_Status = 0;
  }

  public DatabaseManager(Context paramContext)
  {
    this.mDbHelper = new DatabaseHelper(paramContext);
  }

  public static Bitmap getBitmapFromByteArray(byte[] paramArrayOfByte)
  {
    return BitmapFactory.decodeStream(new ByteArrayInputStream(paramArrayOfByte), null, getBitmapOptions(2));
  }

  public static BitmapFactory.Options getBitmapOptions(int paramInt)
  {
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inPurgeable = true;
    localOptions.inInputShareable = true;
    localOptions.inSampleSize = paramInt;
    try
    {
      BitmapFactory.Options.class.getField("inNativeAlloc").setBoolean(localOptions, true);
      return localOptions;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return localOptions;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
      return localOptions;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return localOptions;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      localNoSuchFieldException.printStackTrace();
    }
    return localOptions;
  }

  public static byte[] getByteArrayFromBitmap(Bitmap paramBitmap)
  {
    if ((paramBitmap != null) && (!paramBitmap.isRecycled()))
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      paramBitmap.compress(Bitmap.CompressFormat.PNG, 0, localByteArrayOutputStream);
      return localByteArrayOutputStream.toByteArray();
    }
    return null;
  }

  public long addDevice(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt1, int paramInt2, int paramInt3)
  {
    SQLiteDatabase localSQLiteDatabase = this.mDbHelper.getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("dev_nickname", paramString1);
    localContentValues.put("dev_uid", paramString2);
    localContentValues.put("dev_name", paramString3);
    localContentValues.put("dev_pwd", paramString4);
    localContentValues.put("view_acc", paramString5);
    localContentValues.put("view_pwd", paramString6);
    localContentValues.put("event_notification", Integer.valueOf(paramInt1));
    localContentValues.put("camera_channel", Integer.valueOf(paramInt2));
    localContentValues.put("dev_videoQuality", Integer.valueOf(paramInt3));
    long l = localSQLiteDatabase.insertOrThrow("device", null, localContentValues);
    localSQLiteDatabase.close();
    return l;
  }

  public long addSearchHistory(String paramString, int paramInt, long paramLong1, long paramLong2)
  {
    SQLiteDatabase localSQLiteDatabase = this.mDbHelper.getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("dev_uid", paramString);
    localContentValues.put("search_event_type", Integer.valueOf(paramInt));
    localContentValues.put("search_start_time", Long.valueOf(paramLong1));
    localContentValues.put("search_stop_time", Long.valueOf(paramLong2));
    long l = localSQLiteDatabase.insertOrThrow("search_history", null, localContentValues);
    localSQLiteDatabase.close();
    return l;
  }

  public long addSnapshot(String paramString1, String paramString2, long paramLong)
  {
    SQLiteDatabase localSQLiteDatabase = this.mDbHelper.getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("dev_uid", paramString1);
    localContentValues.put("file_path", paramString2);
    localContentValues.put("time", Long.valueOf(paramLong));
    long l = localSQLiteDatabase.insertOrThrow("snapshot", null, localContentValues);
    localSQLiteDatabase.close();
    return l;
  }

  public SQLiteDatabase getReadableDatabase()
  {
    return this.mDbHelper.getReadableDatabase();
  }

  public void removeDeviceByUID(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = this.mDbHelper.getWritableDatabase();
    localSQLiteDatabase.delete("device", "dev_uid = '" + paramString + "'", null);
    localSQLiteDatabase.close();
  }

  public void removeSnapshotByUID(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = this.mDbHelper.getWritableDatabase();
    localSQLiteDatabase.delete("snapshot", "dev_uid = '" + paramString + "'", null);
    localSQLiteDatabase.close();
  }

  public void updateDeviceAskFormatSDCardByUID(String paramString, boolean paramBoolean)
  {
    SQLiteDatabase localSQLiteDatabase = this.mDbHelper.getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      localContentValues.put("ask_format_sdcard", Integer.valueOf(i));
      localSQLiteDatabase.update("device", localContentValues, "dev_uid = '" + paramString + "'", null);
      localSQLiteDatabase.close();
      return;
    }
  }

  public void updateDeviceChannelByUID(String paramString, int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.mDbHelper.getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("camera_channel", Integer.valueOf(paramInt));
    localSQLiteDatabase.update("device", localContentValues, "dev_uid = '" + paramString + "'", null);
    localSQLiteDatabase.close();
  }

  public void updateDeviceInfoByDBID(long paramLong, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt1, int paramInt2, int paramInt3)
  {
    SQLiteDatabase localSQLiteDatabase = this.mDbHelper.getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("dev_uid", paramString1);
    localContentValues.put("dev_nickname", paramString2);
    localContentValues.put("dev_name", paramString3);
    localContentValues.put("dev_pwd", paramString4);
    localContentValues.put("view_acc", paramString5);
    localContentValues.put("view_pwd", paramString6);
    localContentValues.put("event_notification", Integer.valueOf(paramInt1));
    localContentValues.put("camera_channel", Integer.valueOf(paramInt2));
    localContentValues.put("dev_videoQuality", Integer.valueOf(paramInt3));
    localSQLiteDatabase.update("device", localContentValues, "_id = '" + paramLong + "'", null);
    localSQLiteDatabase.close();
  }

  public void updateDeviceSnapshotByUID(String paramString, Bitmap paramBitmap)
  {
    SQLiteDatabase localSQLiteDatabase = this.mDbHelper.getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("snapshot", getByteArrayFromBitmap(paramBitmap));
    localSQLiteDatabase.update("device", localContentValues, "dev_uid = '" + paramString + "'", null);
    localSQLiteDatabase.close();
  }

  public void updateDeviceSnapshotByUID(String paramString, byte[] paramArrayOfByte)
  {
    SQLiteDatabase localSQLiteDatabase = this.mDbHelper.getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("snapshot", paramArrayOfByte);
    localSQLiteDatabase.update("device", localContentValues, "dev_uid = '" + paramString + "'", null);
    localSQLiteDatabase.close();
  }

  private class DatabaseHelper extends SQLiteOpenHelper
  {
    private static final String DB_FILE = "IOTCamViewer.db";
    private static final int DB_VERSION = 6;
    private static final String SQLCMD_CREATE_TABLE_DEVICE = "CREATE TABLE device(_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, dev_nickname\t\t\tNVARCHAR(30) NULL, dev_uid\t\t\t\tVARCHAR(20) NULL, dev_name\t\t\t\tVARCHAR(30) NULL, dev_pwd\t\t\t\tVARCHAR(30) NULL, view_acc\t\t\t\tVARCHAR(30) NULL, view_pwd\t\t\t\tVARCHAR(30) NULL, event_notification \tINTEGER, ask_format_sdcard\t\tINTEGER,camera_channel\t\t\tINTEGER, snapshot\t\t\t\tBLOB,dev_videoQuality\t\t\t\tINTEGER);";
    private static final String SQLCMD_CREATE_TABLE_SEARCH_HISTORY = "CREATE TABLE search_history(_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, dev_uid\t\t\tVARCHAR(20) NULL, search_event_type\tINTEGER, search_start_time\tINTEGER, search_stop_time\tINTEGER);";
    private static final String SQLCMD_CREATE_TABLE_SNAPSHOT = "CREATE TABLE snapshot(_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, dev_uid\t\t\tVARCHAR(20) NULL, file_path\t\t\tVARCHAR(80), time\t\t\t\tINTEGER);";
    private static final String SQLCMD_DROP_TABLE_DEVICE = "drop table if exists device;";
    private static final String SQLCMD_DROP_TABLE_SEARCH_HISTORY = "drop table if exists search_history;";
    private static final String SQLCMD_DROP_TABLE_SNAPSHOT = "drop table if exists snapshot;";

    public DatabaseHelper(Context arg2)
    {
      super(arg2,"IOTCamViewer.db", null, 6);
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("CREATE TABLE device(_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, dev_nickname\t\t\tNVARCHAR(30) NULL, dev_uid\t\t\t\tVARCHAR(20) NULL, dev_name\t\t\t\tVARCHAR(30) NULL, dev_pwd\t\t\t\tVARCHAR(30) NULL, view_acc\t\t\t\tVARCHAR(30) NULL, view_pwd\t\t\t\tVARCHAR(30) NULL, event_notification \tINTEGER, ask_format_sdcard\t\tINTEGER,camera_channel\t\t\tINTEGER, snapshot\t\t\t\tBLOB,dev_videoQuality\t\t\t\tINTEGER);");
      paramSQLiteDatabase.execSQL("CREATE TABLE search_history(_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, dev_uid\t\t\tVARCHAR(20) NULL, search_event_type\tINTEGER, search_start_time\tINTEGER, search_stop_time\tINTEGER);");
      paramSQLiteDatabase.execSQL("CREATE TABLE snapshot(_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, dev_uid\t\t\tVARCHAR(20) NULL, file_path\t\t\tVARCHAR(80), time\t\t\t\tINTEGER);");
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      paramSQLiteDatabase.execSQL("drop table if exists device;");
      paramSQLiteDatabase.execSQL("drop table if exists search_history;");
      paramSQLiteDatabase.execSQL("drop table if exists snapshot;");
      onCreate(paramSQLiteDatabase);
    }
  }
}
