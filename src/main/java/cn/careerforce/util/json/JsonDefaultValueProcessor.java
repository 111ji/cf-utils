/**
 *
 */
package cn.careerforce.util.json;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.util.JSONUtils;

/**
 *
 * <b style="color:#e94d08;">json 默认值转换器</b>
 *
 * @author yangdh
 *
 */
public class JsonDefaultValueProcessor implements DefaultValueProcessor
{
	@SuppressWarnings("rawtypes")
	@Override
	public Object getDefaultValue(Class type)
	{
		if (JSONUtils.isArray(type))
			return new JSONArray();
		if (JSONUtils.isNumber(type))
		{
			if (JSONUtils.isDouble(type))
			{
				return null;
			}
			return null;
		}
		if (JSONUtils.isBoolean(type))
			return null;
		if (JSONUtils.isString(type))
		{
			return "";
		}
		return JSONNull.getInstance();
	}
}