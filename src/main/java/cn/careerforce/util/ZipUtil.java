package cn.careerforce.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 字符串压缩解压缩工具类
 * <p>
 * Created by yangdh on 16/9/2.
 */
public class ZipUtil
{
    /**
     * 使用gzip进行压缩
     *
     * @param primStr 原字符串
     * @return 压缩后的字符串
     */
    public static String gzip(String primStr)
    {
        if (primStr == null || primStr.length() == 0)
        {
            return primStr;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        GZIPOutputStream gzip = null;
        try
        {
            gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (gzip != null)
            {
                try
                {
                    gzip.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return new sun.misc.BASE64Encoder().encode(out.toByteArray());
    }

    /**
     * <p>Description:使用gzip进行解压缩</p>
     *
     * @param compressedStr 压缩的字符串
     * @return 原字符串
     */
    public static String gunzip(String compressedStr)
    {
        if (compressedStr == null)
        {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        byte[] compressed = null;
        String decompressed = null;
        try
        {
            compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
            in = new ByteArrayInputStream(compressed);
            ginzip = new GZIPInputStream(in);

            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1)
            {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        }
        catch (Exception e)
        {
            decompressed = compressedStr;
        }
        finally
        {
            if (ginzip != null)
            {
                try
                {
                    ginzip.close();
                }
                catch (IOException ignored)
                {
                }
            }
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException ignored)
                {
                }
            }
            try
            {
                out.close();
            }
            catch (IOException ignored)
            {
            }
        }

        return decompressed;
    }


    public static void main(String args[]) throws Exception
    {
        String jsonString = "";
        System.out.println(jsonString.length());

        String gunziped = ZipUtil.gunzip(jsonString);
        System.out.println(gunziped);
        System.out.println(gunziped.length());

        String base64String = Base64.encode(jsonString.getBytes("UTF-8"));
        System.out.println(base64String);
        System.out.println(base64String.length());

        String unBase64String = new String(Base64.decode(base64String), "UTF-8");
        System.out.println(unBase64String);
        System.out.println(unBase64String.length());

        String gzipedString = ZipUtil.gzip(jsonString);
        System.out.println(gzipedString);
        System.out.println(gzipedString.length());

         gunziped = ZipUtil.gunzip(gzipedString);
        System.out.println(gunziped);
        System.out.println(gunziped.length());
    }

}
