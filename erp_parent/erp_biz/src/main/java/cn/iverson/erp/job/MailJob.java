package cn.iverson.erp.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;
import cn.iverson.erp.biz.IStoredetailBiz;
import cn.iverson.erp.entity.Storealert;
import cn.iverson.erp.utils.MailUtil;

public class MailJob {
	
	private IStoredetailBiz storedetailBiz;
	private MailUtil mailUtil;
	private String to;
	private String subject;
	private String text;
	
	public void doJob(){
		List<Storealert> storealertList = storedetailBiz.getStorealertList();
		if(null != storealertList && storealertList.size()>0){
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				mailUtil.sendMail(to, 
						  subject.replace("[time]",simpleDateFormat.format(new Date())),
						  text.replace("[count]",storealertList.size()+""));
				System.out.println("邮件发送成功");
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setStoredetailBiz(IStoredetailBiz storedetailBiz) {
		this.storedetailBiz = storedetailBiz;
	}
	public void setMailUtil(MailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setText(String text) {
		this.text = text;
	}
}
