package cn.careerforce.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * <b style="color:#e94d08;">Cookie 工具类</b>
 *
 * @author yangdh
 *
 */
public class CookieUtils
{
	/**
	 * 设置cookie值
	 *
	 * @param response
	 *            http response 对象
	 * @param name
	 *            cookie 名称
	 * @param value
	 *            cookie 值
	 * @param domain
	 *            cookie 域名
	 * @param maxAge
	 *            cookie 有效时长，秒
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String domain, Integer maxAge)
	{
		if (value == null)
		{
			value = "";
		}
		Cookie cookie = new Cookie(name, value);
		if (maxAge != null)
			cookie.setMaxAge(maxAge);
		if (domain != null && !"".equals(domain))
		{
			cookie.setDomain(domain);
		}
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * 获取cookie
	 *
	 * @param request
	 *            http request 对象
	 * @param name
	 *            cookie 名称
	 * @return Cookie 对象，不存在为null
	 */
	public static Cookie getCookie(HttpServletRequest request, String name)
	{
		Cookie cookies[] = request.getCookies();
		// Return null if there are no cookies or the name is invalid.
		if (cookies == null || name == null || name.length() == 0)
		{
			return null;
		}
		// Otherwise, we do a linear scan for the cookie.
		Cookie cookie = null;
		for (int i = 0; i < cookies.length; i++)
		{
			// If the current cookie name matches the one we're looking for,
			// we've
			// found a matching cookie.
			if (cookies[i].getName().equals(name))
			{
				cookie = cookies[i];
				// The best matching cookie will be the one that has the correct
				// domain name. If we've found the cookie with the correct
				// domain name,
				// return it. Otherwise, we'll keep looking for a better match.
				break;
			}
		}
		return cookie;
	}

	/**
	 * 清空cookie
	 *
	 * @param request
	 *            http request 对象
	 * @param response
	 *            http response 对象
	 * @param path
	 *            cookie 路径
	 */
	public static void clearCookie(HttpServletRequest request, HttpServletResponse response, String path)
	{
		Cookie[] cookies = request.getCookies();
		try
		{
			for (int i = 0; i < cookies.length; i++)
			{
				Cookie cookie = new Cookie(cookies[i].getName(), null);
				cookie.setMaxAge(0);
				if (StrUtil.isNotNull(path))
					cookie.setPath(path);
				response.addCookie(cookie);
			}
		}
		catch (Exception ex)
		{
			System.out.println("清空Cookies发生异常！");
		}

	}

}