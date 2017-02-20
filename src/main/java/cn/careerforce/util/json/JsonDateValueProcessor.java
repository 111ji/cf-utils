/**
 *
 */
package cn.careerforce.util.json;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 *
 * <b style="color:#e94d08;">json 日期转换器 字符串转日期 默认格式：yyyy-MM-dd</b>
 *
 * @author yangdh
 *
 */
public class JsonDateValueProcessor implements JsonValueProcessor
{
	private String datePattern = "yyyy-MM-dd";

	public JsonDateValueProcessor()
	{
	}

	public JsonDateValueProcessor(String format)
	{
		this.datePattern = format;
	}

	@Override
	public Object processArrayValue(Object value, JsonConfig jsonConfig)
	{
		return process(value);
	}

	@Override
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig)
	{
		return process(value);
	}

	private Object process(Object value)
	{
		try
		{
			if ((value instanceof Date))
			{
				SimpleDateFormat sdf = new SimpleDateFormat(this.datePattern, Locale.US);
				return sdf.format((Date) value);
			}
			return value == null ? "" : value.toString();
		}
		catch (Exception e)
		{
		}
		return "";
	}

	public String getDatePattern()
	{
		return this.datePattern;
	}

	public void setDatePattern(String pDatePattern)
	{
		this.datePattern = pDatePattern;
	}
}