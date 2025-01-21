package sspd.sms.teacheroptions.services;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sspd.sms.teacheroptions.db.Timpls;

public class TeacherServices {

    private ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

    private Timpls tdb = context.getBean(Timpls.class );


}
