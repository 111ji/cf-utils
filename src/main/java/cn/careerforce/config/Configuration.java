package cn.careerforce.config;

import cn.careerforce.util.StrUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * <b style="color:#e94d08;">配置文件 管理类； 可以通过该类读取 classpath 下的 config.properties
 * 文件</b>
 */
public class Configuration
{
    private static Logger logger = Logger.getLogger(Configuration.class);

    private static final String CONFIG_FILE = "/config.properties";
    private static final String BUNDLE_FILE = "config";
    private static Properties properties = null;

    private static boolean loaded = false;

    public static synchronized void load(String external_config_file)
    {
        if (loaded)
            return;

        loaded = true;
        properties = new Properties();

        InputStreamReader inputStreamReader = null;
        logger.info("========================");
        logger.info("start loading config properties...");

        if (StrUtil.isNotNull(external_config_file))
        {
            try
            {
                FileInputStream fis = new FileInputStream(new File(external_config_file));
                inputStreamReader = new InputStreamReader(fis);
                // 加载配置文件;
                properties.load(inputStreamReader);

                logger.info("config properties loaded from external file: ");
                logger.info(properties.toString());
            }
            catch (Exception ee)
            {
                System.out.println(ee.getMessage());
            }
        }

        if (properties.isEmpty())
        {

            try
            {
                InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE);
                inputStreamReader = new InputStreamReader(in);
                // 加载配置文件;
                properties.load(inputStreamReader);

                logger.info("config properties loaded from external resource: ");
                logger.info(properties.toString());
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

        if (properties.isEmpty())
        {
            try
            {
                InputStream in = Configuration.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
                inputStreamReader = new InputStreamReader(in);
                // 加载配置文件;
                properties.load(inputStreamReader);

                logger.info("config properties loaded from internal resource: ");
                logger.info(properties.toString());
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }

        if (properties.isEmpty())
        {
            try
            {
                ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_FILE);
                Enumeration<String> en = rb.getKeys();
                while (en.hasMoreElements())
                {
                    String name = en.nextElement();
                    properties.put(name, rb.getObject(name));
                }
                System.out.println("loaded from internal bundle: ");
                System.out.println(properties.toString());
            }
            catch (Exception ex)
            {
                // ex.printStackTrace();
            }
        }

        logger.info("finish loading config properties.");
        logger.info("========================");
    }


    public static String getValue(String key)
    {
        return getValue(key, null);
    }

    public static String getValue(String key, String defaultValue)
    {
        if (!loaded)
            load(null);

        if (properties == null || properties.isEmpty())
            return defaultValue;

        try
        {
            return properties.getProperty(key, defaultValue);
        }
        catch (Exception ex)
        {
            return defaultValue;
        }
    }

    public static int getValue(String key, int defaultValue)
    {
        String val = getValue(key);
        int setting = 0;
        try
        {
            setting = Integer.parseInt(val);
        }
        catch (NumberFormatException e)
        {
            setting = defaultValue;
        }
        return setting;
    }

    /**
     * 开发测试调试模式参数名称
     */
    public static final String DEVELOPER_DEBUG = "developer.debug";

}
