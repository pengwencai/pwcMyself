package com.decoder.util;

import java.io.PrintStream;

public class DecADPCM
{
  static
  {
    try
    {
      System.loadLibrary("ADPCMAndroid");
      
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      System.out.println("loadLibrary(ADPCMAndroid)," + localUnsatisfiedLinkError.getMessage());
    }
  }

  public static native int Decode(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2);

  public static native int ResetDecoder();
}