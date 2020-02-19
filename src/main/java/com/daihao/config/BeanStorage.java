package com.daihao.config;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.daihao.util.HibernateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BeanStorage
{
    @Bean("mailSender")
    public JavaMailSender getJavaMailSender()
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setPort(587);
        mailSender.setUsername("razzermkd@gmail.com");
        mailSender.setPassword("DaiHao@1998");

        Properties pros = mailSender.getJavaMailProperties();
        pros.put("mail.transport.protocol", "smtp");
        pros.put("mail.smtp.socketFactory.port", 587);
        pros.put("mail.smtp.auth", "true");
        pros.put("mail.smtp.starttls.enable", "true");
        pros.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public static InternalResourceViewResolver internalResourceViewResolver()
    {
        System.out.println("Vào bean InternalResourceViewResolver");
        InternalResourceViewResolver vr = new InternalResourceViewResolver();
        vr.setPrefix("/WEB-INF/views/");
        vr.setSuffix(".jsp");
        return vr;
    }

    @Bean
    public static Session getSession()
    {
        System.out.println("Vào bean getSession()");
        return HibernateUtil.getSessionFactory().openSession();
    }


    @Bean
    public static SessionFactory sessionFactory()
    {
        return HibernateUtil.getSessionFactory();
    }

    @Bean
    public static ObjectMapper getMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        System.out.println("Vao multipartResolver");
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(9437184);
        return multipartResolver;
    }
}
