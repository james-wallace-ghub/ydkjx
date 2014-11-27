/*
 * Copyright &copy; 2011 Rebecca G. Bettencourt / Kreative Software
 * IMA4 Component Copyright &copy; 2014 James Wallace
 * <p>
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * <a href="http://www.mozilla.org/MPL/">http://www.mozilla.org/MPL/</a>
 * <p>
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * <p>
 * Alternatively, the contents of this file may be used under the terms
 * of the GNU Lesser General Public License (the "LGPL License"), in which
 * case the provisions of LGPL License are applicable instead of those
 * above. If you wish to allow use of your version of this file only
 * under the terms of the LGPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the LGPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the LGPL License.
 * @since KSFL 1.2
 * @author Rebecca G. Bettencourt, Kreative Software; James Wallace.
 */

package com.kreative.rsrc.misc;

public class IMA4Decoder {
	
    private static IMA4State ima4state;
	private static class IMA4State 
	{ 
		public int prevpred; 
	    public int predindex; 
	    public byte[] outp;
	    public byte[] inp;
	    public IMA4State(byte[] in, byte[] out) {
	        outp = out; 
	        inp = in; 
		}
	} 

    public static int indexTable[] = { 
        -1, -1, -1, -1, 2, 4, 6, 8,  
        -1, -1, -1, -1, 2, 4, 6, 8 
    }; 
    public static int stepsizeTable[] = { 
        7, 8, 9, 10, 11, 12, 13, 14, 16, 17,  
        19, 21, 23, 25, 28, 31, 34, 37, 41, 45,  
        50, 55, 60, 66, 73, 80, 88, 97, 107, 118,  
        130, 143, 157, 173, 190, 209, 230, 253, 279, 307,  
        337, 371, 408, 449, 494, 544, 598, 658, 724, 796,  
        876, 963, 1060, 1166, 1282, 1411, 1552, 1707, 1878, 2066,  
        2272, 2499, 2749, 3024, 3327, 3660, 4026, 4428, 4871, 5358,  
        5894, 6484, 7132, 7845, 8630, 9493, 10442, 11487, 12635, 13899,  
        15289, 16818, 18500, 20350, 22385, 24623, 27086, 29794, 32767 
    }; 
    
	 public static int decode(byte indata[], int inOffset, byte outdata[], int outOffset, int len, IMA4State state, int stride) 
	 { 
	        int inputbuffer = 0; 
	        boolean firstrun = false; 
	        int valpred = state.prevpred; 
	        int index = state.predindex; 
	        int lastIndex = index; 
	        for(; len > 0; len--) 
	        { 
	            int indexnibbles; 
	            if(firstrun) 
	            { 
	                indexnibbles = inputbuffer >> 4 & 0xf; 
	            } else 
	            { 
	                inputbuffer = state.inp[inOffset++]; 
	                indexnibbles = inputbuffer & 0xf; 
	            } 
	            firstrun = !firstrun; 
	            index += indexTable[indexnibbles]; 
	            if(index < 0) 
	                index = 0; 
	            else 
	            if(index > 88) 
	                index = 88; 
	             
	            
	            int sign = (indexnibbles & 8);
	            int step = stepsizeTable[lastIndex];
	            int delta = (indexnibbles & 7);
	            int diff = (step >> 3);
	            
	            if((delta & 4) != 0) diff += step;
	            if((delta & 2) != 0) diff += step >> 1;
	            if((delta & 1) != 0) diff += step >> 2;
	            //flip the sign
	            if(sign != 0) 
                    diff = -diff;
	            
	            valpred += diff; 
	            if(valpred > 32767) 
	                valpred = 32767; 
	            else 
	            if(valpred < -32768) 
	                valpred = -32768; 
	            lastIndex = index; 
	            state.outp[outOffset++] = (byte)(valpred >> 8); 
	            state.outp[outOffset++] = (byte)valpred; 
	            outOffset += stride; 
	        } 
	 
	        state.prevpred = valpred; 
	        state.predindex = index; 
	        return outOffset;
	    } 
	
	private static byte[] decompressStream4(byte[] in, byte[] out, int channels) 
	{
	      ima4state=new IMA4State(in,out);

	      return decodeIMA4(in, out, in.length,out.length, channels);
	}
		
    static byte[] decodeIMA4(byte[] in, byte[] out, int inlen, int outlen,int channels)
    {
      switch (channels) 
      {
          // 32 bytes IMA4 chunk size (with 2 for prediction)
          case 1: //mono
          return decodeIMA4mono(in,out,inlen,32); 
          case 2: //stereo
          return decodeIMA4stereo(in,out,inlen,32);
          default:
          return null;
      }
    }

    private static byte[] decodeIMA4mono(byte[] in, byte[] out, int inlen, int blockSize)
    {
      int inCount  = 0;
      int outCount = 0;
      // IMA4 mono chunk format is 2 bytes header followed by 32 bytes encoded data
      //we need to manipulate
      inlen = (inlen / (blockSize+2) ) * (blockSize+2);

      while (inCount<inlen) {
        int pred = (in[inCount++] << 8);
            pred |=  (in[inCount++] & 0xff);

        int index = pred & 0x7F;

        if (index>88)
            index=88;
        //Gah, signed types!
        ima4state.prevpred=pred & 0xFFFFFF80 ;
        ima4state.predindex=index;

        decode(in,inCount,out,outCount,blockSize<<1,ima4state,0);

        inCount  += blockSize;
        outCount += blockSize<<2;
      }

      return ima4state.outp;
    }
    
    private static byte[] decodeIMA4stereo(byte[] in, byte[] out, int inlen, int blockSize)
    {
      int inCount = 0;
      int outCount = 0;
      // IMA4 stereo chunks are interleaved, so we bounce between channels
      inlen = (inlen / 2 /(blockSize+2) ) * (blockSize+2)*2;

      // IMA4 stereo chunk format is left IMA4 mono chunk followed by right IMA4 mono chunk
      while (inCount<inlen) 
      {
        //LEFT
        int predL = (in[inCount++] << 8);
            predL |=  (in[inCount++] & 0xff);

        int indexL = predL & 0x7F;

        if (indexL>88)
            indexL=88;
        //Gah, signed types!
        ima4state.prevpred=predL & 0xFFFFFF80 ;
        ima4state.predindex=indexL;


        decode(in,inCount,out,outCount,blockSize<<1,ima4state,2);

        inCount += blockSize;

        //RIGHT
        int prevR = (in[inCount++] << 8);
        prevR |=  (in[inCount++] & 0xff);

        int indexR = prevR & 0x7F;

        if (indexR>88)
            indexR=88;

        //Gah, signed types!
        ima4state.prevpred=prevR & 0xFFFFFF80 ;
        ima4state.predindex=indexR;


        decode(in, inCount, out, outCount+2, blockSize<<1, ima4state, 2);

        //loop counters
        inCount += blockSize;
        outCount += blockSize<<3;
      }

      return ima4state.outp;
    }

	public static byte[] decompressIMA4(byte[] compressedData, int channels) {
		byte[] decompressedData = new byte[compressedData.length*4];
		byte[] ret = decompressStream4(compressedData, decompressedData, channels);
		return ret;
	}
}
