package com.decoder.util;

import java.io.PrintStream;

public class DecMp3
{
  static
  {
    try
    {
      System.loadLibrary("Mp3Android");
//      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      System.out.println("loadLibrary(Mp3Android)," + localUnsatisfiedLinkError.getMessage());
    }
  }

  public static native int Decode(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2);

  public static native int InitDecoder(int paramInt1, int paramInt2);

  public static native int UninitDecoder();
}