package com.sbs.internetbanking.springconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.sbs.internetbanking.service.EmailService;
import com.sbs.internetbanking.service.OTPService;
import com.sbs.internetbanking.service.SBSUserDetailsService;
import com.sbs.internetbanking.utilities.IdentifierGenerator;

@Configuration
public class EnvironmentConfig {

	@Bean
	public static PropertyPlaceholderConfigurer properties() {
		PropertyPlaceholderConfigurer propertiesPlaceHolderConfig = new PropertyPlaceholderConfigurer();
		Resource[] resources = new ClassPathResource[] { new ClassPathResource("/config/spring.properties") };
		propertiesPlaceHolderConfig.setLocations(resources);
		propertiesPlaceHolderConfig.setIgnoreUnresolvablePlaceholders(true);
		return propertiesPlaceHolderConfig;
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("/i18n/messages");
		messageSource.setFallbackToSystemLocale(true);
		return messageSource;
	}

	@Bean
	public IdentifierGenerator identifierGenerator() {
		return new IdentifierGenerator();
	}
}