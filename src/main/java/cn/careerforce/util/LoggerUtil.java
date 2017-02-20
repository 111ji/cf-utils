package cn.careerforce.util;

import cn.careerforce.config.Configuration;
import cn.careerforce.config.Global;
import cn.careerforce.util.http.HttpRequest;
import cn.careerforce.util.http.HttpUtils;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 日志记录工具类
 * <p>
 * Created by yangdh on 16/9/2.
 */
public class LoggerUtil
{
    /**
     * 记录访问日志
     *
     * @param request HttpServletRequest
     */
    public static void log(HttpServletRequest request)
    {
        /**
         * 日志： Headers and Parameters
         */
        JSONObject log = new JSONObject();
        log.put("dateTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.put("requestURI", request.getRequestURI());
        Enumeration<String> headerNames = request.getHeaderNames();
        JSONObject headers = new JSONObject();
        while (headerNames.hasMoreElements())
        {
            String key = headerNames.nextElement();
            headers.put(key, request.getHeader(key));
        }
        log.put("headers", headers);
        Map pars = request.getParameterMap();
        Set keysSet = pars.keySet();
        JSONObject parameters = new JSONObject();
        for (Object aKeysSet : keysSet)
        {
            String key = (String) aKeysSet;
            parameters.put(key, request.getParameter(key));
        }
        log.put("parameters", parameters);

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            {
                result.append(line);
            }
            log.put("body", result.toString());
        }
        catch (IOException e)
        {
//            e.printStackTrace();
        }

        Map<String, String> params = new HashMap<>();
        params.put("clientId", Configuration.getValue("cn.careerforce.cloud.appId"));
        log.put("userIp", HttpUtils.getRemoteAddress(request));
        params.put("message", ZipUtil.gzip(log.toString()));
        try
        {
            HttpRequest.getContentByUrl(Configuration.getValue("LOGGER_SERVER_URL", "https://api-logger-service.careerforce.cn/api/logger/info"), Global.default_encoding, "POST", params, null);
        }
        catch (Exception ignored)
        {
        }
    }


    public static void main(String args[]) throws Exception
    {

    }

}
