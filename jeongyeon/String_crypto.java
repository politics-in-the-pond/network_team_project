package my_package;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;

public class String_crypto {
	
	final static int N = 624;	//MT19937_32 initialization
	final static int M = 397;
	final static long MATRIX_A = 0x9908b0dfL;
	final static long UMASK = 0x80000000L;
	final static long LMASK = 0x7fffffffL;
	static long mt[] = new long[N];
	static int mti=N+1;
	
	private static byte[] encrypt(String s) {
		
		byte[] buffer;
		byte[] check_sum;
		
		buffer = s.getBytes();
		check_sum = checksum(buffer);
		
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
		
		//AES128 암호화 실행 key = key[]
		
		return (byte[]) null;
	}
	
	private static String decrypt(byte[] s) {
		byte[] check_sum = null;
		
		byte seed1[] = new byte[4];
		byte seed2[] = new byte[4];
		byte seed3[] = new byte[4];
		byte seed4[] = new byte[4];
		
		System.arraycopy(s, s.length-20, check_sum, 0, 20);
		
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
	
	private static byte[] MT19937(byte[] seed) { //메르센 트위스터 구현부
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
	
	public static long genrand_int32() {
		
		return 0;
	}
	
	@SuppressWarnings("finally")
	private static byte[] checksum(byte[] message_bin) {
		try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(message_bin);
            byte byteData[] = md.digest();
            return byteData;
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace(); 
        } finally {
        	byte error[] = new byte[2];
        	return error;
        }
	}
	
	public static int btoi(byte[] arr){
		return (arr[0] & 0xff)<<24 | (arr[1] & 0xff)<<16 | (arr[2] & 0xff)<<8 | (arr[3] & 0xff); 
	}
	
	public static void main(String[] args) {
		byte[] a = new byte[4];
		a[0] = 1;
		a[1] = 2;
		a[2] = 3;
		a[3] = 4;
		System.out.println(MT19937(a));
		a[0] = 4;
		a[1] = 32;
		a[2] = 1;
		a[3] = 4;
		System.out.println(MT19937(a));
		a[0] = 8;
		a[1] = 4;
		a[2] = 0;
		a[3] = 7;
		System.out.println(MT19937(a));
	}

}
