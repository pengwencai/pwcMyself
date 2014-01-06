package com.decoder.util;

import java.io.PrintStream;

public class DecH264
{
  static
  {
    try
    {
      System.loadLibrary("H264Android");
//      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      System.out.println("loadLibrary(H264Android)," + localUnsatisfiedLinkError.getMessage());
    }
  }

  public static native int DecoderNal(byte[] paramArrayOfByte1, int paramInt, int[] paramArrayOfInt, byte[] paramArrayOfByte2, boolean paramBoolean);

  public static native int InitDecoder();

  public static native int UninitDecoder();
}