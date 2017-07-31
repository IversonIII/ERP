package cn.iverson.demo.mail;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class TestSend {
	
	private JavaMailSender javaMailSender;
	private String from = "curry30@zoho.com.cn";//发件人邮件地址
	private String to = "13822528774@163.com";//收件人邮箱地址
	private String subject = "Mail Test";//邮件标题
	private String text = "如果你看到我,就证明你成功了,我是你爹";//邮件内容
	
	public void sendMail() throws Exception{
		//创建一份邮件
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"utf-8");
		mimeMessageHelper.setFrom(from);
		mimeMessageHelper.setTo(to);
		mimeMessageHelper.setSubject(subject);
		mimeMessageHelper.setText(text);
		javaMailSender.send(mimeMessage);
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

}