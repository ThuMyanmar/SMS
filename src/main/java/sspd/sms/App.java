package sspd.sms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sspd.sms.config.AppConfig;
import sspd.sms.courseoptions.db.Courseimpl;

public class App {





    public static void main(String[] args) {


       ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

       Courseimpl cdb =  context.getBean(Courseimpl.class);

       // Courseimpl cdb = (Courseimpl) context.getBean(Repository.class);



        System.out.println(cdb.getAllTask().size());



    }
}
