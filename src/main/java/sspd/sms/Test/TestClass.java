package sspd.sms.Test;




import org.junit.Test;
import sspd.sms.databases.Studentdb;
import sspd.sms.student.module.Student;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class TestClass {







    Studentdb studb = new Studentdb();






        @Test
    public void getCreate(){

        LocalDate datee = LocalDate.of(2005, 2, 13);

        Date date =  Date.valueOf(datee);

        Student stu = new Student("stu002","Thu Thu Zin",date,"Female","09263189909","mariyanthuthu19@gmail.com","ttt","ttttt");

        int i = studb.create(stu);

      // int i =  studb.delete(stu);

      //  int i =  studb.delete("stu001");


       assertEquals(1,i);

        //assertEquals(1,i);


    }






    @Test
    public void getList() {

        List<Student> list = studb.getDataList();


        assertEquals("List size should be 0", 2, list.size());


    }




}
