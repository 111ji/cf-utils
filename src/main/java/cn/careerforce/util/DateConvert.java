/**
 *
 */
package cn.careerforce.util;

import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

/**
 *
 * <b style="color:#e94d08;">beanutils 的日期转换器 字符串转日期 日期格式：yyyy-MM-dd</b>
 *
 * @author yangdh
 *
 */
@SuppressWarnings("rawtypes")
public class DateConvert implements Converter
{

	@SuppressWarnings("unchecked")
	@Override
	public Object convert(Class arg0, Object arg1)
	{
		String p = (String) arg1;
		if (p == null || p.trim().length() == 0)
		{
			return null;
		}
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.parse(p.trim());
		}
		catch (Exception e)
		{
			return null;
		}
	}

}
