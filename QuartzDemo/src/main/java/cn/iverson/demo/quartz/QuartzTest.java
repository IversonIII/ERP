package cn.iverson.demo.quartz;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QuartzTest {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext_job.xml");
	}

}
