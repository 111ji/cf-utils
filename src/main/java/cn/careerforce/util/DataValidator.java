/**
 * 项目名称：zjob-service
 * 包名称：cn.careerforce.util
 * 文件名称：Validator.java
 * 创建者：yangdh
 * 创建时间：2015年6月11日 下午8:26:39
 * 版本：1.0
 */
package cn.careerforce.util;

/**
 * @author yangdh
 *
 */
public class DataValidator
{

	/** Utils Method start */

	/**
	 * 检查整型是否为空或 -1
	 *
	 * @param num number
	 * @return 是否为空或 -1
	 */
	public static boolean checkNumber(Long num)
	{
		return  checkNumber(num, true);
	}

	/**
	 * 检查整型是否有效: 不为空或 -1
	 *
	 * @param num       number
	 * @param allowZero 是否可以为 0
	 * @return 是否符合
	 */
	public static boolean checkNumber(Long num, boolean allowZero)
	{
		return num != null && num.intValue() != -1 && (allowZero || num.intValue() != 0);
	}

	/** Utils Method end */
}
