package com.xj.api.config.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  
public class JwtConfig {
	
 	@Bean  
    public FilterRegistrationBean basicFilterRegistrationBean(){  
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();  
        registrationBean.setFilter(new JwtFilter());  
          
        List<String> urlPatterns = new ArrayList<>();  
        urlPatterns.add("*.auth");  
        registrationBean.setUrlPatterns(urlPatterns);  
        return registrationBean;  
    }  
}
