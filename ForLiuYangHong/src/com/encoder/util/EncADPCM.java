package com.encoder.util;

import java.io.PrintStream;

public class EncADPCM
{
  static
  {
    try
    {
      System.loadLibrary("ADPCMAndroid");
//      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      System.out.println("loadLibrary(ADPCMAndroid)," + localUnsatisfiedLinkError.getMessage());
    }
  }

  public static native int Encode(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2);

  public static native int ResetEncoder();
}