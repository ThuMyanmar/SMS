package sspd.sms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import sspd.sms.teacheroptions.db.Timpls;

public class App {





    public static void main(String[] args) {


        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        Timpls tdb = context.getBean(Timpls.class    );

        System.out.println(tdb.getAllTask().size());



    }
}
