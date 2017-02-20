package cn.careerforce.dao;

import cn.careerforce.util.Common;

import java.lang.reflect.Field;

/**
 * 实体工具类
 * <p>
 * Created by yangdh on 2016/10/20.
 */
public class ModelUtils
{
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
                if (field.getAnnotation(ColumnDynamic.class) != null)
                    Common.copyField(field, clazz, source, target);
            }
            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
}
