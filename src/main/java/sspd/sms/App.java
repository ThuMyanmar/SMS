package sspd.sms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sspd.sms.config.AppConfig;
import sspd.sms.courseoptions.db.Courseimpl;
import sspd.sms.teacheroptions.db.Timpls;
import sspd.sms.teacheroptions.db.Tsubimpls;
import sspd.sms.teacheroptions.model.TeacherDTO;

public class App {





    public static void main(String[] args) {




       ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Tsubimpls tsubimpls = context.getBean(Tsubimpls.class);

       // Courseimpl cdb = (Courseimpl) context.getBean(Repository.class);


      //  TeacherDTO dt = TeacherDTO.getInstance();





        System.out.println(tsubimpls.getAllTask().size());



    }
}
