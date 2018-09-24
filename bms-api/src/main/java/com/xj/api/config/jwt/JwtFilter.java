package com.xj.api.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.xj.common.base.common.bean.AbstractBean;
import com.xj.common.base.common.exception.EnumSvrResult;
import com.xj.common.base.common.util.JsonUtil;

public class JwtFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if("OPTIONS".equals(request.getMethod())){
			chain.doFilter(request, response);
			return;
		}
		String auth = request.getHeader("token");
		if ((auth != null) && (auth.length() > 7)) {
			String HeadStr = auth.substring(0, 6).toLowerCase();
			if (HeadStr.compareTo("bearer") == 0) {
				auth = auth.substring(7, auth.length());
				System.out.println(auth);
				if (JwtHelper.parseJWT(auth) != null) {
					chain.doFilter(request, response);
					return;
				}
			}
		}
		response.setCharacterEncoding("UTF-8");    
		response.setContentType("application/json; charset=utf-8");   
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  
		response.getWriter().write(JsonUtil.getJsonFromObject(new AbstractBean(EnumSvrResult.ERROR_TOKEN)));  
        return;  
	}

}
