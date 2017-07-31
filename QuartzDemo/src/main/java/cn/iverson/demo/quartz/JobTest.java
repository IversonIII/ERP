package cn.iverson.demo.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JobTest {
	
	public void doJob(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("现在是北京时间:" + simpleDateFormat.format(new Date()));
	}
}
