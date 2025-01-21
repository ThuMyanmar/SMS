package sspd.sms.Test;




import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sspd.sms.databases.*;
import sspd.sms.teacheroptions.db.Timpls;
import sspd.sms.teacheroptions.model.Teacher;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class TestClass {


    Studentdb studb = new Studentdb();
    Teacherdb tdb = new Teacherdb();
    TeacherSubjectdb tstb = new TeacherSubjectdb();
    Coursedb cdb = new Coursedb();
    Classesdb cldb = new Classesdb();
    Certifiactetemplatesdb cte = new Certifiactetemplatesdb();
    Certificatedb cetb = new Certificatedb();




        @Test
    public void getCreate(){

           LocalDate datee = LocalDate.of(2005, 2, 13);
            Date date =  Date.valueOf(datee);

            //Student stu = new Student("stu001","thu thu",date,"Female","09263189909","moior495@gmail.com","south dagon","jreie");
           // int i = studb.create(stu);

          // Teacher teacher = new Teacher("Myo Myo","Basic to Advance","09263189909","ayye20@gmail.com");

           //Teacher tea = new Teacher(14,"Yu Yu","Basic to Advance","09263189909","ayye20@gmail.com");
          // int i = tdb.create(teacher);
          // int i = tdb.delete(tea);
          // int i = tdb.update(tea);
           //int i = studb.update(stu);
            //  int i =  studb.delete("stu001");
            //Student stu = new Student("stu005","Thu Thu Zin",date,"Male","09263189909","mariyanthuthu00@gmail.com","ttt","ttttt");
          //TeacherSubject tsb = new TeacherSubject(16,"EAD");

          //int i = tstb.update(3,"Excel");
          //int y = tstb.delete(tss);

          //Course c = new Course("PowerBI","gg",2.5,130000);
         // Course cr =new Course(4,"A+","dg",1.5,100000);
          //int i = cdb.create(c);
            //Classes cla = new Classes("Power BI",12,16,"iowow",8);
           // Classes cas = new Classes(2,"Power BI",12,16,"iowow",8);

           // Certificatetemplates cft = new Certificatetemplates(10,"Con");
           // Certificatetemplates cfts = new Certificatetemplates(5,12,"grud6r");

           // Certificate ct = new Certificate("cer1001","stu001",date,5);
          //  Certificate certs = new Certificate("cer1002","stu002",date,5);


//            int i =cetb.delete(ct);
//        assertEquals(1,i);



    }


    @Test
    public void getList() {

//        List<Certificate> list = cetb.getDataList();
//
//
//        assertEquals("List size should be 0", 0, list.size());








    }




}
