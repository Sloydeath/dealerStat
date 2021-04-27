package com.leverx.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.mail.MessagingException;
import javax.mail.Session;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Session mailSession() throws IOException {
        return Session.getDefaultInstance(mailProperties());
    }

    private Properties mailProperties() throws IOException {
        Properties mailProperties = new Properties();
        mailProperties.load(MailConfig.class.getClassLoader().getResourceAsStream("mail.properties"));

        return mailProperties;
    }
}
