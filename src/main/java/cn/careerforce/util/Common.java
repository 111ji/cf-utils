package cn.careerforce.util;

import cn.careerforce.config.Global;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * <b style="color:#e94d08;">一些公共的静态方法</b>
 *
 * @author yangdh
 */
@SuppressWarnings("rawtypes")
public class Common
{
    static String separator = null;
    private static char[] chars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    public static final int FILE_TYPE_PICTURE = 0;
    public static final int FILE_TYPE_FILE = 4;
    public static final int FILE_TYPE_COURSE = 6;
    public static final int FILE_TYPE_DOCUMENT = 7;
    public static final int FILE_TYPE_VOICE = 8;

    public static String getAllowFile(int type)
    {
        String res = "";
        switch (type)
        {
            case 0:
                res = "|gif|jpg|jpeg|bmp|png|";
                break;
            case 1:
                res = "|swf|";
                break;
            case 2:
                res = "|flv|wmv|mp4|mpg|rm|";
                break;
            case 3:
                res = "|mp3|";
                break;
            case 4:
                res = "|gif|jpg|jpeg|bmp|png|swf|flv|wmv|mp4|mpg|rm|doc|xls|ppt|rar|zip|pdf|txt|docx|xlsx|pptx|";
                break;
            case 5:
                res = "|gif|jpg|jpeg|bmp|psd|ai|eps|png|fla|swf|rar|zip|";
                break;
            case 7:
                res = "|doc|docx|pdf|txt|xls|xlsx|ppt|pptx|wps|";
                break;
            case 8:
                res = "|mp3|wav|";
                break;
            default:
                res = "";
        }
        return res;
    }

    public static String getFileext(String strSrc)
    {
        int npos = strSrc.lastIndexOf('.');
        if (npos != -1)
        {
            return strSrc.substring(npos + 1).toLowerCase();
        }
        return "";
    }

    public static String getFileName(String strSrc)
    {
        if (strSrc.lastIndexOf("\\") > -1 && strSrc.lastIndexOf("\\") < strSrc.length() - 1)
            strSrc = strSrc.substring(strSrc.lastIndexOf("\\") + 1);
        if (strSrc.lastIndexOf("/") > -1 && strSrc.lastIndexOf("/") < strSrc.length() - 1)
            strSrc = strSrc.substring(strSrc.lastIndexOf("/") + 1);
        int npos = strSrc.lastIndexOf('.');
        if (npos != -1)
        {
            return StrUtil.encodeStr(strSrc.substring(0, npos).toLowerCase());
        }
        return StrUtil.encodeStr(strSrc);
    }

    public static java.util.Date parseTimeStamp(String strdate) throws Exception
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d = df.parse(strdate);
        return d;
    }

    public static String getNowDateString()
    {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime());

    }

    public static String cutStringNew(String strSrc, int maxlen)
    {
        if (strSrc == null)
            return "";
        int nlen = 0;
        StringBuilder sb = new StringBuilder(strSrc.length());
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

    public static String takeOutHtml(String strSrc)
    {
        if (strSrc == null)
            return "";
        strSrc = StrUtil.decodeHTML(strSrc);
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
                sb.append(ch);
        }
        return sb.toString().trim();
    }

    public static String toHTML(String strXml)
    {
        if (strXml == null || strXml.length() == 0)
            return strXml;
        StringBuffer sb = new StringBuffer(strXml.length() + 30);

        for (int i = 0; i < strXml.length(); i++)
        {
            char c = strXml.charAt(i);
            switch (c)
            {
                case '<':
                    sb.append("&lt;");
                    continue;
                case '>':
                    sb.append("&gt;");
                    continue;
                case '&':
                    sb.append("&amp;");
                    continue;
                case '\'':
                    sb.append("&apos;");
                    continue;
                case '"':
                    sb.append("&quot;");
                    continue;
                case '\r':
                    sb.append("&#xd;");
                    continue;
                case '\n':
                    sb.append("&#xa;");
                    continue;
                default:
                    sb.append(c);
                    continue;
            }
        }
        return sb.toString();
    }

    public static Long checkHasVideo(String content)
    {
        if (content != null && content.indexOf("<embed") > -1 && content.indexOf("</embed>") > -1)
            return 1l;
        return 0l;
    }

    public static boolean createDir(String strdir)
    {
        File f = new File(strdir);
        if (f.exists() && f.isDirectory())
            return true;
        return f.mkdirs();
    }

    public static void deletefile(String strdir)
    {
        File f = new File(strdir);
        if (f.exists() && f.isDirectory())
            return;
        f.delete();
    }

    public static String padString(String src, int nsize, char ch)
    {
        StringBuffer sb = new StringBuffer(nsize + 1);
        if (src.length() >= nsize)
        {
            sb.append(src);
        }
        else
        {
            int nmax = nsize - src.length();
            for (int i = 0; i < nmax; i++)
                sb.append(ch);
            sb.append(src);

        }
        return sb.toString();
    }

    public static boolean copyFile(String inputFileName, String outputFileName) throws IOException
    {
        BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(inputFileName));
        BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(outputFileName));
        byte buffer[] = new byte[2048];
        int length = 0;

        try
        {
            length = inStream.read(buffer);

            while (length != -1)
            {
                outStream.write(buffer, 0, length);
                length = inStream.read(buffer);
            }
        }
        finally
        {
            outStream.close();
            inStream.close();
        }

        return true;
    }

    public static String completePath(String path)
    {

        if (separator == null)
        {
            separator = System.getProperty("file.separator");
            if (separator == null)
            {
                separator = "/";
            }
        }

        if (!path.endsWith(separator))
        {
            return path + separator;
        }
        else
        {
            return path;
        }
    }

    public static boolean deleteFile(String filename) throws IOException
    {
        File file = new File(filename);
        file.delete();

        return true;
    }

    public static boolean deleteDirectory(String directoryName) throws IOException
    {
        if (directoryName.indexOf("Temp") == -1)
            return false;
        return subdeleteDirectory(directoryName);

    }

    @SuppressWarnings("unchecked")
    public static java.util.ArrayList ArrayToList(String[] obj)
    {
        java.util.ArrayList list = new java.util.ArrayList();
        for (int i = 0; i < obj.length; i++)
            list.add(obj[i]);
        return list;
    }

    private static boolean subdeleteDirectory(String directoryName) throws IOException
    {
        boolean success = true;
        String filenames[];
        File file;

        file = new File(directoryName);

        if (!file.isDirectory())
        {
            throw new IOException("Error, deleteDirectory called on non-directory " + directoryName);
        }

        filenames = file.list();

        if (filenames == null)
        {
            return success;
        }

        directoryName = completePath(directoryName);

        for (int i = 0; i < filenames.length; i++)
        {
            File subfile = new File(directoryName, filenames[i]);

            if (subfile.isDirectory())
            {
                try
                {
                    if (!subdeleteDirectory(directoryName + filenames[i]))
                    {
                        success = false;
                    }
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                    success = false;
                }
            }
            else
            {
                try
                {
                    if (!deleteFile(directoryName + filenames[i]))
                    {
                        success = false;
                    }
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                    success = false;
                }
            }
        }

        if (success)
        {
            file.delete();
        }

        return success;
    }


    /**
     * 测试
     */
    public static void test()
    {
        System.out.println(System.getProperty("user.dir"));

        File directory = new File("");
        try
        {
            System.out.println(directory.getCanonicalPath());
            System.out.println(directory.getAbsolutePath());
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 根据contextPath 拼凑完整的URL
     *
     * @param contextPath 项目contextPath
     * @param url         url实际请求路径
     * @return 完整的url地址
     */
    public static String getFullLinkUrl(String contextPath, String url)
    {
        if (url == null)
            return "";
        if (url.toLowerCase().indexOf("http") == 0 || url.toLowerCase().indexOf("ftp") == 0)
            return url;
        return contextPath + url;
    }

    /**
     * 复制属性,源对象中不为null的属性复制到目标对象中
     *
     * @param source 源对象
     * @param target 目标对象
     * @return 是否复制成功
     */
    public static boolean copyProperties(Object source, Object target)
    {
        try
        {
            Class clazz = source.getClass();

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields)
            {
                copyField(field, clazz, source, target);
            }
            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    public static void copyField(Field field, Class clazz, Object source, Object target)
    {
        try
        {
            // 属性名
            String fieldName = field.getName();
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getMethodName = "get" + firstLetter + fieldName.substring(1);
            String setMethodName = "set" + firstLetter + fieldName.substring(1);

            Method getMethod = clazz.getMethod(getMethodName);
            Method setMethod = clazz.getMethod(setMethodName, new Class[]{field.getType()});
            Object value = getMethod.invoke(source);
            if (value != null)
            {
                if (String.class.getName().equals(value.getClass().getName()))
                {
                        /* 注释掉判断空值, 以允许更新字段值为空 */
//                      if (StrUtil.isNotNull((String) value))
                    setMethod.invoke(target, new Object[]{value});
                }
                if (Long.class.getName().equals(value.getClass().getName()))
                {
                        /* 注释掉判断-1值, 以允许更新字段值为-1 */
//                    Long val = (Long) value;
//                    if (val != -1)
                    setMethod.invoke(target, new Object[]{value});
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }


    /**
     * 排序字符串
     *
     * @param orderBy 排序参数,如: createTime desc, photoCreateTime asc
     * @return order by sql string
     */
    public static String generateOrderByString(String orderBy)
    {
        if (StrUtil.isNotNull(orderBy))
        {
            if (orderBy.indexOf("_") > 0)
            {
                int idx = orderBy.lastIndexOf("_");
                int desc = StrUtil.getNotNullIntValue(orderBy.substring(idx + 1), 0);
                if (desc == 1)
                    return " order by " + orderBy.substring(0, idx) + " desc";
                else
                    return " order by " + orderBy.substring(0, idx);
            }
            return " order by " + orderBy;
        }
        return null;
    }

    public static String urlEncode(String value) throws UnsupportedEncodingException
    {
        if (StrUtil.isNotNull(value))
            return URLEncoder.encode(value, Global.default_encoding);
        return "";
    }

    /**
     * 获取 Main 方法的参数值
     *
     * @param args    参数
     * @param argName 参数名称
     * @return 如果有返回参数值, 否则返回Null
     */
    public static String getMainArg(String[] args, String argName)
    {
        if (args != null && StrUtil.isNotNull(argName))
            for (int i = 0; i < args.length; i++)
                if (argName.equals(args[i]) && args.length > (i + 1))
                    return args[i + 1];

        return null;
    }

    /**
     * 获取 N 位随机数字符串
     *
     * @param length 参数
     * @return 如果有返回参数值, 否则返回Null
     */
    public static String getRandomNumber(int length)
    {
        if (length < 1)
            return "";

        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(random.nextInt(10));

        return sb.toString();
    }

    /**
     * 获取 N 位随机数字符串
     *
     * @param length 参数
     * @return 如果有返回参数值, 否则返回Null
     */
    public static String getRandomCharAndNumber(int length)
    {
        if (length < 1)
            return "";

        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(chars[random.nextInt(chars.length-1)]);

        return sb.toString();
    }


    /**
     * 测试主方法
     *
     * @param args 参数
     * @throws Exception 异常
     */
    public static void main(String args[]) throws Exception
    {
        System.out.println(cn.careerforce.util.http.HttpRequest.getContentByUrl("https://api.weibo.com/2/users/show.json?access_token=2.00dlvCvBlEopSE22f57c9254m9bdUC&uid=1759082701", cn.careerforce.config.Global.default_encoding, null, null, null));
    }
}
