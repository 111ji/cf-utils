package cn.careerforce.util.sso;

import cn.careerforce.config.Configuration;
import cn.careerforce.config.Global;
import cn.careerforce.util.MD5;
import cn.careerforce.util.http.HttpRequest;
import net.sf.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * SSO 客户端方法
 *
 * @author yangdh
 *
 */
public class ClientUtil
{
	private static final String COOKIE_NAME = "cnvetsso";
	private static final String COOKIE_SLAT = "asdf";

	/**
	 * 根据request中的cookie，获取ticket信息，并从SSO获取当前登录用户信息
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return 当前登录用户信息：JSONObject
	 */
	public static JSONObject getLogedUserJSONObject(HttpServletRequest request)
	{
		String ticket = null, salt = null;

		/**
		 * 从网页参数获取ticket
		 */
		String cookieParam = request.getParameter("cookie_sso");
		if (cookieParam != null && cookieParam.length() == 64)
		{
			ticket = cookieParam.substring(0, 32);
			salt = cookieParam.substring(32);
		}

		/**
		 * 从cookie参数获取ticket
		 */
		if (ticket == null || salt == null)
		{
			Cookie[] myCookie = request.getCookies();
			if (myCookie != null)
			{
				for (int i = 0; i < myCookie.length; i++)
				{
					if (myCookie[i].getName().equals(COOKIE_NAME))
						ticket = myCookie[i].getValue();
					if (myCookie[i].getName().equals(COOKIE_SLAT))
						salt = myCookie[i].getValue();
				}
			}
		}

		/**
		 * 验证ticket
		 */
		if (ticket != null && salt != null && MD5.encyptCookie(ticket, "").equals(salt))
		{
			String jsonStr = validateTicket(ticket);
			if (jsonStr != null && !"".equals(jsonStr) && !"null".equals(jsonStr))
				return JSONObject.fromObject(jsonStr);
		}
		return null;
	}

	/**
	 * 根据ticket参数，从SSO获取当前登录用户信息，并将ticket存入response cookie中
	 *
	 * @param response
	 *            HttpServletResponse
	 * @param cookieParam
	 *            参数Ticket
	 * @return 当前登录用户信息：JSONObject
	 */
	public static JSONObject getLogedUserJSONObject(HttpServletResponse response, String cookieParam)
	{
		String ticket = null, salt = null;
		if (cookieParam != null && cookieParam.length() == 64)
		{
			ticket = cookieParam.substring(0, 32);
			salt = cookieParam.substring(32);
		}
		if (ticket != null && salt != null && MD5.encyptCookie(ticket, "").equals(salt))
		{
			String jsonStr = validateTicket(ticket);
			if (jsonStr != null && !"".equals(jsonStr) && !"null".equals(jsonStr))
			{
				if (response != null)
				{
					Cookie cookie = new Cookie(COOKIE_NAME, ticket);
					cookie.setPath("/");
					response.addCookie(cookie);
					Cookie cookie_salt = new Cookie(COOKIE_SLAT, salt);
					cookie_salt.setPath("/");
					response.addCookie(cookie_salt);
				}
				return JSONObject.fromObject(jsonStr);
			}
		}
		return null;
	}

	/**
	 * 将ticket存入response cookie中
	 *
	 * @param response
	 *            HttpServletResponse
	 * @param cookieParam
	 *            参数Ticket
	 */
	public static void setCookieTicket(HttpServletResponse response, String cookieParam)
	{
		String ticket = null, salt = null;
		if (cookieParam != null && cookieParam.length() == 64)
		{
			ticket = cookieParam.substring(0, 32);
			salt = cookieParam.substring(32);
		}
		if (ticket != null && salt != null && MD5.encyptCookie(ticket, "").equals(salt))
		{
			if (response != null)
			{
				Cookie cookie = new Cookie(COOKIE_NAME, ticket);
				cookie.setPath("/");
				response.addCookie(cookie);
				Cookie cookie_salt = new Cookie(COOKIE_SLAT, salt);
				cookie_salt.setPath("/");
				response.addCookie(cookie_salt);
			}
		}
	}

	/**
	 * 根据ticket参数，从SSO获取当前登录用户信息
	 *
	 * @param cookieParam
	 *            参数Ticket
	 * @return 当前登录用户信息：JSONObject
	 */
	public static JSONObject getLogedUserJSONObject(String cookieParam)
	{
		return getLogedUserJSONObject(null, cookieParam);
	}

	/**
	 * 获取request中的ticket，并将ticket存入response cookie中
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ticket：64位长度的字符串，其中前32位是ticket，后32位是验证字符串
	 */
	public static String getTicketString(HttpServletRequest request, HttpServletResponse response)
	{
		if (request.getParameter("cookie_sso") != null && request.getParameter("cookie_sso").length() == 64)
			return request.getParameter("cookie_sso");

		String ticket = null, salt = null;
		Cookie[] myCookie = request.getCookies();
		if (myCookie != null)
		{
			for (int i = 0; i < myCookie.length; i++)
			{
				if (myCookie[i].getName().equals(COOKIE_NAME))
					ticket = myCookie[i].getValue();
				if (myCookie[i].getName().equals(COOKIE_SLAT))
					salt = myCookie[i].getValue();
			}
		}
		if (ticket != null && salt != null)
			return ticket + salt;
		return null;
	}

	/**
	 * 刷新session，防止登录超时
	 *
	 * @param ticket
	 *            64位字符串
	 */
	public static void flushSession(String ticket)
	{
		HttpRequest.getContentByUrl(Configuration.getValue("sso_url") + "api/sso/ticket/update?ticket=" + ticket, Global.default_encoding, Configuration.getValue("referer"));
	}

	/**
	 * 验证ticket有效性，如果有效，返回用户信息JSON字符串
	 *
	 * @param ticket
	 *            64位字符串
	 * @return 如果有效，返回用户信息JSON字符串，否则返回空字符串
	 */
	private static String validateTicket(String ticket)
	{
		String result = LoginUtil.getUserInfoByTicket(ticket, null, null);
		try
		{
			JSONObject res = JSONObject.fromObject(result);
			if (res.getInt("result") == 0)
				return URLDecoder.decode(res.getString("message"), Global.default_encoding);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return "";
	}
}
