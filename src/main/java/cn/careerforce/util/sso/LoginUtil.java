/*
 * 项目名称：sj-utils
 * 包名称：cn.careerforce.sj_utils.login
 * 文件名称：LoginUtil.java
 * 创建者：fanxiaoyu
 * 创建时间：2015年5月28日 下午5:46:02
 * 版本：1.0
 */
package cn.careerforce.util.sso;

import cn.careerforce.config.Configuration;
import cn.careerforce.util.StrUtil;
import cn.careerforce.util.http.HttpRequest;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * <b style="color:#e94d08;">sso 登录Client端常用方法</b>
 *
 * @author fanxiaoyu
 *
 */
public abstract class LoginUtil
{

	private static Logger log = Logger.getLogger(LoginUtil.class);

	/**
	 * 根据邮箱登录
	 *
	 * @param email
	 *            登录邮箱
	 * @param password
	 *            登录密码
	 * @param remember
	 *            是否记住登录 0 不记住，1 记住
	 * @param clientid
	 *            应用 appid
	 * @param token
	 *            应用 app secret
	 * @return 登录结果，json 字符串
	 */
	public static String loginByMail(String email, String password, String remember, String clientid, String token)
	{
		// 密码经过MD5加密
		// password = MD5.encyptPass(password);
		if (StrUtil.isNull(clientid))
			clientid = Configuration.getValue("open_clientid");
		if (StrUtil.isNull(token))
			token = Configuration.getValue("open_token");

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("clientid", clientid);
		parameters.put("token", token);
		parameters.put("email", email);
		parameters.put("password", password);
		parameters.put("rettype", "json");
		parameters.put("remember", remember);
		String loginReulst = HttpRequest.sendPost(Configuration.getValue("sso_url") + "api/sso/login", parameters);

		log.info("获取登录信息 ： " + loginReulst);
		return loginReulst;
	}

	/**
	 * 根据手机号登录
	 *
	 * @param mobile
	 *            登录手机号
	 * @param password
	 *            登录密码
	 * @param remember
	 *            是否记住登录 0 不记住，1 记住
	 * @param clientid
	 *            应用 appid
	 * @param token
	 *            应用 app secret
	 * @return 登录结果，json 字符串
	 */
	public static String loginByMobile(String mobile, String password, String remember, String clientid, String token)
	{
		if (StrUtil.isNull(clientid))
			clientid = Configuration.getValue("open_clientid");
		if (StrUtil.isNull(token))
			token = Configuration.getValue("open_token");

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("clientid", clientid);
		parameters.put("token", token);
		parameters.put("mobile", mobile);
		parameters.put("password", password);
		parameters.put("rettype", "json");
		parameters.put("remember", remember);
		String loginReulst = HttpRequest.sendPost(Configuration.getValue("sso_url") + "api/sso/login", parameters);

		log.info("获取登录信息 ： " + loginReulst);
		return loginReulst;
	}

	/**
	 * 获取登录返回值对应状态
	 *
	 * @param resultCode
	 *            结果代码
	 * @return 结果说明
	 */
	public static String getLoginErrorMsg(int resultCode)
	{
		String errmsg = "登录失败";
		switch (resultCode)
		{
		case 6:
			errmsg = "用户名或密码不正确 ";
			break;
		case 7:
			errmsg = "用户名或密码不正确";
			break;
		case 8:
			errmsg = "非法用户";
			break;
		case 9:
			errmsg = "不合法数据";
			break;
		case 15:
			errmsg = "该手机号已经注册，但未授权登录尚技";
			break;
		}
		return errmsg;
	}

	/**
	 * 验证ticket
	 *
	 * @param ticket
	 *            登录票据
	 * @param clientid
	 *            应用 appid
	 * @param token
	 *            应用 app secret
	 * @return json字符串:
	 *
	 *         <pre>
	 * {
	 *     "result": 0,
	 *     "message": {
	 *         "user": {
	 *             "clientid": 123583160,
	 *             "email": "",
	 *             "hash": "",
	 *             "id": 123591479,
	 *             "loginhash": "",
	 *             "mobile": "18601949783",
	 *             "password": null,
	 *             "regip": "",
	 *             "regtime": 1426413892,
	 *             "status": 1,
	 *             "username": ""
	 *         }
	 *     }
	 * }
	 * </pre>
	 */
	public static String getUserInfoByTicket(String ticket, String clientid, String token)
	{
		if (StrUtil.isNull(clientid))
			clientid = Configuration.getValue("open_clientid");
		if (StrUtil.isNull(token))
			token = Configuration.getValue("open_token");

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("clientid", clientid);
		parameters.put("token", token);
		parameters.put("ticket", ticket);
		String loginReulst = HttpRequest.sendPost(Configuration.getValue("sso_url") + "api/sso/ticket/validate", parameters);

		log.info(" 根据 ticket 获取登录信息 ： " + loginReulst);

		return loginReulst;
	}

	/**
	 * 根据openId和openType 获取用户信息
	 *
	 * @param openId
	 *            openId
	 * @param openType
	 *            openType
	 * @param clientid
	 *            应用 appid
	 * @param token
	 *            应用 app secret
	 * @return json字符串
	 */
	public static String getUserInfoByOpen(String openId, String openType, String clientid, String token)
	{
		if (StrUtil.isNull(clientid))
			clientid = Configuration.getValue("open_clientid");
		if (StrUtil.isNull(token))
			token = Configuration.getValue("open_token");

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("clientid", clientid);
		parameters.put("token", token);
		parameters.put("action", "getAccountByOpenId");
		parameters.put("openId", openId);
		parameters.put("openType", openType);
		String loginReulst = HttpRequest.sendPost(Configuration.getValue("sso_url") + "api/sso/thirdparty", parameters);

		log.info(" 根据 openId 和 openType 获取登录信息 ： " + loginReulst);

		return loginReulst;
	}
}
