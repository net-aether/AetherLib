package net.aether.lib.misc;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import net.aether.lib.annotation.Since;
import net.aether.lib.annotation.WIP;

/**
 * This class provides various utility methods regarding math
 * 
 * @author Cheos
 */
@Since(V0_0_1)
public final class MathUtils {
	/**
	 * @param a
	 * @param b
	 * @return the greatest common denominator of a and b
	 */
	public static int gcd(int a, int b) {
		int t;
		while (b != 0) {
			t = b;
			b = a % b;
			a = t;
		}
		return a;
	}

	/**
	 * @param a
	 * @param b
	 * @return the greatest common denominator of a and b
	 */
	public static long gcd(long a, long b) {
		long t;
		while (b != 0) {
			t = b;
			b = a % b;
			a = t;
		}
		return a;
	}
	
	/**
	 * @param i
	 * @return true if i is an even number, otherwise false
	 */
	public static boolean isEven(int i) {
		return i % 2 == 0;
	}

	/**
	 * @param i
	 * @return true if i is an even number, otherwise false
	 */
	public static boolean isEven(long i) {
		return i % 2 == 0;
	}
	
	/**
	 * The Primes subclass focuses on methods regarding primes
	 * 
	 * @author Cheos
	 */
	@WIP
	public static final class Primes {
		/**
		 * @param a
		 * @param b
		 * @return true, if a and b do not share any denominators and are thus coprime, otherwise false
		 */
		public static boolean areCoprime(int a, int b) {
			return gcd(a, b) == 1;
		}

		/**
		 * @param a
		 * @param b
		 * @return true, if a and b do not share any denominators and are thus coprime, otherwise false
		 */
		public static boolean areCoprime(long a, long b) {
			return gcd(a, b) == 1;
		}
		
		/**
		 * Note: this method is very inefficient, especially for larger numbers.
		 * {@link #isPrime(int)} aims to implement a more efficient algorithm, but is not implemented yet
		 * 
		 * @param i
		 * @return true if i is prime, otherwise false
		 */
		public static boolean isTrulyPrime(int i) {
			if (i <= 3) return i > 1;
			if (i % 2 == 0 || i % 3 == 0) return false;
			
			int j = 5;
			while (j * j <= i) {
				if (i % j == 0 || i % (j + 2) == 0) return false;
				j += 6;
			}
			return true;
		}
		
		/**
		 * Not implemented yet
		 * @param i
		 * @return
		 */
		@WIP(since="2021-03-03")
		public static boolean isPrime(int i) {
			
			return false;
		}

		/**
		 * Not implemented yet
		 * @param i
		 * @return
		 */
		@WIP(since="2021-03-03")
		public static boolean isPrime(long i) {
			
			return false;
		}
	}
}
