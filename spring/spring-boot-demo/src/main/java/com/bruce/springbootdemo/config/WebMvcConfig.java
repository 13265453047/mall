package com.bruce.springbootdemo.config;

import com.bruce.springbootdemo.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;


/**
 * spring mvc 配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // @Value("${swagger.doc.enable}")
    private Boolean enable = Boolean.FALSE;

    /**
     * 跨域
     *
     * @return
     */
    @SuppressWarnings("all")
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Collections.singletonList(CorsConfiguration.ALL));
        corsConfiguration.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
        corsConfiguration.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        corsConfiguration.addExposedHeader(HttpHeaders.AUTHORIZATION);
        source.registerCorsConfiguration("/**", corsConfiguration);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (enable) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/META-INF/resources/");
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/META-INF/resources/webjars/");
            registry.addResourceHandler("/static/**")
                    .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
            registry.addResourceHandler("doc.html").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "META-INF/resources/webjars/");
        }
    }

    /**
     * http请求(远程restful风格)
     *
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/path/**")
                .addPathPatterns("/orders/pay")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }

}
