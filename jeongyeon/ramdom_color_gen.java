package my_package;

import java.util.Arrays;

public class random_color {
	
	final int N = 624;	//MT19937_32 initialization
	final int M = 397;
	final long MATRIX_A = 0x9908b0dfL;
	final long UMASK = 0x80000000L;
	final long LMASK = 0x7fffffffL;
	
	public int[] pick_color(String s) {
		int seed = 0;
		for(int i=0;i<s.length();i++) {
			seed += (int) s.charAt(i);
		}
		System.out.println(seed);
		int Hue = MT19937(seed); //0~359
		double saturation = 0.4;
		double value = 1;
		
		double c = value * saturation;
		double x = c * (1-Math.abs(((Hue*1.0/60) % 2) -1));
		double m = value - c;
		
		int[] color = get_RGB(c,x,Hue,m);
		return color;
	}
	
/*
   Copyright (C) 1997 - 2002, Makoto Matsumoto and Takuji Nishimura,
   All rights reserved.

   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions
   are met:

     1. Redistributions of source code must retain the above copyright
        notice, this list of conditions and the following disclaimer.

     2. Redistributions in binary form must reproduce the above copyright
        notice, this list of conditions and the following disclaimer in the
        documentation and/or other materials provided with the distribution.

     3. The names of its contributors may not be used to endorse or promote
        products derived from this software without specific prior written
        permission.

   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
   "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
   A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
   CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
   EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
   PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
   PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
   LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
   NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
	private int MT19937(int s) { //메르센 트위스터 구현부. unsigned형이 없어 원본의 MT19937과는 결과값이 다를 수도 있을 것으로 예상. 난수생성에는 문제 없는듯함.
		
		long mt[] = new long[N];
		int mti=N+1;
		mt[0] = s & 0xFFFFFFFF;
		for(mti=1;mti<N;mti++) {
			mt[mti] = (0x6C078965 * (mt[mti-1] ^ ( mt[mti-1] >> 30 )) + mti);
		}
		
		long y;
		long mag01[] = new long[2];
		mag01[0] = 0x0L;
		mag01[1] = MATRIX_A;
		
		if(mti>=N) {
			int kk;
			
			for(kk=0;kk<N-M;kk++) {
				y = (mt[kk] & UMASK) | (mt[kk+1] & LMASK);
				mt[kk] = mt[kk+M] ^ (y >> 1) ^ mag01[(int) (y & 0x1L)];
			}
			
			for(;kk<N-1;kk++) {
				y = (mt[kk] & UMASK) | (mt[kk+1] & LMASK);
				mt[kk] = mt[kk+(M-N)] ^ (y >> 1) ^ mag01[(int) (y & 0x1L)];
			}
			
			y = (mt[N-1] & UMASK) | (mt[0] & LMASK);
			mt[N-1] = mt[M-1] ^ (y >> 1) ^ mag01[(int) (y & 0x1L)];
			
			mti=0;
		}
		
		y = mt[mti++];
		
		y ^= (y >> 11);
		y ^= (y >> 7) & 0x9D2C6780L;
		y ^= (y >> 15) & 0xEFC60000L;
		y ^= (y >> 18);
		System.out.println(y);
		s = (int) (y%360);
		return s;
	}
	
	private int[] get_RGB(double c, double x, double h, double m) {
		double R = 0;
		double G = 0;
		double B = 0;
		
		if(0<=h&&h<60) {
			R=c;
			G=x;
			B=0;
		}
		if(60<=h&&h<120) {
			R=x;
			G=c;
			B=0;
		}
		if(120<=h&&h<180) {
			R=0;
			G=c;
			B=x;
		}
		if(180<=h&&h<240) {
			R=0;
			G=x;
			B=c;
		}
		if(240<=h&&h<300) {
			R=x;
			G=0;
			B=c;
		}
		if(300<=h&&h<360) {
			R=c;
			G=0;
			B=x;
		}
		
		//System.out.println("R : "+h);
		R=(R+m)*255;
		G=(G+m)*255;
		B=(B+m)*255;
		//System.out.println("R : "+R);
		return color_to_string(R,G,B);
	}
	
	private int[] color_to_string(double r, double g, double b) {
		int R = (int)r;
		int G = (int)g;
		int B = (int)b;
		//String tmp="";
		//tmp = tmp.concat(String.format("%02x", R));
		//System.out.println("R : "+tmp);
		//tmp = tmp.concat(String.format("%02x", G));
		//System.out.println("G : "+tmp);
		//tmp = tmp.concat(String.format("%02x", B));
		//System.out.println("B : "+tmp);
		int[] RGB = new int[3];
		RGB[0] = R;
		RGB[1] = G;
		RGB[2] = B;
		return RGB;
	}
	
	public static void main(String[] args) {
		random_color rc = new random_color();
		String seed = "안녕"; //예시
		System.out.println(Arrays.toString(rc.pick_color(seed)));
		seed = "안녕"; //예시
		System.out.println(Arrays.toString(rc.pick_color(seed)));
		seed = "김솦트"; //예시
		System.out.println(Arrays.toString(rc.pick_color(seed)));
		seed = "네트워크"; //예시
		System.out.println(Arrays.toString(rc.pick_color(seed)));
		seed = "힘들어"; //예시
		System.out.println(Arrays.toString(rc.pick_color(seed)));
		seed = "아아.ㅇ"; //예시
		System.out.println(Arrays.toString(rc.pick_color(seed)));
		seed = "안녕"; //예시
		System.out.println(Arrays.toString(rc.pick_color(seed)));
	}
}
