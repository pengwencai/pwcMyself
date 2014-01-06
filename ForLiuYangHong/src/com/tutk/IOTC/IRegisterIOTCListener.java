package com.tutk.IOTC;

import android.graphics.Bitmap;

public abstract interface IRegisterIOTCListener
{
  public abstract void receiveChannelInfo(Camera paramCamera, int paramInt1, int paramInt2);

  public abstract void receiveFrameData(Camera paramCamera, int paramInt, Bitmap paramBitmap);

  public abstract void receiveFrameInfo(Camera paramCamera, int paramInt1, long paramLong, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public abstract void receiveIOCtrlData(Camera paramCamera, int paramInt1, int paramInt2, byte[] paramArrayOfByte);

  public abstract void receiveSessionInfo(Camera paramCamera, int paramInt);
}