/**
 *
 */
package cn.careerforce.util;

import org.apache.log4j.Level;

/**
 *
 * <b style="color:#e94d08;">自定义日志级别 级别为 6000 较高级别</b>
 *
 * @author yangdh
 *
 */
public class Log4jLevelUser extends Level
{
	private static final long serialVersionUID = -1105147098113819109L;

	public Log4jLevelUser(int level, String levelStr, int syslogEquivalent)
	{
		super(level, levelStr, syslogEquivalent);
	}

	public static Log4jLevelUser toLevel(int val, Level defaultLevel)
	{
		return DISASTER;
	}

	public static Log4jLevelUser toLevel(String sArg, Level defaultLevel)
	{

		return DISASTER;

	}

	public static final Log4jLevelUser DISASTER = new Log4jLevelUser(60000, "DISASTER", 0);

}
