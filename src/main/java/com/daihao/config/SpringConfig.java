package com.daihao.config;

import javax.servlet.Filter;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.daihao.interceptor.SecurityInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan({"com.daihao", "com.daihao.DTO"})
public class SpringConfig extends AbstractAnnotationConfigDispatcherServletInitializer implements WebMvcConfigurer
{

    @Bean
    public SecurityInterceptor getSecurityInterceptor()
    {
        return new SecurityInterceptor();
    }

    @Bean(name = "localeResolver")
    public LocaleResolver getLocalResolver()
    {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setCookieName("lang");
        localeResolver.setCookieMaxAge(60 * 60 * 24);
        return localeResolver;
    }

    @Bean(name = "messageSource")
    public MessageSource getMessageSource()
    {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("/resources/lang/message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        registry.addInterceptor(interceptor);

        registry.addInterceptor(getSecurityInterceptor()).excludePathPatterns("/login", "/logout", "/api/user/login", "/resources/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
//        registry.addResourceHandler("/resources/**").addResourceLocations("D:/J5_test/").setCachePeriod(360000);
    }

    @Override
    protected Filter[] getServletFilters()
    {
        return new Filter[] {new CharacterEncodingFilter("UTF-8")};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {getClass()};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

}
