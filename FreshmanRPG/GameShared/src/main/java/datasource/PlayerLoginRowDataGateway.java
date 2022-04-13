package datasource;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Defines the behavior required for interfacing with a data source that has
 * information about which servers are managing which map files
 *
 * @author Merlin
 *
 */
public abstract class PlayerLoginRowDataGateway
{

	/**
	 *
	 * @return the player's password
	 */
	public abstract String getPassword();

	/**
	 * @param playerName the player's name
	 */
	public abstract void setName(String playerName);

	/**
	 * @param password the player's new password
	 */
	public abstract void setPassword(String password);

	/**
	 * store the information into the data source
	 *
	 * @throws DatabaseException if the data cannot be stored into the data
	 *             source
	 */
	public abstract void persist() throws DatabaseException;

	/**
	 * Initialize the data source to a known state (for testing)
	 */
	public abstract void resetData();

	/**
	 * @return the player's name
	 */
	public abstract String getPlayerName();

	/**
	 * @return the salt
	 */
	public abstract byte[] getSalt();

	/**
	 * Set the salt
	 * @param salt for the pw
	 */
	public abstract void setSalt(byte[] salt);

	/**
	 * @return the player's unique ID
	 */
	public abstract int getPlayerID();

	/**
	 * deletes a row
	 * @throws DatabaseException shouldn't 
	 */
	public abstract void deleteRow() throws DatabaseException;

	/**
	 * @param password The password to test
	 * @return True if the password is correct
	 */
	public abstract boolean checkPassword(String password);

	/**
	 * Utility method, taken from https://www.owasp.org/index.php/Hashing_Java and
	 *  modified slightly for simplicity & documentation
	 * @param password The password to hash
	 * @param salt A randomly generated (but consistent) salt
	 * @return The hashed & salted password
	 */
	public static byte[] hashPassword(final String password, final byte[] salt)
	{
		try
		{
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

			// 10 Iterations. Higher is safer, but slower. This seems to be a good balance.
			// 256 bit keylength. The documentation indicates that is sufficient.
			PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10, 256);

			SecretKey key = skf.generateSecret(spec);
			byte[] res = key.getEncoded();

			return res;
		}
		catch (NoSuchAlgorithmException | InvalidKeySpecException e)
		{
			/*
			 * These exceptions will be thrown if the specified encryption algorithm ('PBKDF2WithHmacSHA512'), above,
			 * is not available or supported on the current platform.  This *shouldn't* be an issue unless porting to
			 * a different architecture (such as ARM).
			 */
			throw new RuntimeException(e);
		}
	}

}
