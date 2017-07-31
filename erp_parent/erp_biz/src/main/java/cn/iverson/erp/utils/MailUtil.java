package cn.iverson.erp.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtil {
	
	private JavaMailSender javaMailSender;
	private String from;
	
	public void sendMail(String to,String subject,String text) throws MessagingException{
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		mimeMessageHelper.setFrom(from);
		mimeMessageHelper.setTo(to);
		mimeMessageHelper.setSubject(subject);
		mimeMessageHelper.setText(text);
		javaMailSender.send(mimeMessage);
	}
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
}