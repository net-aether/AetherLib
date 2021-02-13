package net.aether.lib.security;

/**
 * Interface to be implemented by all classes that implement some type of encryption
 * 
 * @author Cheos
 *
 * @param <P> type for plain  "text"
 * @param <C> type for cipher "text"
 */
public interface ICryptoUnit<P, C> {
	/**
	 * The reverse function of {@link #decrypt(C)}<br>
	 * {@code decrypt(encrypt(obj));} <b>MUST</b> return obj all times,<br>
	 * although there may be setups with two {@link ICryptoUnit ICryptoUnits},<br>
	 * where one can decrypt the other ones encrypted text
	 * 
	 * @param plain
	 * @return an encrypted form of <b>plain</b>
	 */
	C encrypt(P plain );
	/**
	 * The reverse function of {@link #encrypt(P)}<br>
	 * {@code encrypt(decrypt(obj));} <b>MUST</b> return obj all times,<br>
	 * although there may be setups with two {@link ICryptoUnit ICryptoUnits},<br>
	 * where one can decrypt the other ones encrypted text
	 * 
	 * @param cipher
	 * @return a  decrypted form of <b>cipher</b>
	 */
	P decrypt(C cipher);
}
