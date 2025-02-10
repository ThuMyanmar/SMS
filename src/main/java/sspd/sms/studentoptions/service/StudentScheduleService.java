package sspd.sms.studentoptions.service;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import sspd.sms.studentoptions.db.StudentScheduleimpl;
import sspd.sms.studentoptions.model.StudentSchedule;

@Service
public class StudentScheduleService  {

    private final  StudentScheduleimpl studentScheduleimpl;

    public StudentScheduleService(StudentScheduleimpl studentScheduleimpl) {
        this.studentScheduleimpl = studentScheduleimpl;
    }

    public void insertStudentSchedule(StudentSchedule studentSchedule, Session session) {


        studentScheduleimpl.insertTask(studentSchedule, session);


    }



}
