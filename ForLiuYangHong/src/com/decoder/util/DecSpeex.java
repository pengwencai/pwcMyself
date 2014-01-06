package com.decoder.util;

import java.io.PrintStream;

public class DecSpeex
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

  public static native int Decode(byte[] paramArrayOfByte, int paramInt, short[] paramArrayOfShort);

  public static native int InitDecoder(int paramInt);

  public static native int UninitDecoder();
}