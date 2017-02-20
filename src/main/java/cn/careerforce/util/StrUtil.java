/**
 *
 */
package cn.careerforce.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * <b style="color:#e94d08;">字符串工具类</b>
 *
 * @author yangdh
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class StrUtil
{

	private static String varStr = "@";

	public StrUtil()
	{
	}

	public static boolean checkContainString(String parent, String son, String seperator)
	{
		if (parent != null && !"".equals(parent))
		{
			String[] ss = parent.split(seperator);
			for (int i = 0; i < ss.length; i++)
			{
				if (ss[i].equals(son))
					return true;
			}
		}
		return false;
	}

	public static String replaceAllForTemplet(String i11lI1, String i1l111IIlIIlI, String iIllll1)
	{
		String i1I1 = replaceAll(i11lI1, varStr + i1l111IIlIIlI, iIllll1);
		return i1I1;
	}

	public static int getNotNullIntValue(String iIllll1)
	{
		int ill1IIII = 0;
		try
		{
			ill1IIII = Integer.parseInt(iIllll1);
		}
		catch (NumberFormatException illl)
		{
			ill1IIII = 0;
		}
		return ill1IIII;
	}

	public static int getNotNullIntValue(String iI1llll, int iIIIlI1l1Il1l1)
	{
		int ill1IIII = getNotNullIntValue(iI1llll);
		if (ill1IIII == 0)
		{
			ill1IIII = iIIIlI1l1Il1l1;
		}
		return ill1IIII;
	}

	public static Long getNotNullLongValue(String iIllll1)
	{
		Long ill1IIII = 0L;
		try
		{
			ill1IIII = Long.parseLong(iIllll1);
		}
		catch (NumberFormatException illl)
		{
			ill1IIII = 0L;
		}
		return ill1IIII;
	}

	public static Long getNotNullLongValue(String iI1llll, Long iIIIlI1l1Il1l1)
	{
		Long ill1IIII = getNotNullLongValue(iI1llll);
		if (ill1IIII == 0)
		{
			ill1IIII = iIIIlI1l1Il1l1;
		}
		return ill1IIII;
	}

	public static Timestamp getNotNullTimestampValue(String iIllll1, String iIII11IlIll11)
	{
		Timestamp value;
		try
		{
			if (iIllll1 == null || iIllll1.equals(""))
			{
				value = new Timestamp(System.currentTimeMillis());
			}
			else
			{
				SimpleDateFormat iI1Ill = new SimpleDateFormat(iIII11IlIll11);
				value = new Timestamp(iI1Ill.parse(iIllll1).getTime());
			}
		}
		catch (Exception illl)
		{
			illl.printStackTrace();
			value = new Timestamp(System.currentTimeMillis());
		}
		return value;
	}

	public static String getNotNullStringValue(String iI1llll, String iIIIlI1l1Il1l1)
	{
		String ill1IIII = iI1llll;
		if (iI1llll == null || iI1llll.trim().equals(""))
		{
			ill1IIII = iIIIlI1l1Il1l1;
		}
		return ill1IIII;
	}

	public static String getNotNullStringValue(String iI1llll)
	{
		String ill1IIII = iI1llll;
		if (iI1llll == null || iI1llll.trim().equals(""))
		{
			return "";
		}
		else
		{
			return ill1IIII;
		}
	}

	public static float getNotNullFloatValue(String iIllll1)
	{
		float ill1IIII = 0;
		try
		{
			ill1IIII = Float.parseFloat(iIllll1);
		}
		catch (Exception illl)
		{
			ill1IIII = 0;
		}
		return ill1IIII;
	}

	public static float getNotNullFloatValue(String iI1llll, float iIIIlI1l1Il1l1)
	{
		float ill1IIII = getNotNullFloatValue(iI1llll);
		if (ill1IIII == 0)
		{
			ill1IIII = iIIIlI1l1Il1l1;
		}
		return ill1IIII;
	}

	public static boolean isNumber(String i11lI1)
	{
		boolean iIII1lII11l = true;
		try
		{
			int i1I1ll = Integer.parseInt(i11lI1.trim());
			if (i1I1ll < 0)
			{
				return false;
			}
		}
		catch (NumberFormatException illl)
		{
			return false;
		}
		return iIII1lII11l;
	}

	public static String replaceAll(String ill11Ill1, String iII1IIlIIIIl, String il1l111llIl1)
	{
		if (ill11Ill1 == null)
		{
			return null;
		}
		StringBuffer ilIl111l1 = new StringBuffer();
		int i11I11I1I1Il1Il = ill11Ill1.length();
		int i1l1IlII1III = iII1IIlIIIIl.length();
		int i1II1I1I1l1;
		int iIlIlI;
		for (i1II1I1I1l1 = 0; (iIlIlI = ill11Ill1.indexOf(iII1IIlIIIIl, i1II1I1I1l1)) >= 0; i1II1I1I1l1 = iIlIlI + i1l1IlII1III)
		{
			ilIl111l1.append(ill11Ill1.substring(i1II1I1I1l1, iIlIlI));
			ilIl111l1.append(il1l111llIl1);
		}

		if (i1II1I1I1l1 < i11I11I1I1Il1Il)
		{
			ilIl111l1.append(ill11Ill1.substring(i1II1I1I1l1));
		}
		return ilIl111l1.toString();
	}

	public static String replaceAllSpace(String ill11Ill1)
	{
		return replaceAll(ill11Ill1, " ", "");
	}

	public static String replaceAllFullwidthSpace(String ill11Ill1)
	{
		return replaceAll(replaceAll(ill11Ill1, "　", " "), "  ", " ").trim();
	}

	public static boolean checkStringRule(String ill11Ill1)
	{
		boolean i1ll1l1 = true;
		if (ill11Ill1 == null || ill11Ill1.trim().equals(""))
		{
			return true;
		}
		if (ill11Ill1.indexOf("<") > -1 || ill11Ill1.indexOf(">") > -1 || ill11Ill1.indexOf(".") > -1 || ill11Ill1.indexOf("\"") > -1 || ill11Ill1.indexOf("'") > -1 || ill11Ill1.indexOf(",") > -1 || ill11Ill1.indexOf("$") > -1 || ill11Ill1.indexOf("#") > -1 || ill11Ill1.indexOf("%") > -1 || ill11Ill1.indexOf("!") > -1 || ill11Ill1.indexOf("~") > -1 || ill11Ill1.indexOf("^") > -1 || ill11Ill1.indexOf("&") > -1 || ill11Ill1.indexOf("*") > -1 || ill11Ill1.indexOf("(") > -1 || ill11Ill1.indexOf(")") > -1 || ill11Ill1.indexOf("=") > -1 || ill11Ill1.indexOf("+") > -1 || ill11Ill1.indexOf("|") > -1 || ill11Ill1.indexOf("\\") > -1 || ill11Ill1.indexOf(";") > -1 || ill11Ill1.indexOf(":") > -1 || ill11Ill1.indexOf("?") > -1 || ill11Ill1.indexOf("\u3000") > -1)
		{
			i1ll1l1 = false;
		}
		return i1ll1l1;
	}

	public static String encodeHTML(String i11lI1)
	{
		if (i11lI1 == null)
			return "";
		else
		{
			i11lI1 = i11lI1.replaceAll("<", "&lt;");
			i11lI1 = i11lI1.replaceAll(">", "&gt;");
			i11lI1 = i11lI1.replaceAll(" ", "&nbsp;");
			i11lI1 = i11lI1.replaceAll("\r\n", "<br/>");
			i11lI1 = i11lI1.replaceAll("\n", "<br/>");
			return i11lI1;
		}
	}

	public static String decodeHTML(String i11lI1)
	{
		if (i11lI1 == null)
		{
			return i11lI1;
		}
		else
		{
			String i1I1 = i11lI1;
			i1I1 = replaceAll(i1I1, "&lt;", "<");
			i1I1 = replaceAll(i1I1, "&gt;", ">");
			i1I1 = replaceAll(i1I1, "&nbsp;", " ");
			i1I1 = replaceAll(i1I1, "<br/>", "\r\n");
			return i1I1;
		}
	}

	public static String filterStringQuote(String i11lI1)
	{
		if (i11lI1 == null)
			return "";
		else
		{
			i11lI1 = i11lI1.replace("\"", "\\\"");
			i11lI1 = i11lI1.replace("\'", "\\\'");
			i11lI1 = i11lI1.replace("\r\n", " ");
			i11lI1 = i11lI1.replace("\n", " ");
			return i11lI1;
		}
	}

	public static Vector strToVector(String i11lI1)
	{
		Vector ilI1 = new Vector();
		if (i11lI1 == null || i11lI1.trim().equals(""))
		{
			return ilI1;
		}
		String i1llI1Il1I1I[] = i11lI1.split("\\,");
		for (int i11I = 0; i11I < i1llI1Il1I1I.length; i11I++)
		{
			String i1I1ll = i1llI1Il1I1I[i11I].trim().toLowerCase();
			if (i1I1ll != null && !i1I1ll.equals(""))
			{
				ilI1.add(i1I1ll);
			}
		}

		return ilI1;
	}

	public static Vector strArrayToVector(String i1llI1Il1I1I[])
	{
		Vector ilI1 = new Vector();
		if (i1llI1Il1I1I == null || i1llI1Il1I1I.length == 0)
		{
			return ilI1;
		}
		for (int i11I = 0; i11I < i1llI1Il1I1I.length; i11I++)
		{
			String i1I1ll = i1llI1Il1I1I[i11I].trim().toLowerCase();
			if (i1I1ll != null && !i1I1ll.equals(""))
			{
				ilI1.add(i1I1ll);
			}
		}

		return ilI1;
	}

	public static Vector vectorUnite(Vector ilI1I, Vector iIIIl)
	{
		Vector iI1ll1l = new Vector();
		iI1ll1l.addAll(ilI1I);
		iI1ll1l.addAll(iIIIl);
		return iI1ll1l;
	}

	public static Collection mapToCollection(HashMap ilIIlI)
	{
		return ilIIlI.values();
	}

	public static boolean isNull(String i11lI1)
	{
		return i11lI1 == null || i11lI1.trim().equals("");
	}

	public static boolean isNotNull(String i11lI1)
	{
		return i11lI1 != null && !i11lI1.trim().equals("");
	}

	public static boolean isCompareStr(String i11lI1)
	{
		boolean iI1l = false;
		if (isNull(i11lI1))
		{
			return false;
		}
		if (i11lI1.equals("<") || i11lI1.equals(">") || i11lI1.equals(">=") || i11lI1.equals("<=") || i11lI1.equals("=") || i11lI1.equals("=="))
		{
			iI1l = true;
		}
		return iI1l;
	}

	public static String removeLawlessStr(String i11lI1)
	{
		if (i11lI1 == null || i11lI1.trim().equals(""))
		{
			return i11lI1;
		}
		else
		{
			String i1lII11llI = i11lI1;
			i1lII11llI = replaceAll(i1lII11llI, "'", "");
			i1lII11llI = replaceAll(i1lII11llI, "%", "");
			i1lII11llI = replaceAll(i1lII11llI, "SELECT", "[SELECT]");
			i1lII11llI = replaceAll(i1lII11llI, "UPDATE", "[UPDATE]");
			i1lII11llI = replaceAll(i1lII11llI, "DELETE", "[DELETE]");
			return i1lII11llI;
		}
	}

	public static String paddingWithZero(Integer source, int len)
	{
		if (source == null)
			source = 1;
		String res = source + "";
		while ((res).length() < len)
			res = "0" + res;
		return res;
	}

	public static String encodeUnicode(String gbString)
	{
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++)
		{
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2)
			{
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + "\\u" + hexB;
		}
		return unicodeBytes;
	}

	public static String decodeUnicode(String dataStr)
	{
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1)
		{
			end = dataStr.indexOf("\\u", start + 2);
			String charStr = "";
			if (end == -1)
			{
				charStr = dataStr.substring(start + 2, dataStr.length());
			}
			else
			{
				charStr = dataStr.substring(start + 2, end);
			}
			char letter = (char) Integer.parseInt(charStr, 16);
			buffer.append(new Character(letter).toString());
			start = end;
		}
		return buffer.toString();
	}

	public static String encodeStr(String source)
	{
		return MD5.crypt(source).substring(0, 10);
	}

	// // ADD Start by yufd 2011-11-30

	public static String convertToHref(String Str)
	{
		if (Str == null || Str.equals(""))
			return Str;

		Matcher matcher = null;
		Pattern pattern = null;

		String str2 = "";
		pattern = Pattern.compile("(http://[A-Za-z0-9\\./=\\?%\\-&_~`@':+!]+)|(www\\.[A-Za-z0-9\\./=\\?%\\-&_~`@':+!]+)", Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(Str);
		StringBuffer stringbuffer = new StringBuffer();

		for (; matcher.find(); matcher.appendReplacement(stringbuffer, str2))
		{
			if (matcher.group(2) != null)
				str2 = "<a href=\"http://" + matcher.group(2) + "\" target=\"_blank\"><font color=\"#3333FF\">" + matcher.group(2) + "</font></a>";
			else
				str2 = "<a href=\"" + matcher.group(1) + "\" target=\"_blank\"><font color=\"#3333FF\">" + matcher.group(1) + "</font></a>";
		}

		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

	public static void main(String[] args)
	{
		// System.out.println(paddingWithZero(567, 5));

		// System.out.println((int)'中');
		// System.out.println((char)20013);
		// System.out.println(Integer.toHexString('中'));
		// String unicode_str = encodeUnicode("中国人sdf");
		// System.out.println(unicode_str);
		// System.out.println(decodeUnicode(unicode_str));
		// System.out.println(encodeStr("中国"));
		//
		// Log log = LogFactory.getLog(StrUtil.class);
		// log.info("sdf");

	}

}
