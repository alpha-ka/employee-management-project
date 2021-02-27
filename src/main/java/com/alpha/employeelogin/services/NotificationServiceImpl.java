package com.alpha.employeelogin.services;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.alpha.employeelogin.model.Employee;
import com.alpha.employeelogin.model.OutofOffice;
import com.sun.mail.handlers.multipart_mixed;

@Service
public class NotificationServiceImpl implements EmailService {

	private Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private JavaMailSender javamailsender;

	@Autowired
	private SpringTemplateEngine thymeleafSpringTemplateEngine;

	@Value("${spring.mail.username}")
	private String from;

	@Override
	public void Simpleemail(String to, String subject, String text) {
		// TODO Auto-generated method stub
		logger.info("Siimple mail initiated....");
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);

		javamailsender.send(message);
		logger.info("Simple mail sent successfully....");
	}

	@Override
	public void SendHTMLMail(String to, String subject, String htmlbody) throws MessagingException {
		// TODO Auto-generated method stub

		logger.info("HTML mail initiated....");
		MimeMessage message = javamailsender.createMimeMessage();

		MimeMessageHelper mailhelper = new MimeMessageHelper(message, true, "UTF-8");

		mailhelper.setFrom(from);
		mailhelper.setTo(to);
		mailhelper.setSubject(subject);
		mailhelper.setText(htmlbody, true);
		mailhelper.addAttachment("logo.png",new ClassPathResource("logo.png"));
		// mailhelper.addAttachment("alpha-logo.png",new
		// ClassPathResource("alpha-logo.png"));

		javamailsender.send(message);
		logger.info("Registration mail sent successfully....");

	}

	@Override
	public void SendMailUsingThymeleafTemplate(String to, String subject, Map<String, Object> templatemodel)
			throws MessagingException {
		// TODO Auto-generated method stub

		Context context = new Context();
		context.setVariables(templatemodel);
		String htmlbody = thymeleafSpringTemplateEngine.process("/emailtemplate/registration.html", context);
		SendHTMLMail(to, subject, htmlbody);
	}

	@Override
	public void sendRegistrationMail(String to, String subject, Map<String, Object> templatemodel)
			throws MessagingException {
		// TODO Auto-generated method stub
		
		Context context = new Context();
		context.setVariables(templatemodel);
		String htmlbody = thymeleafSpringTemplateEngine.process("/emailtemplate/registration.html", context);
		SendHTMLMail(to, subject, htmlbody);

	}
	
	@Override
	public void sendAdminAccessRequestMail(String to, String subject, Map<String, Object> templatemodel)
			throws MessagingException {
		// TODO Auto-generated method stub
		Context context = new Context();
		context.setVariables(templatemodel);
		String htmlbody = thymeleafSpringTemplateEngine.process("/emailtemplate/admin_access_request.html", context);
		SendHTMLMail(to, subject, htmlbody);
	}


	@Override
	public void sendEmployeeUpdateMail(String to, String subject, Map<String, Object> templatemodel)
			throws MessagingException {
		// TODO Auto-generated method stub
		
		Context context = new Context();
		context.setVariables(templatemodel);
		String htmlbody = thymeleafSpringTemplateEngine.process("/emailtemplate/profile_updated.html", context);
		SendHTMLMail(to, subject, htmlbody);

	}

	@Override
	public void sendOOFMail(String to, String subject, Map<String, Object> templatemodel)
			throws MessagingException {
		// TODO Auto-generated method stub
		
		Context context = new Context();
		context.setVariables(templatemodel);
		String htmlbody = thymeleafSpringTemplateEngine.process("/emailtemplate/out_of_office.html", context);
		SendHTMLMail(to, subject, htmlbody);

	}

	 


// 
//
//	public void sendRegMail(Employee employee) throws MessagingException
//	{
//		
//	
//		logger.info("Registration mail initiated....");
//		MimeMessage message=javamailsender.createMimeMessage();
//		
//		MimeMessageHelper mailhelper=new MimeMessageHelper(message);
//		
//		
//		String htmlbody="Dear "+employee.getFirstname()+" "+employee.getLastname()+", <br>"
//				+ "Registration success."	;
//		
//		mailhelper.setFrom(from);
//		mailhelper.setTo(employee.getEmail());
//		mailhelper.setSubject("Registration");
//		mailhelper.setText(htmlbody, true);
//	//	mailhelper.addAttachment("alpha-logo.png",new ClassPathResource("alpha-logo.png"));
//	
//		javamailsender.send(message);
//		logger.info("Registration mail sent successfully....");
//	}
//	
//	public void sendOOFMail(Employee employee,OutofOffice oof) throws MessagingException
//	{
//		
//	
//		logger.info("Out of Office mail initiated....");
//		MimeMessage message=javamailsender.createMimeMessage();
//		
//		MimeMessageHelper mailhelper=new MimeMessageHelper(message);
//		
//		
//		String htmlbody=employee.getFirstname()+" "+employee.getLastname()+" has applied for"+oof.getCategory() +".  Details are as below:"
//				+ "<table   style='font-size:10.0pt;font-family:\"Arial\",\"sans-serif\";color:#333333' ><tr><td>No.of Days</td><td>: "+oof.getDays()+"</td> </tr>"
//						+ "<tr><td>Leave Period </td><td>: "+oof.getStartdate()+" - "+oof.getEnddate()+"</td> </tr>"
//						+ "<tr><td>Reason </td><td>: "+oof.getReason()+"</td> </tr> </table>"				
//				;
//		mailhelper.setFrom(from);
//		mailhelper.setTo(employee.getEmail());
//		mailhelper.setSubject("Out of leave request - "+oof.getCategory());
//		mailhelper.setText(htmlbody, true);
//		//mailhelper.addAttachment("logo.png",new ClassPathResource("alpha-logo.png"));
//	
//		javamailsender.send(message);
//		
//		logger.info("Out of Office mail sent successfully....");
//		
//	}

}
