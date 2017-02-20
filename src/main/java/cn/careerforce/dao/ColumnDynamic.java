package cn.careerforce.dao;

import java.lang.annotation.*;

/**
 * 需要更新的字段标注
 *
 * Created by yangdh on 2016/10/20.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ColumnDynamic
{
}
