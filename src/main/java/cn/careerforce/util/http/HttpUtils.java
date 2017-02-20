package cn.careerforce.util.http;

import cn.careerforce.util.CookieUtils;
import cn.careerforce.util.StrUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

/**
 * http web 工具方法
 * <p>
 * Created by yangdh on 2016/10/17.
 */
public class HttpUtils
{


    /**
     * 获取网页参数,如果参数未获取到,从Cookie获取
     *
     * @param name    参数名
     * @param request request
     * @return 参数值
     */
    public static String getParameterOrCookie(String name, HttpServletRequest request)
    {
        String val = request.getHeader(name);
        if (StrUtil.isNotNull(val))
            return val;

        val = request.getParameter(name);
        if (StrUtil.isNotNull(val))
            return val;

        Cookie c = CookieUtils.getCookie(request, name);
        if (c != null)
            return c.getValue();

        return null;
    }

    /**
     * 获取客户IP地址
     *
     * @param request request
     * @return IP地址字符串
     */
    public static String getRemoteAddress(HttpServletRequest request)
    {
        String ipStr = request.getHeader("X-Real-IP");
        if (StrUtil.isNull(ipStr) || "unknown".equalsIgnoreCase(ipStr))
            ipStr = request.getHeader("x-forwarded-for");
        if (StrUtil.isNull(ipStr) || "unknown".equalsIgnoreCase(ipStr))
            ipStr = request.getHeader("Proxy-Client-IP");
        if (StrUtil.isNull(ipStr) || "unknown".equalsIgnoreCase(ipStr))
            ipStr = request.getHeader("WL-Proxy-Client-IP");
        if (StrUtil.isNull(ipStr) || "unknown".equalsIgnoreCase(ipStr))
            ipStr = request.getHeader("HTTP_CLIENT_IP");
        if (StrUtil.isNull(ipStr) || "unknown".equalsIgnoreCase(ipStr))
            ipStr = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (StrUtil.isNull(ipStr) || "unknown".equalsIgnoreCase(ipStr))
            ipStr = request.getRemoteAddr();

        // 多级反向代理
        if (!StrUtil.isNull(ipStr) && !StrUtil.isNull(ipStr.trim()))
        {
            StringTokenizer st = new StringTokenizer(ipStr, ",");
            if (st.countTokens() > 1)
            {
                return st.nextToken();
            }
        }

        return ipStr;
    }
}
