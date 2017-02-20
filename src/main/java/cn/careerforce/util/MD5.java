/**
 *
 */
package cn.careerforce.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * <b style="color:#e94d08;">MD5工具类</b>
 *
 * @author yangdh
 *
 */
public class MD5
{
	private static final String ENCRYPT_COOKIE_SALT = "raysee_sso_cookie_876";
	private static final String ENCRYPT_HASH_SALT = "raysee_876";

	/**
	 * Encodes a string
	 *
	 * @param str
	 *            String to encode
	 * @return Encoded String
	 */
	public static String crypt(String str)
	{
		if (str == null || str.length() == 0)
		{
			throw new IllegalArgumentException("String to encript cannot be null or zero length");
		}

		StringBuffer hexString = new StringBuffer();

		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] hash = md.digest();

			for (int i = 0; i < hash.length; i++)
			{
				if ((0xff & hash[i]) < 0x10)
				{
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				}
				else
				{
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}

		return hexString.toString();
	}

	public static String encyptCookie(String val, String remoteuser)
	{
		return MD5.crypt(MD5.crypt(val + ENCRYPT_COOKIE_SALT + (remoteuser != null ? remoteuser : "")));
	}

	public static String encyptPass(String pass)
	{
		return MD5.crypt(MD5.crypt(pass));
	}

	public static String encyptResetPasswordHash(String email)
	{
		return MD5.crypt(MD5.crypt(email + ENCRYPT_HASH_SALT)).substring(25) + DateUtil.getNowTime();
	}

	public static Long getResetPasswordHashDate(String hash)
	{
		return DateUtil.toDate(Long.parseLong(hash.substring(7))).getTime();
	}

	public static void main(String args[]) throws Exception
	{
		System.out.println(MD5.encyptPass("yangdihua"));
	}

}
