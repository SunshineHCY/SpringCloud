package com.example.demo.fileter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class IPFilter extends ZuulFilter {

	private Logger logger = LoggerFactory.getLogger(IPFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		/*RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		logger.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
		Object accessToken = request.getParameter("token");
		if(accessToken == null) {
			logger.warn("token is empty");
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			try {
				ctx.getResponse().getWriter().write("token is empty");
			}catch (Exception e){
				return  e;
			}

			return null;
		}
		logger.info("ok");
		return null;
	}*/


	RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest req = ctx.getRequest();
		String ipAddr = this.getIpAddr(req);
		logger.info("请求IP地址为：[{}]", ipAddr);
		// 配置本地IP白名单，生产环境可放入数据库或者redis中
		List<String> ips = new ArrayList<String>();
		ips.add("172.0.0.1");
		ips.add("0:0:0:0:0:0:0:1");

		if (!ips.contains(ipAddr)) {
			logger.info("IP地址校验不通过！！！");
			ctx.setResponseStatusCode(401);
			ctx.setSendZuulResponse(false);
			ctx.setResponseBody("IpAddr is forbidden!");
		}else{
			ctx.setResponseStatusCode(401);
			ctx.setSendZuulResponse(false);
			ctx.setResponseBody("IpAddr is pass!");
		}
		logger.info("IP校验结束。");
		return null;
	}

	/**
	 * 获取Ip地址
	 *
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {

		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}