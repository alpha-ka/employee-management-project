package com.alpha.employeelogin.templateConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class TemplateConfiguration {

	
	@Bean
	public ITemplateResolver thymeleafClassLoaderTemplateResolver()
	{
		ClassLoaderTemplateResolver templateResolver=new ClassLoaderTemplateResolver();
		
		templateResolver.setPrefix("/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML");
		templateResolver.setCharacterEncoding("UTF-8");
		
		return templateResolver;
		
		
	}
	
	
	
	@Bean
	public SpringTemplateEngine thymeleafSpringTemplateEngine()
	{
		SpringTemplateEngine templateEngine= new SpringTemplateEngine();
		templateEngine.addDialect(new Java8TimeDialect());
		templateEngine.setTemplateResolver(thymeleafClassLoaderTemplateResolver());
		return templateEngine;
	}
	
	 
	
}
