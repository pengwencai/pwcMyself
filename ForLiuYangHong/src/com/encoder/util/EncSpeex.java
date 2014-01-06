package com.encoder.util;

import java.io.PrintStream;

public class EncSpeex
{
  static
  {
    try
    {
      System.loadLibrary("SpeexAndroid");
//      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      System.out.println("loadLibrary(SpeexAndroid)," + localUnsatisfiedLinkError.getMessage());
    }
  }

  public static native int Encode(short[] paramArrayOfShort, int paramInt, byte[] paramArrayOfByte);

  public static native int InitEncoder(int paramInt);

  public static native int UninitEncoder();
}