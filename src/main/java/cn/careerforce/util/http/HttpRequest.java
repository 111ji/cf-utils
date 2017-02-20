package cn.careerforce.util.http;

import cn.careerforce.config.Global;
import cn.careerforce.util.StrUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b style="color:#e94d08;">Http请求方法</b>
 */
public class HttpRequest
{

    private static void closeConnection(BufferedReader in, PrintWriter out)
    {
        try
        {
            if (out != null)
            {
                out.close();
            }
            if (in != null)
            {
                in.close();
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1的形式，多个用 and 符号连接。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param)
    {
        String result = "";
        BufferedReader in = null;
        try
        {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet())
            {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += line;
            }
        }
        catch (Exception e)
        {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url        发送请求的 URL
     * @param parameters 请求的参数列表
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, String> parameters)
    {
        StringBuilder sb = new StringBuilder();// 存储参数

        try
        {
            // 编码请求参数
            if (parameters.size() == 1)
            {
                for (String name : parameters.keySet())
                    sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
            }
            else if (parameters.size() > 1)
            {
                for (String name : parameters.keySet())
                    sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return sendPost(url, sb.toString());
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1的形式，多个用 and 符号连接。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try
        {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += line;
            }
        }
        catch (Exception e)
        {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally
        {
            closeConnection(in, out);
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param strurl     发送请求的 URL
     * @param jsonString json 字符串
     * @return 所代表远程资源的响应结果
     */
    public static String sendJSONPost(String strurl, String jsonString)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try
        {
            URL realUrl = new URL(strurl);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(jsonString);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += line;
            }
        }
        catch (Exception e)
        {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally
        {
            closeConnection(in, out);
        }
        return result;
    }

    /**
     * 获取网络内容
     *
     * @param strurl   网络URL
     * @param encoding 编码
     * @return 返回的资源
     */
    public static String getContentByUrl(String strurl, String encoding)
    {
        return getContentByUrl(strurl, encoding, null, null, null);
    }

    /**
     * 获取网络内容，默认UTF-8编码
     *
     * @param strurl 网络URL
     * @return 返回的资源
     */
    public static String getContentByUrl(String strurl)
    {
        return getContentByUrl(strurl, Global.default_encoding, null, null, null);
    }

    /**
     * 获取网络内容
     *
     * @param strUrl   网络URL
     * @param encoding 编码
     * @param method   网络请求方法Get或Post
     * @return 返回的资源
     */
    public static String getContentByUrl(String strUrl, String encoding, String method)
    {
        return getContentByUrl(strUrl, encoding, method, null, null);
    }

    /**
     * 获取网络内容
     *
     * @param strUrl   网络URL
     * @param encoding 编码
     * @param method   网络请求方法Get或Post
     * @return 返回的资源
     */
    public static String getContentByUrl(String strUrl, String encoding, String method, Map<String, String> parameters, Map<String, String> headers)
    {
        return getContentByUrl(strUrl, encoding, method, parameters, headers, null);
    }


    /**
     * 获取网络内容
     *
     * @param strUrl   网络URL
     * @param encoding 编码
     * @param method   网络请求方法Get或Post
     * @return 返回的资源
     */
    public static String getContentByUrl(String strUrl, String encoding, String method, Map<String, String> parameters, Map<String, String> headers, String requestBody)
    {
        StringBuilder buffer = new StringBuilder();
        HttpClient http;
        HttpMethodBase methodBase;
        try
        {
            URL url = new URL(strUrl);
            http = new HttpClient();

            if (strUrl.startsWith("https"))
            {
                Protocol myHttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);
                Protocol.registerProtocol("https", myHttps);
                http.getHostConfiguration().setHost(url.getHost(), 443, myHttps);
            }

            if ("get".equalsIgnoreCase(method))
                methodBase = new GetMethod(strUrl);
            else
                methodBase = new PostMethod(strUrl);

            int statusCode = 0;
            try
            {
                if (headers != null)
                    for (String key : headers.keySet())
                        methodBase.addRequestHeader(key, headers.get(key));

                if (parameters != null)
                {
                    HttpMethodParams params = new HttpMethodParams();
                    HttpClientParams httpParams = new HttpClientParams();
                    NameValuePair[] queryParams = new NameValuePair[parameters.size()];
                    int i = 0;
                    for (String key : parameters.keySet())
                    {
                        queryParams[i] = new NameValuePair(key, parameters.get(key));
                        params.setParameter(key, parameters.get(key));
                        httpParams.setParameter(key, parameters.get(key));
                        i++;
                    }
                    http.setParams(httpParams);
                    methodBase.setParams(params);
                    methodBase.setQueryString(queryParams);
                }

                if (StrUtil.isNotNull(requestBody) && !"get".equalsIgnoreCase(method))
                    ((PostMethod) methodBase).setRequestEntity(new StringRequestEntity(requestBody, "application/x-www-formurlencoded", "UTF-8"));


                statusCode = http.executeMethod(methodBase);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if (statusCode == 200)
            {
                InputStream is;
                BufferedReader reader;
                try
                {
//                    buffer.append(new String(methodBase.getResponseBody(), encoding));
                    is = methodBase.getResponseBodyAsStream();
                    reader = new BufferedReader(new InputStreamReader(is, encoding));
                    String rl;
                    while ((rl = reader.readLine()) != null)
                        buffer.append(rl);
                    reader.close();
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        return buffer.toString();
    }

    /**
     * 获取网络文件，转存到fileDes中，fileDes需要带文件后缀名
     *
     * @param fileUrl 网络文件URL
     * @param fileDes 保存到目标文件路径 完整绝对路径，含文件名和类型后缀
     * @throws Exception 操作异常
     */
    public static void saveUrlFile(String fileUrl, String fileDes) throws Exception
    {
        File toFile = new File(fileDes);
        if (toFile.exists())
        {
            throw new Exception("file exist");
        }
        toFile.createNewFile();
        FileOutputStream outImgStream = new FileOutputStream(toFile);
        outImgStream.write(getUrlFileData(fileUrl));
        outImgStream.close();
    }

    /**
     * 获取链接地址文件的byte数据
     *
     * @param fileUrl 网络文件路径URL
     * @return 文件二进制数据
     * @throws Exception 操作异常
     */
    public static byte[] getUrlFileData(String fileUrl) throws Exception
    {
        URL url = new URL(fileUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.connect();
        InputStream cin = httpConn.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = cin.read(buffer)) != -1)
        {
            outStream.write(buffer, 0, len);
        }
        cin.close();

        byte[] fileData = outStream.toByteArray();
        outStream.close();
        System.out.println("file data length: " + fileData.length);
        return fileData;
    }

    /**
     * 获取链接地址的字符数据，wichSep是否换行标记
     *
     * @param urlStr  url地址
     * @param withSep 是否包含换行符
     * @return 结果字符串
     * @throws Exception 操作异常
     */
    public static String getUrlDetail(String urlStr, boolean withSep) throws Exception
    {
        URL url = new URL(urlStr);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.connect();
        InputStream cin = httpConn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(cin, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String rl = null;
        while ((rl = reader.readLine()) != null)
        {
            if (withSep)
            {
                sb.append(rl).append(System.getProperty("line.separator"));
            }
            else
            {
                sb.append(rl);
            }
        }
        return sb.toString();
    }

    /**
     * 测试主函数
     *
     * @param args 参数
     * @throws Exception 异常
     */
    public static void main(String args[]) throws Exception
    {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Authorization", "Basic MTIzNTgzMTc5OmNjNzFkYjJkYjZhZmViMWE1Zjg3NTM1N2FmNjI5ODJj");
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("grant_type", "client_credentials");
        System.out.println(getContentByUrl("https://appkey.careerforce.cn/oauth/accessToken",
                Global.default_encoding, "post",
                parameters, headers, null));
    }
}