package com.youtube.jwt.config;

import com.youtube.jwt.util.MailUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setHost(MailUtils.HOST);
        mailSender.setPort(MailUtils.PORT);
        mailSender.setProtocol(MailUtils.PROTOCOL);
        mailSender.setUsername(MailUtils.USERNAME);
        mailSender.setPassword(MailUtils.PASSWORD);
        Properties mailProperties=new Properties();
        mailProperties.put("mail.smtp.auth",true);
        mailProperties.put("mail.smtp.starttls.enable",true);
        mailProperties.put("mail.debug",true);

        mailSender.setJavaMailProperties(mailProperties);
        return mailSender;
    }
}
