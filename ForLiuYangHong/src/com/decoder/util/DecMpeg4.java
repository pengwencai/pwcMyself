package com.decoder.util;

import java.io.PrintStream;

public class DecMpeg4
{
  static
  {
    try
    {
      System.loadLibrary("FFmpeg");
//      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      System.out.println("loadLibrary(FFmpeg)," + localUnsatisfiedLinkError.getMessage());
    }
  }

  public static native int Decode(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3);

  public static native int InitDecoder(int paramInt1, int paramInt2);

  public static native int UninitDecoder();
}