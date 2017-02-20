/**
 *
 */
package cn.careerforce.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

/**
 *
 * <b style="color:#e94d08;">Tld自定义标签方法</b>
 *
 * @author yangdh
 *
 */
public class TldFunctions
{
	public static String cutStringNew(String strSrc, int maxlen)
	{
		if (strSrc == null)
			return "";
		int nlen = 0;
		StringBuffer sb = new StringBuffer(strSrc.length());
		boolean isin = false;
		for (int i = 0; i < strSrc.length(); i++)
		{
			char ch = strSrc.charAt(i);
			int ntemp = ch;
			if (ntemp == 60)
				isin = true;
			if (isin && ntemp == 62)
			{
				isin = false;
				continue;
			}
			if (!isin)
			{
				if (ntemp > 0xFE)
				{
					nlen += 2;
				}
				else
					nlen += 1;
				if (nlen > maxlen)
				{
					sb.append("..");
					break;
				}
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	public static String getFullLinkUrl(String contextPath, String url)
	{
		if (url == null)
			return "";
		if (url.toLowerCase().indexOf("http") == 0 || url.toLowerCase().indexOf("ftp") == 0)
			return url;
		return contextPath + url;
	}

	public static Integer toInteger(Float val)
	{
		return val.intValue();
	}

	public static Timestamp toDate(Long time)
	{
		if (time == null || time.longValue() == 0)
			return null;
		return new Timestamp(time * 1000);
	}

	public static String toDateString(Long time)
	{
		if (time == null || time.longValue() == 0)
			return null;
		return new Timestamp(time * 1000).toString().substring(0, 10);
	}

	public static String toDateLength(Long time, int length)
	{
		if (time == null || time.longValue() == 0)
			return null;
		return new Timestamp(time * 1000).toString().substring(0, length);
	}

	public static Object toJSON(String source)
	{
		if (source == null || "".equals(source))
			return null;
		try
		{
			return JSONObject.fromObject(source);
		}
		catch (Exception ex)
		{
			try
			{
				return JSONArray.fromObject(source);
			}
			catch (Exception eee)
			{
			}
		}
		return null;
	}

	public static boolean ifnull(Object i11lI1)
	{
		if (i11lI1 == null)
			return true;
		else if (JSONNull.getInstance().equals(i11lI1))
			return true;
		else
			return false;
	}

	public static Long getNowTime()
	{
		return (System.currentTimeMillis() / 1000);
	}

	public static String decodeHTML(String i11lI1)
	{
		if (i11lI1 == null)
			return "";
		else
		{
			i11lI1 = i11lI1.replace("&lt;", "<");
			i11lI1 = i11lI1.replace("&gt;", ">");
			i11lI1 = i11lI1.replace("&nbsp;", " ");
			i11lI1 = i11lI1.replace("<br/>", "\r\n");
			return i11lI1;
		}
	}

	public static String encode(String url)
	{
		try
		{
			return URLEncoder.encode(url, cn.careerforce.config.Global.default_encoding);
		}
		catch (UnsupportedEncodingException e)
		{
			return url;
		}
	}

	public static String toTime(Long second)
	{
		long hour = 0;
		long minute = 0;

		hour = second / 3600;
		minute = (second % 3600) / 60;
		second = (second % 3600) % 60;

		String strh = hour >= 10 ? String.valueOf(hour) : "0" + String.valueOf(hour);
		String strm = minute >= 10 ? String.valueOf(minute) : "0" + String.valueOf(minute);
		String strs = second >= 10 ? String.valueOf(second) : "0" + String.valueOf(second);
		return strh + ":" + strm + ":" + strs;
	}

	public static String getWeekOfDate(Date dt)
	{
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	public static String formatcardnum(String cardnum)
	{
		int l = cardnum.length();
		int loop = (int) Math.ceil(l / 4.0);

		if (loop <= 1)
			return cardnum;
		else
		{
			String temp = "";
			String fnum = "";
			for (int i = 1; i <= loop; i++)
			{
				temp = cardnum.substring(4 * (i - 1), i * 4 > l ? l : i * 4);
				fnum = fnum + temp + " ";
			}
			if (fnum.endsWith(" "))
				fnum = fnum.substring(0, fnum.lastIndexOf(" "));
			return fnum;
		}
	}

}
