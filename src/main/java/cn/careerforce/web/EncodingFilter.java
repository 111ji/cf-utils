/**
 *
 */
package cn.careerforce.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * <b style="color:#e94d08;">web 编码过滤器 默认编码为 UTF-8</b>
 *
 * @author yangdh
 *
 */
public class EncodingFilter implements Filter
{
	FilterConfig config = null;
	// default to UTF-8
	private String targetEncoding = "UTF-8";

	@Override
	public void init(FilterConfig config) throws ServletException
	{
		this.config = config;
		this.targetEncoding = config.getInitParameter("encoding");
	}

	@Override
	public void destroy()
	{
		config = null;
		targetEncoding = null;
	}

	@Override
	public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) srequest;
		request.setCharacterEncoding(targetEncoding);
		chain.doFilter(srequest, sresponse);
	}
}
