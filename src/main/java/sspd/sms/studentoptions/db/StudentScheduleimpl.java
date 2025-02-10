package sspd.sms.studentoptions.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import sspd.sms.DAO.Taskdao;
import sspd.sms.studentoptions.model.StudentSchedule;

import java.util.List;

@Repository
public class StudentScheduleimpl implements Taskdao<StudentSchedule> {


    private final SessionFactory sessionFactory;


    public StudentScheduleimpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<StudentSchedule> getAllTask() {
        return List.of();
    }

    @Override
    public void insertTask(StudentSchedule task) {

    }

    public void insertTask(StudentSchedule task, Session session) {


        if (session == null) {

            try (Session newSession = sessionFactory.openSession()) {
                Transaction tx = newSession.beginTransaction();
                newSession.persist(task);
                tx.commit();
            }
        } else {

            session.persist(task);
        }

    }

    @Override
    public void updateTask(StudentSchedule task) {

    }

    @Override
    public void deleteTask(StudentSchedule task) {

    }

    @Override
    public void bactchTask(List<StudentSchedule> list) {

    }
}
