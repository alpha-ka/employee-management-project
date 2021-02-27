package com.alpha.employeelogin.templateConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class TemplateConfiguration {

	
	@Bean
	public ITemplateResolver thymeleafClassLoaderTemplateResolver()
	{
		ClassLoaderTemplateResolver templateresolver=new ClassLoaderTemplateResolver();
		
		templateresolver.setPrefix("/templates/");
		templateresolver.setSuffix(".html");
		templateresolver.setTemplateMode("HTML");
		templateresolver.setCharacterEncoding("UTF-8");
		
		return templateresolver;
		
		
	}
	
	
	
	@Bean
	public SpringTemplateEngine thymeleafSpringTemplateEngine()
	{
		SpringTemplateEngine templateEngine= new SpringTemplateEngine();
		templateEngine.setTemplateResolver(thymeleafClassLoaderTemplateResolver());
		return templateEngine;
	}
}
