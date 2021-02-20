package com.zhys.common;

import com.invoice.po.InvoiceHead;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.math.BigDecimal;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
  @Autowired
  private ResponseResultInterceptor responseResultInterceptor;
  
  @Autowired
  private AllowCrossDomainInterceptor allowCrossDomainInterceptor;
  
  public void addInterceptors(InterceptorRegistry registry) {
    String apiUri = "/**";
    registry.addInterceptor(this.responseResultInterceptor).addPathPatterns(new String[] { apiUri });
    registry.addInterceptor(this.allowCrossDomainInterceptor).addPathPatterns(new String[] { apiUri });
  }
  
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins(new String[] { "*" }).allowedMethods(new String[] { "POST", "GET", "PUT", "OPTIONS", "DELETE" }).maxAge(3600L)
      .allowCredentials(true);
  }
}
