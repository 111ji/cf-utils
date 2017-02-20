package cn.careerforce.util.json;

import net.sf.ezmorph.Morpher;
import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.bean.BeanMorpher;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * json 转换工具方法
 * <p>
 * Created by yangdh on 2016/10/17.
 */
public class JSONUtils
{

    /**
     * parse json object from string
     *
     * @param jsonStr json 字符串
     * @return json 对象
     */
    public static JSONObject getJSONObject(String jsonStr)
    {
        try
        {
            JsonConfig jsonConfig = new JsonConfig();
            DefaultValueProcessor myDefaultValueProcessor = new JsonDefaultValueProcessor()
            {
                @Override
                public Object getDefaultValue(Class type)
                {
                    return null;
                }
            };
            jsonConfig.registerDefaultValueProcessor(Integer.class, myDefaultValueProcessor);
            jsonConfig.registerDefaultValueProcessor(Long.class, myDefaultValueProcessor);
            jsonConfig.registerDefaultValueProcessor(Double.class, myDefaultValueProcessor);
            jsonConfig.registerDefaultValueProcessor(Boolean.class, myDefaultValueProcessor);
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());

            return JSONObject.fromObject(jsonStr, jsonConfig);
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    /**
     * parse json array from string
     *
     * @param jsonStr json 字符串
     * @return json 数组对象
     */
    public static JSONArray getJSONArray(String jsonStr)
    {
        try
        {
            return JSONArray.fromObject(jsonStr);
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static Object getObjectInJSONArray(Class cls, Object obj)
    {
        try
        {
            MorpherRegistry morpherRegistry = net.sf.json.util.JSONUtils.getMorpherRegistry();
            Morpher dynaMorpher = new BeanMorpher(cls, morpherRegistry);
            morpherRegistry.registerMorpher(dynaMorpher);
            return morpherRegistry.morph(cls, obj);
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    /**
     * 根据json对象，获取bean
     *
     * @param cls bean class类型
     * @param obj json对象
     * @return bean
     */
    public static Object getJSONBean(Class cls, Object obj)
    {
        try
        {
            String[] dateFormats = new String[]{"", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"};
            net.sf.json.util.JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
            return JSONObject.toBean((JSONObject) obj, cls);
        }
        catch (Exception ex)
        {
            // ex.printStackTrace();
            return null;
        }
    }

    /**
     * format json
     *
     * @param message
     *            JSON字符串
     * @param objclass
     *            要得到的对象Class
     * @return 对象
     */
    public static Object formatJsonObject(String message, Class objclass)
    {
        try
        {
            String[] dateFormats = new String[] { "", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" };
            net.sf.json.util.JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));

            JsonConfig jsonconfig = new JsonConfig();
            jsonconfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor("yyyy-MM-dd"));

            JSONObject obj = JSONObject.fromObject(message, jsonconfig);

            return JSONObject.toBean(obj, objclass);
        }
        catch (Exception e)
        {
            return null;
        }

    }

    public static String getJSONString(Object obj)
    {
        String res = "";

        JsonConfig jsonConfig = new JsonConfig();
        DefaultValueProcessor myDefaultValueProcessor = new JsonDefaultValueProcessor()
        {
            @Override
            public Object getDefaultValue(Class type)
            {
                return null;
            }
        };
        jsonConfig.registerDefaultValueProcessor(Integer.class, myDefaultValueProcessor);
        jsonConfig.registerDefaultValueProcessor(Long.class, myDefaultValueProcessor);
        jsonConfig.registerDefaultValueProcessor(Double.class, myDefaultValueProcessor);
        jsonConfig.registerDefaultValueProcessor(Boolean.class, myDefaultValueProcessor);
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        jsonConfig.registerDefaultValueProcessor(String.class, myDefaultValueProcessor);
        jsonConfig.registerDefaultValueProcessor(BigDecimal.class, myDefaultValueProcessor);
        try
        {
            res = JSONObject.fromObject(obj, jsonConfig).toString();
        }
        catch (Exception ex)
        {
            // ex.printStackTrace();
            try
            {
                res = JSONArray.fromObject(obj, jsonConfig).toString();
            }
            catch (Exception ex1)
            {
                // ex1.printStackTrace();
            }
        }
        // System.out.print(res);
        return res;
    }
}
