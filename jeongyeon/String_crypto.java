package my_package;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;

public class String_crypto {
	
	final int N = 624;	//MT19937_32 initialization
	final int M = 397;
	final long MATRIX_A = 0x9908b0dfL;
	final long UMASK = 0x80000000L;
	final long LMASK = 0x7fffffffL;

	private byte[] encrypt(String s) {
		
		byte[] buffer;
		byte[] check_sum = new byte[20];
		
		buffer = s.getBytes();
		check_sum = checksum(buffer);
		
		StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < check_sum.length; i++)
            hexString.append(Integer.toHexString(0xFF & check_sum[i]));
        
        String s3 = hexString.toString();
				
		byte seed1[] = new byte[4];
		byte seed2[] = new byte[4];
		byte seed3[] = new byte[4];
		byte seed4[] = new byte[4];
		
		System.arraycopy(check_sum, 0, seed1, 0, 4);
		System.arraycopy(check_sum, 4, seed2, 0, 4);
		System.arraycopy(check_sum, 8, seed3, 0, 4);
		System.arraycopy(check_sum, 12, seed4, 0, 4);
		
		byte key[] = new byte[16];
		byte tempkey[] = new byte[4];
		
		tempkey = MT19937(seed1);
		System.arraycopy(tempkey, 0, key, 0, 4);
		tempkey = MT19937(seed2);
		System.arraycopy(tempkey, 0, key, 4, 4);
		tempkey = MT19937(seed3);
		System.arraycopy(tempkey, 0, key, 8, 4);
		tempkey = MT19937(seed4);
		System.arraycopy(tempkey, 0, key, 12, 4);
		
		StringBuffer hexString2 = new StringBuffer();
        for (int i = 0; i < key.length; i++)
            hexString2.append(Integer.toHexString(0xFF & key[i]));
        
        String s1 = hexString2.toString();
		
		System.out.println("문자열 : " + s + "		/해시 : " + s3 + "		/AES키 : " + s1);
		
		//AES128 암호화 실행 key = key[]
		
		return (byte[]) null;
	}
	
	
	
	private String decrypt(byte[] b) {
		byte[] check_sum = new byte[20];
		byte[] s = null;
		
		byte seed1[] = new byte[4];
		byte seed2[] = new byte[4];
		byte seed3[] = new byte[4];
		byte seed4[] = new byte[4];
		
		System.arraycopy(b, b.length-20, check_sum, 0, 20);
		System.arraycopy(b, 0, s, 0, b.length-20);
		
		System.arraycopy(check_sum, 0, seed1, 0, 4);
		System.arraycopy(check_sum, 4, seed2, 0, 4);
		System.arraycopy(check_sum, 8, seed3, 0, 4);
		System.arraycopy(check_sum, 12, seed4, 0, 4);
		
		byte key[] = new byte[16];
		byte tempkey[] = new byte[4];
		
		tempkey = MT19937(seed1);
		System.arraycopy(tempkey, 0, key, 0, 4);
		tempkey = MT19937(seed2);
		System.arraycopy(tempkey, 0, key, 4, 4);
		tempkey = MT19937(seed3);
		System.arraycopy(tempkey, 0, key, 8, 4);
		tempkey = MT19937(seed4);
		System.arraycopy(tempkey, 0, key, 12, 4);
	
		//AES128 복호화 실행 key = key[]
		
		return (String) null;
	}
	
	private byte[] MT19937(byte[] seed) { //메르센 트위스터 구현부. unsigned형이 없어 원본의 MT19937과는 결과값이 다를걸로 예상.
		
		long mt[] = new long[N];
		int mti=N+1;
		
		int s = btoi(seed);
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
		
		ByteBuffer buf = ByteBuffer.allocate(8);
		buf.putLong(y);
		byte[] result = buf.array();
		
		return (byte[]) result;
	}
	
	private byte[] checksum(byte[] message_bin){
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(message_bin);
            byte[] byteData = new byte[20];
            byteData = md.digest();
            return byteData;
            
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return message_bin;
	}
	
	private int btoi(byte[] arr){
		return (arr[0] & 0xff)<<24 | (arr[1] & 0xff)<<16 | (arr[2] & 0xff)<<8 | (arr[3] & 0xff); 
	}
	
	public byte[] do_encrypt(String s){
		return this.encrypt(s);
	}
	
	public String do_decrypt(byte[] b){
		return this.decrypt(b);
	}

}
