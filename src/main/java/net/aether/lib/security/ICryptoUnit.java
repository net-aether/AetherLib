package net.aether.lib.security;

/**
 * @deprecated This interface and its methods are not in use and have thus been deprecated<br><br>
 * 
 * Interface to be implemented by all classes that implement some type of encryption
 * 
 * @author Cheos
 *
 * @param <P> type for plain  "text"
 * @param <C> type for cipher "text"
 */
@Deprecated
public interface ICryptoUnit<P, C> {
	/**
	 * @deprecated This interface and its methods are not in use and have thus been deprecated<br><br>
	 * 
	 * The reverse function of {@link #decrypt(C)}<br>
	 * {@code decrypt(encrypt(obj));} <b>MUST</b> return obj all times,<br>
	 * although there may be setups with two {@link ICryptoUnit ICryptoUnits},<br>
	 * where one can decrypt the other ones encrypted text
	 * 
	 * @param plain
	 * @return an encrypted form of <b>plain</b>
	 */
	@Deprecated
	C encrypt(P plain );
	/**
	 * @deprecated This interface and its methods are not in use and have thus been deprecated<br><br>
	 * 
	 * The reverse function of {@link #encrypt(P)}<br>
	 * {@code encrypt(decrypt(obj));} <b>MUST</b> return obj all times,<br>
	 * although there may be setups with two {@link ICryptoUnit ICryptoUnits},<br>
	 * where one can decrypt the other ones encrypted text
	 * 
	 * @param cipher
	 * @return a  decrypted form of <b>cipher</b>
	 */
	@Deprecated
	P decrypt(C cipher);
}
